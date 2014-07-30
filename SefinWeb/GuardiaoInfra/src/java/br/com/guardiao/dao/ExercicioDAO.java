/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Exercicio;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 16/04/2013 - DAO do Modelo Exerccicio
 *
 * @author Gilmário
 */
@Stateless
public class ExercicioDAO extends DAO<Exercicio, Integer> implements Serializable {

    /**
     *
     */
    public ExercicioDAO() {
        super(Exercicio.class);
    }

    public void alterarExercicioPadrao() throws Exception {
        getEm().createQuery("update Exercicio set padrao=false").executeUpdate();
    }
}
