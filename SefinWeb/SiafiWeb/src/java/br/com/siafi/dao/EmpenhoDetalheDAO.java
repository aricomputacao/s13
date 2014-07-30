/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author ari
 */
@Stateless
public class EmpenhoDetalheDAO extends DAO<EmpenhoDetalhe, Integer> implements Serializable {

    public EmpenhoDetalheDAO() {
        super(EmpenhoDetalhe.class);
    }

    public List<EmpenhoDetalhe> listarPorSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return getEm().createQuery("SELECT e FROM EmpenhoDetalhe e WHERE e.solicitacaoFinanceira =:sol").setParameter("sol", solicitacaoFinanceira).getResultList();
    }

    public List<EmpenhoDetalhe> listaSemPagamento() {
        return getEm().createQuery("SELECT e FROM EmpenhoDetalhe e WHERE e.dataPagamento  IS NULL ")
                .getResultList();
    }

    public List<EmpenhoDetalhe> listarPagamentos(UnidadeOrcamentaria uo, Credor credor, List<UnidadeOrcamentaria> uos, Date dataInicio, Date dataFinal, Date dataPagamentoInicio, Date dataPagamentoFinal) {
        String sql = "SELECT e FROM EmpenhoDetalhe e where ";
        if (uos != null && !uos.isEmpty()) {
            sql += " e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN(:uos) ";
        }
        if (uo != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria =:uo ";
        }
        if (credor != null) {
            sql += " AND e.solicitacaoFinanceira.credor = :credor ";
        }
        if (dataInicio != null && dataFinal != null) {
            sql += " AND e.solicitacaoFinanceira.dataSolicitacao BETWEEN :data1 AND :data2 ";
        }
        if (dataPagamentoInicio != null && dataPagamentoFinal != null) {
            sql += " AND e.dataPagamento BETWEEN :datap1 AND :datap2 ";
        }

        sql += " ORDER BY e.solicitacaoFinanceira.cota.unidadeOrcamentaria, e.dataPagamento ";

        Query q = getEm().createQuery(sql);
        if (uos != null && !uos.isEmpty()) {
            q.setParameter("uos", uos);
        }
        if (uo != null) {
            q.setParameter("uo", uo);
        }
        if (credor != null) {
            q.setParameter("credor", credor);
        }
        if (dataInicio != null && dataFinal != null) {
            q.setParameter("data1", dataInicio);
            q.setParameter("data2", dataFinal);
        }
        if (dataPagamentoInicio != null && dataPagamentoFinal != null) {
            q.setParameter("datap1", dataPagamentoInicio);
            q.setParameter("datap2", dataPagamentoFinal);
        }
        return q.getResultList();
    }
}
