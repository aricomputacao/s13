/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Dotacao;
import br.com.siafi.modelo.ElementoDespesa;
import br.com.guardiao.modelo.Exercicio;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.DotacalPk;
import br.com.siafi.modelo.NaturezaDespesa;
import br.com.siafi.modelo.Orcamento;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 - Dao do modelo Dotacao
 *
 * @author Gilmário
 */
@Stateless
public class DotacaoDAO extends DAO<Dotacao, DotacalPk> implements Serializable {

    /**
     *
     */
    public DotacaoDAO() {
        super(Dotacao.class);
    }

    public Dotacao dotacaoProjetoAtividadeNatureza(ProjetoAtividade pa, NaturezaDespesa nd, Orcamento o) throws Exception {
        TypedQuery<Dotacao> q;
        q = getEm().createQuery("SELECT d FROM Dotacao d WHERE d.projetoAtividade = :pa and d.naturezaDespesa = :nd  AND d.orcamento = :o ORDER BY d.id", Dotacao.class);
        q.setParameter("pa", pa);
        q.setParameter("nd", nd);
        q.setParameter("o", o);
        return q.getSingleResult();

    }

    public List<Dotacao> dotacaoProjetoAtividadeOrcamento(ProjetoAtividade pa, Orcamento o, ElementoDespesa ed) throws Exception {
        TypedQuery<Dotacao> q;
        q = getEm().createQuery("SELECT d FROM Dotacao d WHERE d.projetoAtividade = :pa AND d.orcamento = :o AND d.naturezaDespesa.elementoDespesa = :ed ORDER BY d.id", Dotacao.class);
        q.setParameter("pa", pa);
        q.setParameter("o", o);
        q.setParameter("ed", ed);
        return q.getResultList();

    }

    public List<Dotacao> dotacaoElementoDespesaOrcamento(Orcamento o, ElementoDespesa elementoDespesa) throws Exception {
        TypedQuery<Dotacao> q;
        q = getEm().createQuery("SELECT d FROM Dotacao d WHERE d.orcamento = :o AND d.naturezaDespesa.elementoDespesa = :ed ORDER BY d.id", Dotacao.class);
        q.setParameter("o", o);
        q.setParameter("ed", elementoDespesa);
        return q.getResultList();
    }

    // Gil fez correção na query 16/01/2014
    public List<Dotacao> dotacaoUnidadeOrcamentaria(UnidadeOrcamentaria uo, Exercicio e) throws Exception {
        TypedQuery<Dotacao> q;
        q = getEm().createQuery("SELECT d FROM Dotacao d WHERE d.orcamento.exercicio = :e and d.projetoAtividade.unidadeOrcamentaria = :uo "
                + " ORDER BY d.projetoAtividade.unidadeOrcamentaria, d.projetoAtividade, d.id ", Dotacao.class);
        q.setParameter("uo", uo);
        q.setParameter("e", e);
        return q.getResultList();
    }

    public List<Dotacao> dotacaoUnidadeOrcamentaria(List<UnidadeOrcamentaria> uos, Exercicio e) throws Exception {
        TypedQuery<Dotacao> q;
        q = getEm().createQuery("SELECT d FROM Dotacao d WHERE d.orcamento.exercicio = :e and d.projetoAtividade.unidadeOrcamentaria IN (:uos) ORDER BY d.projetoAtividade.unidadeOrcamentaria, d.projetoAtividade, d.id ", Dotacao.class);
        q.setParameter("uos", uos);
        q.setParameter("e", e);
        return q.getResultList();
    }

    public Dotacao dotacaoPorCodigoSimples(String cod, Orcamento o) throws Exception {
        return (Dotacao) getEm().createQuery("SELECT o FROM Dotacao o WHERE o.orcamento =:o AND o.dotacalPk.id_reduzido =:cod ORDER BY o.id")
                .setParameter("o", o)
                .setParameter("cod", cod)
                .getSingleResult();
    }

