/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.gestor.dao.EmpenhoDetalheDAO;
import br.com.gestor.dao.GestorDAO;
import br.com.siafi.controller.SolicitacaoLiquidacaoController;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class utilitarioMB implements Serializable {

    @EJB
    private SolicitacaoLiquidacaoController solicitacaoLiquidacaoController;
    @EJB
    private EmpenhoDetalheDAO dao;

    public void ajusteDataLiquidacao() {
        try {
            List<SolicitacaoLiquidacao> liquidacoes;
            liquidacoes = solicitacaoLiquidacaoController.listarTodos("id");
            for (SolicitacaoLiquidacao s : liquidacoes) {

            }
        } catch (Exception ex) {
            Logger.getLogger(utilitarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
