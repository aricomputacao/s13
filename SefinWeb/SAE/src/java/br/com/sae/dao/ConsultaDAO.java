/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sae.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class ConsultaDAO extends DAO<SolicitacaoFinanceira, String> implements Serializable {

    private List<SituacaoSolicitacao> l = new ArrayList<>();

    public ConsultaDAO() {
        super(SolicitacaoFinanceira.class);
    }

    public List<EmpenhoDetalhe> listarLiquidacaoPorSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) {
        return getEm().createQuery("SELECT e FROM EmpenhoDetalhe e WHERE e.solicitacaoFinanceira =:sol").setParameter("sol", solicitacaoFinanceira).getResultList();
    }

    public List<SolicitacaoFinanceira> listarSolicitacaoUnidadeOrcamentariaCredor(UnidadeOrcamentaria uo, String doc, Date dtIni, Date dtFim) {
        l.add(SituacaoSolicitacao.Cancelado);
        l.add(SituacaoSolicitacao.Negado);
        l.add(SituacaoSolicitacao.Pendente);
        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.situacaoSolicitacao NOT IN (:sit) and s.cota.unidadeOrcamentaria = :uo AND s.credor.documento = :doc and s.dataSolicitacao BETWEEN :ini and :fim ORDER BY s.situacaoSolicitacao", SolicitacaoFinanceira.class);
        q.setParameter("uo", uo);
        q.setParameter("doc", doc);
        q.setParameter("ini", dtIni);
        q.setParameter("fim", dtFim);
        q.setParameter("sit", l);

        return q.getResultList();

    }

    public List<SolicitacaoFinanceira> listarSolicitacaoCredor(String doc, Date dtIni, Date dtFim) {

        l.add(SituacaoSolicitacao.Cancelado);
        l.add(SituacaoSolicitacao.Negado);
        l.add(SituacaoSolicitacao.Pendente);
        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.situacaoSolicitacao NOT IN (:sit) and s.credor.documento = :doc AND s.dataSolicitacao BETWEEN :ini and :fim ORDER BY s.situacaoSolicitacao", SolicitacaoFinanceira.class);
        q.setParameter("ini", dtIni);
        q.setParameter("fim", dtFim);
        q.setParameter("doc", doc);
        q.setParameter("sit", l);
        return q.getResultList();
    }

}
