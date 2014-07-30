/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.TipoCredor;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 27/06/2013
 *
 * @author: ari
 */
@Stateless
public class TipoCredorDAO extends DAO<TipoCredor, Integer> implements Serializable {

    public TipoCredorDAO() {
        super(TipoCredor.class);
    }
}
