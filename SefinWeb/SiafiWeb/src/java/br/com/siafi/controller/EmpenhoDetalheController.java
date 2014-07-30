/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.dao.ContaDAO;
import br.com.siafi.dao.EmpenhoDetalheDAO;
import br.com.siafi.modelo.Conta;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class EmpenhoDetalheController extends Controller<EmpenhoDetalhe, Serializable> implements Serializable {

    @EJB
    private EmpenhoDetalheDAO dao;
    @EJB
    private br.com.gestor.dao.EmpenhoDetalheDAO gestorEmpenhoDetalheDao;
    @EJB
    private ContaDAO contaDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(EmpenhoDetalhe t) throws Exception {
        dao.atualizar(t);
    }

    private List<EmpenhoDetalhe> importarDetalhes(List<br.com.gestor.modelo.EmpenhoDetalhe> listaDetalhesGestor, SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        List<EmpenhoDetalhe> retorno = new ArrayList<>();
        for (br.com.gestor.modelo.EmpenhoDetalhe det : listaDetalhesGestor) {
            EmpenhoDetalhe empeDet = new EmpenhoDetalhe();
            if (det.getConta() != null) {
                if (!det.getConta().equals("")) {
                    Conta conta = contaDao.carregar(Integer.parseInt(det.getConta()));
                    empeDet.setConta(conta);
                }
            }
            empeDet.setDataAutorizacao(det.getDataAutorizacao());
            empeDet.setDataPagamento(det.getDataPagamento());
            empeDet.setDocumentoPagamento(det.getDocumentoPagamento());
            empeDet.setEmpenho(det.getNumeroEmpenho());
            empeDet.setId(det.getId());
            empeDet.setSolicitacaoFinanceira(solicitacaoFinanceira);
            empeDet.setValor(det.getValor());
            dao.atualizar(empeDet);
            retorno.add(empeDet);
        }
        return retorno;
    }

    public List<EmpenhoDetalhe> importarDetalhesPagamento(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        List<br.com.gestor.modelo.EmpenhoDetalhe> listaDetalhesGestor = gestorEmpenhoDetalheDao.listarDetalhesPagamento(solicitacaoFinanceira.getEmpenho());
        return importarDetalhes(listaDetalhesGestor, solicitacaoFinanceira);
    }

    /**
     * Importa somente os detalhes de pagamentos pendentes de uma solicitação
     * financeira
     *
     * @param solicitacaoFinanceira
     * @return
     * @throws Exception
     */
    public List<EmpenhoDetalhe> importarDetalhesPagamentoPendete(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        List<br.com.gestor.modelo.EmpenhoDetalhe> listaDetalhesGestor = gestorEmpenhoDetalheDao.listarDetalhesPagamentoPendente(solicitacaoFinanceira.getEmpenho());
        return importarDetalhes(listaDetalhesGestor, solicitacaoFinanceira);
    }

    /**
     * Importa detalhes de pagamentos pendentes de uma lista de solicitações
     * fianceiras
     *
     * @param listaSolicitacao
     * @return
     * @throws Exception
     */
    public List<EmpenhoDetalhe> importarListarEmpenhosDetalhesPendentes(List<SolicitacaoFinanceira> listaSolicitacao) throws Exception {
        List<EmpenhoDetalhe> retorno = new ArrayList<>();
        for (SolicitacaoFinanceira s : listaSolicitacao) {
            retorno.addAll(importarDetalhesPagamentoPendete(s));
        }
        return retorno;
    }

    public List<EmpenhoDetalhe> listarPorSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return dao.listarPorSolicitacao(solicitacaoFinanceira);
    }

    public void exportar(Integer id, Date dataAutorizado) throws Exception {
        br.com.gestor.modelo.EmpenhoDetalhe empenhoDet = gestorEmpenhoDetalheDao.carregar(id);
        empenhoDet.setDataAutorizacao(dataAutorizado);
        // empenhoDet.setConta(conta);
        gestorEmpenhoDetalheDao.atualizar(empenhoDet);
    }

    public boolean checarPagamentoLiberado(List<EmpenhoDetalhe> empenhoDetalhes) {
        boolean autorizado = !empenhoDetalhes.isEmpty();
        for (EmpenhoDetalhe e : empenhoDetalhes) {
            if (e.getDataPagamento() == null) {
                autorizado = false;
            }
        }
        return autorizado;
    }

    public boolean liquidacoesPagas(List<EmpenhoDetalhe> empenhoDetalhes) {
        for (EmpenhoDetalhe e : empenhoDetalhes) {
            if (e.getSolicitacaoFinanceira().getSituacaoSolicitacao() != SituacaoSolicitacao.Pago) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checa se as liquidações estão pagas
     *
     * @param empenhoDetalhes
     * @return
     */
    public boolean checaLiquidacoesPagas(List<EmpenhoDetalhe> empenhoDetalhes) {
        for (EmpenhoDetalhe e : empenhoDetalhes) {
            if (e.getDataPagamento() == null) {
                return false;
            }
        }
        return true;
    }

    public BigDecimal somarLiquidacoes(List<EmpenhoDetalhe> empenhoDetalhes) {
        BigDecimal bd = BigDecimal.ZERO;
        for (EmpenhoDetalhe empenhoDetalhe : empenhoDetalhes) {
            bd = bd.add(empenhoDetalhe.getValor());
        }
        return bd;
    }

    public List<EmpenhoDetalhe> listaSemPagamento() {
        return dao.listaSemPagamento();
    }

    public List<EmpenhoDetalhe> listarPagamentos(UnidadeOrcamentaria unidadeOrcamentaria, Credor credor, List<UnidadeOrcamentaria> unidadeOrcamentarias, Date dataInicio, Date dataFinal, Date dataPagamentoInicio, Date dataPagamentoFinal) {
        return dao.listarPagamentos(unidadeOrcamentaria, credor, unidadeOrcamentarias, dataInicio, dataFinal, dataPagamentoInicio, dataPagamentoFinal);
    }

}
