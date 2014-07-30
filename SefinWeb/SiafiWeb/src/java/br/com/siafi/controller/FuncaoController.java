/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.FuncaoDAO;
import br.com.siafi.modelo.Funcao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class FuncaoController extends Controller<Funcao, String> implements Serializable {

    @EJB
    private FuncaoDAO dao;
    @EJB
    private br.com.gestor.dao.FuncaoDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Funcao t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Funcao> lista = gestorDao.listarTodos();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.Funcao funcao : lista) {
            System.out.println(funcao.toString());
            Funcao f = new Funcao();
            f.setId(funcao.getId());
            f.setNome(funcao.getNome());
            f.setUso(funcao.getUso());
            dao.atualizar(f);
        }
    }

}
