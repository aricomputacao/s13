/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.ExercicioController;
import br.com.guardiao.controler.SistemaConfiguracaoController;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.controller.CategoriaController;
import br.com.siafi.controller.CotaController;
import br.com.siafi.controller.CentroCustoController;
import br.com.siafi.controller.ConvenioController;
import br.com.siafi.controller.DotacaoController;
import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.siafi.controller.OrcamentoController;
import br.com.siafi.controller.OrdemCompraController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.Convenio;
import br.com.siafi.modelo.Dotacao;
import br.com.sefin.modelo.dto.DotacaoDto;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.Encaminhamento;
import br.com.guardiao.modelo.Exercicio;
import br.com.sefin.enumerated.TipoFluxo;
import br.com.siafi.modelo.Orcamento;
import br.com.siafi.modelo.OrdemCompra;
import br.com.siafi.modelo.ProjetoAtividade;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import br.com.sefin.enumerated.Vinculo;
import br.com.siafi.controller.AditivoController;
import br.com.siafi.controller.ItemOrdemCompraController;
import br.com.siafi.modelo.Aditivo;
import br.com.siafi.modelo.ItemOrdemCompra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;

/**
 * Classe do Projeto Siafi - Criado em 29/04/2013 - Classe gerenciadora do
 * modelo Solicitação Financeira
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class SolicitacaoFinanceiraMB extends BeanGenerico<SolicitacaoFinanceira> implements Serializable {

    @EJB
    private SolicitacaoFinanceiraController controller;
    @EJB
    private ItemOrdemCompraController itemOrdemCompraControler;
    @EJB
    private EmpenhoDetalheController empenhoDetalheControler;
    @EJB
    private UnidadeOrcamentariaController unidadeOrcamentariaController;
    @EJB
    private ExercicioController exercicioControler;
    @EJB
    private CategoriaController categoriaControler;
    @EJB
    private CotaController cotaControler;
    @EJB
    private CentroCustoController despesaControler;
    @EJB
    private DotacaoController dotacaoControler;
    @EJB
    private OrcamentoController orcamentoControler;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private OrdemCompraController ordemCompraControler;
    @EJB
    private EncaminhamentoController encaminhamentoControler;
    @EJB
    private ConvenioController convenioController;
    @EJB
    private AditivoController aditivoControler;
    @EJB
    private SistemaConfiguracaoController sistemaConfiguracaoController;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private List<Encaminhamento> listaEncaminhamentos;
    private List<Contrato> contratos;
    private List<SolicitacaoFinanceira> lista;
    private List<Exercicio> exercicios;
    private List<Categoria> listaCategorias;
    private List<CentroCusto> listaDespesas;
    private List<ProjetoAtividade> projetoAtividades;
    private List<Dotacao> dotacaos;
    private List<UnidadeOrcamentaria> unidadeOrcamentarias;
    private List<OrdemCompra> ordemCompraLista;
    private List<EmpenhoDetalhe> empenhoDetalhes;
    private List<Convenio> conveniosList;
    private List<DotacaoDto> listaDotacaoDtos;
    private List<Aditivo> listaAditivos;
    private List<SituacaoSolicitacao> listaSituacaoSolicitacaos;
    private Convenio convenio;
    private SolicitacaoFinanceira solicitacaoFinanceira;
    private SituacaoSolicitacao situacaoSolicitacao;
    private Vinculo vinculo;
    private Vinculo vinculoOrdemCompra;
    private Vinculo vinculoContrato;
    private ProjetoAtividade projetoAtividade;
    private Dotacao dotacao;
    private Credor credor;
    private Aditivo aditivo;
    private BigDecimal saldoCota;
    private boolean filtroData;
    private boolean filtroSituacao;
    private boolean filtroNumero;
    private Date dataInicial;
    private Date dataFinal;
    private String numero;
    private String empenho;
    private String contratoNumero;
    private OrdemCompra ordemCompra;
    private Mes competenciaConsulta;
    private Integer anoConsulta;
    private String idUnidade;
    private BigDecimal saldoDotacao;
    private Boolean mostraSaldoDotacao;

    /**
     *
     */
    public SolicitacaoFinanceiraMB() {
        super(SolicitacaoFinanceira.class);

    }

    @PostConstruct
    private void atualizaRegistrosIniciais() {
        try {
            mostraSaldoDotacao = (Boolean) sistemaConfiguracaoController.pegarValorConfiguracaoDef(Boolean.TRUE, "CHECAR_SALDO_DOTACAO", "SAF");
            listaSituacaoSolicitacaos = new ArrayList<>();
            lista = new ArrayList<>();
            contratos = new ArrayList<>();
            listaAditivos = new ArrayList<>();
            ordemCompraLista = new ArrayList<>();
            vinculoOrdemCompra = Vinculo.Ordem_de_Compra;
            vinculoContrato = Vinculo.Contrato;
            unidadeOrcamentarias = unidadeOrcamentariaController.listarTodos("orgao");
            empenhoDetalhes = new ArrayList<>();
            projetoAtividades = new ArrayList<>();
            listaDotacaoDtos = new ArrayList<>();
            numero = "";
            empenho = "";
            solicitacaoFinanceira = (SolicitacaoFinanceira) beanNavegacao.getRegistroMapa("solicitacaofinanceira", new SolicitacaoFinanceira());
            if (solicitacaoFinanceira.getId() == null) {
                solicitacaoFinanceira.setCota(new Cota());

                projetoAtividade = new ProjetoAtividade();
                dotacao = new Dotacao();

                solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Pendente);
                solicitacaoFinanceira.setDataSolicitacao(new Date());

                solicitacaoFinanceira.setMesCompetencia(Mes.values()[Calendar.getInstance().get(Calendar.MONTH)]);
                solicitacaoFinanceira.setAnoCompetencia(Calendar.getInstance().get(Calendar.YEAR));
                credor = new Credor();
                contratoNumero = "";
                aditivo = new Aditivo();
                conveniosList = new ArrayList<>();
                ordemCompra = new OrdemCompra();

                solicitacaoFinanceira.setExercicio(exercicioControler.exercicioVigente());
                solicitacaoFinanceira.setValor(BigDecimal.ZERO);
                solicitacaoFinanceira.setUsuario(usuarioMb.getUsuarioSelecionado());

            } else {
                if (solicitacaoFinanceira.getOrdemCompra() != null) {
                    ordemCompra = solicitacaoFinanceira.getOrdemCompra();
                    listarOrdemdeCompra();
                } else if (solicitacaoFinanceira.getContrato() != null) {
//                    aditivo = new Aditivo();
//                    aditivo.setContrato(solicitacaoFinanceira.getContrato());
//                    aditivo = aditivoControler.ultimoAditivo(solicitacaoFinanceira.getContrato());
                    aditivo = solicitacaoFinanceira.getAditivo();
                    listarContratos();
                }
                if (solicitacaoFinanceira.getConvenio() != null) {
                    convenio = solicitacaoFinanceira.getConvenio();
                    listarConvenios();
                }

                listaCategorias = categoriaControler.listaCategoriaPorCota(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria());
                listaDespesas = despesaControler.listarDespesaPorUnidadeOrcCategoria(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), solicitacaoFinanceira.getCota().getCategoria());
                Collections.sort(listaDespesas);
                atualizaSaldoCota();
                listarProjetoAtividadeUnidadeOrcamentaria();
                projetoAtividade = solicitacaoFinanceira.getDotacao().getProjetoAtividade();
                listarDotacaoProjetoAtividadeOrcamento();
                dotacao = solicitacaoFinanceira.getDotacao();
            }
            anoConsulta = solicitacaoFinanceira.getAnoCompetencia();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro emitir solicitação", ex, this.getClass().getName());
        }
    }

    private void limpaCota() {
        solicitacaoFinanceira.getCota().setCategoria(null);
        solicitacaoFinanceira.getCota().setDespesa(null);
    }

    public void atualiza() {
        try {
            controller.atualiza(solicitacaoFinanceira);
            MenssagemUtil.addMessageInfo(solicitacaoFinanceira.getId() + "\r\n Solicitação Atualizada com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Não foi possível salvar solicitação", ex, this.getClass().getName());
        }
    }

    public void alteraUnidadeOrcamentaria() {
        try {
            limpaCota();
            convenio = new Convenio();
            listaCategorias = categoriaControler.listaCategoriaPorCota(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria());
            saldoCota = new BigDecimal("0.00");
            if (solicitacaoFinanceira.getCota() != null) {
                if (solicitacaoFinanceira.getCota().getUnidadeOrcamentaria() != null) {
                    idUnidade = solicitacaoFinanceira.getCota().getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().toString();
                }
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao alterar unidade orçamentaria", ex, this.getClass().getName());
        }
    }

    public void alterarCategoria() {
        try {
            listaDespesas = despesaControler.listarDespesaPorUnidadeOrcCategoria(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), solicitacaoFinanceira.getCota().getCategoria());
            Collections.sort(listaDespesas);
            saldoCota = new BigDecimal("0.00");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao alterar unidade orçamentaria", ex, this.getClass().getName());
        }
    }

    public void alteraDespesa() {
        saldoCota = new BigDecimal("0.00");
        atualizaCota();
        atualizaSaldoCota();
        dotacao = new Dotacao();
        projetoAtividade = new ProjetoAtividade();
    }

    public boolean checarSaldoDotacao() {
        try {
            BigDecimal x = dotacaoControler.saldo(dotacao);
            if (x.compareTo(solicitacaoFinanceira.getValor()) >= 0) {
                solicitacaoFinanceira.setDotacao(dotacao);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao verificar saldo dotação", ex, this.getClass().getName());
            return false;
        }
    }

    //Renderizar botão de pagamento na contabilidade
    public Boolean checarFluxoContabilidadePagar(SolicitacaoFinanceira sf) {
        return ((sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade) && (sf.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade))) && (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Liquidado)));
    }

    //Renderizar botão de liquidar na contabilidade
    public Boolean checarFluxoContabilidadeLiquidar(SolicitacaoFinanceira sf) {
        return ((sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade) || sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria)) && (sf.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) && (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Empenhado)));
    }

    //Renderizar seleção de destino para tesouraria caso fluxo seja Contabilidade_Tesouraria
    public Boolean rendenrizarSelecionarDestino() {
        return ((solicitacaoFinanceira.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria)) && (solicitacaoFinanceira.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) && (solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Empenhado)));
    }

    //Renderizar botão de empenhar na contabilidade
    public Boolean checarFluxoContabilidadeEmpenhar(SolicitacaoFinanceira sf) {
        return ((sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade) || sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria)) && (sf.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) && (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)));
    }

    //Selecionar empenho para empenhar diretamente na contabilidae
    public void selecionarSolicitacaoEmpenharContabilidae(SolicitacaoFinanceira sf) {
        solicitacaoFinanceira = sf;
        String s = controller.getEmpenho(solicitacaoFinanceira);
        solicitacaoFinanceira.setEmpenho(s);
    }

    //Salvar empenho na contabilidade
    public void empenharNaContabilidade() {
        try {
            solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Empenhado);
            controller.salvarouAtualizar(solicitacaoFinanceira);
            MenssagemUtil.addMessageInfo("Solicitação financeira empenhada com sucesso.");
            solicitacaoFinanceira = new SolicitacaoFinanceira();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao empenhar solicitação", ex, this.getClass().getName());
        }
    }

    //Selecionar empenho para liquidar diretamente na contabilidae
    public void selecionarSolicitacaoLiquidarContabilidae(SolicitacaoFinanceira sf) {
        try {
            empenhoDetalhes = empenhoDetalheControler.importarDetalhesPagamento(sf);
            solicitacaoFinanceira = sf;
            solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Liquidado);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErroLogger(ex, this.getClass().getName());
        }
    }

    //Salvar liquidação na contabilidade
    public void liquidarNaContabilidade() {
        try {
            controller.liquidarComFluxoAlternativo(solicitacaoFinanceira);
            MenssagemUtil.addMessageInfo("Solicitação financeira liquidada com sucesso!");
            solicitacaoFinanceira = new SolicitacaoFinanceira();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao liquidar solicitação", ex, this.getClass().getName());
        }
    }

    /**
     * Seleciona a solicitação para fazer o pagamento
     *
     * @param sf
     */
    public void selecionaSolicitacaoPagamento(SolicitacaoFinanceira sf) {
        try {
            solicitacaoFinanceira = sf;
            // Buscar e importar e atualizar as liquidações dessa solicitação
            empenhoDetalhes = empenhoDetalheControler.importarDetalhesPagamento(solicitacaoFinanceira);
            solicitacaoFinanceira.setValorLiquido(empenhoDetalheControler.somarLiquidacoes(empenhoDetalhes));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Não foi possível realizar o pagamento!", ex, this.getClass().getName());
        }
    }

    //Varifica se existem liquidações e se as mesmas estão pagas para poder liberar a solicitação para pagamento
    public boolean getPagamentoLiberado() {
        return empenhoDetalheControler.checarPagamentoLiberado(empenhoDetalhes);
    }

    public boolean checarLiquidacao(SolicitacaoFinanceira sf) {
        try {
            return (controller.checarLiquidacao(sf) && getPagamentoLiberado());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Não foi verificar o saldo das liquidações!", ex, this.getClass().getName());
            return false;
        }
    }

    //Verifica setodas as liquidações  estão pagas para redenrizar o botão pagar
    public boolean getLiquidacoesPagas() {
        return empenhoDetalheControler.liquidacoesPagas(empenhoDetalhes);

    }

    public void pagarNaContabilidade() {
        try {
            controller.pagarSolicitacaoFinanceira(solicitacaoFinanceira);
            MenssagemUtil.addMessageInfo("Processo pago com sucesso!");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Não foi possível realizar o pagamento!", ex, this.getClass().getName());
        }
    }

    // Valida o valor na hora de fazer o pagamento de uma solicitação
    public void validaValor(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        BigDecimal valor = (BigDecimal) value;
        if (valor.compareTo(new BigDecimal(BigInteger.ZERO)) < 1) {
            ((UIInput) component).setValid(false);
            MenssagemUtil.addMessageErro("O valor deve ser maior que zero.");
        } else if (valor.compareTo(solicitacaoFinanceira.getValor()) >= 1) {
            ((UIInput) component).setValid(false);
            MenssagemUtil.addMessageErro("O valor liquido da solicitação não pode ser maior que o valor da solicitação.");
        } else {
            ((UIInput) component).setValid(true);
        }
    }

    public void addSolicitacao() {
        try {
            controller.addSolicitacao(solicitacaoFinanceira, dotacao, ordemCompra, aditivo, convenio, usuarioMb.getUsuarioSelecionado());
            MenssagemUtil.addMessageInfo(solicitacaoFinanceira.getId() + "\r\nSolicitação financeira salva com sucesso.");
            String historico = solicitacaoFinanceira.getHistorico();
            solicitacaoFinanceira = new SolicitacaoFinanceira();
            solicitacaoFinanceira.setHistorico(historico);
            novo();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.toString(), ex, this.getClass().getName());
        }

    }

