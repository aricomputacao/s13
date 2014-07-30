/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.HistoricoPadraoDAO;
import br.com.siafi.modelo.HistoricoPadrao;
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
public class HistoricoPadraoController extends Controller<HistoricoPadrao, Long> implements Serializable {

    @EJB
    private HistoricoPadraoDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(HistoricoPadrao t) throws Exception {
        if (t.getId() != null) {
            dao.atualizar(t);
        } else {
            dao.salvar(t);
        }
    }

    public List<HistoricoPadrao> listar(UnidadeOrcamentaria uo, String nome) throws Exception {
        return dao.listar(uo, nome);
    }

}
