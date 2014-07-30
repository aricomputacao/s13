/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.NavegacaoGenerico;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.controller.EncaminhamentoLiquidacaoController;
import br.com.siafi.controller.SolicitacaoLiquidacaoController;
import br.com.siafi.dataiterativa.SolicitacaoLiquidacaoDataListDataList;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
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
 * Encaminar Solicitações de Liquidação
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class EncaminhamentoLiquidacaoMB extends BeanGenerico<EncaminhamentoLiquidacao> implements Serializable {

    @EJB
    private EncaminhamentoLiquidacaoController controller;
    @EJB
    private SolicitacaoLiquidacaoController solicitacaoLiquidacaoController;
    @Inject
    private UsuarioMB usuarioMB;
    @EJB
    private EmpenhoDetalheController empenhoDetalheController;
    @EJB
    private AreaAdministrativaController administrativaController;
    @EJB
    private EncaminhamentoController encaminhamentoProcessoController;

//    private SolicitacaoLiquidacao solicitacaoLiquidacao;
    private AreaAdministrativa destino;
    private EncaminhamentoLiquidacao encaminhamentoLiquidacao;
    private SolicitacaoLiquidacaoDataListDataList solicitacaoLiquidacaoLista;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private StatusSolicitacaoLiquidacao statusSolicitacaoLiquidacao;
    private List<SolicitacaoLiquidacao> solicitacaoLiquidacaoSelecionadas;
    private List<EncaminhamentoLiquidacao> listaEncaminhamentoLiquidacaos;
    private String observacao;
    private boolean renderBtnPagar;
    private String idUnidade;
    private Date dataEncaminhamento;

    private List<AreaAdministrativa> listaAreaAdministrativas;

    public EncaminhamentoLiquidacaoMB() {
        super(EncaminhamentoLiquidacao.class);
    }

    @PostConstruct
    private void iniciar() {
        try {
            listaAreaAdministrativas = administrativaController.listarAreasFinanceiro();
            encaminhamentoLiquidacao = new EncaminhamentoLiquidacao();
            observacao = "";
            renderizarBtnPagar();
            listaEncaminhamentoLiquidacaos = new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(EncaminhamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listaEncaminhamentosData() {
        try {
            listaEncaminhamentoLiquidacaos = controller.encaminhamentoLiquidacaos(dataEncaminhamento, usuarioMB.getUsuarioSelecionado().getAreaAdministrativa(), destino);
            if (listaAreaAdministrativas.isEmpty()) {
                MenssagemUtil.addMessageWarn(getMsg("lista_vazia"));

            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro_consulta"), ex, this.getClass().getName());

        }
    }

    /**
     * Listar todas as solicitações pertencentes a area do usuario
     *
     */
    public void listarSolicitacoesLocais() {
        solicitacaoLiquidacaoLista = new SolicitacaoLiquidacaoDataListDataList(controller.listarSolicitacoes(unidadeOrcamentaria, statusSolicitacaoLiquidacao, usuarioMB.getUsuarioSelecionado().getAreaAdministrativa()));
    }

    private void iniciaNovoEncaminhamento(SolicitacaoLiquidacao sl) {
        encaminhamentoLiquidacao = new EncaminhamentoLiquidacao();
        encaminhamentoLiquidacao.setDataEncaminhamento(new Date());
        encaminhamentoLiquidacao.setSolicitacaoLiquidacao(sl);
        encaminhamentoLiquidacao.setUsuarioEncaminhou(usuarioMB.getUsuarioSelecionado());
    }

    public void listarEncaminhamentos(SolicitacaoLiquidacao liquidacao) {
        try {
            listaEncaminhamentoLiquidacaos = controller.listar(liquidacao);
        } catch (Exception ex) {
            Logger.getLogger(EncaminhamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void renderizarBtnPagar() {
        renderBtnPagar = usuarioMB.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria);
    }

    /**
     * Selecionar o destino da solicitação de acordo com a situação e a
     * localização
     *
     * @param sl
     */
    public void selecionaDestino(SolicitacaoLiquidacao sl) {
        try {
            destino = controller.selecionaDestino(sl);
            iniciaNovoEncaminhamento(sl);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Não foi possivel selecionar o destino correto.", ex, this.getClass().getName());
        }
    }

    private void encaminar(EncaminhamentoLiquidacao e) throws Exception {
        controller.salvarouAtualizar(e);
        if (e.getDestino().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
            e.getSolicitacaoLiquidacao().setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Aprovado);
            solicitacaoLiquidacaoController.atualizar(e.getSolicitacaoLiquidacao());
        }
    }

    public void encaminharLista() {
        try {
            for (SolicitacaoLiquidacao s : solicitacaoLiquidacaoSelecionadas) {
                EncaminhamentoLiquidacao e = new EncaminhamentoLiquidacao();
                e.setDataEncaminhamento(new Date());
                e.setSolicitacaoLiquidacao(s);
                e.setUsuarioEncaminhou(usuarioMB.getUsuarioSelecionado());
                e.setDestino(destino);
                e.setObservacao(observacao);
                encaminar(e);
            }
            listarSolicitacoesLocais();
            MenssagemUtil.addMessageInfo("Solicitações Encaminhadas");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao Encaminhar solicitações", ex, this.getClass().getName());
        }
    }

    public void selecionaDestinoDevolucao(SolicitacaoLiquidacao sl) {
        try {
            // Paga o encaminhado do ultimo destino
            destino = controller.ultimoEncaminhamento(sl).getUsuarioEncaminhou().getAreaAdministrativa();
            iniciaNovoEncaminhamento(sl);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Não foi possivel selecionar o destino correto.", ex, this.getClass().getName());
        }
    }

    public void pagarLista() {
        try {
            for (SolicitacaoLiquidacao s : solicitacaoLiquidacaoSelecionadas) {
                s.setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Finalizado);
                empenhoDetalheController.importarDetalhesPagamento(s.getSolicitacaoFinanceira());
                //Encaminha para contabilidade
                encaminhamentoLiquidacao.setDataEncaminhamento(new Date());
                encaminhamentoLiquidacao.setDestino(administrativaController.administrativaTipo(TipoAreaAdm.Contabilidade));
                encaminhamentoLiquidacao.setSolicitacaoLiquidacao(s);
                encaminhamentoLiquidacao.setObservacao("");
                encaminhamentoLiquidacao.setUsuarioEncaminhou(usuarioMB.getUsuarioSelecionado());

                solicitacaoLiquidacaoController.atualizar(s);
                controller.salvar(encaminhamentoLiquidacao);
                encaminhamentoLiquidacao = new EncaminhamentoLiquidacao();

                encaminhamentoProcessoController.encaminharLiquidacaoesPagasContabilidae(s.getSolicitacaoFinanceira(), usuarioMB.getUsuarioSelecionado());

            }
            listarSolicitacoesLocais();
            MenssagemUtil.addMessageInfo("Solicitações Encaminhadas");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao Encaminhar solicitações", ex, this.getClass().getName());
        }
    }

    public void devolver() {
        try {
            encaminhamentoLiquidacao.setObservacao(observacao);
            encaminhamentoLiquidacao.setDestino(destino);
            if (encaminhamentoLiquidacao.getUsuarioEncaminhou().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
                encaminhamentoLiquidacao.getSolicitacaoLiquidacao().setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Aberto);
                solicitacaoLiquidacaoController.atualizar(encaminhamentoLiquidacao.getSolicitacaoLiquidacao());
            }
            controller.salvarouAtualizar(encaminhamentoLiquidacao);
            listarSolicitacoesLocais();
            MenssagemUtil.addMessageInfo("Solicitação de Liquidação Devolvido.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar Encaminhamento", ex, this.getClass().getName());
        }
    }

    public void selecionaSolicitacoesEncaminhar() {
        if (!solicitacaoLiquidacaoSelecionadas.isEmpty()) {
            try {
                destino = destino = controller.selecionaDestino(solicitacaoLiquidacaoSelecionadas.get(0));
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao selecionar destino", ex, this.getClass().getName());
            }
        } else {
            destino = new AreaAdministrativa();
            MenssagemUtil.addMessageWarn("Selecione uma solicitação para poder encaminar");
        }
    }

    /*+++++++++++++++++++++++++++Getters e setters+++++++*/
    public AreaAdministrativa getDestino() {
        return destino;
    }

    public void setDestino(AreaAdministrativa destino) {
        this.destino = destino;
    }

    public EncaminhamentoLiquidacao getEncaminhamentoLiquidacao() {
        return encaminhamentoLiquidacao;
    }

    public void setEncaminhamentoLiquidacao(EncaminhamentoLiquidacao encaminhamentoLiquidacao) {
        this.encaminhamentoLiquidacao = encaminhamentoLiquidacao;
    }

    public SolicitacaoLiquidacaoDataListDataList getSolicitacaoLiquidacaoLista() {
        return solicitacaoLiquidacaoLista;
    }

    public void setSolicitacaoLiquidacaoLista(SolicitacaoLiquidacaoDataListDataList solicitacaoLiquidacaoLista) {
        this.solicitacaoLiquidacaoLista = solicitacaoLiquidacaoLista;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public StatusSolicitacaoLiquidacao getStatusSolicitacaoLiquidacao() {
        return statusSolicitacaoLiquidacao;
    }

    public void setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao statusSolicitacaoLiquidacao) {
        this.statusSolicitacaoLiquidacao = statusSolicitacaoLiquidacao;
    }

    public List<StatusSolicitacaoLiquidacao> getListaStatus() {
        List<StatusSolicitacaoLiquidacao> status = new ArrayList<>();
        status.add(StatusSolicitacaoLiquidacao.Aberto);
        status.add(StatusSolicitacaoLiquidacao.Aprovado);
        return status;
    }

    public void impressaoEncaminhamentos() {
        try {
            Map<String, Object> m = new HashMap<>();
            m.put("dt", dataEncaminhamento);
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaEncaminhamentoLiquidacaos, m, "WEB-INF/relatorios/rel_encaminhamentos_liquidacoes.jasper", "Relatório de Encaminhamentos de Liquidações");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        } catch (Exception ex) {
            Logger.getLogger(EncaminhamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<SolicitacaoLiquidacao> getSolicitacaoLiquidacaoSelecionadas() {
        return solicitacaoLiquidacaoSelecionadas;
    }

    public void setSolicitacaoLiquidacaoSelecionadas(List<SolicitacaoLiquidacao> solicitacaoLiquidacaoSelecionadas) {
        this.solicitacaoLiquidacaoSelecionadas = solicitacaoLiquidacaoSelecionadas;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isRenderBtnPagar() {
        return renderBtnPagar;
    }

    public void setRenderBtnPagar(boolean renderBtnPagar) {
        this.renderBtnPagar = renderBtnPagar;
    }

    public List<EncaminhamentoLiquidacao> getListaEncaminhamentoLiquidacaos() {
        return listaEncaminhamentoLiquidacaos;
    }

    public void setListaEncaminhamentoLiquidacaos(List<EncaminhamentoLiquidacao> listaEncaminhamentoLiquidacaos) {
        this.listaEncaminhamentoLiquidacaos = listaEncaminhamentoLiquidacaos;
    }

    public void selecionaSecretaria() {
        if (idUnidade.length() == 4) {
            for (UnidadeOrcamentaria u : usuarioMB.getUsuarioSelecionado().getUnidadeOrcamentarias()) {
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

    public void alteraUnidadeOrcamentaria() {
        if (unidadeOrcamentaria != null) {
            idUnidade = unidadeOrcamentaria.getUnidadeOrcamentariaPK().toString();
        }
    }

    public Date getDataEncaminhamento() {
        return dataEncaminhamento;
    }

    public void setDataEncaminhamento(Date dataEncaminhamento) {
        this.dataEncaminhamento = dataEncaminhamento;
    }

    public List<AreaAdministrativa> getListaAreaAdministrativas() {
        return listaAreaAdministrativas;
    }

    public void setListaAreaAdministrativas(List<AreaAdministrativa> listaAreaAdministrativas) {
        this.listaAreaAdministrativas = listaAreaAdministrativas;
    }

}
