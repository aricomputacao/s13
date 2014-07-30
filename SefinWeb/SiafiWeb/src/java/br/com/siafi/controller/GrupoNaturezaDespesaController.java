/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.GrupoNaturezaDespesaDAO;
import br.com.siafi.modelo.GrupoNaturezaDespesa;
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
public class GrupoNaturezaDespesaController extends Controller<GrupoNaturezaDespesa, String> implements Serializable {

    @EJB
    private GrupoNaturezaDespesaDAO dao;
    @EJB
    private br.com.gestor.dao.GrupoNaturezaDespesaDAO gestoDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(GrupoNaturezaDespesa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.GrupoNaturezaDespesa> lista = gestoDao.listarTodos();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.GrupoNaturezaDespesa grupoNaturezaDespesa : lista) {
            System.out.println(grupoNaturezaDespesa.toString());
            GrupoNaturezaDespesa g = new GrupoNaturezaDespesa();
            g.setId(grupoNaturezaDespesa.getId());
            g.setNome(grupoNaturezaDespesa.getNome());
            g.setEspecificacao(grupoNaturezaDespesa.getEspecificacao());
            dao.atualizar(g);
        }

    }

}
