/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.CreditoAdicionalDetalhe;
import br.com.siafi.modelo.CreditoAdicionalDetalhePk;
import br.com.siafi.modelo.Dotacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author gilmario
 */
@Stateless
public class CreditoAdicionalDetalheDAO extends DAO<CreditoAdicionalDetalhe, CreditoAdicionalDetalhePk> implements Serializable {

    public CreditoAdicionalDetalheDAO() {
        super(CreditoAdicionalDetalhe.class);
    }

    public List<CreditoAdicionalDetalhe> listaCreditoAdicioalDetalhe(Dotacao dotacao) throws Exception {
        TypedQuery<CreditoAdicionalDetalhe> q;
        q = getEm().createQuery("SELECT c from CreditoAdicionalDetalhe c WHERE c.dotacao = :d", CreditoAdicionalDetalhe.class);
        q.setParameter("d", dotacao);
        return q.getResultList();
    }

    public BigDecimal totalAdicionado(Dotacao dotacao) throws Exception {
        BigDecimal valor = getEm().createQuery("SELECT SUM(c.valor) from CreditoAdicionalDetalhe c WHERE c.dotacao = :d AND c.tipo <>'A' AND c.tipo IS NOT NULL ", BigDecimal.class).setParameter("d", dotacao).getSingleResult();
        if (valor != null) {
            return valor;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal totalAnulado(Dotacao dotacao) throws Exception {
        BigDecimal valor = getEm().createQuery("SELECT SUM(c.valor) from CreditoAdicionalDetalhe c WHERE c.dotacao = :d AND c.tipo = 'A' ", BigDecimal.class).setParameter("d", dotacao).getSingleResult();
        if (valor != null) {
            return valor;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public List<CreditoAdicionalDetalhe> listar(Dotacao dotacao) throws Exception {
        return getEm().createQuery("SELECT c FROM CreditoAdicionalDetalhe c WHERE c.dotacao =:d").setParameter("d", dotacao).getResultList();
    }

}
