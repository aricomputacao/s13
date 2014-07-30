/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.converter;

import br.com.siafi.controller.CategoriaController;
import br.com.siafi.modelo.Categoria;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import javax.persistence.PersistenceException;

/**
 * Classe do Projeto ******* - Criado em 29/04/2013 -
 *
 * @author Gilmário
 */
@Named
public class CategoriaConverter implements Converter {

    @EJB
    private CategoriaController controller;

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Categoria getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            try {
                return controller.carregar(Long.parseLong(value));
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenceException ex) {
                Logger.getLogger(CategoriaConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EJBException ex) {
                Logger.getLogger(CategoriaConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CategoriaConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            if (((Categoria) value).getId() != null) {
                return ((Categoria) value).getId().toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
