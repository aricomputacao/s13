/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.ElementoDespesaDAO;
import br.com.siafi.modelo.ElementoDespesa;
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
public class ElementoDespesaController extends Controller<ElementoDespesa, String> implements Serializable {

    @EJB
    private ElementoDespesaDAO dao;
    @EJB
    private br.com.gestor.dao.ElementoDespesaDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(ElementoDespesa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.ElementoDespesa> lista = gestorDao.listarTodos();
        for (br.com.gestor.modelo.ElementoDespesa elementoDespesa : lista) {
            System.out.println(elementoDespesa.toString());
            ElementoDespesa e = new ElementoDespesa();
            e.setId(elementoDespesa.getId());
            e.setNome(elementoDespesa.getNome());
            e.setEspecificacao(elementoDespesa.getEspecificacao());
            dao.atualizar(e);
        }
    }

}
