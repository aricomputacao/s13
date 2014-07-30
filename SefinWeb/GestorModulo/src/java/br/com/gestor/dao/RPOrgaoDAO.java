/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.RPOrgao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/10/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorRPOrgaoDAO")
public class RPOrgaoDAO extends GestorDAO<RPOrgao> implements Serializable {

    public RPOrgaoDAO() {
        super(RPOrgao.class);
    }
    
}
