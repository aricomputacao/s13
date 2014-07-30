/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.ExercicioController;
import br.com.guardiao.modelo.Exercicio;
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
 * Classe do Projeto Siafi - Criado em 29/04/2013 -
 *
 * @author Gilmário
 */
@Named
public class ExercicioConverter implements Converter, Serializable {

    @EJB
    private ExercicioController controller;

    /**
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            try {
                Exercicio e = controller.carregar(Integer.parseInt(value));
                return e;
            } catch (SQLException ex) {
                Logger.getLogger(ExercicioConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenceException ex) {
                Logger.getLogger(ExercicioConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EJBException ex) {
                Logger.getLogger(ExercicioConverter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ExercicioConverter.class.getName()).log(Level.SEVERE, null, ex);
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
        if (value != null && value instanceof Exercicio) {
            if (((Exercicio) value).getAno() != null) {
                return ((Exercicio) value).getAno().toString();
            } else {
                System.out.println("Saida");
                return null;

            }
        } else {
            System.out.println("Saida2");
            return null;
        }
    }
}
