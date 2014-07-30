/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.UnidadeOrcamentariaPK;
import br.com.siafi.dao.LicitacaoUnidadeOrcamentariaDAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.LicitacaoUnidadeOrcamentaria;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class LicitacaoUnidadeOrcamentariaController extends Controller<LicitacaoUnidadeOrcamentaria, UnidadeOrcamentariaPK> implements Serializable {

    @EJB
    private LicitacaoUnidadeOrcamentariaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<LicitacaoUnidadeOrcamentaria> listarLicitacaoUnidadeOrcamentaria(UnidadeOrcamentaria orcamentaria, Credor c) throws Exception {
        return dao.listarLicitacaoUnidadeOrcamentaria(orcamentaria, c);
    }
}
