/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.GrupoItem;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto ******* - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorGrupoItemDao")
public class GrupoItemDAO extends GestorDAO<GrupoItem> implements Serializable {

    public GrupoItemDAO() {
        super(GrupoItem.class);
    }
}
