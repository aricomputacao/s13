/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.siafi.modelo.Encaminhamento;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author gilmario
 */
@Stateless
public class EncaminhamentoLiquidacaoDAO extends DAO<EncaminhamentoLiquidacao, Long> implements Serializable {

    public EncaminhamentoLiquidacaoDAO() {
        super(EncaminhamentoLiquidacao.class);
    }

    public List<EncaminhamentoLiquidacao> listar(SolicitacaoLiquidacao solicitacaoLiquidacao) throws Exception {
        return getEm().createQuery("SELECT e FROM EncaminhamentoLiquidacao e WHERE e.solicitacaoLiquidacao =:sl  ORDER BY e.id ").setParameter("sl", solicitacaoLiquidacao).getResultList();
    }

    public EncaminhamentoLiquidacao ultimoEncaminhamento(SolicitacaoLiquidacao sl) throws Exception {
        return getEm().createQuery("SELECT MAX(e) FROM EncaminhamentoLiquidacao e WHERE e.solicitacaoLiquidacao =:sl ", EncaminhamentoLiquidacao.class).setParameter("sl", sl).getSingleResult();
    }

    public List<EncaminhamentoLiquidacao> listarReceber(AreaAdministrativa administrativa, List<UnidadeOrcamentaria> unidadesOrcamentarias, UnidadeOrcamentaria orcamentaria, StatusSolicitacaoLiquidacao situacao) throws Exception {
        String sql = "SELECT en FROM EncaminhamentoLiquidacao en WHERE en.dataRecebimento IS NULL AND en.destino = :administrativa";
        if (orcamentaria != null) {
            sql += " AND en.solicitacaoLiquidacao.solicitacaoFinanceira.cota.unidadeOrcamentaria =:orcamentaria ";
        }
        if (unidadesOrcamentarias != null && !unidadesOrcamentarias.isEmpty()) {
            sql += " AND en.solicitacaoLiquidacao.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:unidades) ";
        }

        if (situacao != null) {
            sql += " AND en.solicitacaoLiquidacao.statusSolicitacaoLiquidacao =:situacao ";
        }

        Query q = getEm().createQuery(sql, EncaminhamentoLiquidacao.class);
        q.setParameter("administrativa", administrativa);

        if (orcamentaria != null) {
            q.setParameter("orcamentaria", orcamentaria);
        }
        if (unidadesOrcamentarias != null && !unidadesOrcamentarias.isEmpty()) {
            q.setParameter("unidades", unidadesOrcamentarias);
        }
        if (situacao != null) {
            q.setParameter("situacao", situacao);
        }
        return q.getResultList();

    }

    public List<SolicitacaoLiquidacao> listarSolicitacoes(UnidadeOrcamentaria unidadeOrcamentaria, StatusSolicitacaoLiquidacao statusSolicitacaoLiquidacao, AreaAdministrativa areaAdministrativa) {
//        String sql = "SELECT en FROM EncaminhamentoLiquidacao en WHERE en.dataRecebimento IS NULL AND en.destino = :administrativa";
//        if (orcamentaria != null) {
//            sql += " AND en.solicitacaoLiquidacao.solicitacaoFinanceira.cota.unidadeOrcamentaria =:un ";
//        }
//        if (unidadesOrcamentarias != null && !unidadesOrcamentarias.isEmpty()) {
//            sql += " AND en.solicitacaoLiquidacao.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uns) ";
//        }
//
//        if (situacao != null) {
//            sql += " AND en.solicitacaoLiquidacao.statusSolicitacaoLiquidacao =:situacao ";
//        }
//
//        Query q = getEm().createQuery(sql, Encaminhamento.class);
//        q.setParameter("administrativa", administrativa);
//
//        if (orcamentaria != null) {
//            q.setParameter("orcamentaria", orcamentaria);
//        }
//        if (unidadesOrcamentarias != null && !unidadesOrcamentarias.isEmpty()) {
//            q.setParameter("unidades", unidadesOrcamentarias);
//        }
//        if (situacao != null) {
//            q.setParameter("situacao", situacao);
//        }
//        return q.getResultList();
        // Remover as encaminhadas
        List<SolicitacaoLiquidacao> encs = listarEncaminhadas(unidadeOrcamentaria, areaAdministrativa);
        if (encs.isEmpty()) {
            return getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.cota.unidadeOrcamentaria =:unidade AND s.areaAdministrativa =:area AND s.statusSolicitacaoLiquidacao =:status ").setParameter("unidade", unidadeOrcamentaria).setParameter("status", statusSolicitacaoLiquidacao).setParameter("area", areaAdministrativa).getResultList();
        } else {
            return getEm().createQuery("SELECT s FROM SolicitacaoLiquidacao s WHERE s.solicitacaoFinanceira.cota.unidadeOrcamentaria =:unidade AND s.areaAdministrativa =:area AND s.statusSolicitacaoLiquidacao =:status AND s NOT IN (:lista)").setParameter("unidade", unidadeOrcamentaria).setParameter("status", statusSolicitacaoLiquidacao).setParameter("area", areaAdministrativa).setParameter("lista", encs).getResultList();
        }

    }

    public List<SolicitacaoLiquidacao> listarEncaminhadas(UnidadeOrcamentaria unidadeOrcamentaria, AreaAdministrativa areaAdministrativa) {
        return getEm().createQuery("SELECT en.solicitacaoLiquidacao FROM EncaminhamentoLiquidacao en WHERE en.solicitacaoLiquidacao.solicitacaoFinanceira.cota.unidadeOrcamentaria =:unidade AND en.solicitacaoLiquidacao.areaAdministrativa =:area AND en.dataRecebimento IS NULL ").setParameter("unidade", unidadeOrcamentaria).setParameter("area", areaAdministrativa).getResultList();
    }

    /**
     * Metodo para retornar os encaminhamentos de uma origem para um destino
     * selecionado
     *
     * @param origem
     * @param destino
     * @return
     */
    public List<EncaminhamentoLiquidacao> listar(AreaAdministrativa origem, AreaAdministrativa destino,Date d) {
       
        return getEm().createQuery("SELECT en FROM EncaminhamentoLiquidacao en WHERE en.usuarioEncaminhou.areaAdministrativa = :or and en.destino = :des AND en.dataEncaminhamento = :dt ")
                .setParameter("or", origem)
                .setParameter("des", destino)
                .setParameter("dt", d)
                .getResultList();

    }

}
