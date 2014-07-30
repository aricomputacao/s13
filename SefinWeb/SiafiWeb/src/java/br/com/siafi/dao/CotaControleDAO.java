/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.sefin.enumerated.Mes;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.CotaControle;
import br.com.guardiao.modelo.Exercicio;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

/**
 * @Sistema SiafiWeb
 * @Data 05/08/2013
 * @author gilmario
 */
@Stateless
public class CotaControleDAO extends DAO<CotaControle, Long> implements Serializable {

    public CotaControleDAO() {
        super(CotaControle.class);
    }

    // Correção : Utilizar o campo ano para poder funcionar corretamente
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CotaControle localizaCotaControleUnique(Mes competencia, Exercicio exercicio, Cota cota) throws Exception {
        Query q = getEm().createQuery("SELECT cco FROM CotaControle cco WHERE cco.competencia=:competencia AND cco.exercicio =:exercicio AND cco.cota = :cota ");
        q.setParameter("competencia", competencia);
        q.setParameter("exercicio", exercicio);
        q.setParameter("cota", cota);
        return (CotaControle) q.getSingleResult();
    }

    public CotaControle valorAtualCota(Cota cota, Mes competencia, Exercicio exercicio) throws Exception {
        Query q = getEm().createQuery("SELECT max(cco) FROM CotaControle cco WHERE (cco.competencia <= :competencia AND cco.exercicio =:exercicio AND cco.cota = :cota) OR (cco.competencia >= :competencia AND cco.exercicio < :exercicio AND cco.cota = :cota)");
        q.setParameter("competencia", competencia);
        q.setParameter("exercicio", exercicio);
        q.setParameter("cota", cota);
        return (CotaControle) q.getSingleResult();
    }
}
