/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.Dotacao;
import br.com.siafi.modelo.Licitacao;
import br.com.siafi.modelo.LicitacaoDotacao;
import br.com.siafi.modelo.Orcamento;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class LicitacaoDotacaoDAO extends DAO<LicitacaoDotacao, Integer> implements Serializable {

    public LicitacaoDotacaoDAO() {
        super(LicitacaoDotacao.class);
    }

    public Boolean existeLicitacaoDotacao(Licitacao lic, Dotacao dot) throws Exception {
        TypedQuery<Long> q = getEm().createQuery("SELECT COUNT(d.id) FROM LicitacaoDotacao d WHERE  d.licitacao = :lic AND d.dotacao = :dot ", Long.class);
        q.setParameter("lic", lic);
        q.setParameter("dot", dot);
        return q.getSingleResult() > 0;
    }

    public List<ProjetoAtividade> listarLicitacaoDotacao(Orcamento o, Licitacao l, CentroCusto cc, UnidadeOrcamentaria uo) throws Exception {
        TypedQuery<ProjetoAtividade> q = getEm().createQuery("SELECT d.dotacao.projetoAtividade FROM LicitacaoDotacao d WHERE d.dotacao.projetoAtividade.unidadeOrcamentaria = :uo AND d.licitacao = :l AND d.dotacao.orcamento = :o AND d.dotacao.naturezaDespesa.elementoDespesa = :cc ", ProjetoAtividade.class);
        q.setParameter("l", l);
        q.setParameter("o", o);
        q.setParameter("uo", uo);
        q.setParameter("cc", cc.getElementoDespesa());

        return q.getResultList();
    }

    public List<ProjetoAtividade> listarLicitacaoDotacao(Orcamento o, CentroCusto cc, UnidadeOrcamentaria uo) throws Exception {
        TypedQuery<ProjetoAtividade> q;
        q = getEm().createQuery("SELECT DISTINCT d.dotacao.projetoAtividade FROM LicitacaoDotacao d WHERE d.dotacao.projetoAtividade.unidadeOrcamentaria = :uo AND d.dotacao.orcamento = :o AND d.dotacao.naturezaDespesa.elementoDespesa = :cc ", ProjetoAtividade.class);
        q.setParameter("uo", uo);
        q.setParameter("o", o);
        q.setParameter("cc", cc.getElementoDespesa());

        return q.getResultList();
    }
}
