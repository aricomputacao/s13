/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.ProgramaDAO;
import br.com.siafi.modelo.Programa;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class ProgramaController extends Controller<Programa, String> implements Serializable {

    @EJB
    private ProgramaDAO dao;
    @EJB
    private br.com.gestor.dao.ProgramaDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Programa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Programa> lista = gestorDao.listarTodos();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.Programa programa : lista) {
            System.out.println(programa.toString());
            Programa p = new Programa();
            p.setId(programa.getId());
            p.setNome(programa.getNome());
            dao.atualizar(p);
        }
    }

}
