/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.dataiterativa.EncaminhamentoDataList;
import br.com.siafi.modelo.Encaminhamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

/**
 * @Sistema SiafiWeb
 * @Data 19/07/2013
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class ReceberEncaminhamentoMB implements Serializable {

    @EJB
    private EncaminhamentoController controler;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private UsuarioMB usuarioMb;
    //private List<Encaminhamento> listEncaminhamentos;
    private EncaminhamentoDataList encaminhamentoDataList;
    private List<Encaminhamento> encaminhamentosArray;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Encaminhamento encaminhamento;
    private SituacaoSolicitacao situacaoSolicitacao;
    private String idUnidade;

    @PostConstruct
    public void iniciar() {
        unidadeOrcamentaria = new UnidadeOrcamentaria();
    }

    public ReceberEncaminhamentoMB() {
        encaminhamento = new Encaminhamento();
        //listEncaminhamentos = new ArrayList<>();
        encaminhamentoDataList = new EncaminhamentoDataList();
        encaminhamentosArray = new ArrayList<>();

    }

    public void salvar() {
        try {
            salvar(encaminhamento);
            MenssagemUtil.addMessageInfo("Encaminhamento recebido com sucesso," + encaminhamento.getSolicitacaoFinanceira().getId());
        } catch (Exception ex) {
            Logger.getLogger(ReceberEncaminhamentoMB.class.getName()).log(Level.SEVERE, null, ex);
            MenssagemUtil.addMessageErro(ex);
        }
    }

    private void salvar(Encaminhamento e) throws PersistenceException, EJBException, Exception {
        e.setDataRecebimento(new Date());
        e.getSolicitacaoFinanceira().setLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa());
        e.setUsuarioRecebeu(usuarioMb.getUsuarioSelecionado());
        if (e.getSolicitacaoFinanceira().getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado) && e.getDestino().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
            solicitacaoFinanceiraControler.exportaSolicitacao(e.getSolicitacaoFinanceira());
        }
        controler.salvarouAtualizar(e);
        solicitacaoFinanceiraControler.salvarouAtualizar(e.getSolicitacaoFinanceira());
        // Receber protocolo no SPU
        //controler.receberSPU(e);

    }

    public void salvarSelecionados() {
        try {
            for (Encaminhamento e : encaminhamentosArray) {
                salvar(e);
            }
            listaReceber();
            MenssagemUtil.addMessageInfo("Todos os processos selecionados foram recebidos.");
        } catch (Exception ex) {
            Logger.getLogger(ReceberEncaminhamentoMB.class.getName()).log(Level.SEVERE, null, ex);
            MenssagemUtil.addMessageErro("Erro ao receber os processos " + ex);
        }
    }

    public void listaReceber() {
        try {
            encaminhamentoDataList = new EncaminhamentoDataList(controler.listaReceber(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), unidadeOrcamentaria, situacaoSolicitacao));
            if (((List) encaminhamentoDataList.getWrappedData()).isEmpty()) {
                MenssagemUtil.addMessageInfo("Não há encaminhamentos pendentes de recebimento.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar Encaminhamentos", ex, this.getClass().getName());
        }
    }

    public Encaminhamento getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(Encaminhamento encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public EncaminhamentoDataList getEncaminhamentoDataList() {
        return encaminhamentoDataList;
    }

    public void setEncaminhamentoDataList(EncaminhamentoDataList encaminhamentoDataList) {
        this.encaminhamentoDataList = encaminhamentoDataList;
    }

    public List<Encaminhamento> getEncaminhamentosArray() {
        return encaminhamentosArray;
    }

    public void setEncaminhamentosArray(List<Encaminhamento> encaminhamentosArray) {
        this.encaminhamentosArray = encaminhamentosArray;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public SituacaoSolicitacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
    }

    public void selecionaSecretaria() {
        if (idUnidade.length() == 4) {
            for (UnidadeOrcamentaria u : usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias()) {
                if (idUnidade.equals(u.getUnidadeOrcamentariaPK().toString())) {
                    unidadeOrcamentaria = u;
                    break;
                } else {
                    unidadeOrcamentaria = null;
                }
            }
            if (unidadeOrcamentaria == null) {
                idUnidade = "";
                MenssagemUtil.addMessageInfo("Nenhuma secretária encontrada");
            }
        } else {
            unidadeOrcamentaria = null;
        }

    }

    public void ajustaCodigo() {

        if (unidadeOrcamentaria == null) {
            idUnidade = "";
        } else {
            idUnidade = unidadeOrcamentaria.getUnidadeOrcamentariaPK().toString();
        }
    }

    public String getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
    }

}
