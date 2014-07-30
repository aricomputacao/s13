/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.LicitacaoUnidadeOrcamentaria;
import br.com.siafi.modelo.LicitacaoUnidadeOrcamentariaPk;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class LicitacaoUnidadeOrcamentariaDAO extends DAO<LicitacaoUnidadeOrcamentaria, LicitacaoUnidadeOrcamentariaPk> implements Serializable {

    public LicitacaoUnidadeOrcamentariaDAO() {
        super(LicitacaoUnidadeOrcamentaria.class);
    }

    public List<LicitacaoUnidadeOrcamentaria> listarLicitacaoUnidadeOrcamentaria() throws Exception {
        TypedQuery<LicitacaoUnidadeOrcamentaria> q = getEm().createQuery("SELECT d FROM LicitacaoUnidadeOrcamentaria d ", LicitacaoUnidadeOrcamentaria.class);

        return q.getResultList();
    }

    public List<LicitacaoUnidadeOrcamentaria> listarLicitacaoUnidadeOrcamentaria(UnidadeOrcamentaria o, Credor c) throws Exception {
        Query q = getEm().createQuery("SELECT DISTINCT l.licitacao FROM LicitacaoUnidadeOrcamentaria l WHERE l.unidadeOrcamentaria = :o AND "
                + "l.licitacao IN ( SELECT i.licitacao FROM ItemLicitacao i WHERE i.credor=:credor) AND l.licitacao NOT IN ( SELECT s.contrato.licitacao FROM SolicitacaoFinanceira s WHERE s.contrato IS NOT NULL AND s.situacaoSolicitacao<>'Negado' AND s.situacaoSolicitacao <> 'Cancelado'  )", LicitacaoUnidadeOrcamentaria.class);
        q.setParameter("o", o);
        q.setParameter("c", c);
        return q.getResultList();
    }

}