//    public void listar() {
//        lista = listar(controller);
//    }
    public void carraregarContratos() {
        //contrato = new Contrato();
        aditivo = new Aditivo();
        listarContratos();
    }

    public void listarFiltros() {
        try {
            lista = controller.listarFiltros(dataInicial, dataFinal, numero, listaSituacaoSolicitacaos, solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), getCampoOrdem(), credor, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), empenho, anoConsulta, competenciaConsulta);
            if (lista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma Solicitação encontrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao executar consulta", ex, this.getClass().getName());
        } finally {
            dataFinal = null;
            dataInicial = null;
            numero = "";
            empenho = "";
            situacaoSolicitacao = null;
            credor = new Credor();
        }
    }

    //Metodo utilizado para listar processos na solicitação de liquidação
    public void listarSolictacoesLiquidadas() {
        try {
            listaSituacaoSolicitacaos.clear();
            listaSituacaoSolicitacaos.add(SituacaoSolicitacao.Liquidado);
            lista = controller.listarFiltros(null, null, numero, listaSituacaoSolicitacaos, solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), getCampoOrdem(), credor, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), empenho, anoConsulta, competenciaConsulta);
            if (lista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma Solicitação encontrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao executar consulta", ex, this.getClass().getName());
        } finally {
            dataFinal = null;
            dataInicial = null;
            numero = "";
            empenho = "";
            situacaoSolicitacao = null;
            credor = new Credor();
        }
    }

    public void novo() {
        saldoDotacao = null;
        if (solicitacaoFinanceira.getId() == null) {
            solicitacaoFinanceira.setCota(new Cota());
            solicitacaoFinanceira.setUsuario(usuarioMb.getUsuarioSelecionado());
        }
        atualizaRegistrosIniciais();
        solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Pendente);
        solicitacaoFinanceira.setDataSolicitacao(new Date());

        solicitacaoFinanceira.setMesCompetencia(Mes.values()[Calendar.getInstance().get(Calendar.MONTH)]);
        solicitacaoFinanceira.setAnoCompetencia(Calendar.getInstance().get(Calendar.YEAR));
        dotacao = new Dotacao();
        projetoAtividade = new ProjetoAtividade();
        aditivo = new Aditivo();
        contratos.clear();
        listaDotacaoDtos.clear();

    }

    public void atualizaSaldoCota() {
        if (solicitacaoFinanceira.getCota() != null) {
            if (solicitacaoFinanceira.getCota().getValor() != null) {
                saldoCota = controller.verificaSaldo(solicitacaoFinanceira);
            } else {
                solicitacaoFinanceira.setValor(new BigDecimal(0));
            }
        } else {
            solicitacaoFinanceira.setValor(new BigDecimal(0));
        }
    }

    public void cancelarSolicitacao() {
        try {
            controller.cancelarProcesso(solicitacaoFinanceira);
            MenssagemUtil.addMessageInfo("Solicitação financeira cancelada com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex.getMessage(), ex, this.getClass().getName());
        }
    }

    public void listarLiquidacoes(SolicitacaoFinanceira sf) {
        try {
            empenhoDetalhes = empenhoDetalheControler.importarDetalhesPagamento(sf);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar", ex, this.getClass().getName());
        }
    }

    /**
     * Rotina de validação de saldo da solicitação
     *
     * @param fc
     * @param com
     * @param value
     */
    public void validaSaldo(FacesContext fc, UIComponent com, Object value) {
        BigDecimal vaDouble = (BigDecimal) value;
        saldoCota = controller.verificaSaldo(solicitacaoFinanceira);
        if (vaDouble.compareTo(saldoCota) > 0) {
            ((UIInput) com).setValid(false);
            MenssagemUtil.addMessageWarn("Saldo insuficiente\r\nNão há saldo suficiente para fazer esta solicitação.");
        } else {
            ((UIInput) com).setValid(true);
        }
    }

    private void atualizaCota() {
        if (solicitacaoFinanceira.getCota().getCategoria() != null && solicitacaoFinanceira.getCota().getUnidadeOrcamentaria() != null && solicitacaoFinanceira.getCota().getDespesa() != null) {
            try {
                solicitacaoFinanceira.setCota(cotaControler.busca(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), solicitacaoFinanceira.getCota().getCategoria(), solicitacaoFinanceira.getCota().getDespesa()));
            } catch (Exception ex) {
                Logger.getLogger(SolicitacaoFinanceiraMB.class.getName()).log(Level.SEVERE, null, ex);
                MenssagemUtil.addMessageWarn("Não há cota para essa despesa.");
            }
        } else {
            solicitacaoFinanceira.setCota(new Cota());
        }
    }

    // listar somente os projetos/atividade que possuem a cota selecionada
    public void listarProjetoAtividadeUnidadeOrcamentaria() {
        try {
            Orcamento o = orcamentoControler.getOrcamentoVigente();
            projetoAtividades = dotacaoControler.listarProjetoPorElementoUnidadeOrcamentaria(o, solicitacaoFinanceira.getCota().getDespesa(), solicitacaoFinanceira.getCota().getUnidadeOrcamentaria());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar projeto atividade", ex, this.getClass().getName());
        }

    }

    public void listarDotacaoProjetoAtividadeOrcamento() {
        Orcamento o;
        try {
            o = orcamentoControler.getOrcamentoVigente();
            listaDotacaoDtos = dotacaoControler.dotacaoProjetoAtividadeOrcamento(projetoAtividade, o, solicitacaoFinanceira.getCota().getDespesa().getElementoDespesa());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public void listarContratos() {
        try {
            listaAditivos.clear();
            listaAditivos = aditivoControler.listarAditivosValidos(contratoNumero, solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), solicitacaoFinanceira.getCredor());
            if (listaAditivos.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta de contratos", ex, this.getClass().getName());
        } finally {
            contratoNumero = "";
        }
    }

    public BigDecimal mostraSaldoContrato(Contrato c) {
        try {
            return controller.saldoContrato(c);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal mostraSaldoAditivo(Aditivo a) {
        try {
            return controller.saldoAditivo(a);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
            return BigDecimal.ZERO;
        }
    }

    public void impressao() {
        if (!lista.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(lista, m, "WEB-INF/relatorios/relatorio_solicitacao.jasper", "Relatório de Solicitações Financeiras");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar Impressão", ex, this.getClass().getName());
            }
        }
    }

    public void impressaoComprovanteEcaminhamento(SolicitacaoFinanceira sol) {
        List<SolicitacaoFinanceira> financeiras = new ArrayList<>();
        if (sol.getId() != null) {
            try {
                financeiras.clear();
                financeiras.add(sol);
                Map<String, Object> m = new HashMap<>();
                m.put("unidadeOrcamentaria", sol.getCota().getUnidadeOrcamentaria());
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(financeiras, m, "WEB-INF/relatorios/comprovante_encaminhamento.jasper", "Dados do Processo");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar Impressão", ex, this.getClass().getName());
            }
        }
    }

    public boolean podeImprimirEmpenho(SolicitacaoFinanceira sol) {
        return (sol.getId() != null && sol.getEmpenho() != null);
    }

    public void impressaoEmpenho(SolicitacaoFinanceira sol) {
        if (sol.getId() != null && sol.getEmpenho() != null) {
            try {
                List<SolicitacaoFinanceira> listaSol = new ArrayList<>();
                List<ItemOrdemCompra> listaItens = new ArrayList<>();
                listaSol.add(sol);
                Date dataEmpenho = controller.getDataEmpenho(sol);
                BigDecimal totalItem = BigDecimal.ZERO;
                Map<String, Object> m = new HashMap<>();
                BigDecimal saldo = dotacaoControler.saldoDotacaoGestor(dataEmpenho, sol.getEmpenho(), sol.getDotacao().getDotacalPk().getId_reduzido());
                if (sol.getOrdemCompra() != null) {
                    listaItens = itemOrdemCompraControler.listarPorOrdemCompra(sol.getOrdemCompra());
                    for (ItemOrdemCompra i : listaItens) {
                        totalItem = totalItem.add(i.getQuantidade().multiply(i.getItemLicitacao().getValor()));
                    }
                }
                m.put("dataEmpenho", dataEmpenho);
                m.put("saldoDotacao", saldo);
                m.put("total_itens", totalItem);

                m.put("ITENS", listaItens);

                byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaSol, m, "WEB-INF/relatorios/empenho.jasper", "Dados do Processo");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar Impressão", ex, this.getClass().getName());
            }
        } else {
            MenssagemUtil.addMessageInfo("Essa solicitação ainda não está empenhada.");
        }
    }

    public void listarOrdemdeCompra() {
        try {
            ordemCompraLista = ordemCompraControler.listarSolicitacao(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), solicitacaoFinanceira.getCredor());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar ordem de compra", ex, this.getClass().getName());
        }
    }

    public void listarEncaminhamentosPorSolicitacao() {
        try {
            listaEncaminhamentos = encaminhamentoControler.buscarPorSolicitacao(solicitacaoFinanceira);
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaoFinanceiraMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Renderiza btn de lista de liquidações
    public boolean renderizarBtnLiquidacao(SolicitacaoFinanceira item) {
        return (item.getSituacaoSolicitacao() == SituacaoSolicitacao.Cancelado
                || item.getSituacaoSolicitacao() == SituacaoSolicitacao.Pendente
                || item.getSituacaoSolicitacao() == SituacaoSolicitacao.Aprovado);
    }

    public boolean checarVinculoCompra() {
        return solicitacaoFinanceira.getVinculo() == vinculoOrdemCompra;
    }

    public boolean liberarEditar(SolicitacaoFinanceira s) {
        return !((s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Pendente) || s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().equals(s.getLocal()));
    }

    public boolean liberarEditarNota(SolicitacaoFinanceira s) {
        return ((!s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Pago)) && (!s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Concluido)) && (!s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Cancelado))) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().equals(s.getLocal());
    }

    public boolean podeEditarHistorico(SolicitacaoFinanceira s) {
        return (s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Pendente) || s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().equals(s.getLocal());
    }

    public boolean podeEditarHistorico() {
        return podeEditarHistorico(solicitacaoFinanceira);
    }

    public boolean liberarCancelar(SolicitacaoFinanceira s) {
        return !(!s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Concluido) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().equals(s.getLocal()));
