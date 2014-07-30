/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.dao.UsuarioDAO;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author gilmario
 */
@Named
public class UsuarioConverter implements Converter, Serializable {

    @EJB
    private UsuarioDAO dao;

    @Override
    public Usuario getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Usuario u = dao.carregar(value);
            if (u != null) {
                return u;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return ((Usuario) value).getDocumento();
        } catch (Exception e) {
            return null;
        }
    }
}
