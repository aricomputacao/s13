/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.UnidadeMedida;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SIAFI - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorUnidadeMedidaDao")
public class UnidadeMedidaDAO extends GestorDAO<UnidadeMedida> implements Serializable {

    public UnidadeMedidaDAO() {
        super(UnidadeMedida.class);
    }
}
