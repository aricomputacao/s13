/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.converter;

import br.com.siafi.controller.CategoriaDespesaController;
import br.com.siafi.modelo.CategoriaDespesa;
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
 * Arquivo do projeto SiafiWeb criando em 31/07/2013
 *
 * @author ari
 */
@Named
public class CategoriaDespesaConverter implements Converter {

    @EJB
    private CategoriaDespesaController controller;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value != null && !"".equals(value)) {
            try {
                return controller.carregar(value);
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

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            if (((CategoriaDespesa) value).getId() != null) {
                return ((CategoriaDespesa) value).getId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
