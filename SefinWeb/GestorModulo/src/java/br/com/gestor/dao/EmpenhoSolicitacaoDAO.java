/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.EmpenhoSolicitacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * @Sistema GestorModulo
 * @Data 17/07/2013
 * @author gilmario
 */
@Stateless(name = "GestorEmpenhoSolicitacaoDao")
public class EmpenhoSolicitacaoDAO extends GestorDAO<EmpenhoSolicitacao> implements Serializable {

    public EmpenhoSolicitacaoDAO() {
        super(EmpenhoSolicitacao.class);
    }

    public void salvar(EmpenhoSolicitacao empenhoSolicitacao) {
        getEm().merge(empenhoSolicitacao);
    }

    public String buscarEmpenho(String codigoSolicitacao) {
        TypedQuery<String> q;
        q = getEm().createQuery("SELECT e.numeroEmpenho FROM EmpenhoSolicitacao e WHERE e.numeroSolicitacao =:num", String.class);
        q.setParameter("num", codigoSolicitacao);

        if (!q.getResultList().isEmpty()) {
            return q.getSingleResult();
        } else {
            return null;
        }

    }

    public String buscarModalidadeEmpenho(String codigoSolicitacao) {
        TypedQuery<String> q;
        q = getEm().createQuery("SELECT e.modalidade FROM EmpenhoSolicitacao e WHERE e.numeroSolicitacao =:num", String.class);
        q.setParameter("num", codigoSolicitacao);

        if (!q.getResultList().isEmpty()) {
            return q.getSingleResult();
        } else {
            return null;
        }

    }

    public EmpenhoSolicitacao buscarEmpenhoAut(String codigoSolicitacao, String empenho) {
        return getEm().createQuery("SELECT e FROM EmpenhoSolicitacao e WHERE e.numeroSolicitacao =:num AND e.numeroEmpenho=:numero ", EmpenhoSolicitacao.class)
                .setParameter("numero", empenho)
                .setParameter("num", codigoSolicitacao).getSingleResult();
    }

    //Utilizado para pegar o registro na feempaut para excluir no cancelamento do empenho
    public EmpenhoSolicitacao buscarEmpenhoAut(String codigoSolicitacao) {
        TypedQuery<EmpenhoSolicitacao> q;

        q = getEm().createQuery("SELECT e FROM EmpenhoSolicitacao e WHERE e.numeroSolicitacao =:num  ", EmpenhoSolicitacao.class);
        q.setParameter("num", codigoSolicitacao);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return q.getSingleResult();

        }
    }

    public Date buscarDataEmpenho(String codigoSolicitacao, String empenho) {
        return getEm().createQuery("SELECT e.dataEmpenho FROM EmpenhoSolicitacao e WHERE e.numeroSolicitacao =:num AND e.numeroEmpenho=:numero ", Date.class)
                .setParameter("numero", empenho)
                .setParameter("num", codigoSolicitacao).getSingleResult();
    }

    public void removerEmpAut(EmpenhoSolicitacao es) {
        getEm().remove(es);
    }

    public BigDecimal saldoDotacao(Date dataEmpenho, String numeroEmpenho, String dotReduzido) {
        System.out.println("SELECT TOTAL FROM SP_SALDO_DOTACAO_EMPENHO (" + new SimpleDateFormat("YYYY-MM-dd").format(dataEmpenho) + "," + Integer.parseInt(dotReduzido) + ",'2014-12-31','0','" + numeroEmpenho + "',0)");
        return new BigDecimal((Double) getEm().createNativeQuery(" SELECT TOTAL FROM SP_SALDO_DOTACAO_EMPENHO ('" + new SimpleDateFormat("YYYY-MM-dd").format(dataEmpenho) + "'," + Integer.parseInt(dotReduzido) + ",'2014-12-31','0','" + numeroEmpenho + "',0) ").getSingleResult());
//        return (BigDecimal) getEm().createNativeQuery(" SELECT TOTAL FROM SP_SALDO_DOTACAO_EMPENHO '2014-07-01',?,'2014-12-31','0',?,0 ").setParameter(1, dataEmpenho).setParameter(2, dotReduzido).setParameter(3, numeroEmpenho).getSingleResult();
    }
}
