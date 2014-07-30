/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe do Projeto guardiao criada em 02/04/2013
 *
 * @author: ari
 */
@Named
public class UnidadeOrcamentariaConverter implements Converter, Serializable {

    @EJB
    private UnidadeOrcamentariaController dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            if (string != null) {
                UnidadeOrcamentaria c = dao.buscarId(string);
                return c;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        } else {
            UnidadeOrcamentaria u = (UnidadeOrcamentaria) o;
            if (u.getUnidadeOrcamentariaPK() != null) {
                if (u.getUnidadeOrcamentariaPK().getId() != null) {
                    return u.getUnidadeOrcamentariaPK().getId()
                            + u.getUnidadeOrcamentariaPK().getIdOrgao() + u.getUnidadeOrcamentariaPK().getExercicio_ano();
                }
            }
            return null;
        }
    }
}
