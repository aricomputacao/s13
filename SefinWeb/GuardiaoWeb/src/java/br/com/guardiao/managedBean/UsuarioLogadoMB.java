/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.UsuarioController;
import br.com.guardiao.modelo.Permissao;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Classe do Projeto Guardião - Criado em 14/05/2013 - Classe gerenciadora do
 * usuario logado no sistema
 *
 * @author Gilmário
 */
@Named
@SessionScoped
public class UsuarioLogadoMB implements Serializable {

    @EJB
    private UsuarioController controler;
    private Usuario usuario;
    private final Map<String, Permissao> menu;

    public UsuarioLogadoMB() {
        usuario = new Usuario();
        menu = new HashMap<>();
    }

    @Inject
    public void usuarioLogado() {
        try {
            if (usuario.getDocumento() == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                ExternalContext external = context.getExternalContext();
                String documento = external.getRemoteUser();
                if (documento != null) {
                    usuario = controler.carregar(documento);
                    popularMenu();
                }
            } else {
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void popularMenu() {
        for (Permissao p : usuario.getPermissoes()) {
            menu.put(p.getTarefa().getMnemonico(), p);
        }
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
