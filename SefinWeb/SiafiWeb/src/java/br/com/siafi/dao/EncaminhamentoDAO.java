/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.Usuario;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Encaminhamento;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * @Sistema SiafiWeb
 * @Data 19/07/2013
 * @author gilmario
 */
@Stateless(name = "SiafiEncaminhamentoDao")
public class EncaminhamentoDAO extends DAO<Encaminhamento, Long> implements Serializable {

    public EncaminhamentoDAO() {
        super(Encaminhamento.class);
    }

    public List<Encaminhamento> listar(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.solicitacaoFinanceira=:sol ORDER BY e.id").setParameter("sol", solicitacaoFinanceira).getResultList();
    }

    public Encaminhamento verificaSolicitacaoAberta(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return (Encaminhamento) getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.solicitacaoFinanceira =:sol AND e.usuarioRecebeu IS NULL").setParameter("sol", solicitacaoFinanceira).getSingleResult();
    }

    public List<Encaminhamento> listarReceber(AreaAdministrativa administrativa) throws Exception {
        TypedQuery<Encaminhamento> q;
        q = getEm().createQuery("SELECT en FROM Encaminhamento en WHERE en.dataRecebimento IS NULL AND en.destino = :administrativa", Encaminhamento.class);
        q.setParameter("administrativa", administrativa);

        return q.getResultList();
    }

    public List<Encaminhamento> listarReceber(AreaAdministrativa administrativa, List<UnidadeOrcamentaria> unidadesOrcamentarias) throws Exception {
        if (unidadesOrcamentarias.isEmpty()) {
            return new ArrayList<>();
        } else {
            Query q = getEm().createQuery("SELECT en FROM Encaminhamento en WHERE en.dataRecebimento IS NULL AND en.destino = :administrativa AND en.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:unidades) ", Encaminhamento.class);
            q.setParameter("administrativa", administrativa);
            q.setParameter("unidades", unidadesOrcamentarias);
            return q.getResultList();
        }
    }

    public List<Encaminhamento> listarReceber(AreaAdministrativa administrativa, List<UnidadeOrcamentaria> unidadesOrcamentarias, SituacaoSolicitacao situacao) throws Exception {
        if (unidadesOrcamentarias.isEmpty()) {
            return new ArrayList<>();
        } else {
            String sql = "SELECT en FROM Encaminhamento en WHERE en.dataRecebimento IS NULL AND en.destino = :administrativa AND en.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:unidades)";
            if (situacao != null) {
                sql += " AND en.solicitacaoFinanceira.situacaoSolicitacao =:situacao ";
            }
            Query q = getEm().createQuery(sql, Encaminhamento.class);
            q.setParameter("administrativa", administrativa);
            if (situacao != null) {
                q.setParameter("situacao", situacao);
            }
            q.setParameter("unidades", unidadesOrcamentarias);
            return q.getResultList();
        }
    }

    public List<Encaminhamento> listarReceber(AreaAdministrativa administrativa, List<UnidadeOrcamentaria> unidadesOrcamentarias, UnidadeOrcamentaria orcamentaria, SituacaoSolicitacao situacao) throws Exception {
        if (unidadesOrcamentarias.isEmpty()) {
            return new ArrayList<>();
        } else {

            String sql = "SELECT en FROM Encaminhamento en WHERE en.dataRecebimento IS NULL AND en.destino = :administrativa AND en.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:unidades) AND en.solicitacaoFinanceira.cota.unidadeOrcamentaria =  :orcamentaria";
            if (situacao != null) {
                sql += " AND en.solicitacaoFinanceira.situacaoSolicitacao =:situacao ";
            }

            Query q = getEm().createQuery(sql, Encaminhamento.class);
            if (situacao != null) {
                q.setParameter("situacao", situacao);
            }
            q.setParameter("administrativa", administrativa);
            q.setParameter("unidades", unidadesOrcamentarias);
            q.setParameter("orcamentaria", orcamentaria);
            return q.getResultList();
        }
    }

    public Encaminhamento buscarUltimoEncaminhamento(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        Query q = getEm().createQuery("SELECT MAX(e) FROM Encaminhamento e WHERE e.solicitacaoFinanceira =:sol");
        q.setParameter("sol", solicitacaoFinanceira);
        return (Encaminhamento) q.getSingleResult();
    }

