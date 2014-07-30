/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util;

import br.com.guardiao.controler.TarefaController;
import br.com.guardiao.modelo.Tarefa;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gilmario
 */
public abstract class NavegacaoGenerico implements Serializable {

    @EJB
    private TarefaController tarefaControler;
    private final Map<String, Object> map;
    private String pagina;
    private String mnemonicoSistema;
    private String extensao;

    public NavegacaoGenerico() {
        pagina = "";
        map = new HashMap<>();

    }

    protected abstract void setarExtensao();

    public String getMnemonicoSistema() {
        return mnemonicoSistema;
    }

    public void setMnemonicoSistema(String mnemonicoSistema) {
        this.mnemonicoSistema = mnemonicoSistema;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    /**
     * Esse metodo redireciona e passa um parametro para o bean de destino no
     * caso um objeto de um determinado modelo a ser editado
     *
     * @param mnemonico - destino
     * @param key - chave de um valor a ser adicionado na sessão
     * @param valor - valor a ser adiocionado na sessão
     */
    public void redirecionar(String mnemonico, String key, Object valor) {
        try {
            map.put(key, valor);
        } catch (Exception e) {
            Logger.getLogger(NavegacaoGenerico.class.getName()).log(Level.SEVERE, null, e);
        }
        redirecionar(mnemonico);
    }

    /**
     * Esse método redireciona para uma página especifica
     *
     * @param mnemonico
     */
    public void redirecionar(String mnemonico) {
        try {
            String modulo = mnemonico.substring(3, mnemonico.length() - 4);
            String subModulo = "";
            if (modulo.length() > 2) {
                for (int i = 0; i < modulo.length(); i += 2) {
                    subModulo += "/" + modulo.substring(i, i + 2);
                }
                subModulo += "/";
            } else {
                subModulo = "/" + modulo + "/";
            }
            redirecionarPagina(getNomeSistema() + subModulo + mnemonico + "." + getExtensao());
        } catch (Exception e) {
            Logger.getLogger(NavegacaoGenerico.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void redirecionarPagina(String pag) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(pag);
        } catch (IOException e) {
            Logger.getLogger(NavegacaoGenerico.class.getName()).log(Level.SEVERE, null, e);
        }
//        return pag.concat("?faces-redirect=true");
    }

    /**
     * Esse é um metodo teste de redirecionamento rápido e ainda está em
     * implantação
     *
     */
    public void redirecionarRapido() {
        if (pagina != null) {
            if (pagina.length() >= 9) {
                redirecionar(pagina);
            }
            pagina = "";
        }
    }

    /**
     * Pagar o nome do sistema
     *
     * @return
     */
    public String getNomeSistema() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }

    /**
     * Geters e Setters das propriedades sem nenhuma alteração
     *
     * @param key
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

    /**
     * Geters e Setters das propriedades sem nenhuma alteração
     *
     * @return
     */
    public String getPagina() {
        return pagina;
    }

    /**
     * Geters e Setters das propriedades sem nenhuma alteração
     *
     * @param pagina
     */
    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    /**
     *
     */
    public void index() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(getNomeSistema());
        } catch (IOException ex) {
            Logger.getLogger(NavegacaoGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logout1() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(getNomeSistema() + "/j_spring_security_logout");

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            if (session != null) {
                session.invalidate();

            }

        } catch (IOException ex) {
            Logger.getLogger(NavegacaoGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void logout() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(getNomeSistema() + "/j_spring_security_logout");

        } catch (IOException ex) {
            Logger.getLogger(NavegacaoGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> completar(String digito) {
        List<String> lista = new ArrayList<>();
        try {
            List<Tarefa> tarefas = tarefaControler.listarPorSistema("mnemonico", digito.toUpperCase(), getMnemonicoSistema());
            for (Tarefa t : tarefas) {
                lista.add(t.getMnemonico());
            }
        } catch (Exception ex) {
            Logger.getLogger(NavegacaoGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}
