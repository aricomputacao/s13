/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.OrdenadorUnidadeOrcamentaria;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author gilmario
 */
@Stateless(name = "gestorOrdenadorUnidadeOrcamentariaDao")
public class OrdenadorUnidadeOrcamentariaDAO extends GestorDAO<OrdenadorUnidadeOrcamentaria> implements Serializable {

    public OrdenadorUnidadeOrcamentariaDAO() {
        super(OrdenadorUnidadeOrcamentaria.class);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public String getOrdenador(String idOrgao, String idUnidade) {
        String ordenador = null;
        try {
            ordenador = getEm().createQuery("SELECT s.nome FROM OrdenadorUnidadeOrcamentaria s WHERE s.id.idorgao =:idorgao AND s.id.idunidade =:unidade AND s.dataFinal IS NULL ", String.class).setParameter("unidade", idUnidade).setParameter("idorgao", idOrgao).getSingleResult();
        } catch (Exception e) {

        }
        return ordenador;
    }
}
