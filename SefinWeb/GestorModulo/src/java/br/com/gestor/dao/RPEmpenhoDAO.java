/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.RPEmpenho;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto Siafi - Criado em 10/10/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorRPEmpenhoDAO")
public class RPEmpenhoDAO extends GestorDAO<RPEmpenho> implements Serializable {

    public RPEmpenhoDAO() {
        super(RPEmpenho.class);
    }

  
}
