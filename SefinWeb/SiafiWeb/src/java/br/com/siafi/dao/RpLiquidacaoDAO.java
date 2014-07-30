/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.RpLiquidacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author gilmario
 */
@Stateless
public class RpLiquidacaoDAO extends DAO<RpLiquidacao, Integer> implements Serializable {

    public RpLiquidacaoDAO() {
        super(RpLiquidacao.class);
    }

    public List<RpLiquidacao> listarLiquidacaoLiberar() throws Exception {
        TypedQuery<RpLiquidacao> q;
        q = getEm().createQuery("SELECT l FROM RpLiquidacao l WHERE l.rpEmpenho IN (SELECT e FROM RpEmpenho e WHERE e.unidadeOrcamentariaAtual.orgao.id.id = :org) AND l.liberado = :lib", RpLiquidacao.class);
        q.setParameter("org", "06");
        q.setParameter("lib", Boolean.FALSE);
        return q.getResultList();
    }
}
