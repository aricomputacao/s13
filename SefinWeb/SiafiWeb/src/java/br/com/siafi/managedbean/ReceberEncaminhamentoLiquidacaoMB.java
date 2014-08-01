/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.controller.EncaminhamentoLiquidacaoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.controller.SolicitacaoLiquidacaoController;
import br.com.siafi.dataiterativa.EncaminhamentoLiquidacaoDataList;
import br.com.siafi.modelo.Encaminhamento;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ReceberEncaminhamentoLiquidacaoMB implements Serializable {

    @EJB
    private EncaminhamentoLiquidacaoController controler;
    @EJB
    private SolicitacaoLiquidacaoController solicitacaoControler;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private EncaminhamentoController encaminhamentoController;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraController;
    private EncaminhamentoLiquidacaoDataList encaminhamentoDataList;
    private List<EncaminhamentoLiquidacao> encaminhamentosArray;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private EncaminhamentoLiquidacao encaminhamento;
    private StatusSolicitacaoLiquidacao situacaoSolicitacao;
    private String idUnidade;

    @PostConstruct
    public void iniciar() {
        unidadeOrcamentaria = new UnidadeOrcamentaria();
    }

    public ReceberEncaminhamentoLiquidacaoMB() {
        encaminhamento = new EncaminhamentoLiquidacao();
        encaminhamentoDataList = new EncaminhamentoLiquidacaoDataList();
        encaminhamentosArray = new ArrayList<>();
    }

    private void salvar(EncaminhamentoLiquidacao e) throws PersistenceException, EJBException, Exception {
        e.setDataRecebimento(new Date());
        e.getSolicitacaoLiquidacao().setAreaAdministrativa(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa());
        e.setUsuarioRecebeu(usuarioMb.getUsuarioSelecionado());
        if (e.getUsuarioEncaminhou().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
            e.getSolicitacaoLiquidacao().setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Finalizado);
        }
        if (e.getDestino().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria)) {
            e.getSolicitacaoLiquidacao().setDataRecebidoTesouraria(new Date());
        }
        solicitacaoControler.atualizar(e.getSolicitacaoLiquidacao());
        controler.salvarouAtualizar(e);
    }

    /**
     *
     * @param el Metodo utilizado para receber liquidação e caso haja processo
     * aberto recebe o processo original
     *
     */
    private void receberProcessoLiquidacao(EncaminhamentoLiquidacao el) {
        try {
            Encaminhamento e = encaminhamentoController.buscarUltimoEncaminhamentoAbertoTesouraria(el.getSolicitacaoLiquidacao().getSolicitacaoFinanceira());
            e.setDataRecebimento(new Date());
            e.setUsuarioRecebeu(usuarioMb.getUsuarioSelecionado());
            e.getSolicitacaoFinanceira().setLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa());
            encaminhamentoController.atualizar(e);
            solicitacaoFinanceiraController.atualiza(e.getSolicitacaoFinanceira());
        } catch (Exception ex) {
            Logger.getLogger(ReceberEncaminhamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo utilizado para salvar processos selecionados
     */
    public void salvarSelecionados() {
        try {
            for (EncaminhamentoLiquidacao e : encaminhamentosArray) {
                salvar(e);
                if (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria)) {
                    receberProcessoLiquidacao(e);

                }
            }
            listaReceber();
            MenssagemUtil.addMessageInfo("Todos os processos selecionados foram recebidos.");
        } catch (Exception ex) {
            Logger.getLogger(ReceberEncaminhamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
            MenssagemUtil.addMessageErro("Erro ao receber os processos " + ex);
        }
    }

    public void listaReceber() {
        try {
            encaminhamentoDataList = new EncaminhamentoLiquidacaoDataList(controler.listarReceber(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), unidadeOrcamentaria, situacaoSolicitacao));
            if (((List) encaminhamentoDataList.getWrappedData()).isEmpty()) {
                MenssagemUtil.addMessageInfo("Não há encaminhamentos pendentes de recebimento.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar Encaminhamentos", ex, this.getClass().getName());
        }
    }

    public EncaminhamentoLiquidacaoDataList getEncaminhamentoDataList() {
        return encaminhamentoDataList;
    }

    public void setEncaminhamentoDataList(EncaminhamentoLiquidacaoDataList encaminhamentoDataList) {
        this.encaminhamentoDataList = encaminhamentoDataList;
    }

    public List<EncaminhamentoLiquidacao> getEncaminhamentosArray() {
        return encaminhamentosArray;
    }

    public void setEncaminhamentosArray(List<EncaminhamentoLiquidacao> encaminhamentosArray) {
        this.encaminhamentosArray = encaminhamentosArray;
    }

    public EncaminhamentoLiquidacao getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(EncaminhamentoLiquidacao encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public StatusSolicitacaoLiquidacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(StatusSolicitacaoLiquidacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
    }

    public List<StatusSolicitacaoLiquidacao> getListaStatus() {
        return Arrays.asList(StatusSolicitacaoLiquidacao.values());
    }

    public void alteraUnidadeOrcamentaria() {
        if (unidadeOrcamentaria != null) {
            idUnidade = unidadeOrcamentaria.getUnidadeOrcamentariaPK().toString();
        }
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

    public String getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
    }

}
