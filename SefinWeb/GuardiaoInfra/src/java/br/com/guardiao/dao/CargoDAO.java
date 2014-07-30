/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Cargo;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto guardiao criada em 02/04/2013 - Dao do modelo Cargo
 *
 * @author: ari
 */
@Stateless
public class CargoDAO extends DAO<Cargo, Long> implements Serializable {

    public CargoDAO() {
        super(Cargo.class);
    }
}
