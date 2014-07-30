/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sefin.enumerated.ModalidadeEmpenho;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.sefin.enumerated.TipoFluxo;
import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.controller.EncaminhamentoLiquidacaoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.controller.SolicitacaoLiquidacaoController;
import br.com.siafi.dataiterativa.SolicitacaoFinanceiraDataList;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.Encaminhamento;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import net.sf.jasperreports.engine.JRException;

/**
 * @Sistema SiafiWeb
 * @Data 19/07/2013
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class EncaminhamentoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private EncaminhamentoController controler;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private EmpenhoDetalheController empenhoDetalheControler;
    @EJB
    private EncaminhamentoLiquidacaoController encaminhamentoLiquidacaoController;
    @EJB
    private SolicitacaoLiquidacaoController solicitacaoLiquidacaoController;
    @EJB
    private AreaAdministrativaController areaAdministrativaController;
//    @EJB
//    private EncaminhamentoController encaminhamentoControler;

    private Encaminhamento encaminhamento;
    //private List<Encaminhamento> listaEncaminhamento;
    private SolicitacaoFinanceiraDataList solicitacaoFinanceiraDataList;
    private AreaAdministrativa areaAdministrativa;
    private AreaAdministrativa areaDestino;
    private List<AreaAdministrativa> listAreaAdministrativa;
    private List<SolicitacaoFinanceira> solicitacoesList;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private List<EmpenhoDetalhe> listaEmpenhoDetalhe;
    private SituacaoSolicitacao situacaoSolicitacao;
    private BigDecimal valorLiquidacao;

    private String notaLiquidacao;
    private String idUnidade;
    /**
     * Variavel utilizada na tela de encaminhameto para checar se o processo ta
     * sendo pago todo ou so parte
     */
    private boolean processoCompleto;

    public EncaminhamentoMB() {
    }

    @PostConstruct
    private void iniciar() {
        listAreaAdministrativa = new ArrayList<>();
        solicitacaoFinanceiraDataList = new SolicitacaoFinanceiraDataList();
        // Evitar limpar a lista caso a mesma esteja com registros
        // Esse metodo é utilizado em outros locais nesse beans
        if (solicitacoesList == null) {
            solicitacoesList = new ArrayList<>();
        }
        encaminhamento = new Encaminhamento();
        listaEmpenhoDetalhe = new ArrayList<>();
        valorLiquidacao = BigDecimal.ZERO;
        notaLiquidacao = "";
        processoCompleto = true;
    }

    /**
     * iniciar o encaminhamento setando a data e usuario e selecionando a area
     * adminitrativa de sertino de acordo como local e Situação da Solicitação
     * Selecionada
     */
    private void iniciaEncaminhamento() {
        try {
            encaminhamento.setDataEncaminhamento(new Date());
            encaminhamento.setUsuarioEncaminhou(usuarioMb.getUsuarioSelecionado());
            listAreaAdministrativa = controler.selecionarAreaEncaminhamento(encaminhamento.getSolicitacaoFinanceira());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao iniciar encaminhamento", ex, this.getClass().getName());
        }
    }

    /**
     * Inicia a devolução do processo
     */
    private void iniciaDevolucaoEncaminhamento() {
        encaminhamento.setDataEncaminhamento(new Date());
        encaminhamento.setUsuarioEncaminhou(usuarioMb.getUsuarioSelecionado());
    }

    /**
     * Metodo utilizado para encaminhar uma liquidação para tesouraria criando
     * um processo de liquidação e enviado para tesouraria. Processo encaminhado
     * pela primeira vez para tesouraria Encaminha processo completo
     */
    private void encaminharLiquidacaoCompleta(SolicitacaoFinanceira sol) {
        try {
            SolicitacaoLiquidacao sl = new SolicitacaoLiquidacao();
            EncaminhamentoLiquidacao el = new EncaminhamentoLiquidacao();
            sl.setAreaAdministrativa(sol.getLocal());
            sl.setDocumento(sol.getNotaFiscal());
            sl.setSolicitacaoFinanceira(sol);
            sl.setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Aprovado);
            sl.setValor(sol.getValor());
            solicitacaoLiquidacaoController.salvar(sl);

            el.setObservacao(" ");
            el.setDataEncaminhamento(new Date());
            el.setDestino(areaAdministrativaController.administrativaTipo(TipoAreaAdm.Tesouraria));
            el.setSolicitacaoLiquidacao(sl);
            el.setUsuarioEncaminhou(usuarioMb.getUsuarioSelecionado());
            encaminhamentoLiquidacaoController.salvar(el);
        } catch (Exception ex) {
            Logger.getLogger(EncaminhamentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo para rederizar tela de pagamento
     *
     * @param b
     */
    public void setarProcessoCompleto(boolean b) {
        processoCompleto = b;
        if (!b) {
            SolicitacaoFinanceira s = solicitacoesList.get(0);
            solicitacoesList.clear();
            solicitacoesList.add(s);
        }
    }

    /**
     *
     * @param sol Encaminha processo com o valor da parte da liquidação
     */
    private void encaminharLiquidacaoParte(SolicitacaoFinanceira sol) {
        try {
            SolicitacaoLiquidacao sl = new SolicitacaoLiquidacao();

            //Abri uma liquidação
            EncaminhamentoLiquidacao el = new EncaminhamentoLiquidacao();
            sl.setAreaAdministrativa(sol.getLocal());
            sl.setDocumento(notaLiquidacao);
            sl.setSolicitacaoFinanceira(sol);
            sl.setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Aprovado);
            sl.setValor(valorLiquidacao);
            solicitacaoLiquidacaoController.salvar(sl);

            //Concatena a nota da liquidação com a nota registrada no processo original
            if (!sol.getNotaFiscal().equals(notaLiquidacao)) {
                sol.setNotaFiscal(sol.getNotaFiscal().concat(", ".concat(notaLiquidacao)));
                solicitacaoFinanceiraControler.atualiza(sol);
            }

            //Encaminha a liquidação para tesouraria
            el.setObservacao("");
            el.setDataEncaminhamento(new Date());
            el.setDestino(areaAdministrativaController.administrativaTipo(TipoAreaAdm.Tesouraria));
            el.setSolicitacaoLiquidacao(sl);
            el.setUsuarioEncaminhou(usuarioMb.getUsuarioSelecionado());
            encaminhamentoLiquidacaoController.salvar(el);
        } catch (Exception ex) {
            Logger.getLogger(EncaminhamentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Seleciona a solicitação Verifica se não existe um encaminhamento em
     * aberto para a solicitação
     *
     * @param solicitacaoFinanceira
     */
    public void selecionaSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            encaminhamento = controler.verificaSolicitacaoAberta(solicitacaoFinanceira);
        } catch (Exception ex) {
            encaminhamento = null;
        }
        if (encaminhamento == null) {
            encaminhamento = new Encaminhamento();
            encaminhamento.setSolicitacaoFinanceira(solicitacaoFinanceira);
            iniciaEncaminhamento();
        }
    }

    /**
     * Seleciona a solicitação Verifica se não existe um encaminhamento em
     * aberto para a solicitação
     *
     * @param solicitacaoFinanceira
     */
    public void selecionaSolicitacaoLiquidar(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            listaEmpenhoDetalhe = empenhoDetalheControler.importarDetalhesPagamento(solicitacaoFinanceira);
            if (!listaEmpenhoDetalhe.isEmpty()) {
                try {
                    encaminhamento = controler.verificaSolicitacaoAberta(solicitacaoFinanceira);
                } catch (Exception e) {
                    encaminhamento = null;
                }
                if (encaminhamento == null) {
                    encaminhamento = new Encaminhamento();
                    encaminhamento.setSolicitacaoFinanceira(solicitacaoFinanceira);
                    iniciaEncaminhamento();
                }
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }

    }

    /**
     * Seleciona a solicitação Verifica se não existe um encaminhamento em
     * aberto para a solicitação
     *
     * @param solicitacaoFinanceira
     */
    public void selecionaSolicitacaoEmpenhar(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            encaminhamento = controler.verificaSolicitacaoAberta(solicitacaoFinanceira);
        } catch (Exception ex) {
            encaminhamento = null;
        }
        if (encaminhamento == null) {
            encaminhamento = new Encaminhamento();
            encaminhamento.setSolicitacaoFinanceira(solicitacaoFinanceira);
            String empenho = solicitacaoFinanceiraControler.getEmpenho(solicitacaoFinanceira);
            ModalidadeEmpenho modalidadeEmpenho = solicitacaoFinanceiraControler.getModalidade(solicitacaoFinanceira);
            solicitacaoFinanceira.setEmpenho(empenho);
            solicitacaoFinanceira.setModalidadeEmpenho(modalidadeEmpenho);
            iniciaEncaminhamento();
        }
    }

    /**
     * Seleciona a solicitação para fazer o pagamento
     *
     * @param solicitacaoFinanceira
     */
    public void selecionaSolicitacaoPagamento(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            encaminhamento.setSolicitacaoFinanceira(solicitacaoFinanceira);
            // Buscar e importar e atualizar as liquidações dessa solicitação
            listaEmpenhoDetalhe = empenhoDetalheControler.importarDetalhesPagamento(solicitacaoFinanceira);
            if (!listaEmpenhoDetalhe.isEmpty()) {
                solicitacaoFinanceira.setValorLiquido(empenhoDetalheControler.somarLiquidacoes(listaEmpenhoDetalhe));
                solicitacaoFinanceira.setDataPagamento(listaEmpenhoDetalhe.get(listaEmpenhoDetalhe.size() - 1).getDataPagamento());
                if (listaEmpenhoDetalhe.get(0).getConta() != null) {
                    solicitacaoFinanceira.setFonteRecurso(listaEmpenhoDetalhe.get(0).getConta().getFonteRecurso());
                }
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     * Seleciona a solicitação Verifica se não existe um encaminhamento em
     * aberto para a solicitação e inicia o processo de devolução da solicitação
     *
     * @param solicitacaoFinanceira
     */
    public void selecionaSolicitacaoDevolucao(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            encaminhamento = controler.verificaSolicitacaoAberta(solicitacaoFinanceira);
        } catch (Exception ex) {
            encaminhamento = null;
        }
        if (encaminhamento == null) {
            try {
                encaminhamento = new Encaminhamento();
                // buscar o ultimo encaminhamento dessa solicitacao e verifica se o mesmo não está em aberto
                Encaminhamento utlmoEncaminhamento = controler.buscaUltimoEncaminhamento(solicitacaoFinanceira);
                if (utlmoEncaminhamento != null) {
                    encaminhamento.setDestino(utlmoEncaminhamento.getUsuarioEncaminhou().getAreaAdministrativa());
                    encaminhamento.setSolicitacaoFinanceira(solicitacaoFinanceira);
                    iniciaDevolucaoEncaminhamento();
                } else {
                    MenssagemUtil.addMessageInfo("Não pode devolver esse encaminhamento.");
                }
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar devolução", ex, this.getClass().getName());
            }
        }
    }

    public void somarValorPagamento() {
        BigDecimal v = BigDecimal.ZERO;
        for (EmpenhoDetalhe emp : listaEmpenhoDetalhe) {
            v = v.add(emp.getValor());
        }
        if (encaminhamento.getSolicitacaoFinanceira().getInss() != null) {
            v = v.subtract(encaminhamento.getSolicitacaoFinanceira().getInss());
        }
        if (encaminhamento.getSolicitacaoFinanceira().getIrrf() != null) {
            v = v.subtract(encaminhamento.getSolicitacaoFinanceira().getIrrf());
        }
        if (encaminhamento.getSolicitacaoFinanceira().getIss() != null) {
            v = v.subtract(encaminhamento.getSolicitacaoFinanceira().getIss());
        }
        encaminhamento.getSolicitacaoFinanceira().setValorLiquido(v);
    }

    public SituacaoSolicitacao getAprovado() {
        return SituacaoSolicitacao.Aprovado;
    }

    public SituacaoSolicitacao getDocumentacaoLiberada() {
        return SituacaoSolicitacao.DocumentaçãoLiberada;
    }

    public SituacaoSolicitacao getPago() {
        return SituacaoSolicitacao.Pago;
    }

    public SituacaoSolicitacao getEmpenhado() {
        return SituacaoSolicitacao.Empenhado;
    }

    public SituacaoSolicitacao getLiquidado() {
        return SituacaoSolicitacao.Liquidado;
    }

    /**
     * Devolver
     *
     */
    public void devolver() {
        try {
            controler.salvarouAtualizar(encaminhamento);
            controler.excluirSolicitacaoGestor(encaminhamento);

            MenssagemUtil.addMessageInfo("Solicitação financeira devolvida com sucesso.");
            iniciar();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     * Somente a contabilidade pode empenhar solicitações.
     *
     */
    public void encaminhar() {

        try {
            // Escrever o numero da nota na solicitação
            controler.salvarouAtualizar(encaminhamento);

            solicitacaoFinanceiraControler.salvar(encaminhamento.getSolicitacaoFinanceira());
            MenssagemUtil.addMessageInfo("Solicitação financeira encaminhada com sucesso.");
            iniciar();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     * Somente a contabilidade pode empenhar solicitações.
     *
     */
    public void empenhar() {
        try {
            controler.salvarouAtualizar(encaminhamento);

            encaminhamento.getSolicitacaoFinanceira().setSituacaoSolicitacao(SituacaoSolicitacao.Empenhado);
            solicitacaoFinanceiraControler.salvarouAtualizar(encaminhamento.getSolicitacaoFinanceira());
            MenssagemUtil.addMessageInfo("Solicitação financeira empenhada com sucesso.");
            iniciar();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     * Somente a contabilidade pode empenhar solicitações.
     *
     */
    public void concluir() {
        try {
            encaminhamento.getSolicitacaoFinanceira().setSituacaoSolicitacao(SituacaoSolicitacao.Concluido);
            solicitacaoFinanceiraControler.salvarouAtualizar(encaminhamento.getSolicitacaoFinanceira());
            solicitacoesList.remove(encaminhamento.getSolicitacaoFinanceira());

            //Concluir e arquivar protocolo no SPU
//            controler.concluirProtocoloSPU(encaminhamento);
            //
            MenssagemUtil.addMessageInfo("Solicitação financeira concluida com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     * Somente a contabilidade pode liquidar solicitações.
     *
     */
    public void liquidar() {
        try {
            controler.salvarouAtualizar(encaminhamento);

            encaminhamento.getSolicitacaoFinanceira().setSituacaoSolicitacao(SituacaoSolicitacao.Liquidado);
            solicitacaoFinanceiraControler.salvarouAtualizar(encaminhamento.getSolicitacaoFinanceira());
            MenssagemUtil.addMessageInfo("Solicitação financeira foi liquidada.");
            iniciar();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     * Somente a controladoria pode liberar a documentação.
     *
     */
    public void liberarDocumentacao() {
        try {
            controler.salvarouAtualizar(encaminhamento);

            encaminhamento.getSolicitacaoFinanceira().setSituacaoSolicitacao(SituacaoSolicitacao.DocumentaçãoLiberada);
            solicitacaoFinanceiraControler.salvarouAtualizar(encaminhamento.getSolicitacaoFinanceira());
            MenssagemUtil.addMessageInfo("Documentação Liberada");
            iniciar();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     * Somente a tesouraria pode pagar solicitações. O valor pago não pode ser
     * maior que o valor real da socicitação
     */
    public void pagar() {
        try {
            if (solicitacaoFinanceiraControler.pagarSolicitacaoFinanceira(encaminhamento.getSolicitacaoFinanceira())) {
                MenssagemUtil.addMessageInfo("Solicitação Paga");
                ((List) solicitacaoFinanceiraDataList.getWrappedData()).remove(encaminhamento.getSolicitacaoFinanceira());
            } else {
                MenssagemUtil.addMessageWarn("Não foi possível pagar o processo, existem liquidações pendentes!");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    /**
     *
     * Revogado a regra.(Agora o usuario deve acessar somente as Solicitações da
     * suas unidadesOrçametarias). Solicitações do unidade orçamentaria do
     * usuário se o usuario for da area administrativa que possue super acesso
     * liberar todas da sua area administrativa se não liberar somente as da
     * unidade orçamentária selecionada.
     *
     */
    public void listarSolicitacoes() {
        try {
            if (unidadeOrcamentaria == null) {
                // Lista pela lista de unidade orçamentarias do usuario
                //solicitacoesList = solicitacaoFinanceiraControler.listarPorLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), situacaoSolicitacao);
                solicitacaoFinanceiraDataList = new SolicitacaoFinanceiraDataList(solicitacaoFinanceiraControler.listarPorLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), situacaoSolicitacao));
//                listaEncaminhamento = encaminhamentoControler.listarPorLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), situacaoSolicitacao);
            } else {
                //solicitacoesList = solicitacaoFinanceiraControler.listarPorLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), unidadeOrcamentaria, situacaoSolicitacao);
                solicitacaoFinanceiraDataList = new SolicitacaoFinanceiraDataList(solicitacaoFinanceiraControler.listarPorLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), unidadeOrcamentaria, situacaoSolicitacao));
                //              listaEncaminhamento = encaminhamentoControler.listarPorLocal(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), unidadeOrcamentaria, situacaoSolicitacao);
            }
            if (solicitacaoFinanceiraDataList.getRowCount() == 0) {
                MenssagemUtil.addMessageInfo("Nenhum resultado encontrado.");
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e.getMessage(), e, this.getClass().getName());
        }
    }

    public void impressaoProcesso() {
        if (encaminhamento.getId() != null) {
            try {
                List<Encaminhamento> lista = new ArrayList<>();
                lista.add(encaminhamento);
                Map<String, Object> m = new HashMap<>();
                m.put("processo", encaminhamento.getSolicitacaoFinanceira());
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(lista, m, "WEB-INF/relatorios/ordem_compra.jasper", "Ordem de Compra");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public void impressaoEncaminhaments(List<Encaminhamento> l) {
        try {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/relatorio_encaminhamentos.jasper", "Relatório de Encaminhamentos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        }

    }

    public Encaminhamento getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(Encaminhamento encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public AreaAdministrativa getAreaAdministrativa() {
        return areaAdministrativa;
    }

    public void setAreaAdministrativa(AreaAdministrativa areaAdministrativa) {
        this.areaAdministrativa = areaAdministrativa;
    }

    public List<SolicitacaoFinanceira> getSolicitacoesList() {
        return solicitacoesList;
    }

    public void setSolicitacoesList(List<SolicitacaoFinanceira> solicitacoesList) {
        this.solicitacoesList = solicitacoesList;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<AreaAdministrativa> getListAreaAdministrativa() {
        return listAreaAdministrativa;
    }

    public void setListAreaAdministrativa(List<AreaAdministrativa> listAreaAdministrativa) {
        this.listAreaAdministrativa = listAreaAdministrativa;
    }

    // Valida o valor na hora de fazer o pagamento de uma solicitação
    public void validaValor(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        BigDecimal valor = (BigDecimal) value;
        if (valor.compareTo(new BigDecimal(BigInteger.ZERO)) < 1) {
            ((UIInput) component).setValid(false);
            MenssagemUtil.addMessageErro("O valor deve ser maior que zero.");
        } else if (valor.compareTo(encaminhamento.getSolicitacaoFinanceira().getValor()) >= 1) {
            ((UIInput) component).setValid(false);
            MenssagemUtil.addMessageErro("O valor liquido da solicitação não pode ser maior que o valor da solicitação.");
        } else {
            ((UIInput) component).setValid(true);
        }
    }

    public List<EmpenhoDetalhe> getListaEmpenhoDetalhe() {
        return listaEmpenhoDetalhe;
    }

    public void setListaEmpenhoDetalhe(List<EmpenhoDetalhe> listaEmpenhoDetalhe) {
        this.listaEmpenhoDetalhe = listaEmpenhoDetalhe;
    }

    //Varifica se existem liquidações e se as mesmas estão pagas para poder liberar a solicitação para pagamento
    public boolean getPagamentoLiberado() {
        boolean autorizado = !listaEmpenhoDetalhe.isEmpty();
        for (EmpenhoDetalhe e : listaEmpenhoDetalhe) {
            if (e.getDataPagamento() == null) {
                autorizado = false;
            }
        }
        return autorizado;
    }

    //Verifica se todas as liquidações  estão pagas para redenrizar o botão pagar
    public boolean getLiquidacoesPagas() {
        for (EmpenhoDetalhe e : listaEmpenhoDetalhe) {
            if (e.getSolicitacaoFinanceira().getSituacaoSolicitacao() != SituacaoSolicitacao.Pago) {
                return false;
            }
        }
        return true;
    }

    public boolean renderBtnPagar() {
        BigDecimal v = new BigDecimal(BigInteger.ZERO);
        BigDecimal v2 = new BigDecimal(BigInteger.ZERO);
        for (EmpenhoDetalhe e : listaEmpenhoDetalhe) {
            if (e.getDataPagamento() != null) {
                v = v.add(e.getValor());
                v2 = e.getSolicitacaoFinanceira().getValor();

            }
        }
        return (v.equals(v2));

    }

    //Checar se o processo está pago
    public boolean getChecarPago(SolicitacaoFinanceira sf) {
        return sf.getSituacaoSolicitacao() == SituacaoSolicitacao.Pago;
    }

    public void impressao() {
        if (solicitacoesList.isEmpty()) {
            MenssagemUtil.addMessageInfo("Não hé nenhum registro para impressão");
        } else {
            try {
                String titulo = "Relatório de Solicitações Financeiras - Tesouraria";
                if (situacaoSolicitacao == SituacaoSolicitacao.Pago) {
                    titulo += " - Pago";
                }
                if (situacaoSolicitacao == SituacaoSolicitacao.Liquidado) {
                    titulo += " - Liquidado";
                }
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(solicitacoesList, m, "WEB-INF/relatorios/relatorio_solicitacao.jasper", titulo);
                // byte[] rel = new AssistentedeRelatorio().relatorioemByte(solicitacoesList, m, "WEB-INF/relatorios/relatorio_solicitacao.jasper", "Relatório de Solicitações Financeiras");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public boolean checarLiquidacao(SolicitacaoFinanceira sf) {
        try {
            return (solicitacaoFinanceiraControler.checarLiquidacao(sf) && getPagamentoLiberado());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao checar liquidação", ex, this.getClass().getName());
            return false;
        }
    }

    //Inicio ----- Regra para renderizar botões SAF030101
    public Boolean btnEncaminhar(SolicitacaoFinanceira item) {
        return (item.getSituacaoSolicitacao().equals(getAprovado()) || item.getSituacaoSolicitacao().equals(getEmpenhado()) || item.getSituacaoSolicitacao().equals(getLiquidado())) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro);
    }

    public Boolean btnEmpenhar(SolicitacaoFinanceira item) {
        return item.getSituacaoSolicitacao().equals(getAprovado()) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && !item.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade);
    }

    public Boolean btnLiquidar(SolicitacaoFinanceira item) {
        return item.getSituacaoSolicitacao().equals(getDocumentacaoLiberada()) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade);
    }

    public Boolean btnConcluir(SolicitacaoFinanceira item) {
        return item.getSituacaoSolicitacao() == getPago() && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm() == TipoAreaAdm.Contabilidade;
    }

    public Boolean btnLiberarDocumentacao(SolicitacaoFinanceira item) {
        return item.getSituacaoSolicitacao() == getEmpenhado() && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm() == TipoAreaAdm.Controladoria;
    }

    public Boolean btnPagar(SolicitacaoFinanceira item) {
        return (item.getSituacaoSolicitacao() == getLiquidado() || item.getSituacaoSolicitacao() == getPago()) && (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm() == TipoAreaAdm.Tesouraria
                || usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm() == TipoAreaAdm.TesourariaSaude);
    }

    public Boolean btnEncaminharSolicitacaoPaga(SolicitacaoFinanceira item) {
        return item.getSituacaoSolicitacao() == getPago() && (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm() == TipoAreaAdm.Tesouraria
                || usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm() == TipoAreaAdm.TesourariaSaude);
    }

    public Boolean btnDevolver(SolicitacaoFinanceira item) {
        return (!item.getSituacaoSolicitacao().equals(getPago()));
    }

    //Fim ----- Regra para renderizar botões SAF030101
    public boolean getPodeImprimir() {
        return solicitacaoFinanceiraDataList.getRowCount() > 0 && (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) || usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.TesourariaSaude));
    }

    public SituacaoSolicitacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
    }

    public boolean getExibirStatus() {
        return usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) || usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.TesourariaSaude);
    }

    public SolicitacaoFinanceiraDataList getSolicitacaoFinanceiraDataList() {
        return solicitacaoFinanceiraDataList;
    }

    public void setSolicitacaoFinanceiraDataList(SolicitacaoFinanceiraDataList solicitacaoFinanceiraDataList) {
        this.solicitacaoFinanceiraDataList = solicitacaoFinanceiraDataList;
    }

    public AreaAdministrativa getAreaDestino() {
        return areaDestino;
    }

    public void setAreaDestino(AreaAdministrativa areaDestino) {
        this.areaDestino = areaDestino;
    }

    //*************************************************************************************************
    public Boolean getPodeLiberarDocumentacao() {
        return SituacaoSolicitacao.Empenhado.equals(situacaoSolicitacao) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Controladoria) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    public Boolean getPodeEmpenhar() {
        return SituacaoSolicitacao.Aprovado.equals(situacaoSolicitacao) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    public Boolean getPodeEncaminhar() {
        return (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro) || (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) && SituacaoSolicitacao.Pago.equals(situacaoSolicitacao))) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    public Boolean getPodeLiquidar() {
        return SituacaoSolicitacao.DocumentaçãoLiberada.equals(situacaoSolicitacao) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    public Boolean getPodeLiquidarContabilidade() {
        return SituacaoSolicitacao.Empenhado.equals(situacaoSolicitacao) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    public Boolean getPodeConcluir() {
        return SituacaoSolicitacao.Pago.equals(situacaoSolicitacao) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    public Boolean getPodePagar() {
        return SituacaoSolicitacao.Liquidado.equals(situacaoSolicitacao) && (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) || usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    public Boolean getPodeEncaminharTesouraria() {
        return SituacaoSolicitacao.Liquidado.equals(situacaoSolicitacao) && (usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) && solicitacaoFinanceiraDataList.getRowCount() > 0;
    }

    /**
     * Metodos de encaminhamento em lista
     */
    // ***************************************************************************************************
    public void selecionaSolicitacoesEncaminhar() {
        try {
            listAreaAdministrativa = controler.selecionarAreaEncaminhamento(solicitacoesList.get(0));
            processoCompleto = true;
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar area administrativa", ex, this.getClass().getName());
        }
    }

    public void selecionaSolicitacoesEmpenhar() {
        for (SolicitacaoFinanceira s : solicitacoesList) {
            s.setEmpenho(solicitacaoFinanceiraControler.getEmpenho(s));
            s.setModalidadeEmpenho(solicitacaoFinanceiraControler.getModalidade(s));
        }
        if (!solicitacoesList.isEmpty()) {
            try {
                listAreaAdministrativa = controler.selecionarAreaEncaminhamento(solicitacoesList.get(0));
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao selecionar area administrativa", ex, this.getClass().getName());
            }
        }
    }

    public void selecionaSolicitacoesLiberarDocumentacao() {
        try {
            listAreaAdministrativa = controler.selecionarAreaEncaminhamento(solicitacoesList.get(0));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar area administrativa", ex, this.getClass().getName());
        }
    }

    public void selecionaSolicitacoesLiquidar() {
        for (int i = 0; solicitacoesList.size() > i; i++) {
            try {
                List<EmpenhoDetalhe> lista = empenhoDetalheControler.importarDetalhesPagamento(solicitacoesList.get(i));
                if (lista.isEmpty()) {
                    MenssagemUtil.addMessageWarn("Não existem liquidações registradas para o empenho: ".concat(solicitacoesList.get(i).getEmpenho()));
                    solicitacoesList.remove(i);
                    i--;

                }
            } catch (Exception e) {
                MenssagemUtil.addMessageErro("Ocorreu um erro desconhecido", e, this.getClass().getName());
            }
        }
        if (!solicitacoesList.isEmpty()) {
            try {
                listAreaAdministrativa = controler.selecionarAreaEncaminhamento(solicitacoesList.get(0));
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao selecionar area administrativa", ex, this.getClass().getName());
            }
        } else {
            listAreaAdministrativa.clear();
        }
    }

    public void selecionaSolicitacoesPagar() {
        for (int i = 0; solicitacoesList.size() > i; i++) {
            try {
                List<EmpenhoDetalhe> liquidacoes = empenhoDetalheControler.importarDetalhesPagamento(solicitacoesList.get(i));
                boolean remove = false;
                if (!liquidacoes.isEmpty()) {
                    for (EmpenhoDetalhe e : liquidacoes) {
                        if (e.getDataPagamento() == null) {
                            remove = true;
                        }
                    }
                    if (!remove) {
                        solicitacoesList.get(i).setValorLiquido(empenhoDetalheControler.somarLiquidacoes(liquidacoes));
                        solicitacoesList.get(i).setDataPagamento(liquidacoes.get(liquidacoes.size() - 1).getDataPagamento());
                        if (liquidacoes.get(0).getConta() != null) {
                            solicitacoesList.get(i).setFonteRecurso(liquidacoes.get(0).getConta().getFonteRecurso());
                        }
                    } else {
                        solicitacoesList.remove(i);
                        i--;
                    }
                } else {
                    solicitacoesList.remove(i);
                    i--;
                }
            } catch (Exception e) {
                MenssagemUtil.addMessageErro("Erro ao selecionar solicitações e Liquidações", e, this.getClass().getName());
            }
        }
        if (!solicitacoesList.isEmpty()) {
            try {
                listAreaAdministrativa = controler.selecionarAreaEncaminhamento(solicitacoesList.get(0));
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao selecionar area administrativa", ex, this.getClass().getName());
            }
        }
    }

    public void encaminharSolicitacoes() {
        for (SolicitacaoFinanceira s : solicitacoesList) {
            try {
                if (s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Liquidado) && areaDestino.getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria)) {
                    //Encaminha as liquidações
                    if (processoCompleto) {
                        encaminharLiquidacaoCompleta(s);

                    } else {
                        encaminharLiquidacaoParte(s);
                    }
                }
                //Encaminha a solicitação
                encaminharSolicitacao(s);
                MenssagemUtil.addMessageInfo("Solicitações Encaminhadas.");

            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao Encaminhar Solicitação " + s.getId(), ex, this.getClass().getName());
            }
        }
        listarSolicitacoes();
    }

    public void pagarSolicitacoes() {
        for (SolicitacaoFinanceira s : solicitacoesList) {
            try {
                pagarSolicitacao(s);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao Encaminhar Solicitação " + s.getId(), ex, this.getClass().getName());
            }
        }
        listarSolicitacoes();
        MenssagemUtil.addMessageInfo("Solicitações Pagas.");
    }

    public void concluirSolicitacoes() {
        for (SolicitacaoFinanceira s : solicitacoesList) {
            try {
                concluirSolicitacao(s);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao Encaminhar Solicitação " + s.getId(), ex, this.getClass().getName());
            }
        }
        listarSolicitacoes();
        MenssagemUtil.addMessageInfo("Solicitações Concluidas.");
    }

    public void liquidarSolicitacoes() {
        for (SolicitacaoFinanceira s : solicitacoesList) {
            try {
                if (s.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
                    liquidarSolicitacao(s);

                } else {
                    liquidarSolicitacaoLiberada(s);
                }
                //  solicitacoesList.remove(s);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao Encaminhar Solicitação " + s.getId(), ex, this.getClass().getName());
            }
        }
        listarSolicitacoes();
        MenssagemUtil.addMessageInfo("Solicitações Liquidadas.");
    }

    public void empenharSolicitacoes() {
        for (SolicitacaoFinanceira s : solicitacoesList) {
            if (s.getEmpenho() != null) {
                try {
                    empenhar(s);
                } catch (Exception ex) {
                    MenssagemUtil.addMessageErro("Erro ao empenhar Solicitação " + s.getId(), ex, this.getClass().getName());
                }
            }
        }
        listarSolicitacoes();
        MenssagemUtil.addMessageInfo("Solicitações Empenhadas.");
    }

    public void liberarSolicitacoes() {
        for (SolicitacaoFinanceira s : solicitacoesList) {
            try {
                liberarDocumentacao(s);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao liberar Solicitação " + s.getId(), ex, this.getClass().getName());
            }
        }
        listarSolicitacoes();
        MenssagemUtil.addMessageInfo("Solicitações Liberadas e Encaminhadas.");
    }

    private void empenhar(SolicitacaoFinanceira sol) throws Exception {
        sol.setSituacaoSolicitacao(SituacaoSolicitacao.Empenhado);
        if (sol.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro)) {
            // Gerar Encaminhamento
            encaminharSolicitacao(sol);
            // encaminhar para o SPU
            // controler.encaminharSPU(encaminhamento);
        }
        solicitacaoFinanceiraControler.salvarouAtualizar(sol);
    }

    private void pagarSolicitacao(SolicitacaoFinanceira sol) throws Exception {
        sol.setSituacaoSolicitacao(SituacaoSolicitacao.Pago);
        solicitacaoFinanceiraControler.salvarouAtualizar(sol);
    }

    private void liberarDocumentacao(SolicitacaoFinanceira sol) throws Exception {
        sol.setSituacaoSolicitacao(SituacaoSolicitacao.DocumentaçãoLiberada);
        if (sol.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro)) {
            // Gerar Encaminhamento
            encaminharSolicitacao(sol);
            // encaminhar para o SPU
            // controler.encaminharSPU(encaminhamento);
        }
        solicitacaoFinanceiraControler.salvarouAtualizar(sol);
    }

    private void liquidarSolicitacaoLiberada(SolicitacaoFinanceira sol) throws Exception {
        sol.setSituacaoSolicitacao(SituacaoSolicitacao.Liquidado);
        encaminharSolicitacao(sol);
        solicitacaoFinanceiraControler.salvarouAtualizar(sol);
    }

    private void liquidarSolicitacao(SolicitacaoFinanceira sol) throws Exception {
        sol.setSituacaoSolicitacao(SituacaoSolicitacao.Liquidado);
        if (sol.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria)) {
            encaminharSolicitacao(sol);
        }
        solicitacaoFinanceiraControler.salvarouAtualizar(sol);
    }

    private void concluirSolicitacao(SolicitacaoFinanceira s) throws Exception {
        s.setSituacaoSolicitacao(SituacaoSolicitacao.Concluido);
        solicitacaoFinanceiraControler.salvarouAtualizar(s);
    }

    private void encaminharSolicitacao(SolicitacaoFinanceira sol) throws Exception {
        Encaminhamento e = new Encaminhamento();
        e.setDataEncaminhamento(new Date());
        e.setDestino(areaDestino);
        e.setObservacao(" ");
        e.setSolicitacaoFinanceira(sol);
        e.setUsuarioEncaminhou(usuarioMb.getUsuarioSelecionado());
        controler.salvarouAtualizar(e);
    }

    public BigDecimal getValorLiquidacao() {
        return valorLiquidacao;
    }

    public void setValorLiquidacao(BigDecimal valorLiquidacao) {
        this.valorLiquidacao = valorLiquidacao;
    }

    public String getNotaLiquidacao() {
        return notaLiquidacao;
    }

    public void setNotaLiquidacao(String notaLiquidacao) {
        this.notaLiquidacao = notaLiquidacao;
    }

    public boolean isProcessoCompleto() {
        return processoCompleto;
    }

    public void setProcessoCompleto(boolean processoCompleto) {
        this.processoCompleto = processoCompleto;
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
