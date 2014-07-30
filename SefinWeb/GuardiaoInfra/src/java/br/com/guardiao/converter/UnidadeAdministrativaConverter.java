/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.UnidadeAdministrativaController;
import br.com.guardiao.modelo.UnidadeAdministrativa;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe gerenciado do Projeto Error on line 11, column 35 in
 * Templates/Classes/Class.java Expecting a string, date or number here,
 * Expression project is instead a freemarker.template.SimpleHash criado em
 * 24/03/2013
 *
 * @author Ari
 */
@Named
public class UnidadeAdministrativaConverter implements Converter {

    @EJB
    private UnidadeAdministrativaController dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {

            UnidadeAdministrativa u = dao.carregar(string);
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null) {
            UnidadeAdministrativa m = (UnidadeAdministrativa) o;
            return m.getId();
        } else {
            return null;
        }
    }
}