    public Encaminhamento buscarUltimoEncaminhamentoSetor(SolicitacaoFinanceira solicitacaoFinanceira, AreaAdministrativa area) throws Exception {
        Query q = getEm().createQuery("SELECT MAX(e) FROM Encaminhamento e WHERE e.solicitacaoFinanceira =:sol AND e.usuarioEncaminhou.areaAdministrativa=:origem ");
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("origem", area);
        return (Encaminhamento) q.getSingleResult();
    }

    //Busca encaminhameto em aberto para tesouraria
    public Encaminhamento buscarUltimoEncaminhamentoAbertoTesouraria(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        Query q = getEm().createQuery("SELECT MAX(e) FROM Encaminhamento e WHERE e.solicitacaoFinanceira =:sol AND e.destino.tipoAreaAdm  = :tp AND e.dataRecebimento IS NULL  and e.usuarioRecebeu IS NULL");
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("tp", TipoAreaAdm.Tesouraria);

        return (Encaminhamento) q.getSingleResult();
    }
    
    public Encaminhamento buscarUltimoEncaminhamentoTesouraria(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        Query q = getEm().createQuery("SELECT MAX(e) FROM Encaminhamento e WHERE e.solicitacaoFinanceira =:sol AND e.destino.tipoAreaAdm  = :tp");
        q.setParameter("sol", solicitacaoFinanceira);
        q.setParameter("tp", TipoAreaAdm.Tesouraria);

        return (Encaminhamento) q.getSingleResult();
    }

    public List<Encaminhamento> listarPorLocal(AreaAdministrativa areaAdministrativa) throws Exception {
        Query q = getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.destino =:dest AND e.solicitacaoFinanceira.local =:dest ");
        q.setParameter("dest", areaAdministrativa);
        return q.getResultList();
    }

