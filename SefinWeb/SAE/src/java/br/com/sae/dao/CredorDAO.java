/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sae.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class CredorDAO extends DAO<Credor, Integer> implements Serializable{

    public CredorDAO() {
        super(Credor.class);
    }
    
}
