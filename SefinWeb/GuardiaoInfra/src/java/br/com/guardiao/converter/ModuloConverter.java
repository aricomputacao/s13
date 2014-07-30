/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.ModuloController;
import br.com.guardiao.modelo.Modulo;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe do Projeto guardiao - Criado em 22/03/2013 - Classe de conversão da
 * entidade Módulo
 *
 * @author Gilmário
 */
@Named
public class ModuloConverter implements Converter {

    @EJB
    private ModuloController dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            Long id = Long.parseLong(string);
            Modulo m = dao.carregar(id);
            return m;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        try {
            return ((Modulo) o).getId().toString();
        } catch (Exception e) {
            return null;
        }
    }
}