    public List<Encaminhamento> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria, AreaAdministrativa areaAdministrativa) throws Exception {
        Query q = getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.destino =:dest AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria=:uo AND e.solicitacaoFinanceira.local =:dest ");
        q.setParameter("dest", areaAdministrativa);
        q.setParameter("uo", unidadeOrcamentaria);
        return q.getResultList();
    }

    public List<Encaminhamento> listarPorUnidadeOrcamentaria(List<UnidadeOrcamentaria> unidadesOrcamentarias, AreaAdministrativa areaAdministrativa) throws Exception {
        Query q = getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.destino =:dest AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uo) AND e.solicitacaoFinanceira.local =:dest ");
        q.setParameter("dest", areaAdministrativa);
        q.setParameter("uo", unidadesOrcamentarias);
        return q.getResultList();
    }

    public List<Encaminhamento> listarPorLocal(AreaAdministrativa areaAdministrativa, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        Query q = getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.destino =:dest AND e.solicitacaoFinanceira.situacaoSolicitacao =:situacao AND e.solicitacaoFinanceira.local=:dest  ");
        q.setParameter("dest", areaAdministrativa);
        q.setParameter("situacao", situacaoSolicitacao);
        return q.getResultList();
    }

    public List<Encaminhamento> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria, AreaAdministrativa areaAdministrativa, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        Query q = getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.destino =:dest AND e.solicitacaoFinanceira.situacaoSolicitacao =:situacao AND e.solicitacaoFinanceira.local=:dest AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria=:uo ");
        q.setParameter("dest", areaAdministrativa);
        q.setParameter("situacao", situacaoSolicitacao);
        q.setParameter("uo", unidadeOrcamentaria);
        return q.getResultList();
    }

    public List<Encaminhamento> listarPorUnidadeOrcamentaria(List<UnidadeOrcamentaria> unidadesOrcamentarias, AreaAdministrativa areaAdministrativa, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        Query q = getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.destino =:dest AND e.solicitacaoFinanceira.situacaoSolicitacao =:situacao AND e.solicitacaoFinanceira.local=:dest AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uo) ");
        q.setParameter("dest", areaAdministrativa);
        q.setParameter("situacao", situacaoSolicitacao);
        q.setParameter("uo", unidadesOrcamentarias);
        return q.getResultList();
    }

    public List<Encaminhamento> buscarPorSoolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        Query q = getEm().createQuery("SELECT e FROM Encaminhamento e WHERE e.solicitacaoFinanceira =:sol  ORDER BY e.id ");
        q.setParameter("sol", solicitacaoFinanceira);
        return q.getResultList();
    }

    public Long contarNRecebidos(AreaAdministrativa areaAdministrativa, List<UnidadeOrcamentaria> unidadesUsario) throws Exception {
        return getEm().createQuery("SELECT COUNT(e) FROM Encaminhamento e WHERE e.destino=:destino AND e.dataRecebimento IS NULL AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN(:unidades) ", Long.class).setParameter("unidades", unidadesUsario).setParameter("destino", areaAdministrativa).getSingleResult();
    }

    public Long contarEncaminhados(Usuario usuario) throws Exception {
        return getEm().createQuery("SELECT COUNT(e) FROM Encaminhamento e WHERE e.usuarioEncaminhou=:usuario AND e.dataRecebimento IS NULL ", Long.class).setParameter("usuario", usuario).getSingleResult();
    }

    /**
     * Gerar Relatorio com os encaminhamento de Um setor. Mostrar os Ultimos
     * encaminhamentos feitos por um determinado setor distinguindo por
     * solicitação financeira
     *
     * @param localizacao
     * @param dataInicio
     * @param dataFinal
     * @param destino
     * @param credor
     * @return
     * @throws java.lang.Exception
     */
    public List<Encaminhamento> listaEncaminhamentosDataDestino(AreaAdministrativa localizacao, Date dataInicio, Date dataFinal, AreaAdministrativa destino, Credor credor) throws Exception {
        String sqlQuery = "SELECT DISTINCT e.solicitacaoFinanceira FROM Encaminhamento e WHERE e.dataEncaminhamento BETWEEN :datai AND :dataf AND e.usuarioEncaminhou.areaAdministrativa =:localizacao ";
        if (destino != null) {
            sqlQuery += " AND e.destino =:destino";
        }
        if (credor != null) {
            sqlQuery += " AND e.solicitacaoFinanceira.credor =:credor ";
        }
        Query q = getEm().createQuery(sqlQuery);
        q.setParameter("datai", dataInicio);
        q.setParameter("dataf", dataFinal);
        q.setParameter("localizacao", localizacao);
        if (destino != null) {
            q.setParameter("destino", destino);
        }
        if (credor != null) {
            q.setParameter("credor", credor);
        }
        List<SolicitacaoFinanceira> sols = q.getResultList();
        List<Encaminhamento> encs = new ArrayList<>();
        for (SolicitacaoFinanceira sol : sols) {
            Encaminhamento enc = buscarUltimoEncaminhamentoSetor(sol, localizacao);
            if (enc != null) {
                encs.add(enc);
            }
        }
        return encs;
    }

    public List<Encaminhamento> listaEncaminhamentosNRecebidos(List<UnidadeOrcamentaria> unidades, UnidadeOrcamentaria unidade, AreaAdministrativa area, SituacaoSolicitacao situacao) throws Exception {
        String sql = "SELECT e FROM Encaminhamento e WHERE e.usuarioRecebeu IS NULL AND e.dataRecebimento IS NULL AND e.solicitacaoFinanceira.local =:area ";
        if (situacao != null) {
            sql += " AND e.solicitacaoFinanceira.situacaoSolicitacao =:situacao ";
        }
        if (unidades != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) ";
        }
        if (unidade != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria =:uo ";
        }
        sql += " ORDER BY e.solicitacaoFinanceira.id ";

        Query q = getEm().createQuery(sql);
        q.setParameter("area", area);
        if (situacao != null) {
            q.setParameter("situacao", situacao);
        }
        if (unidade != null) {
            q.setParameter("uo", unidade);
        }
        if (unidades != null) {
            q.setParameter("uos", unidades);
        }
        return q.getResultList();

    }

    public Long contarEncaminhamentosNRecebidos(List<UnidadeOrcamentaria> unidades, UnidadeOrcamentaria unidade, AreaAdministrativa area, SituacaoSolicitacao situacao) throws Exception {
        String sql = "SELECT COUNT(e) FROM Encaminhamento e WHERE e.usuarioRecebeu IS NULL AND e.dataRecebimento IS NULL AND e.solicitacaoFinanceira.local =:area ";
        if (situacao != null) {
            sql += " AND e.solicitacaoFinanceira.situacaoSolicitacao =:situacao ";
        }
        if (unidades != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) ";
        }
        if (unidade != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria =:uo ";
        }

        Query q = getEm().createQuery(sql, Long.class);
        q.setParameter("area", area);
        if (situacao != null) {
            q.setParameter("situacao", situacao);
        }
        if (unidade != null) {
            q.setParameter("uo", unidade);
        }
        if (unidades != null) {
            q.setParameter("uos", unidades);
        }
        return (Long) q.getSingleResult();

    }
}
