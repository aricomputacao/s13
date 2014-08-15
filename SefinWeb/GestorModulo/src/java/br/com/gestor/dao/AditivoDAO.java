/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.Aditivo;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Classe do Projeto Siafi - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorAditivoDao")
public class AditivoDAO extends GestorDAO<Aditivo> implements Serializable {

    public AditivoDAO() {
        super(Aditivo.class);
    }

    /**
     * Metodo repassado pela S&S Informatica para pegar o saldo atual dos
     * contratos
     *
     * @param id
     * @return
     */
    public BigDecimal calcularSaldoContratoEmpenhado(Integer id) {
        BigDecimal vl = BigDecimal.ZERO;
        Query q = getEm().createNativeQuery("  select (sum(a.adcvalor) -"
                + "        ((select sum(e.empvalor)   as vlEmpenhado from empenho e where e.empcontrato = a.adccontrato ) ))"
                + "                from aditivocontrato a where a.adccontrato = :con and a.adcmodalidade <> '3' group by a.adccontrato");
        q.setParameter("con", id);

        if (q.getResultList().isEmpty()) {
            return vl;
        } else {
            vl = (BigDecimal) q.getSingleResult();
            return vl == null ? BigDecimal.ZERO : vl;

        }

    }

    public BigDecimal calcularSaldoContratoEmpenhadoAnulado(Integer id) {
        BigDecimal vl = BigDecimal.ZERO;
        Query q = getEm().createNativeQuery("      select sum(ea.emavalor) from empenhoanulacao ea inner join empenho em on em.empcod = ea.emaempenho and em.empcontrato = :con");
        q.setParameter("con", id);

        if (q.getResultList().isEmpty()) {
            return vl;
        } else {
            vl = (BigDecimal) q.getSingleResult();
            return vl == null ? BigDecimal.ZERO : vl;

        }

    }
}
