/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.SistemaConfiguracaoController;
import br.com.guardiao.controler.UsuarioController;
import br.com.guardiao.modelo.Permissao;
import br.com.guardiao.modelo.Usuario;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.util.MenssagemUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Bean gerenciado do Projeto SiafiWeb criado em 24/04/2013
 *
 * @author ari
 */
@Named
@Stateful
public class UsuarioMB implements Serializable {

    @EJB
    private UsuarioController usuarioControler;
    @EJB
    private SistemaConfiguracaoController sistemaConfiguracaoControler;
    private Usuario usuarioSelecionado;
    private boolean navegadorMobile;
    private final Map<String, Permissao> menu = new HashMap();
    private int tentativaLogin;
    private boolean fechadoParaSIM;

    /**
     *
     */
    public UsuarioMB() {
        usuarioSelecionado = new Usuario();
        navegadorMobile = false;
        tentativaLogin = 1;
    }

    /**
     *
     */
    @Inject
    public void usuarioLogado() {
        String documento = null;
        try {
            fechadoParaSIM = ((Boolean) sistemaConfiguracaoControler.pegarValorConfiguracaoDef(Boolean.FALSE, "ACESSO", "SAF"));
            if (usuarioSelecionado.getDocumento() == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext external = context.getExternalContext();
                documento = external.getRemoteUser();
                if (documento != null) {
                    usuarioSelecionado = usuarioControler.carregar(documento);
                    popularMenu();
                    verificaNavegador(context);
                }
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName() + " " + documento);
        }
    }

    private void verificaTentativaLogin() {
        if (tentativaLogin > 4) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/limit.xhtml");
            } catch (IOException ex) {
                MenssagemUtil.addMessageErro(ex, this.getClass().getName());
            }
        }
    }

    private void popularMenu() {
        for (Permissao p : usuarioSelecionado.getPermissoes()) {
            menu.put(p.getTarefa().getMnemonico(), p);
        }
    }

    /**
     *
     * @param tarNome
     * @return
     */
    public Permissao permissao(String tarNome) {
        return menu.get(tarNome);
    }

    /**
     *
     * @param tarNome
     * @return
     */
    public boolean permissaoIncluir(String tarNome) {
        if (menu.containsKey(tarNome)) {
            return menu.get(tarNome).isIncluir();
        } else {
            return false;
        }
    }

    /**
     *
     * @param tarNome
     * @return
     */
    public boolean permissaoExcluir(String tarNome) {
        if (menu.containsKey(tarNome)) {
            return menu.get(tarNome).isExcluir();
        } else {
            return false;
        }
    }

    /**
     *
     * @param tarNome
     * @return
     */
    public boolean permissaoConsultar(String tarNome) {
        if (menu.containsKey(tarNome)) {
            return menu.get(tarNome).isConsultar();
        } else {
            return false;
        }
    }

    /**
     *
     * @param tarNome
     * @return
     */
    public boolean permissaoEditar(String tarNome) {
        if (menu.containsKey(tarNome)) {
            return menu.get(tarNome).isEditar();
        } else {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    /**
     *
     * @param usuarioSelecionado
     */
    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

//    /**
//     *
//     * @return
//     */
//    public String getDocumento() {
//        return documento;
//    }
//
//    /**
//     *
//     * @param documento
//     */
//    public void setDocumento(String documento) {
//        this.documento = documento;
//    }
    public Map<String, Permissao> getMenu() {
        return menu;
    }

    private void verificaNavegador(FacesContext context) {
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        String cabecalho = req.getHeader("User-Agent");
        navegadorMobile = cabecalho.contains("Mobile");
    }

    public boolean isNavegadorMobile() {
        return navegadorMobile;
    }

    public void setNavegadorMobile(boolean navegadorMobile) {
        this.navegadorMobile = navegadorMobile;
    }

    public boolean permissaoAdm() {
        return usuarioSelecionado.getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Gestor);
    }

    public boolean isFechadoParaSIM() {
        return fechadoParaSIM;
    }

    public void setFechadoParaSIM(boolean fechadoParaSIM) {
        this.fechadoParaSIM = fechadoParaSIM;
    }

}
