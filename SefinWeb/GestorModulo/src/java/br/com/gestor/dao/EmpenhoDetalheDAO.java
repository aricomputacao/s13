/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.EmpenhoDetalhe;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * @Sistema GestorModulo
 * @Data 24/07/2013
 * @author gilmario
 */
@Stateless(name = "GestorEmpenhoDetalheDao")
public class EmpenhoDetalheDAO extends GestorDAO<EmpenhoDetalhe> implements Serializable {

    public EmpenhoDetalheDAO() {
        super(EmpenhoDetalhe.class);
    }

    public List<EmpenhoDetalhe> listarDetalhesPagamento(String empenho) {
        return getEm().createQuery("SELECT e FROM EmpenhoDetalhe e WHERE e.numeroEmpenho =:empenho").setParameter("empenho", empenho).getResultList();
    }

    public List<EmpenhoDetalhe> listarDetalhesPagamentoAutorizado(String empenho) {
        return getEm().createQuery("SELECT e FROM EmpenhoDetalhe e WHERE e.numeroEmpenho =:empenho AND e.dataAutorizacao IS NOT NULL ").setParameter("empenho", empenho).getResultList();
    }

    public List<EmpenhoDetalhe> listarDetalhesPagamentoPendente(String empenho) {
        return getEm().createQuery("SELECT e FROM EmpenhoDetalhe e WHERE e.numeroEmpenho =:empenho AND e.dataAutorizacao IS NULL ").setParameter("empenho", empenho).getResultList();
    }
}
