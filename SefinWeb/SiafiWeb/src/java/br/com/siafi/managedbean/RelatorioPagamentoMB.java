/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class RelatorioPagamentoMB implements Serializable {

    @Inject
    private UsuarioMB usuarioMb;
    @EJB
    private EmpenhoDetalheController controller;
    private List<SolicitacaoLiquidacao> listaSolicitacaoLiquidacao;
    private List<EmpenhoDetalhe> empenhoDetalhes;
    private Credor credor;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Date dataInicio;
    private Date dataFinal;
    private Date dataPagamentoInicio;
    private Date dataPagamentoFinal;

    public RelatorioPagamentoMB() {
    }

    public void listar() {
        try {
            empenhoDetalhes = controller.listarPagamentos(unidadeOrcamentaria, credor, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), dataInicio, dataFinal, dataPagamentoInicio, dataPagamentoFinal);
            //            listaSolicitacaoLiquidacao = solicitacaoLiquidacaoController.listarPagamentos(unidadeOrcamentaria, credor, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), dataPagamentoInicio, dataPagamentoFinal);
            if (empenhoDetalhes.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum processo encontrado.");
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e, this.getClass().getName());
        }
    }

    public void impressao() {
        if (!empenhoDetalhes.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(empenhoDetalhes, m, "WEB-INF/relatorios/relatorio_pagamentos.jasper", "Relatório e Pagamentos de Efetuados");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public List<EmpenhoDetalhe> getEmpenhoDetalhes() {
        return empenhoDetalhes;
    }

    public void setEmpenhoDetalhes(List<EmpenhoDetalhe> empenhoDetalhes) {
        this.empenhoDetalhes = empenhoDetalhes;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataPagamentoInicio() {
        return dataPagamentoInicio;
    }

    public void setDataPagamentoInicio(Date dataPagamentoInicio) {
        this.dataPagamentoInicio = dataPagamentoInicio;
    }

    public Date getDataPagamentoFinal() {
        return dataPagamentoFinal;
    }

    public void setDataPagamentoFinal(Date dataPagamentoFinal) {
        this.dataPagamentoFinal = dataPagamentoFinal;
    }

    public List<SolicitacaoLiquidacao> getListaSolicitacaoLiquidacao() {
        return listaSolicitacaoLiquidacao;
    }

    public void setListaSolicitacaoLiquidacao(List<SolicitacaoLiquidacao> listaSolicitacaoLiquidacao) {
        this.listaSolicitacaoLiquidacao = listaSolicitacaoLiquidacao;
    }

}
