/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.CredorUnidadeOrcamentariaDAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.CredorUnidadeOrcamentaria;
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
public class CredorUnidadeOrcamentariaController extends Controller<CredorUnidadeOrcamentaria, Long> implements Serializable {

    @EJB
    private CredorUnidadeOrcamentariaDAO dao;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void salvarCredorUnidade(CredorUnidadeOrcamentaria cuo, UnidadeOrcamentaria orcamentaria, List<Credor> list) throws Exception {
        if (cuo.getId() == null) {
            cuo.setUnidadeOrcamentaria(orcamentaria);
        }
        for (Credor credor : list) {
            if (!cuo.getCredores().contains(credor)) {
                cuo.getCredores().add(credor);
            }
        }
        salvarouAtualizar(cuo);
    }

    public List<CredorUnidadeOrcamentaria> credorUnidadeOrcamentarias(List<UnidadeOrcamentaria> uos) {
        return dao.credorUnidadeOrcamentarias(uos);
    }

    public CredorUnidadeOrcamentaria buscaCredorUnidadeOrcamentaria(UnidadeOrcamentaria uo) {
        return dao.buscaCredorUnidadeOrcamentaria(uo);
    }

}
