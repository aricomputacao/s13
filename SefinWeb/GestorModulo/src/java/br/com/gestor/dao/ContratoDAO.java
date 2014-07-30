/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.Contrato;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto ******* - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorContratoDao")
public class ContratoDAO extends GestorDAO<Contrato> implements Serializable {

    public ContratoDAO() {
        super(Contrato.class);
    }
}
