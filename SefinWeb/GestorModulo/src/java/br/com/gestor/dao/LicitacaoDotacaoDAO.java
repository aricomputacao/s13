/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.LicitacaoDotacao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "gestorLicitacaoDotacaoDao")
public class LicitacaoDotacaoDAO extends GestorDAO<LicitacaoDotacao> implements Serializable {

    public LicitacaoDotacaoDAO() {
        super(LicitacaoDotacao.class);
    }
}
