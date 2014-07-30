/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Licitacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto SiafiWeb criada em 26/06/2013
 *
 * @author: ari
 */
@Stateless
public class LicitacaoDAO extends DAO<Licitacao, Integer> implements Serializable {

    public LicitacaoDAO() {
        super(Licitacao.class);
    }

    public List<Licitacao> listarUnidadeOrcamentariaCredor(UnidadeOrcamentaria un, Credor credor) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoUnidadeOrcamentaria l WHERE l.unidadeOrcamentaria = :un AND l.licitacao IN ( SELECT i.licitacao FROM ItemLicitacao i WHERE i.credor=:credor) ", Licitacao.class);
        q.setParameter("un", un);
        q.setParameter("credor", credor);
        return q.getResultList();
    }

    public List<Licitacao> listarUnidadeOrcamentariaCredorNumero(UnidadeOrcamentaria un, Credor credor, String numero) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoUnidadeOrcamentaria l WHERE l.unidadeOrcamentaria = :un AND l.licitacao IN ( SELECT i.licitacao FROM ItemLicitacao i WHERE i.credor=:credor) AND l.licitacao.numero LIKE :numero", Licitacao.class);
        q.setParameter("un", un);
        q.setParameter("credor", credor);
        q.setParameter("numero", "%" + numero + "%");
        return q.getResultList();
    }

    public List<Licitacao> listarUnidadeOrcamentariaCredorNumero(List<UnidadeOrcamentaria> unidades, Credor credor, String numero) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoUnidadeOrcamentaria l WHERE l.unidadeOrcamentaria IN (:un) AND l.licitacao IN ( SELECT i.licitacao FROM ItemLicitacao i WHERE i.credor=:credor) AND l.licitacao.numero LIKE :numero", Licitacao.class);
        q.setParameter("un", unidades);
        q.setParameter("credor", credor);
        q.setParameter("numero", "%" + numero + "%");
        return q.getResultList();
    }

    // Listar Licitações quen não estão sendo utilizadas em contratos
    public List<Licitacao> listarUnidadeOrcamentariaCredorLimpas(UnidadeOrcamentaria un, Credor credor) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoDotacao l WHERE l.dotacao.projetoAtividade.unidadeOrcamentaria = :un AND l.licitacao IN ( SELECT i.licitacao FROM ItemLicitacao i WHERE i.credor=:credor) AND l.licitacao NOT IN ( SELECT s.contrato.licitacao FROM SolicitacaoFinanceira s WHERE s.contrato IS NOT NULL AND s.situacaoSolicitacao<>'Negado' AND s.situacaoSolicitacao <> 'Cancelado'  )", Licitacao.class);
        q.setParameter("un", un);
        q.setParameter("credor", credor);
        return q.getResultList();
    }

    // Listar Licitações quen não estão sendo utilizadas em contratos em cima da tabela licitaca_unidade_orcamentaria
    public List<Licitacao> listarLicitacaoUnidadeOrcCredor(UnidadeOrcamentaria un, Credor credor) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoUnidadeOrcamentaria l WHERE l.unidadeOrcamentaria = :un AND l.licitacao IN ( SELECT i.licitacao FROM ItemLicitacao i WHERE i.credor=:credor) AND l.licitacao NOT IN ( SELECT s.contrato.licitacao FROM SolicitacaoFinanceira s WHERE s.contrato IS NOT NULL AND s.situacaoSolicitacao<>'Negado' AND s.situacaoSolicitacao <> 'Cancelado'  )", Licitacao.class);
        q.setParameter("un", un);
        q.setParameter("credor", credor);
        return q.getResultList();
    }

    public List<Licitacao> listarUnidadeOrcamentariaNumero(UnidadeOrcamentaria un, String numero) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT li.licitacao FROM LicitacaoUnidadeOrcamentaria li WHERE li.unidadeOrcamentaria = :un AND li.licitacao.numero LIKE  :numero", Licitacao.class);
        q.setParameter("un", un);
        q.setParameter("numero", "%" + numero + "%");
        return q.getResultList();
    }

    public List<Licitacao> listarUnidadeOrcamentariaNumero(List<UnidadeOrcamentaria> un, String numero) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT li.licitacao FROM LicitacaoUnidadeOrcamentaria li WHERE li.unidadeOrcamentaria IN (:un) AND li.licitacao.numero LIKE  :numero", Licitacao.class);
        q.setParameter("un", un);
        q.setParameter("numero", "%" + numero + "%");
        return q.getResultList();
    }

    public List<Licitacao> listarUnidadeOrcamentariaNumeroCredor(UnidadeOrcamentaria un, String numero, Credor credor) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT li.licitacao FROM LicitacaoUnidadeOrcamentaria li WHERE li.unidadeOrcamentaria = :un AND li.licitacao IN ( SELECT i.licitacao FROM ItemLicitacao i WHERE i.credor=:credor). AND li.licitacao.numero LIKE  :numero", Licitacao.class);
        q.setParameter("un", un);
        q.setParameter("credor", credor);
        q.setParameter("numero", "%" + numero + "%");
        return q.getResultList();
    }

    public List<Licitacao> listarNumeroCredor(String numero, Credor credor) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT i.licitacao FROM ItemLicitacao i WHERE i.credor = :credor AND i.licitacao.numero LIKE :numero", Licitacao.class);
        q.setParameter("credor", credor);
        q.setParameter("numero", "%" + numero + "%");
        return q.getResultList();
    }

    public List<Licitacao> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria un) throws Exception {
        Query q;
        q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoUnidadeOrcamentaria l WHERE l.unidadeOrcamentaria = :un");
        q.setParameter("un", un);
        return q.getResultList();
    }

    public List<Licitacao> listarPorUnidadeOrcamentaria(List<UnidadeOrcamentaria> un) throws Exception {
        Query q;
        q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoUnidadeOrcamentaria l WHERE lunidadeOrcamentaria IN (:un) ");
        q.setParameter("un", un);
        return q.getResultList();
    }

    public List<Licitacao> listarPorOrdemCompra(Licitacao licitacao) throws Exception {
        Query q;
        q = getEm().createQuery("SELECT li from Licitacao li WHERE li  in (SELECT o.licitacao from OrdemCompra o WHERE o.licitacao = :licitacao) ");
        q.setParameter("licitacao", licitacao);
        return q.getResultList();
    }

    public List<UnidadeOrcamentaria> listarPorLicitacao(Licitacao licitacao) throws Exception {
        return getEm().createQuery("SELECT DISTINCT ld.unidadeOrcamentaria FROM LicitacaoUnidadeOrcamentaria ld WHERE ld.licitacao =:licitacao", UnidadeOrcamentaria.class).setParameter("licitacao", licitacao).getResultList();
    }

    public BigDecimal calcularValorUtilizado(Licitacao lic) throws Exception {
        TypedQuery<BigDecimal> q = getEm().createQuery("select sum(sol.valor) as valor FROM SolicitacaoFinanceira sol WHERE SOL.licitacao =:licitacao AND sol.situacaoSolicitacao=:aprovada AND sol.situacaoSolicitacao=:pendente ", BigDecimal.class);
        q.setParameter("licitacao", lic);
        q.setParameter("aprovada", SituacaoSolicitacao.Aprovado);
        q.setParameter("pendente", SituacaoSolicitacao.Pendente);
        return q.getSingleResult();
    }

    public BigDecimal calcularValorTotal(Licitacao lic) throws Exception {
        TypedQuery<BigDecimal> q = getEm().createQuery("SELECT sum(ad.valor) AS valor FROM Aditivo ad WHERE ad.contrato.licitacao = :licitacao ", BigDecimal.class);
        q.setParameter("licitacao", lic);
        return q.getSingleResult();
    }
}
