/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.Pendencia;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class PendenciaDAO extends DAO<Pendencia, String> implements Serializable {

    public PendenciaDAO() {
        super(Pendencia.class);
    }

}
