/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.Programa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 - Dao do modelo Programa
 *
 * @author Gilmário
 */
@Stateless
public class ProgramaDAO extends DAO<Programa, String> implements Serializable {

    public ProgramaDAO() {
        super(Programa.class);
    }
}
