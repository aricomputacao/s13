/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.StatusOrdemCompra;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.OrdemCompra;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author ari
 */
@Stateless
public class OrdemCompraDAO extends DAO<OrdemCompra, Long> implements Serializable {

    public OrdemCompraDAO() {
        super(OrdemCompra.class);
    }

    public List<OrdemCompra> listar(UnidadeOrcamentaria uo) throws Exception {
        return getEm().createQuery("SELECT a FROM OrdemCompra a WHERE a.unidadeOrcamentaria = :uo ORDER BY a.id ").setParameter("uo", uo).getResultList();
    }

    public List<OrdemCompra> listarOrdemSolicitacao(UnidadeOrcamentaria uo, Credor c) throws Exception {
        Query q = getEm().createQuery("SELECT o FROM OrdemCompra o WHERE o.unidadeOrcamentaria = :uo AND o.credor =:credor AND o.status = :status ");
        q.setParameter("uo", uo);
        q.setParameter("credor", c);
        q.setParameter("status", StatusOrdemCompra.Pendente);
        return q.getResultList();
    }

    public List<OrdemCompra> listarOrdemSolicitacao(UnidadeOrcamentaria uo) throws Exception {
        Query q = getEm().createQuery("SELECT o FROM OrdemCompra o WHERE o.unidadeOrcamentaria = :uo AND o.status = :status ");
        q.setParameter("uo", uo);
        q.setParameter("status", StatusOrdemCompra.Pendente);
        return q.getResultList();
    }

    public List<OrdemCompra> listar(UnidadeOrcamentaria uo, Date dataInicial, Date dataFinal, StatusOrdemCompra status) throws Exception {
        String sql = "SELECT a FROM OrdemCompra a WHERE a.unidadeOrcamentaria = :uo";
        if (dataInicial != null && dataFinal != null) {
            sql += " AND a.dataEmissao BETWEEN :datai AND :dataf ";
        }
        if (status != null) {
            sql += " AND a.status = :status ";
        }
        sql += " ORDER BY a.id";
        Query q = getEm().createQuery(sql);
        q.setParameter("uo", uo);
        if (dataInicial != null && dataFinal != null) {
            q.setParameter("datai", dataInicial).setParameter("dataf", dataFinal);
        }
        if (status != null) {
            q.setParameter("status", status);
        }
        return q.getResultList();
    }

    public List<OrdemCompra> listar(Contrato c) {
        return getEm().createQuery("SELECT o FROM OrdemCompra o WHERE o.contrato =:c ").setParameter("c", c).getResultList();
    }
}
