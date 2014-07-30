/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.Exercicio;
import br.com.siafi.modelo.Orcamento;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 - Dao do modelo Orçamento
 *
 * @author Gilmário
 */
@Stateless
public class OrcamentoDAO extends DAO<Orcamento, Long> implements Serializable {

    public OrcamentoDAO() {
        super(Orcamento.class);
    }

    public Orcamento orcamentoAtual(Exercicio exercicio) throws Exception {
        return (Orcamento) getEm().createQuery(" FROM Orcamento where exercicio =:exercicio").setParameter("exercicio", exercicio).getSingleResult();
    }
}
