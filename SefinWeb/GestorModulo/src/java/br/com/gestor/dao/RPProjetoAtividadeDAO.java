/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.RPProjetoAtividade;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/10/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorRPProjetoAtividadeDAO")
public class RPProjetoAtividadeDAO extends GestorDAO<RPProjetoAtividade> implements Serializable {

    public RPProjetoAtividadeDAO() {
        super(RPProjetoAtividade.class);
    }
}
