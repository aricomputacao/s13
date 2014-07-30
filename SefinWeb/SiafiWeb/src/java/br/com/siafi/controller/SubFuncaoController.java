/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.modelo.SubFuncao;
import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.SubFuncaoDAO;
import br.com.siafi.modelo.Subfuncao;
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
public class SubFuncaoController extends Controller<Subfuncao, String> implements Serializable {

    @EJB
    private SubFuncaoDAO dao;
    @EJB
    private br.com.gestor.dao.SubFuncaoDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Subfuncao t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<SubFuncao> lista = gestorDao.listarTodos();
        System.out.println(lista.size());
        for (SubFuncao subFuncao : lista) {
            System.err.println(subFuncao.toString());
            Subfuncao f = new Subfuncao();
            f.setId(subFuncao.getId());
            f.setNome(subFuncao.getNome());
            f.setUso(subFuncao.getUso());
            dao.atualizar(f);
        }
    }

}
