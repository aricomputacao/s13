/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.siafi.converter;

import br.com.siafi.controller.ProjetoAtividadeController;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
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
 * Classe do Projeto SiafiWeb criada em 14/06/2013
 * 
 * @author: ari
 */
@Named
public class ProjetoAtividadeConverter implements Converter,Serializable{

    @EJB
    private ProjetoAtividadeController controler;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            try {
                return controler.carregar((value));
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
            if (((ProjetoAtividade) value).getId() != null) {
                return ((ProjetoAtividade) value).getId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
