/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.MovimentacaoBanco;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

/**
 *
 * @author ari
 */
@Stateless
public class MovimentacaoBancoDAO extends DAO<MovimentacaoBanco, Long> implements Serializable {

    public MovimentacaoBancoDAO() {
        super(MovimentacaoBanco.class);
    }

    public List<MovimentacaoBanco> listarMovimentacaoBancos() {
        return getEm().createQuery("SELECT c FROM MovimentacaoBanco c").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public MovimentacaoBanco carregarMovimentacaoBancos(Long l) {
        Query q = getEm().createQuery("SELECT c FROM MovimentacaoBanco c WHERE c.id = :id")
                .setParameter("id", l);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (MovimentacaoBanco) q.getResultList().get(0);
        }

    }

    public List<MovimentacaoBanco> listaPorData(Date dataIni, Date dataFim) {
        return getEm().createQuery("SELECT c FROM MovimentacaoBanco c WHERE c.data BETWEEN :dtIni and :dtFim")
                .setParameter("dtIni", dataIni)
                .setParameter("dtFim", dataFim)
                .getResultList();
    }

}
