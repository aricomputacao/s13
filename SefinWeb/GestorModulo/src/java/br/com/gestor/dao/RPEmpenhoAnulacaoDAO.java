/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.RPEmpenhoAnulacao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 17/10/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorRPEmpenhoAnulacaoDAO")
public class RPEmpenhoAnulacaoDAO extends GestorDAO<RPEmpenhoAnulacao> implements Serializable {

    public RPEmpenhoAnulacaoDAO() {
        super(RPEmpenhoAnulacao.class);
    }
}
