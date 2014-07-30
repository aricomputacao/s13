/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.modelo.Sistema;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe de convesor do Projeto guardião em 22/03/2013
 *
 * @author Ari
 */
@Named
public class SistemaConverter implements Converter {

    @EJB
    private SistemaController dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            if (!"".equals(string)) {
                Long id = Long.parseLong(string);
                Sistema m = dao.carregar(id);
                return m;
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
        }
        Sistema s = (Sistema) o;
        if (s.getId() != null) {
            return s.getId().toString();
        }
        return null;

    }
}
