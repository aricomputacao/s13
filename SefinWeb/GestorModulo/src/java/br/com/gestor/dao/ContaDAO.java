/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gestor.dao;

import br.com.gestor.modelo.Conta;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Arquivo do projeto GestorModulo criando em 29/07/2013
 * @author ari
 */
@Stateless(name = "GestorContaDao")
public class ContaDAO extends GestorDAO<Conta> implements Serializable{
    public ContaDAO(){
        super(Conta.class);
    }

}
