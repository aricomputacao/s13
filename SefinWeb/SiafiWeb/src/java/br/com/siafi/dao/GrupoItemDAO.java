/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.GrupoItem;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 26/06/2013
 *
 * @author: ari
 */
@Stateless
public class GrupoItemDAO extends DAO<GrupoItem, Integer> implements Serializable {

    public GrupoItemDAO() {
        super(GrupoItem.class);
    }
}
