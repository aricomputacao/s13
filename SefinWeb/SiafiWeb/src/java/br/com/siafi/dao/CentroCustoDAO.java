/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.CentroCustoDto;
import br.com.guardiao.modelo.Exercicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto Siafi - Criado em 16/04/2013 - Dao do Modelo despesa
 *
 * @author Gilmário
 */
@Stateless
public class CentroCustoDAO extends DAO<CentroCusto, Integer> implements Serializable {

    public CentroCustoDAO() {
        super(CentroCusto.class);
    }

    public List<CentroCusto> listarDespesaPorUnidadeOrcCategoria(UnidadeOrcamentaria uo, Categoria c) throws Exception {
        TypedQuery<CentroCusto> qry = getEm().createQuery("SELECT DISTINCT (c.despesa) from Cota c WHERE c.unidadeOrcamentaria = :uo AND c.categoria = :c and c.ativo = :a", CentroCusto.class);
        qry.setParameter("uo", uo);
        qry.setParameter("c", c);
        qry.setParameter("a", true);
        return qry.getResultList();
    }

    public List<CentroCusto> listarCentroCustoOrdenado() throws Exception {
        TypedQuery<CentroCusto> qry = getEm().createQuery("SELECT c  from CentroCusto c   ORDER BY c.nome ", CentroCusto.class);

        return qry.getResultList();
    }

    public List<CentroCustoDto> listarUnidadeOrcamentariaMesDespesa(Exercicio exercicio, UnidadeOrcamentaria unidadeOrcamentaria, Mes mes) throws Exception {
        List<SituacaoSolicitacao> situacaoCusteio = new ArrayList<>();
        situacaoCusteio.add(SituacaoSolicitacao.Pago);
        situacaoCusteio.add(SituacaoSolicitacao.Aprovado);
        situacaoCusteio.add(SituacaoSolicitacao.Empenhado);
        situacaoCusteio.add(SituacaoSolicitacao.Liquidado);
        situacaoCusteio.add(SituacaoSolicitacao.Concluido);
        situacaoCusteio.add(SituacaoSolicitacao.AutorizadoPagamento);
        situacaoCusteio.add(SituacaoSolicitacao.DocumentaçãoLiberada);
        TypedQuery<CentroCustoDto> q = getEm().createQuery("SELECT NEW br.com.siafi.modelo.CentroCustoDto(s.cota.unidadeOrcamentaria, s.cota.despesa, sum(s.valor)) FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:unidade AND s.mesCompetencia =:competencia AND s.situacaoSolicitacao IN(:situacoes) AND s.exercicio =:exercicio GROUP BY s.cota.despesa , s.cota.unidadeOrcamentaria ", CentroCustoDto.class);
        q.setParameter("situacoes", situacaoCusteio);
        q.setParameter("unidade", unidadeOrcamentaria);
        q.setParameter("exercicio", exercicio);
        q.setParameter("competencia", mes);

        return q.getResultList();
    }

}
