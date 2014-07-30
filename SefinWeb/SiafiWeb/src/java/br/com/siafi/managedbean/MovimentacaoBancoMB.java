/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.MovimentacaoBancoController;
import br.com.siafi.dataiterativa.MovimentacaoBancoDataList;
import br.com.siafi.modelo.MovimentacaoBanco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class MovimentacaoBancoMB extends BeanGenerico<MovimentacaoBanco> implements Serializable {

    @Inject
    private BeanNavegacaoMB beanNavegacaoMB;
    @EJB
    private MovimentacaoBancoController controller;
    private MovimentacaoBanco transferenciaBancaria;
    private List<MovimentacaoBanco> listaMovimentacaoBanco;
    private MovimentacaoBancoDataList movimentacaoBancoDataList;
    private Date dataIni;
    private Date dataFim;

    public MovimentacaoBancoMB() {
        super(MovimentacaoBanco.class);
    }

    @PostConstruct
    private void iniciar() {
        transferenciaBancaria = (MovimentacaoBanco) beanNavegacaoMB.getRegistroMapa("transferencia_bancaria", new MovimentacaoBanco());
        listaMovimentacaoBanco = new ArrayList<>();
        dataIni = new Date();
        dataFim = new Date();
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(transferenciaBancaria);
            iniciar();
            MenssagemUtil.addMessageInfo(getMsg("cadastro"));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro"));
        }
    }

    public void listarPorData() {
        try {
            movimentacaoBancoDataList = new MovimentacaoBancoDataList(controller.listarProData(dataIni, dataFim));
            if (movimentacaoBancoDataList.getRowCount() == 0) {
                MenssagemUtil.addMessageWarn(getMsg("lista_vazia"));
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro_consulta"));
            Logger.getLogger(MovimentacaoBancoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarTodos() {
        try {
            movimentacaoBancoDataList = new MovimentacaoBancoDataList(controller.listarMovimentacaoBancos());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro_consulta"));
            Logger.getLogger(MovimentacaoBancoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void impressao() {
        try {
           
            Map<String, Object> m = new HashMap<>();

            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaMovimentacaoBanco, m, "WEB-INF/relatorios/rel_movimentacao_bancaria.jasper", "Movimentação Bancaria");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        }
    }

    public MovimentacaoBanco getTransferenciaBancaria() {
        return transferenciaBancaria;
    }

    public void setTransferenciaBancaria(MovimentacaoBanco transferenciaBancaria) {
        this.transferenciaBancaria = transferenciaBancaria;
    }

    public List<MovimentacaoBanco> getListaMovimentacaoBanco() {
        return listaMovimentacaoBanco;
    }

    public void setListaMovimentacaoBanco(List<MovimentacaoBanco> listaMovimentacaoBanco) {
        this.listaMovimentacaoBanco = listaMovimentacaoBanco;
    }

    public MovimentacaoBancoDataList getMovimentacaoBancoDataList() {
        return movimentacaoBancoDataList;
    }

    public void setMovimentacaoBancoDataList(MovimentacaoBancoDataList movimentacaoBancoDataList) {
        this.movimentacaoBancoDataList = movimentacaoBancoDataList;
    }

    public Date getDataIni() {
        return dataIni;
    }

    public void setDataIni(Date dataIni) {
        this.dataIni = dataIni;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

}
