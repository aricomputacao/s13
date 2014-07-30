/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.HistoricoPadrao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class HistoricoPadraoDAO extends DAO<HistoricoPadrao, Long> implements Serializable {

    public HistoricoPadraoDAO() {
        super(HistoricoPadrao.class);
    }

    public List<HistoricoPadrao> listar(UnidadeOrcamentaria orcamentaria, String nome) throws Exception {
        return getEm().createQuery("SELECT h FROM HistoricoPadrao h WHERE h.unidadeOrcamentaria =:unidade AND h.nome like :nome").setParameter("nome", nome.toUpperCase() + "%").setParameter("unidade", orcamentaria).getResultList();
    }

}
