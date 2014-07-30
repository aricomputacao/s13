/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.TipoFonteRecursoDAO;
import br.com.siafi.modelo.TipoFonteRecurso;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 11/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class TipoFonteRecursoController extends Controller<TipoFonteRecurso, String> implements Serializable {

    @EJB
    private TipoFonteRecursoDAO dao;
    @EJB
    private br.com.gestor.dao.TipoFonteRecursoDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(TipoFonteRecurso t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.TipoFonteRecurso> lista = gestorDao.listarTodos();
        for (br.com.gestor.modelo.TipoFonteRecurso tipoFonteRecurso : lista) {
            System.out.println(tipoFonteRecurso);
            TipoFonteRecurso t = new TipoFonteRecurso();
            t.setId(tipoFonteRecurso.getId());
            t.setNome(tipoFonteRecurso.getNome());
            dao.atualizar(t);
        }
    }

}
