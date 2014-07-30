/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.ContaDAO;
import br.com.siafi.dao.MovimentacaoBancoDAO;
import br.com.siafi.modelo.Conta;
import br.com.siafi.modelo.MovimentacaoBanco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class MovimentacaoBancoController extends Controller<MovimentacaoBanco, Long> implements Serializable {

    @EJB
    private MovimentacaoBancoDAO dao;
    @EJB
    private br.com.gestor.dao.MovimentacaoBancoDAO gestorDAO;
    @EJB
    private ContaDAO contaDAO;

    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<MovimentacaoBanco> listarMovimentacaoBancos() {
        return dao.listarMovimentacaoBancos();
    }

    public void importar() throws Exception {
        List<MovimentacaoBanco> movBancosSiafi = dao.listarMovimentacaoBancos();
        List<br.com.gestor.modelo.MovimentacaoBanco> movBancoGestor = gestorDAO.listaImportar();

        for (br.com.gestor.modelo.MovimentacaoBanco mbG : movBancoGestor) {
            MovimentacaoBanco mb = dao.carregarMovimentacaoBancos(Long.valueOf(mbG.getMbacod()));
            if (mb == null) {
                mb = new MovimentacaoBanco();
                mb.setId(Long.valueOf(mbG.getMbacod()));
                mb.setContaDestino(contaDAO.gerenciar(mbG.getContaCredito()));
                mb.setContaOrigem(contaDAO.gerenciar(mbG.getContaDebito()));
                mb.setData(mbG.getMbadatalanca());
                mb.setHistorico(mbG.getMbahistorico());
                mb.setOficio(mbG.getMbanumdocumento());
                mb.setValor(mbG.getMbavalor());
                dao.salvar(mb);
            }

        }
    }

    public List<MovimentacaoBanco> listarProData(Date dataIni, Date dataFim) {
        return dao.listaPorData(dataIni, dataFim);
    }

}
