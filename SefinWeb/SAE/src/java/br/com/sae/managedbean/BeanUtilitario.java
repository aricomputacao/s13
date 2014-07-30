/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sae.managedbean;

import br.com.guardiao.modelo.CriptografiaSenha;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sae.controller.AcessoCredorController;
import br.com.sae.modelo.AcessoCredor;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.el.ELResolver;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

/**
 * Data de criação 10/10/2013
 *
 * @author Ari
 */
@Named
@SessionScoped
public class BeanUtilitario implements Serializable {

    @EJB
    private AcessoCredorController usuarioController;
    private AcessoCredor usuarioLogado;
    private Map<String, Object> map;
    private String pagina;
    private String senhaAtual;
    private String novaSenha;
    private String confirmeSenha;

    @PostConstruct
    private void iniciar() {

        map = new HashMap<>();
        usuarioLogado = new AcessoCredor();
        carregarUsuarioLogado();
    }

    public String getMsg(String messageId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String msg = "";
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("mensagens", locale);
        try {
            msg = bundle.getString(messageId);
        } catch (Exception e) {
        }
        return msg;
    }

    public void carregarUsuarioLogado() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext external = context.getExternalContext();

            usuarioLogado = (AcessoCredor) usuarioController.carregar(external.getRemoteUser());
        } catch (PersistenceException ex) {
            Logger.getLogger(BeanUtilitario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EJBException ex) {
            Logger.getLogger(BeanUtilitario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BeanUtilitario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String redirecionarPagina(String pag) {
        return pag.concat("?faces-redirect=true");
    }

    protected Object getRegistroDoMap(String chave, Object t) {
        FacesContext context = FacesContext.getCurrentInstance();
        ELResolver resolver = context.getApplication().getELResolver();
        BeanUtilitario nav = (BeanUtilitario) resolver.getValue(context.getELContext(), null, "beanUtilitario");
        if (nav != null) {
            if (nav.getMap().containsKey(chave)) {
                return nav.getMap().remove(chave);
            }

        }
        return t;
    }

    /**
     * Esse metodo redireciona e passa um parametro para o bean de destino no
     * caso um objeto de um determinado modelo a ser editado
     *
     * @param key - chave de um valor a ser adicionado na sessão
     * @param def
     * @return 
     */
    public Object getRegistroMapa(String key, Object def) {
        if (map.containsKey(key)) {
            return map.remove(key);
        } else {
            return def;
        }

    }

    public void logout() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/SAE/j_spring_security_logout");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if (session != null) {
                session.invalidate();

            }

        } catch (IOException ex) {
            Logger.getLogger(BeanUtilitario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String redirecionar(String pag, String key, Object valor) {
        try {
            map.put(key, valor);

        } catch (Exception e) {
            Logger.getLogger(BeanUtilitario.class.getName()).log(Level.SEVERE, null, e);
        }
        return redirecionarPagina(pag);
    }

    public void validateSenhaAtual(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        CriptografiaSenha cs = new CriptografiaSenha();
        senhaAtual = (String) value;
        if (!usuarioLogado.getSenha().equals(cs.criptografarSenha(senhaAtual))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha atual incorreta!", ""));
        }
    }

    public void alterarSenha() {
        try {
            alterarSenha(usuarioLogado, senhaAtual, novaSenha, confirmeSenha);
            MenssagemUtil.addMessageInfo("Senha alterada!");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao alterar senha", ex, this.getClass().getName());
        } finally {
            novaSenha = "";
            confirmeSenha = "";
            senhaAtual = "";

        }
    }

    protected void alterarSenha(AcessoCredor u, String senhaAtual, String novaSenha, String confirmeSenha) throws PersistenceException, EJBException, Exception {
        CriptografiaSenha cs = new CriptografiaSenha();
        if (u.getSenha().equals(cs.criptografarSenha(senhaAtual))) {
            if (novaSenha.equals(confirmeSenha)) {
                u.setSenha(cs.criptografarSenha(novaSenha));
                usuarioController.atualizar(u);
            }
        }

    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public AcessoCredor getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(AcessoCredor usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

}
