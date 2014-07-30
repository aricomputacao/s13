/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.CreditoAdicional;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/10/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorCreditoAdicionalDao")
public class CreditoAdicionalDAO extends GestorDAO<CreditoAdicional> implements Serializable {

    public CreditoAdicionalDAO() {
        super(CreditoAdicional.class);
    }
}
