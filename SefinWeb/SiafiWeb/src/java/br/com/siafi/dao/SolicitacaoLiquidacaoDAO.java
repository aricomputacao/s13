/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class SolicitacaoLiquidacaoDAO extends DAO<SolicitacaoLiquidacao, Long> implements Serializable {

    public SolicitacaoLiquidacaoDAO() {
        super(SolicitacaoLiquidacao.class);
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira = :sol", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(String solicitacaoFinanceira, List<UnidadeOrcamentaria> uos) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.id = :sol AND s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos)", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uos", uos);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(List<UnidadeOrcamentaria> uos) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uos", uos);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(String solicitacaoFinanceira, UnidadeOrcamentaria uo) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.id = :sol AND s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uo", uo);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(UnidadeOrcamentaria uo) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uo", uo);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(String solicitacaoFinanceira, List<UnidadeOrcamentaria> uos, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.id = :sol AND s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uos", uos);
        q.setParameter("c", c);
        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(List<UnidadeOrcamentaria> uos, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uos", uos);
        q.setParameter("c", c);
        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(String solicitacaoFinanceira, UnidadeOrcamentaria uo, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.id = :sol AND s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uo", uo);
        q.setParameter("c", c);
        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(UnidadeOrcamentaria uo, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uo", uo);
        q.setParameter("c", c);
        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(String solicitacaoFinanceira, List<UnidadeOrcamentaria> uos, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.solicitacaoFinanceira.id = :sol AND s.statusSolicitacaoLiquidacao = :sit and s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uos", uos);
        q.setParameter("c", c);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(List<UnidadeOrcamentaria> uos, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.statusSolicitacaoLiquidacao = :sit and s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uos", uos);
        q.setParameter("c", c);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(String solicitacaoFinanceira, UnidadeOrcamentaria uo, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.solicitacaoFinanceira.id = :sol  AND s.statusSolicitacaoLiquidacao = :sit and s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uo", uo);
        q.setParameter("c", c);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(UnidadeOrcamentaria uo, Credor c) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo AND s.statusSolicitacaoLiquidacao = :sit  AND s.solicitacaoFinanceira.credor = :c ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uo", uo);
        q.setParameter("c", c);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(UnidadeOrcamentaria uo) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo AND s.statusSolicitacaoLiquidacao = :sit  ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uo", uo);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(List<UnidadeOrcamentaria> uos) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) AND s.statusSolicitacaoLiquidacao = :sit  ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("uos", uos);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(String solicitacaoFinanceira, UnidadeOrcamentaria uo) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.solicitacaoFinanceira.id = :sol AND s.solicitacaoFinanceira.cota.unidadeOrcamentaria = :uo AND s.statusSolicitacaoLiquidacao = :sit ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uo", uo);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);

        return q.getResultList();
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(String solicitacaoFinanceira, List<UnidadeOrcamentaria> uos) {
        TypedQuery<SolicitacaoLiquidacao> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.areaAdministrativa.tipoAreaAdm = :loc and s.solicitacaoFinanceira.id = :sol AND s.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) AND s.statusSolicitacaoLiquidacao = :sit ORDER BY s.solicitacaoFinanceira.cota.unidadeOrcamentaria,s.solicitacaoFinanceira.credor", SolicitacaoLiquidacao.class);
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("uos", uos);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("sit", StatusSolicitacaoLiquidacao.Aprovado);
        return q.getResultList();
    }

}
