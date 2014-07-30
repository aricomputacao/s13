/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.OrgaoController;
import br.com.guardiao.modelo.Orgao;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 * Classe do Projeto guardiao - Criado em 22/03/2013 - Classe de conversão da
 * entidade Orgao
 *
 * @author Ari
 */
@Named
public class OrgaoConverter implements Converter, Serializable {

    @EJB
    private OrgaoController dao;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {

            Orgao o = dao.buscarNome(string);
            return o;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        String v;
        if (o == null) {
            return null;
        }
        Orgao s = (Orgao) o;

        v = s.getNome();
        return v;

    }
}
