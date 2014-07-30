/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gestor.dao;

import br.com.gestor.modelo.CentroCusto;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Arquivo do projeto GestorModulo criando em 05/08/2013
 * @author ari
 */
@Stateless(name = "GestorCentroCustoDao")
public class CentroCustoDAO extends GestorDAO<CentroCusto> implements Serializable{

    public CentroCustoDAO(){
        super(CentroCusto.class);
    } 
}
