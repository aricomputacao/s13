/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.ExercicioDAO;
import br.com.guardiao.modelo.Exercicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 16/04/2013 - controler do modelo
 * Exercicio
 *
 * @author Gilmário
 */
@Stateless
public class ExercicioController extends Controller<Exercicio, Integer> implements Serializable {

    @EJB
    private ExercicioDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Exercicio t) throws Exception {
        if (t.isPadrao()) {
            dao.alterarExercicioPadrao();
        }
        if (t.getAno() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public boolean validaAno(Integer ano) {
        try {
            return dao.carregar(ano) == null;
        } catch (Exception ex) {
            // Significa que não encontrou
            return true;
        }
    }

    public Exercicio exercicioVigente() throws Exception {
        return dao.buscarUniqueResult("padrao", true);
    }
}
