/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.StatusOrdemCompra;
import br.com.siafi.dao.ItemOrdemCompraDAO;
import br.com.siafi.dao.OrdemCompraDAO;
import br.com.siafi.modelo.Aditivo;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.ItemOrdemCompra;
import br.com.siafi.modelo.OrdemCompra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.codehaus.groovy.ast.ClassHelper;

/**
 *
 * @author ari
 */
@Stateless
public class OrdemCompraController extends Controller<OrdemCompra, Long> implements Serializable {

    @EJB
    private OrdemCompraDAO dao;
    @EJB
    private ItemOrdemCompraDAO itemOrdemCompraDAO;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraController;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<OrdemCompra> listarSolicitacao(UnidadeOrcamentaria uo, Credor c) throws Exception {
        if (c.getId() == null) {
            return dao.listarOrdemSolicitacao(uo);
        } else {
            return dao.listarOrdemSolicitacao(uo, c);
        }
    }

    // se a data inicila ou a data final for nula ele pesquisa apenas por unidade orçamentaria
    public List<OrdemCompra> listar(UnidadeOrcamentaria uo, Date dataInicial, Date dataFinal, StatusOrdemCompra status) throws Exception {
        return dao.listar(uo, dataInicial, dataFinal, status);
    }

    //Valida se o contrato tem saldo para realizar a ordem de compra
    public boolean checarSaldoContrato(Aditivo a, OrdemCompra o) throws Exception {
        BigDecimal saldoAditivo;
        if (o.getId() != null) {
            saldoAditivo = solicitacaoFinanceiraController.saldoAditivo(a, o);
        } else {
            saldoAditivo = solicitacaoFinanceiraController.saldoAditivo(a);
        }
        if (o.getValorTotal().compareTo(saldoAditivo) > 0) {
            MenssagemUtil.addMessageWarn("O Contrato não tem saldo!");
            return false;
        } else {
            return true;
        }
    }

    public void cancelarOrdemCompra(OrdemCompra compra) throws Exception {
        if (compra.getStatus().equals(StatusOrdemCompra.Aprovada)) {
            throw new Exception("A ordem de compra já está vinculada a um processo financeiro!");
        } else if (compra.getStatus().equals(StatusOrdemCompra.Cancelada)) {
            throw new Exception("A ordem de compra já está cancelada!");

        } else {
            compra.setStatus(StatusOrdemCompra.Cancelada);
            dao.atualizar(compra);
        }
    }

    @Override
    public void salvarouAtualizar(OrdemCompra t) throws Exception {
        if (t.getId() != null) {
            dao.atualizar(t);
        } else {
            dao.salvar(t);
        }
    }

    public void salvarouAtualizar(ItemOrdemCompra t) throws Exception {

        itemOrdemCompraDAO.atualizar(t);

    }

    public List<OrdemCompra> listar(Contrato c) {
        return dao.listar(c);
    }

}
