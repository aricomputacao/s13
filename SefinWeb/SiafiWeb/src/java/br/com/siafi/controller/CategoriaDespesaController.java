/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.CategoriaDespesaDAO;
import br.com.siafi.modelo.CategoriaDespesa;
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
public class CategoriaDespesaController extends Controller<CategoriaDespesa, String> implements Serializable {

    @EJB
    private CategoriaDespesaDAO dao;
    @EJB
    private br.com.gestor.dao.CategoriaDespesaDAO gestorCategoriaDespesaDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(CategoriaDespesa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.CategoriaDespesa> lista = gestorCategoriaDespesaDao.listarTodos();
        for (br.com.gestor.modelo.CategoriaDespesa categoriaDespesa : lista) {
            System.out.println(categoriaDespesa.toString());
            CategoriaDespesa c = new CategoriaDespesa();
            c.setId(categoriaDespesa.getId());
            c.setNome(categoriaDespesa.getNome());
            c.setEspecificacao(categoriaDespesa.getEspecificacao());
            dao.atualizar(c);
        }
    }

}
