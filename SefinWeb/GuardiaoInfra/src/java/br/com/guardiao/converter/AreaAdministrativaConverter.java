/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.dao.AreaAdministrativaDAO;
import br.com.guardiao.modelo.AreaAdministrativa;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe do Projeto ******* - Criado em 24/05/2013 -
 *
 * @author Gilmário
 */
@Named
public class AreaAdministrativaConverter implements Converter {

    @EJB
    private AreaAdministrativaController dao;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return dao.carregar(Long.parseLong(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        try {
            AreaAdministrativa s = (AreaAdministrativa) value;
            return s.getId().toString();
        } catch (Exception e) {
            return null;
        }
    }
}
