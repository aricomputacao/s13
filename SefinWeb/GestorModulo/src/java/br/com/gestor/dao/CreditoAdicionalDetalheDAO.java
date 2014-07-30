/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.CreditoAdicionalDetalhe;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/10/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorCreditoAdicionalDetalheDao")
public class CreditoAdicionalDetalheDAO extends GestorDAO<CreditoAdicionalDetalhe> implements Serializable {

    public CreditoAdicionalDetalheDAO() {
        super(CreditoAdicionalDetalhe.class);
    }

    public void corrigeImportacao() {
        getEm().createNativeQuery("UPDATE CREDITOADICIONALDETALHE SET CADEXPORTADO = 0 WHERE CADEXPORTADO = 2").executeUpdate();
    }
}