//        return !((s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Pendente) || s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado) || s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Empenhado) || s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Liquidado) || s.getSituacaoSolicitacao().equals(SituacaoSolicitacao.DocumentaçãoLiberada)) && usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().equals(s.getLocal()));

    }

    public SituacaoSolicitacao getSituacaoAprovado() {
        return SituacaoSolicitacao.Aprovado;
    }

    public SituacaoSolicitacao getSituacaoCancelada() {
        return SituacaoSolicitacao.Cancelado;
    }

    public SituacaoSolicitacao getSituacaoPendente() {
        return SituacaoSolicitacao.Pendente;
    }

    /**
     *
     * @return
     */
    public List<SolicitacaoFinanceira> getLista() {
        return lista;
    }

    /**
     *
     * @param lista
     */
    public void setLista(List<SolicitacaoFinanceira> lista) {
        this.lista = lista;
    }

    /**
     *
     * @return
     */
    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    /**
     *
     * @param solicitacaoFinanceira
     */
    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
    }

    public List<CentroCusto> getListaDespesas() {
        return listaDespesas;
    }

    public void setListaDespesas(List<CentroCusto> listaDespesas) {
        this.listaDespesas = listaDespesas;
    }

    public BigDecimal getSaldoCota() {
        return saldoCota;
    }

    public void setSaldoCota(BigDecimal saldoCota) {
        this.saldoCota = saldoCota;
    }

    public List<Mes> getListaMeses() {
        return Arrays.asList(Mes.values());
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public boolean isFiltroData() {
        return filtroData;
    }

    public void setFiltroData(boolean filtroData) {
        this.filtroData = filtroData;
    }

    public boolean isFiltroSituacao() {
        return filtroSituacao;
    }

    public void setFiltroSituacao(boolean filtroSituacao) {
        this.filtroSituacao = filtroSituacao;
    }

    public boolean isFiltroNumero() {
        return filtroNumero;
    }

    public void setFiltroNumero(boolean filtroNumero) {

        this.filtroNumero = filtroNumero;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public SituacaoSolicitacao[] getSituacoesSolicitacao() {
        return SituacaoSolicitacao.values();
    }

    public SituacaoSolicitacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public List<ProjetoAtividade> getProjetoAtividades() {
        return projetoAtividades;
    }

    public void setProjetoAtividades(List<ProjetoAtividade> projetoAtividades) {
        this.projetoAtividades = projetoAtividades;
    }

    public List<Dotacao> getDotacaos() {
        return dotacaos;
    }

    public void setDotacaos(List<Dotacao> dotacaos) {
        this.dotacaos = dotacaos;
    }

    public ProjetoAtividade getProjetoAtividade() {
        return projetoAtividade;
    }

    public void setProjetoAtividade(ProjetoAtividade projetoAtividade) {
        this.projetoAtividade = projetoAtividade;
    }

    public Dotacao getDotacao() {
        return dotacao;
    }

    public void setDotacao(Dotacao dotacao) {
        this.dotacao = dotacao;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public String getContratoNumero() {
        return contratoNumero;
    }

    public void setContratoNumero(String contratoNumero) {
        this.contratoNumero = contratoNumero;
    }

    public Aditivo getAditivo() {
        return aditivo;
    }

    public void setAditivo(Aditivo aditivo) {
        this.aditivo = aditivo;
    }

    public List<OrdemCompra> getOrdemCompraLista() {
        return ordemCompraLista;
    }

    public void setOrdemCompraLista(List<OrdemCompra> ordemCompraLista) {
        this.ordemCompraLista = ordemCompraLista;
    }

    public OrdemCompra getOrdemCompra() {
        return ordemCompra;
    }

    public void setOrdemCompra(OrdemCompra ordemCompra) {
        this.ordemCompra = ordemCompra;
        solicitacaoFinanceira.setValor(ordemCompra.getValorTotal());
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public List<Encaminhamento> getListaEncaminhamentos() {
        return listaEncaminhamentos;
    }

    public void setListaEncaminhamentos(List<Encaminhamento> listaEncaminhamentos) {
        this.listaEncaminhamentos = listaEncaminhamentos;
    }

    public Vinculo[] getVinculos() {
        return Vinculo.values();
    }

    public Vinculo getVinculo() {
        return vinculo;
    }

    public void setVinculo(Vinculo vinculo) {
        this.vinculo = vinculo;
    }

    public Vinculo getVinculoOrdemCompra() {
        return vinculoOrdemCompra;
    }

    public void setVinculoOrdemCompra(Vinculo vinculoOrdemCompra) {
        this.vinculoOrdemCompra = vinculoOrdemCompra;
    }

    public Vinculo getVinculoContrato() {
        return vinculoContrato;
    }

    public Vinculo getVinculoConvenio() {
        return Vinculo.Convênio;
    }

    public void setVinculoContrato(Vinculo vinculoContrato) {
        this.vinculoContrato = vinculoContrato;
    }

    public List<UnidadeOrcamentaria> getUnidadeOrcamentarias() {
        return unidadeOrcamentarias;
    }

    public void setUnidadeOrcamentarias(List<UnidadeOrcamentaria> unidadeOrcamentarias) {
        this.unidadeOrcamentarias = unidadeOrcamentarias;
    }

    public List<EmpenhoDetalhe> getEmpenhoDetalhes() {
        return empenhoDetalhes;
    }

    public void setEmpenhoDetalhes(List<EmpenhoDetalhe> empenhoDetalhes) {
        this.empenhoDetalhes = empenhoDetalhes;
    }

    public void listarConvenios() {
        try {
            conveniosList = convenioController.listarConveniosAtivos(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria(), solicitacaoFinanceira.getDataSolicitacao(), solicitacaoFinanceira.getCredor());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar convenios", ex, this.getClass().getName());
        }
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public List<Convenio> getConveniosList() {
        return conveniosList;
    }

    public void setConveniosList(List<Convenio> conveniosList) {
        this.conveniosList = conveniosList;
    }

    public List<DotacaoDto> getListaDotacaoDtos() {
        return listaDotacaoDtos;
    }

    public void setListaDotacaoDtos(List<DotacaoDto> listaDotacaoDtos) {
        this.listaDotacaoDtos = listaDotacaoDtos;
    }

    public List<Aditivo> getListaAditivos() {
        return listaAditivos;
    }

    public void validaVinculo() {
        try {
            if (solicitacaoFinanceira.getVinculo().equals(Vinculo.Folha_de_Pagamento) && !usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
                MenssagemUtil.addMessageWarn("Selecione um vinculo diferente.");
                //vinculo = null;
                solicitacaoFinanceira.setVinculo(null);
            } else if (solicitacaoFinanceira.getVinculo().equals(Vinculo.Obrigações) && !usuarioMb.getUsuarioSelecionado().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
                MenssagemUtil.addMessageWarn("Selecione um vinculo diferente.");
                //vinculo = null;
                solicitacaoFinanceira.setVinculo(null);
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao validar vinculo\r\n" + e);
        }
    }

    public void exportarSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            controller.exportaSolicitacao(solicitacaoFinanceira);
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Não foi possivel exportar soliitação", e, this.getClass().getName());
        }
    }

    public void selecionaSecretaria() {
        if (idUnidade.length() == 4) {
            for (UnidadeOrcamentaria u : usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias()) {
                if (idUnidade.equals(u.getUnidadeOrcamentariaPK().toString())) {
                    solicitacaoFinanceira.getCota().setUnidadeOrcamentaria(u);
                    alteraUnidadeOrcamentaria();
                    break;
                } else {
                    solicitacaoFinanceira.getCota().setUnidadeOrcamentaria(null);
                }
            }
            if (solicitacaoFinanceira.getCota().getUnidadeOrcamentaria() == null) {
                idUnidade = "";
                MenssagemUtil.addMessageInfo("Nenhuma secretária encontrada");
            }
        } else {
            solicitacaoFinanceira.getCota().setUnidadeOrcamentaria(null);
        }

    }

    public String getEmpenho() {
        return empenho;
    }

    public void setEmpenho(String empenho) {
        this.empenho = empenho;
    }

    public Mes getCompetenciaConsulta() {
        return competenciaConsulta;
    }

    public void setCompetenciaConsulta(Mes competenciaConsulta) {
        this.competenciaConsulta = competenciaConsulta;
    }

    public int getAnoConsulta() {
        return anoConsulta;
    }

    public void setAnoConsulta(int anoConsulta) {
        this.anoConsulta = anoConsulta;
    }

    public String getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
    }

    public List<SituacaoSolicitacao> getListaSituacaoSolicitacaos() {
        return listaSituacaoSolicitacaos;
    }

    public void setListaSituacaoSolicitacaos(List<SituacaoSolicitacao> listaSituacaoSolicitacaos) {
        this.listaSituacaoSolicitacaos = listaSituacaoSolicitacaos;
    }

    public BigDecimal getSaldoDotacao() {
        return saldoDotacao;
    }

    public void setSaldoDotacao(BigDecimal saldoDotacao) {
        this.saldoDotacao = saldoDotacao;
    }

    public Boolean getMostraSaldoDotacao() {
        return mostraSaldoDotacao;
    }

    public void setMostraSaldoDotacao(Boolean mostraSaldoDotacao) {
        this.mostraSaldoDotacao = mostraSaldoDotacao;
    }

}
