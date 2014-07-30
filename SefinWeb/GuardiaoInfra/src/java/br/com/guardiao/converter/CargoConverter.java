/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.CargoController;
import br.com.guardiao.modelo.Cargo;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe do Projeto guardiao criada em 02/04/2013, servir como conveçor de
 * cargos
 *
 * @author: ari
 */
@Named
public class CargoConverter implements Converter {

    @EJB
    private CargoController dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            Cargo c = dao.carregar(Long.parseLong(string));
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null) {
            return null;
        } else {
            if (((Cargo) o).getId() == null) {
                return null;
            }
        }
        return ((Cargo) o).getId().toString();
    }
}
