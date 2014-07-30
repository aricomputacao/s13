/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.SubElementoDespesaDAO;
import br.com.siafi.modelo.SubElementoDespesa;
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
public class SubElementoDespesaController extends Controller<SubElementoDespesa, String> implements Serializable {

    @EJB
    private SubElementoDespesaDAO dao;
    @EJB
    private br.com.gestor.dao.SubElementoDespesaDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(SubElementoDespesa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.SubElementoDespesa> lista = gestorDao.listarTodos();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.SubElementoDespesa subElementoDespesa : lista) {
            System.out.println(subElementoDespesa.toString());
            SubElementoDespesa s = new SubElementoDespesa();
            s.setId(subElementoDespesa.getId());
            s.setNome(subElementoDespesa.getNome());
            s.setEspecificacao(subElementoDespesa.getEspecificacao());
            dao.atualizar(s);
        }
    }

}