    public BigDecimal saldoUtilizado(Dotacao dotacao) throws Exception {
        List<SituacaoSolicitacao> situacoesList = new ArrayList<>();
        situacoesList.add(SituacaoSolicitacao.Cancelado);
        situacoesList.add(SituacaoSolicitacao.Negado);
        situacoesList.add(SituacaoSolicitacao.Pendente);
        BigDecimal valor = getEm().createQuery(" SELECT SUM(s.valor) FROM SolicitacaoFinanceira s WHERE s.dotacao=:d AND s.situacaoSolicitacao NOT IN(:situacoes) ", BigDecimal.class).setParameter("d", dotacao).setParameter("situacoes", situacoesList).getSingleResult();
        if (valor != null) {
            return valor;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal somaSolicitacaoDotacao(Dotacao d) throws Exception {
        List<SituacaoSolicitacao> sits = new ArrayList<>();
        sits.add(SituacaoSolicitacao.Cancelado);
        sits.add(SituacaoSolicitacao.Negado);
        return getEm().createQuery("SELECT SUM(s.valor) FROM SolicitacaoFinanceira s WHERE  s.dotacao =:dot AND s.situacaoSolicitacao NOT IN (:situacao)", BigDecimal.class)
                .setParameter("dot", d)
                .setParameter("situacao", sits)
                .getSingleResult();
    }

    public BigDecimal saldoUtilizadoGestor(Dotacao dotacao, String numeroEmpenho, Integer ano) throws Exception {
        String chave = ano + numeroEmpenho.substring(2, 4) + numeroEmpenho.substring(0, 2) + numeroEmpenho.substring(4, 8);

        BigDecimal valor = getEm().createQuery("SELECT SUM(s.valor) FROM SolicitacaoFinanceira s WHERE s.dotacao=:d AND s.situacaoSolicitacao <> :situacao AND s.empenho IS NOT NULL AND CONCAT(s.exercicio.ano,SUBSTRING(s.empenho,3,2),SUBSTRING(s.empenho,1,2),SUBSTRING(empenho,5,8)) <= :chave ", BigDecimal.class).setParameter("d", dotacao).setParameter("chave", chave).setParameter("situacao", SituacaoSolicitacao.Cancelado).getSingleResult();

        if (valor != null) {
            return valor;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public List<ProjetoAtividade> listarProjetoPorElementoUnidadeOrcamentaria(Orcamento o, CentroCusto despesa, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT d.projetoAtividade FROM Dotacao d WHERE d.projetoAtividade.unidadeOrcamentaria = :uo AND d.orcamento = :o AND d.naturezaDespesa.elementoDespesa = :cc ", ProjetoAtividade.class);
        q.setParameter("uo", unidadeOrcamentaria);
        q.setParameter("o", o);
        q.setParameter("cc", despesa.getElementoDespesa());

        return q.getResultList();
    }

    public List<Dotacao> listar(UnidadeOrcamentaria unidadeOrcamentaria, List<UnidadeOrcamentaria> unidadeOrcamentarias, Exercicio exercicio, NaturezaDespesa naturezaDespesa, ProjetoAtividade projetoAtividade) throws Exception {
        String sql = "SELECT d FROM Dotacao d WHERE d.projetoAtividade.unidadeOrcamentaria IN(:unds) AND d.orcamento.exercicio =:exercicio ";
        if (unidadeOrcamentaria != null) {
            sql += " AND d.projetoAtividade.unidadeOrcamentaria =:un ";
        }
        if (naturezaDespesa != null) {
            sql += " AND d.naturezaDespesa =:natureza ";
        }
        if (projetoAtividade != null) {
            sql += " AND d.projetoAtividade =:projeto ";
        }

        Query q = getEm().createQuery(sql);
        q.setParameter("exercicio", exercicio);
        q.setParameter("unds", unidadeOrcamentarias);
        if (unidadeOrcamentaria != null) {
            q.setParameter("un", unidadeOrcamentaria);
        }
        if (naturezaDespesa != null) {
            q.setParameter("natureza", naturezaDespesa);
        }
        if (projetoAtividade != null) {
            q.setParameter("projeto", projetoAtividade);
        }

        return q.getResultList();
    }
}
