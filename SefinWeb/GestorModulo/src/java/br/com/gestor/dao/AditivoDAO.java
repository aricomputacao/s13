/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.Aditivo;
import java.io.Serializable;
import javax.ejb.Stateless;

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
}
