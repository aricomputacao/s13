/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.dao.ExercicioDAO;
import br.com.siafi.dao.OrcamentoDAO;
import br.com.guardiao.modelo.Exercicio;
import br.com.siafi.modelo.Orcamento;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class OrcamentoController extends Controller<Orcamento, Exercicio> implements Serializable {

    @EJB
    private OrcamentoDAO dao;
    @EJB
    private ExercicioDAO exercicioDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Orcamento t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public Orcamento getOrcamentoVigente() throws Exception {
        return dao.orcamentoAtual(exercicioDao.buscarUniqueResult("padrao", true));
    }

}
