/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.UnidadeMedidaDAO;
import br.com.siafi.modelo.UnidadeMedida;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SIAFI - Criado em 27/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class UnidadeMedidaController extends Controller<UnidadeMedida, Integer> implements Serializable {

    @EJB
    private UnidadeMedidaDAO dao;
    @EJB
    private br.com.gestor.dao.UnidadeMedidaDAO gestorUnidadeMedidaDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.UnidadeMedida> unidadeMediadeGestor = gestorUnidadeMedidaDao.listarImportacao();
        for (br.com.gestor.modelo.UnidadeMedida u : unidadeMediadeGestor) {
            System.out.println(u.toString());
            UnidadeMedida unidade = new UnidadeMedida();
            unidade.setAbreviacao(u.getAbreviacao());
            unidade.setId(u.getId());
            unidade.setNome(u.getNome());
            dao.atualizar(unidade);
        }
        gestorUnidadeMedidaDao.marcarImportados("UNM");

    }

}
