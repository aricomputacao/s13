/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.CreditoAdicionalDAO;
import br.com.siafi.modelo.CreditoAdicional;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class CreditoAdicionalController extends Controller<CreditoAdicional, Integer> implements Serializable {

    @EJB
    private CreditoAdicionalDAO dao;
    @EJB
    private br.com.gestor.dao.CreditoAdicionalDAO gestorDao;

    @Override
    public void salvarouAtualizar(CreditoAdicional t) throws Exception {
        dao.atualizar(t);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.CreditoAdicional> lista = gestorDao.listarImportacao();
        for (br.com.gestor.modelo.CreditoAdicional j : lista) {
            System.out.println(j.toString());
            CreditoAdicional i = new CreditoAdicional();
            i.setDataDecreto(j.getDataDecreto());
            i.setId(j.getCodigo());
            i.setLei(j.getLei());
            i.setNumeroDecreto(j.getNumeroDecreto());
            i.setStatus(j.getStatus());
            dao.atualizar(i);
        }
        gestorDao.marcarImportados("CRA");
    }

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }
}
