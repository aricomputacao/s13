/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.converter;

import br.com.siafi.controller.CotaController;
import br.com.siafi.modelo.Cota;
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
 * Classe do Projeto Siafi - Criado em 29/04/2013 -
 *
 * @author Gilmário
 */
@Named
public class CotaConverter implements Converter {

    @EJB
    private CotaController controller;

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Cota getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            try {
                return controller.carregar(Long.parseLong(value));
            } catch (SQLException ex) {
                Logger.getLogger(CotaConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenceException ex) {
                Logger.getLogger(CotaConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EJBException ex) {
                Logger.getLogger(CotaConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CotaConverter.class.getName()).log(Level.SEVERE, null, ex);
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
            if (((Cota) value).getId() != null) {
                return ((Cota) value).getId().toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
