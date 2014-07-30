/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.Credor;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 24/06/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorCredorDao")
public class CredorDAO extends GestorDAO<Credor> implements Serializable {

    public CredorDAO() {
        super(Credor.class);
    }
}
