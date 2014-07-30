/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.auditoria.modelo;

import br.com.guardiao.modelo.Usuario;
import br.com.guardiao.util.MenssagemUtil;
import javax.faces.context.FacesContext;
import org.hibernate.envers.RevisionListener;

/**
 * Classe do Projeto Guardião - Criado em 19/04/2013 - Classe de implementação
 * da entidade auditoria. Usada para incluir o usuario da sessão na classe.
 *
 * @author Gilmário
 */
public class RevisionalListener implements RevisionListener {

    /**
     *
     * @param o
     */
    @Override
    public void newRevision(Object o) {
        EntidadeRevisional revi = (EntidadeRevisional) o;
        try {
            Usuario usuario = new Usuario(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
            revi.setUsuario(usuario);
        } catch (Exception e) {
            MenssagemUtil.addMessageErroLogger(e, this.getClass().getName());
        }
    }
}
