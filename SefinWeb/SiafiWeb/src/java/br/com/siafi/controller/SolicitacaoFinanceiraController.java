/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.dao.EmpenhoSolicitacaoDAO;
import br.com.gestor.dao.EmpenhoSolicitacaoItemDAO;
import br.com.gestor.modelo.EmpenhoSolicitacao;
import br.com.gestor.modelo.EmpenhoSolicitacaoItem;
import br.com.gestor.modelo.EmpenhoSolicitacaoItemPk;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.controler.Controller;
import br.com.guardiao.controler.SistemaConfiguracaoController;
import br.com.guardiao.dao.AreaAdministrativaDAO;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.Mes;
import br.com.siafi.dao.SolicitacaoFinanceiraDAO;
import br.com.siafi.modelo.Cota;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.sefin.enumerated.StatusOrdemCompra;
import br.com.siafi.dao.AditivoDAO;
import br.com.siafi.dao.CotaControleDAO;
import br.com.siafi.dao.CredorDAO;
import br.com.siafi.dao.ItemOrdemCompraDAO;
import br.com.siafi.managedbean.SolicitacaoFinanceiraMB;
import br.com.siafi.modelo.CategoriaDespesa;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.CentroCustoDto;
import br.com.siafi.modelo.CentroCustoUnidadeOrcamentariaDto;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Convenio;
import br.com.siafi.modelo.CotaControle;
import br.com.siafi.modelo.Credor;
import br.com.sefin.modelo.dto.CusteioDto;
import br.com.guardiao.modelo.Exercicio;
import br.com.guardiao.modelo.Usuario;
import br.com.sefin.enumerated.ModalidadeEmpenho;
import br.com.sefin.enumerated.TipoFluxo;
import br.com.siafi.modelo.FonteRecurso;
import br.com.siafi.modelo.ItemOrdemCompra;
import br.com.siafi.modelo.OrdemCompra;
import br.com.siafi.modelo.Pendencia;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import br.com.sefin.enumerated.Vinculo;
import br.com.sefin.modelo.dto.DTOInformacao;
import br.com.siafi.dao.EmpenhoDetalheDAO;
import br.com.siafi.modelo.Aditivo;
import br.com.siafi.modelo.Dotacao;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.Encaminhamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 18/04/2013 - Controler gerenciador do
 * modelo SolicitacaoFianceira
 *
 * @author Gilmário
 */
@Stateless
public class SolicitacaoFinanceiraController extends Controller<SolicitacaoFinanceira, String> implements Serializable {

    @EJB
    private SolicitacaoFinanceiraDAO dao;
    @EJB
    private EmpenhoSolicitacaoDAO gestorEmpenhoSolicitacaoDao;
    @EJB
    private EmpenhoSolicitacaoItemDAO empenhoSolicitacaoItemDao;
    @EJB
    private ItemOrdemCompraDAO itemOrdemCompraDao;
    @EJB
    private CredorDAO credorDao;
    @EJB
    private OrdemCompraController ordemCompraControler;
    @EJB
    private DotacaoController dotacaoDao;
    @EJB
    private AditivoDAO aditivoDao;
    @EJB
    private CotaControleDAO cotaControleDao;
    @EJB
    private EmpenhoDetalheDAO empenhoDetalheDao;
    @EJB
    private EncaminhamentoController encaminhamentoControler;
    @EJB
    private AreaAdministrativaDAO administrativaDao;
    @EJB
    private SistemaConfiguracaoController sistemaConfiguracaoController;
    private List<EmpenhoSolicitacaoItem> empenhoSolicitacaoItemGestorLista;
    private EmpenhoSolicitacao empenhoSolicitacao;
    private BigDecimal saldoCotaDisponivel = new BigDecimal(BigInteger.ZERO);

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void cancelarProcesso(SolicitacaoFinanceira sf) throws Exception {
        if (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Pendente)) {
            sf.setSituacaoSolicitacao(SituacaoSolicitacao.Cancelado);
            if (sf.getOrdemCompra() != null) {
                OrdemCompra ordemCompra = sf.getOrdemCompra();
                ordemCompra.setStatus(StatusOrdemCompra.Pendente);
                ordemCompraControler.atualizar(ordemCompra);
                sf.setOrdemCompra(null);
            }
            salvarouAtualizar(sf);
        } else {
            empenhoSolicitacao = gestorEmpenhoSolicitacaoDao.buscarEmpenhoAut(sf.getId());
            if (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
                sf.setSituacaoSolicitacao(SituacaoSolicitacao.Cancelado);

                if (empenhoSolicitacao != null) {
                    gestorEmpenhoSolicitacaoDao.removerEmpAut(empenhoSolicitacao);
                }
                if (sf.getVinculo().equals(Vinculo.Ordem_de_Compra)) {
                    OrdemCompra ordemCompra = sf.getOrdemCompra();
                    ordemCompra.setStatus(StatusOrdemCompra.Pendente);
                    ordemCompraControler.atualizar(ordemCompra);
                    sf.setOrdemCompra(null);
                }
                salvarouAtualizar(sf);
            } else {
//                if (empenhoSolicitacao.getNumeroEmpenho() == null || empenhoSolicitacao.getNumeroEmpenho().equals("")) {
                empenhoSolicitacaoItemGestorLista = empenhoSolicitacaoItemDao.buscarPorSolicitacao(sf.getId());
                for (EmpenhoSolicitacaoItem item : empenhoSolicitacaoItemGestorLista) {
                    empenhoSolicitacaoItemDao.remover(item);
                }
                gestorEmpenhoSolicitacaoDao.removerEmpAut(empenhoSolicitacao);
                sf.setSituacaoSolicitacao(SituacaoSolicitacao.Cancelado);
                if (sf.getVinculo().equals(Vinculo.Ordem_de_Compra)) {
                    OrdemCompra ordemCompra = sf.getOrdemCompra();
                    ordemCompra.setStatus(StatusOrdemCompra.Pendente);
                    ordemCompraControler.atualizar(ordemCompra);
                    sf.setOrdemCompra(null);
                }
                salvarouAtualizar(sf);
//                } else {
//                    Exception e = new Exception("Empenho ainda não foi cancelado no GESTOR!");
//                    throw e;
//                }

            }
        }

    }

    public void atualiza(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        dao.atualizar(solicitacaoFinanceira);
    }

    //Metodo utilizado para liquidar processo na contabilidade testando se o fluxo
    //é contabilidade ou contabilidade_tesouraria
    public void liquidarComFluxoAlternativo(SolicitacaoFinanceira sf) throws Exception {
        if (sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria)) {
            List<TipoAreaAdm> listadeTipos = new ArrayList<>();
            listadeTipos.add(TipoAreaAdm.Tesouraria);
            List<AreaAdministrativa> aas = administrativaDao.listarPorTipo(listadeTipos);
            Encaminhamento enc = new Encaminhamento();
            enc.setDataEncaminhamento(new Date());
            enc.setDestino(aas.get(0));
            enc.setObservacao("Processo encaminhado da contabilidade direto para tesouraria!");
            enc.setSolicitacaoFinanceira(sf);
            enc.setUsuarioEncaminhou(sf.getUsuario());
            encaminhamentoControler.salvarouAtualizar(enc);
        }
        dao.atualizar(sf);
    }

    public boolean checarSaldoContrato(Aditivo aditivo, BigDecimal valorProcesso) throws Exception {
        BigDecimal saldoContrato = saldoAditivo(aditivo).subtract(valorProcesso);
        return saldoContrato.compareTo(BigDecimal.ZERO) >= 0;
    }

    public BigDecimal saldoContrato(Contrato contrato) throws Exception {
        BigDecimal valorContrato = aditivoDao.valorContrato(contrato.getId());
        BigDecimal valorSolicitacao = solicitacaoPorContrato(contrato.getId());
        valorSolicitacao = valorSolicitacao.subtract(solicitacaoPorAditivoOrdemCompra(contrato.getId()));
        if (valorSolicitacao == null) {
            valorSolicitacao = BigDecimal.ZERO;
        }
        if (valorContrato == null) {
            valorContrato = new BigDecimal(BigInteger.ZERO);
        }
        return valorContrato.subtract(valorSolicitacao);
    }

    public BigDecimal saldoAditivoOrdemCompra(Aditivo a) throws Exception {
        BigDecimal valorContrato = a.getValor();
        BigDecimal valorSolicitacao = solicitacaoPorAditivoOrdemCompra(a);
        if (valorSolicitacao == null) {
            valorSolicitacao = BigDecimal.ZERO;
        }
        if (valorContrato == null) {
            valorContrato = new BigDecimal(BigInteger.ZERO);
        }
        return valorContrato.subtract(valorSolicitacao);
    }

    public BigDecimal saldoAditivo(Aditivo a) throws Exception {
        BigDecimal valorContrato = a.getValor();
        BigDecimal valorSolicitacao = solicitacaoPorAditivo(a);
        BigDecimal valorSolicitadoOrdemCompra = solicitacaoPorAditivoOrdemCompra(a);
        if (valorSolicitadoOrdemCompra == null) {
            valorSolicitadoOrdemCompra = BigDecimal.ZERO;
        }
        if (valorSolicitacao == null) {
            valorSolicitacao = BigDecimal.ZERO;
        }
        if (valorContrato == null) {
            valorContrato = new BigDecimal(BigInteger.ZERO);
        }
        return valorContrato.subtract(valorSolicitadoOrdemCompra).subtract(valorSolicitacao);
    }

    public BigDecimal saldoAditivo(Aditivo a, OrdemCompra o) throws Exception {
        BigDecimal valorContrato = a.getValor();
        BigDecimal valorSolicitacao = solicitacaoPorAditivo(a);
        BigDecimal valorSolicitadoOrdemCompra = solicitacaoPorAditivoOrdemCompra(a, o);
        if (valorSolicitadoOrdemCompra == null) {
            valorSolicitadoOrdemCompra = BigDecimal.ZERO;
        }
        if (valorSolicitacao == null) {
            valorSolicitacao = BigDecimal.ZERO;
        }
        if (valorContrato == null) {
            valorContrato = new BigDecimal(BigInteger.ZERO);
        }
        return valorContrato.subtract(valorSolicitadoOrdemCompra).subtract(valorSolicitacao);
    }

    public void salvarProcessoVinculoContrato(SolicitacaoFinanceira solicitacaoFinanceira, Aditivo aditivo) throws Exception {
        if (checarSaldoContrato(aditivo, solicitacaoFinanceira.getValor())) {
            solicitacaoFinanceira.setContrato(aditivo.getContrato());
            solicitacaoFinanceira.setLicitacao(aditivo.getContrato().getLicitacao());
            solicitacaoFinanceira.setAditivo(aditivo);
            solicitacaoFinanceira.setConvenio(null);
        } else {
            Exception e = new Exception("Contrato não tem saldo!");
            throw e;
        }
    }

    public void salvarProcessoVinculoOrdemCompra(SolicitacaoFinanceira solicitacaoFinanceira, OrdemCompra ordemCompra) throws Exception {
        int v = solicitacaoFinanceira.getCota().getValor().compareTo(ordemCompra.getValorTotal());
        if (v >= 0) {
            solicitacaoFinanceira.setOrdemCompra(ordemCompra);
            solicitacaoFinanceira.setLicitacao(ordemCompra.getLicitacao());
            solicitacaoFinanceira.setConvenio(null);
            ordemCompra.setStatus(StatusOrdemCompra.Aprovada);
//            ordemCompraControler.salvarouAtualizar(ordemCompra);

        } else {
            Exception e = new Exception("Ordem de compra com valor inferior ao do processo!!");
            throw e;
        }

    }

    public void salvarVinculoFolhaPagamento(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        solicitacaoFinanceira.setContrato(null);
        solicitacaoFinanceira.setConvenio(null);
        solicitacaoFinanceira.setOrdemCompra(null);
        solicitacaoFinanceira.setLicitacao(null);

    }

    public void salvarVinculoObrigacoes(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        solicitacaoFinanceira.setContrato(null);
        solicitacaoFinanceira.setConvenio(null);
        solicitacaoFinanceira.setOrdemCompra(null);
        solicitacaoFinanceira.setLicitacao(null);

    }

    public void salvarVinculoCompraDireta(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Pendente);
        solicitacaoFinanceira.setContrato(null);
        solicitacaoFinanceira.setOrdemCompra(null);
        solicitacaoFinanceira.setLicitacao(null);
        solicitacaoFinanceira.setConvenio(null);
    }

    public void salvarVinculoConvenio(SolicitacaoFinanceira solicitacaoFinanceira, Convenio convenio) throws Exception {
        solicitacaoFinanceira.setContrato(null);
        solicitacaoFinanceira.setOrdemCompra(null);
        solicitacaoFinanceira.setLicitacao(null);
        solicitacaoFinanceira.setConvenio(convenio);
        //falta checar se o convenio tem saldo

    }

    public void salvarVinculoDiaria(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Pendente);
        solicitacaoFinanceira.setContrato(null);
        solicitacaoFinanceira.setOrdemCompra(null);
        solicitacaoFinanceira.setLicitacao(null);
        solicitacaoFinanceira.setConvenio(null);
    }

    public boolean checarSaldoDotacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        // A configuração deve ser criada atraves do sistema Guadião como tipo boolean
        if ((Boolean) sistemaConfiguracaoController.pegarValorConfiguracaoDef(Boolean.TRUE, "CHECAR_SALDO_DOTACAO", "SAF")) {
            BigDecimal x = dotacaoDao.saldo(solicitacaoFinanceira.getDotacao());
            if (x.compareTo(solicitacaoFinanceira.getValor()) >= 0) {
                solicitacaoFinanceira.setDotacao(solicitacaoFinanceira.getDotacao());
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    //Permite apenas que usuários do financeiro e da contabilidade tenham acesso a abrir processo
    public void podeSalvarSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        if (!(solicitacaoFinanceira.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro) || solicitacaoFinanceira.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade))) {
            Exception e = new Exception("Seu usuário não tem permissão para abrir um processo financeiro!");
            throw e;
        }
    }

    //Renderizar o botão do fluxo da contabilidade
    public boolean fluxoContabilidade(SolicitacaoFinanceira sf) {
        return (sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade) || sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria));

    }

    //Renderizar o botão do fluxo da contabilidade
    public boolean fluxoContabilidadeTesouraria(SolicitacaoFinanceira sf) {
        return (sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria));

    }

    public void encaminharFluxoContabilidade(SolicitacaoFinanceira sf) throws Exception {
        if (sf.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
            if (fluxoContabilidade(sf) && sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
                if (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
                    String s = getEmpenho(sf);
                    if (s == null) {
                        exportaSolicitacao(sf);
                    } else {
                        sf.setSituacaoSolicitacao(SituacaoSolicitacao.Empenhado);
                    }
                } else if (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Empenhado)) {
                    if (!empenhoDetalheDao.listarPorSolicitacao(sf).isEmpty()) {
                        sf.setSituacaoSolicitacao(SituacaoSolicitacao.Liquidado);

                    }
                } else if (sf.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Liquidado)) {
                    List<EmpenhoDetalhe> detalhes = empenhoDetalheDao.listarPorSolicitacao(sf);
                    sf.setSituacaoSolicitacao(SituacaoSolicitacao.Pago);
                    for (EmpenhoDetalhe e : detalhes) {
                        if (e.getDataPagamento() != null) {
                            sf.setSituacaoSolicitacao(SituacaoSolicitacao.Liquidado);
                        }
                    }
                }
                //  salvarouAtualizar(sf);
            }
        }

    }

    public void addSolicitacao(SolicitacaoFinanceira sf, Dotacao d, OrdemCompra compra, Aditivo aditivo, Convenio convenio, Usuario u) throws Exception {
        podeSalvarSolicitacao(sf);
        credorPendencia(sf.getCredor());
        sf.setDotacao(d);
        verificaSaldoAntesDeSalvar(sf);
        if (sf.getVinculo().equals(Vinculo.Ordem_de_Compra)) {
            salvarProcessoVinculoOrdemCompra(sf, compra);
        } else if (sf.getVinculo().equals(Vinculo.Contrato)) {
            salvarProcessoVinculoContrato(sf, aditivo);
        } else if (sf.getVinculo() == Vinculo.Folha_de_Pagamento) {
            //Somente a contabilidade pode emitir solicitações financeiras com esse vínculo
            if (sf.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
                salvarVinculoFolhaPagamento(sf);
            } else {
                throw new Exception("Você não pode Emitir Solicitações financeiras com esse vinculo");
            }
        } else if (sf.getVinculo() == Vinculo.Compra_Direta) {
            salvarVinculoCompraDireta(sf);
        } else if (sf.getVinculo() == Vinculo.Convênio) {
            salvarVinculoConvenio(sf, convenio);
        } else if (sf.getVinculo() == Vinculo.Diária) {
            salvarVinculoDiaria(sf);
        } else if (sf.getVinculo() == Vinculo.Obrigações) {
            //Somente a contabilidade pode emitir solicitações financeiras com esse vínculo
            if (sf.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
                salvarVinculoObrigacoes(sf);
            } else {
                throw new Exception("Você não pode Emitir Solicitações financeiras com esse vinculo");
            }
        }
        sf.setLocal(u.getAreaAdministrativa());
        salvar(sf);
    }

    @Override
    public void salvar(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        verificaSaldoAntesDeSalvar(solicitacaoFinanceira);
        // saldo da cota
        if (solicitacaoFinanceira.getValor().compareTo(saldoCotaDisponivel) <= 0 && checarSaldoDotacao(solicitacaoFinanceira)) {
            if (solicitacaoFinanceira.getId() == null) {
                solicitacaoFinanceira.setId(gerarCodigo());
                if (solicitacaoFinanceira.getCota().isAutorizadoAutomatico() && (solicitacaoFinanceira.getVinculo() != Vinculo.Compra_Direta) && (solicitacaoFinanceira.getVinculo() != Vinculo.Diária)) {
                    solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Aprovado);
                }
                encaminharFluxoContabilidade(solicitacaoFinanceira);
                dao.salvar(solicitacaoFinanceira);
            } else {
                encaminharFluxoContabilidade(solicitacaoFinanceira);
                dao.atualizar(solicitacaoFinanceira);
            }
            if (solicitacaoFinanceira.getOrdemCompra() != null) {
                // Retirei do outro lado para nãocolocar a ordem compra como pendente no caso de erro
                ordemCompraControler.salvarouAtualizar(solicitacaoFinanceira.getOrdemCompra());
            }

        } else {
            Exception e = new Exception("Não foi possivel realizar a solicitação!\r\n Saldo da Dotação Insuficiente.");
            throw e;
        }
    }

    /**
     * Rotina para vericidação de saldo da cota quando o usuário busca a cota
     *
     * @param solicitacaoFinanceira
     * @return
     */
    public BigDecimal verificaSaldo(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            if (solicitacaoFinanceira.getCota().getUnidadeOrcamentaria() != null && solicitacaoFinanceira.getCota() != null) {
                BigDecimal valorUtilizado = saldoUtilizado(solicitacaoFinanceira.getCota(), solicitacaoFinanceira.getMesCompetencia(), solicitacaoFinanceira.getAnoCompetencia());
                // Está em modo de edição trazer o saldo original da solicitação
                BigDecimal valorAnterior = BigDecimal.ZERO;
                if (solicitacaoFinanceira.getId() != null) {
                    valorAnterior = saldoAnterior(solicitacaoFinanceira);
                }
                CotaControle cotaControle = cotaControleDao.valorAtualCota(solicitacaoFinanceira.getCota(), solicitacaoFinanceira.getMesCompetencia(), solicitacaoFinanceira.getExercicio());
                if (cotaControle != null) {
                    saldoCotaDisponivel = cotaControle.getValor().add(valorAnterior).subtract(valorUtilizado);

                } else {
                    MenssagemUtil.addMessageWarn("Não há valor na cota para esse período");
                    saldoCotaDisponivel = new BigDecimal(0);
                }
            } else {
                saldoCotaDisponivel = new BigDecimal(0);

            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaoFinanceiraMB.class
                    .getName()).log(Level.SEVERE, null, ex);
            saldoCotaDisponivel = new BigDecimal(0);
        }
        return saldoCotaDisponivel;
    }

    /**
     * Rotina para vericidação de saldo da cota antes de registrar no banco de
     * dados
     *
     * @param solicitacaoFinanceira
     */
    public void verificaSaldoAntesDeSalvar(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            if (solicitacaoFinanceira.getCota().getUnidadeOrcamentaria() != null && solicitacaoFinanceira.getCota() != null) {
                BigDecimal valorUtilizado = saldoUtilizado(solicitacaoFinanceira.getCota(), solicitacaoFinanceira.getMesCompetencia(), solicitacaoFinanceira.getAnoCompetencia());
                CotaControle cotaControle = cotaControleDao.valorAtualCota(solicitacaoFinanceira.getCota(), solicitacaoFinanceira.getMesCompetencia(), solicitacaoFinanceira.getExercicio());
                BigDecimal valorAnterior = BigDecimal.ZERO;
                if (solicitacaoFinanceira.getId() != null) {
                    valorAnterior = saldoAnterior(solicitacaoFinanceira);
                }
                saldoCotaDisponivel = cotaControle.getValor().subtract(valorUtilizado).add(valorAnterior);
            } else {
                saldoCotaDisponivel = new BigDecimal(0);

            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaoFinanceiraMB.class
                    .getName()).log(Level.SEVERE, null, ex);
            saldoCotaDisponivel = new BigDecimal(0);
        }
    }

    /**
     * Validar o saldo disponivel
     *
     * @param cota
     * @param mes
     * @param ano
     * @return
     * @throws Exception
     */
    public BigDecimal saldoUtilizado(Cota cota, Mes mes, Integer ano) throws Exception {
        // Buscar as solicitações financeiras emitidas para aquela Unidade Orçamentaria
        // que seja relativas a uma determinada cota
        // Somar os valores
        // Não permitir fechar com o valor total maior que o valor da conta///
        BigDecimal valor = dao.saldoUtilizado(cota, mes, ano);
        if (valor == null) {
            valor = new BigDecimal("0.00");
        }
        return valor;
    }

    @Override
    public List<SolicitacaoFinanceira> listarTodos(String ordem) throws Exception {
        return dao.listarTodos(ordem);
    }

    public List<SolicitacaoFinanceira> listarSolicitacoesCusteio(Exercicio e, CategoriaDespesa cd, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        return dao.listarSolicitacoesCusteio(e, cd, unidadeOrcamentarias);
    }

    public String gerarCodigo() throws Exception {
        return dao.gerarCodigo();
    }

    public List<SolicitacaoFinanceira> listarSolicitacaoSituacao(UnidadeOrcamentaria uo, SituacaoSolicitacao situacao, String id, List<UnidadeOrcamentaria> uos) throws Exception {
        if (uo == null) {
            return dao.listarSolicitacoesSituacao(id, uos, situacao);
        } else {
            return dao.listarSolicitacoesSituacao(id, uo, situacao);

        }
    }

    public List<SolicitacaoFinanceira> listarFiltros(Credor credor, int ano, Mes competencia) {
        return dao.listar(credor, ano, competencia);
    }

    public List<SolicitacaoFinanceira> listarFiltros(Date dtIn, Date dtFi, String numero, List<SituacaoSolicitacao> situacaoSolicitacao, UnidadeOrcamentaria unidadeOrcamentaria, String ordem, Credor credor, List<UnidadeOrcamentaria> unidadeOrcamentarias, String empenho, int ano, Mes competenciaConsulta) throws Exception {
        if (credor.getId() == null) {
            credor = null;
        }
        if (unidadeOrcamentaria != null) {
            return dao.listarFiltrosUnidadeOrcamentaria(dtIn, dtFi, numero, situacaoSolicitacao, unidadeOrcamentaria, credor, empenho, ano, competenciaConsulta);
        } else {
            return dao.listarFiltros(dtIn, dtFi, numero, situacaoSolicitacao, credor, unidadeOrcamentarias, empenho, ano, competenciaConsulta);
        }
    }

    public BigDecimal solicitacaoPorContrato(Integer contrato) throws Exception {
        return dao.solicitacaoPorContrato(contrato);
    }

    //Metodo que lista o valor total de ordens de compra realizadas por contrato
    public BigDecimal solicitacaoPorAditivoOrdemCompra(Integer contrato) throws Exception {
        return dao.solicitacaoPorAContratordemCompra(contrato);
    }

    //Metodo que lista o valor total de ordens de compra realizadas por contrato
    public BigDecimal solicitacaoPorAditivoOrdemCompra(Aditivo a) throws Exception {
        return dao.solicitacaoPorAditivoOrdemCompra(a);
    }

    //Metodo que lista o valor total de ordens de compra realizadas por contrato
    public BigDecimal solicitacaoPorAditivoOrdemCompra(Aditivo a, OrdemCompra o) throws Exception {
        return dao.solicitacaoPorAditivoOrdemCompra(a, o);
    }

    //Metodo que lista o valor total de solicitações por contrato em cima do aditivo
    public BigDecimal solicitacaoPorAditivo(Aditivo a) throws Exception {
        return dao.solicitacaoPorAditivo(a);
    }

    public List<SolicitacaoFinanceira> listarPorLocal(AreaAdministrativa areaAdministrativa, UnidadeOrcamentaria unidadeOrcamentaria, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        if (situacaoSolicitacao == null) {
            return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentaria, areaAdministrativa);
        } else {
            return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentaria, areaAdministrativa, situacaoSolicitacao);
        }
    }

    public List<SolicitacaoFinanceira> listarUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentaria);
    }

    public void exportaSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        EmpenhoSolicitacao empenhoSolicitacao;
        try {
            empenhoSolicitacao = gestorEmpenhoSolicitacaoDao.carregar(solicitacaoFinanceira.getId());
            if (empenhoSolicitacao == null) {
                empenhoSolicitacao = new EmpenhoSolicitacao();

            }
        } catch (Exception e) {
            empenhoSolicitacao = new EmpenhoSolicitacao();
        }

        empenhoSolicitacao.setCodCredor(solicitacaoFinanceira.getCredor().getId().toString());
        empenhoSolicitacao.setCodExercicio(solicitacaoFinanceira.getExercicio().getAno());
        empenhoSolicitacao.setCodProjetoAtividadeAcao(solicitacaoFinanceira.getDotacao().getProjetoAtividade().getTipo() + solicitacaoFinanceira.getDotacao().getProjetoAtividade().getAcao());
        empenhoSolicitacao.setDescricao(solicitacaoFinanceira.getHistorico());
        empenhoSolicitacao.setCodUnidadeOrcamentaria(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getIdOrgao() + solicitacaoFinanceira.getCota().getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getId());
        empenhoSolicitacao.setCodCentroCusto(solicitacaoFinanceira.getCota().getDespesa().getId());
        empenhoSolicitacao.setCodDotacao(solicitacaoFinanceira.getDotacao().getDotacalPk().getId_reduzido());
        // empenhoSolicitacao.setNumeroEmpenho(solicitacaoFinanceira.getEmpenho());
        if (solicitacaoFinanceira.getContrato() != null) {
            if (solicitacaoFinanceira.getContrato().getLicitacao() != null) {
                empenhoSolicitacao.setIdLicitacao(solicitacaoFinanceira.getContrato().getLicitacao().getId().toString());
                empenhoSolicitacao.setContrato(solicitacaoFinanceira.getContrato().getId().toString());
            }
        } else if (solicitacaoFinanceira.getOrdemCompra() != null) {
            empenhoSolicitacao.setIdLicitacao(solicitacaoFinanceira.getOrdemCompra().getLicitacao().getId().toString());
            empenhoSolicitacao.setContrato(solicitacaoFinanceira.getOrdemCompra().getContrato().getId().toString());
        }
        empenhoSolicitacao.setNomeCredor(solicitacaoFinanceira.getCredor().getPessoa().getNome());
        empenhoSolicitacao.setNomeUsuario(solicitacaoFinanceira.getUsuario().getDocumento());
        empenhoSolicitacao.setNumeroSolicitacao(solicitacaoFinanceira.getId());
        empenhoSolicitacao.setNumeroelementoDespesa(solicitacaoFinanceira.getDotacao().getNaturezaDespesa().getId());
        empenhoSolicitacao.setSequencia(1);
        empenhoSolicitacao.setValorEmpenho(solicitacaoFinanceira.getValor());
        if (solicitacaoFinanceira.getFonteRecurso() != null) {
            empenhoSolicitacao.setCodTipoFonte(solicitacaoFinanceira.getFonteRecurso().getTipoFonteRecurso().getId());
        }
        gestorEmpenhoSolicitacaoDao.salvar(empenhoSolicitacao);
        if (solicitacaoFinanceira.getOrdemCompra() != null) {
            // Exportar Itens
            List<ItemOrdemCompra> itensOrdemCompra = itemOrdemCompraDao.litarPorOrdemCompra(solicitacaoFinanceira.getOrdemCompra());
            for (ItemOrdemCompra i : itensOrdemCompra) {
                EmpenhoSolicitacaoItemPk pk = new EmpenhoSolicitacaoItemPk();
                pk.setItemCodigo(i.getItemLicitacao().getItem().getId().intValue());
                pk.setNumeroSolicitacao(solicitacaoFinanceira.getId());
                pk.setSequencia(1);
                EmpenhoSolicitacaoItem empenhoSolicitacaoItem = new EmpenhoSolicitacaoItem();
                empenhoSolicitacaoItem.setSolicitacaoItemPk(pk);
                empenhoSolicitacaoItem.setQuantidade(i.getQuantidade());
                empenhoSolicitacaoItem.setValorTotal(i.getValorUnitario().multiply(i.getQuantidade()).setScale(2, RoundingMode.HALF_UP));
                empenhoSolicitacaoItem.setValorUnitario(i.getValorUnitario().setScale(3, RoundingMode.HALF_UP));
                empenhoSolicitacaoItemDao.atualizar(empenhoSolicitacaoItem);
            }
        }
    }

    /*
     * MEtodo para pegar os credores com debito na arrecadação e com pispase em branco
     */
    public void credorPendencia(Credor credor) throws Exception {
        if (credor.getDocumento() != null) {

            Pendencia pen = credorDao.credorComPendencia(credor.getDocumento());
            if (credor.getTipoCredor().getId() == 2) {
                if (credor.getPisPasep() == null || credor.getPisPasep().equals("")) {
                    Exception e = new Exception("Credor sem PIS/PASEP cadastrado!");
                    throw e;

                }
            }
            if (pen != null) {
                Exception e = new Exception("Credor com pendências na SEFIN!");
                throw e;

            }
        }
    }

    //Checar o limite de compra direta
    public boolean checarCompraDireta(Credor credor, UnidadeOrcamentaria uo, Exercicio exe) throws Exception {
        return dao.checarCompraDireta(credor, uo, exe);
    }

    public List<SolicitacaoFinanceira> listaSolicitacaoData(Date dtIni, Date dtFim) throws Exception {
        return dao.listaSolicitacaoData(dtIni, dtFim);
    }

    public List<SolicitacaoFinanceira> listarPorCentroCusto(CentroCusto cc) throws Exception {
        return dao.listarPorCentroCusto(cc);
    }

    //Ordenada por despesa
    public List<SolicitacaoFinanceira> listaSolicitacaoDataDespesa(Date dtIni, Date dtFim, List<UnidadeOrcamentaria> uos) throws Exception {
        return dao.listaSolicitacaoDataDespesa(dtIni, dtFim, uos);
    }

    //Ordenada por despesa
    public List<SolicitacaoFinanceira> listaSolicitacaoDataDespesa(Date dtIni, Date dtFim, List<UnidadeOrcamentaria> uos, UnidadeOrcamentaria uo) throws Exception {
        if (uo == null) {
            return dao.listaSolicitacaoDataDespesa(dtIni, dtFim, uos);

        } else {
            return dao.listaSolicitacaoDataDespesa(dtIni, dtFim, uo);

        }
    }

    //Ordenada por Unidade Orçamentaria
    public List<SolicitacaoFinanceira> listaSolicitacaoUnidadeOrcamentaria(Date dtIni, Date dtFim, List<UnidadeOrcamentaria> uos) throws Exception {
        return dao.listaSolicitacaoUnidadeOrcamentaria(dtIni, dtFim, uos);
    }

    public String getEmpenho(SolicitacaoFinanceira solicitacaoFinanceira) {
        return gestorEmpenhoSolicitacaoDao.buscarEmpenho(solicitacaoFinanceira.getId());
    }

    public ModalidadeEmpenho getModalidade(SolicitacaoFinanceira solicitacaoFinanceira) {
        String mod;
        mod = gestorEmpenhoSolicitacaoDao.buscarModalidadeEmpenho(solicitacaoFinanceira.getId());
        if (mod != null) {
            switch (mod) {
                case "O":
                    return ModalidadeEmpenho.ORDINÁRIO;
                case "E":
                    return ModalidadeEmpenho.ESTIMATIVO;
                default:
                    return ModalidadeEmpenho.GLOBAL;
            }
        } else {
            return null;
        }

    }

    // Listar Solicitações financeiras pendentes de pagamento com Situacao Liquidado
    public List<SolicitacaoFinanceira> listarPendentesPagamento(List<UnidadeOrcamentaria> unidadesOrcamentaria, TipoAreaAdm area) {
        return dao.listarPendentesPagamento(unidadesOrcamentaria, area);
    }

    public List<SolicitacaoFinanceira> listarPorLocal(AreaAdministrativa areaAdministrativa, List<UnidadeOrcamentaria> unidadeOrcamentarias, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        if (situacaoSolicitacao == null) {
            return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentarias, areaAdministrativa);
        } else {
            return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentarias, areaAdministrativa, situacaoSolicitacao);
        }
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(UnidadeOrcamentaria unidadeOrcamentaria, FonteRecurso fonteRecurso, Date dataInicio, Date dataFinal, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        if (fonteRecurso == null) {
            if (unidadeOrcamentaria == null) {
                if (dataInicio == null) {
                    return dao.listarPorFonteRecurso(unidadeOrcamentarias);
                } else {
                    return dao.listarPorFonteRecurso(dataInicio, dataFinal, unidadeOrcamentarias);
                }
            } else if (dataInicio == null) {
                return dao.listarPorFonteRecurso(unidadeOrcamentaria);
            } else {
                return dao.listarPorFonteRecurso(dataInicio, dataFinal, unidadeOrcamentaria);
            }
        } else {
            if (unidadeOrcamentaria == null) {
                if (dataInicio == null) {
                    return dao.listarPorFonteRecurso(fonteRecurso, unidadeOrcamentarias);
                } else {
                    return dao.listarPorFonteRecurso(fonteRecurso, dataInicio, dataFinal, unidadeOrcamentarias);
                }
            } else if (dataInicio == null) {
                return dao.listarPorFonteRecurso(fonteRecurso, unidadeOrcamentaria);
            } else {
                return dao.listarPorFonteRecurso(fonteRecurso, dataInicio, dataFinal, unidadeOrcamentaria);
            }
        }

    }

    public List<SolicitacaoFinanceira> listarPorCota(Mes competencia, UnidadeOrcamentaria uo) throws Exception {
        return dao.listarCompetencia(competencia, uo);

    }

    public List<CentroCustoDto> listarCentroCusto(Date dtIni, Date dtFim) throws Exception {
        return dao.listarCentroCusto(dtIni, dtFim);
    }

    public List<CentroCustoDto> listarCentroCustoDto(UnidadeOrcamentaria uo) throws Exception {
        return dao.listarCentroCustoDto(uo);
    }

    public List<CentroCustoUnidadeOrcamentariaDto> listarCentroCustoUnidadeOrcamentaria(CentroCusto centroCusto, List<UnidadeOrcamentaria> uos) throws Exception {
        return dao.listarUnidadeOrcamentariaCentroCusto(centroCusto, uos);
    }

    public List<CusteioDto> listarCusteioDtoPorUnidadeOrc(List<SolicitacaoFinanceira> solicitacoes) {
        List<CusteioDto> listCusteio = new ArrayList<>();
        CusteioDto custeioDto = new CusteioDto();
        custeioDto.setSolicitacaoFinanceiras(new ArrayList<SolicitacaoFinanceira>());
        for (SolicitacaoFinanceira s : solicitacoes) {
            // verifica se a unidade orçamentaria mudou
            if (s.getCota().getUnidadeOrcamentaria().equals(custeioDto.getUnidadeOrcamentaria())) {
                custeioDto.getSolicitacaoFinanceiras().add(s);
            } else {
                //caso tenha mudado adicona o dto nalista cria uma novo
                custeioDto = new CusteioDto();
                custeioDto.setSolicitacaoFinanceiras(new ArrayList<SolicitacaoFinanceira>());
                custeioDto.setUnidadeOrcamentaria(s.getCota().getUnidadeOrcamentaria());
                custeioDto.getSolicitacaoFinanceiras().add(s);
                listCusteio.add(custeioDto);
            }
        }
        for (CusteioDto cust : listCusteio) {
            cust.calculaTotal();
        }

        return listCusteio;
    }

    public List<SolicitacaoFinanceira> listarSolicitacoesCusteio(Exercicio exercicio, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return dao.listarSolicitacoesCusteio(exercicio, unidadeOrcamentaria);
    }

    public List<SolicitacaoFinanceira> listarPorCentroCustoUnidadeOrcamentaria(CentroCusto centroCusto, UnidadeOrcamentaria orcamentaria) throws Exception {
        return dao.listarPorCentroCustoUnidadeOrcamentaria(centroCusto, orcamentaria);
    }

    public List<SolicitacaoFinanceira> listarPendentesPagamentoPorListaUnidades(List<UnidadeOrcamentaria> unidades) throws Exception {
        return dao.listarPendentesPagamento(unidades);
    }

    public List<SolicitacaoFinanceira> listarPendentesPagamento(UnidadeOrcamentaria unidade, List<UnidadeOrcamentaria> unidades) throws Exception {
        if (unidade != null) {
            return listarPendentesPagamentoPorUnidade(unidade);
        } else {
            return dao.listarPendentesPagamento(unidades);
        }
    }

    public List<SolicitacaoFinanceira> listarPendentesPagamentoPorUnidade(UnidadeOrcamentaria unidade) throws Exception {
        return dao.listarPendentesPagamento(unidade);
    }

    public List<SolicitacaoFinanceira> lista(Date dtIni, Date dtFim, List<UnidadeOrcamentaria> uos) throws Exception {
        return dao.lista(dtIni, dtFim, uos);
    }

    public List<SolicitacaoFinanceira> lista(Date dtIni, Date dtFim, UnidadeOrcamentaria uo) throws Exception {
        return dao.lista(dtIni, dtFim, uo);
    }

    public Boolean pagarSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        BigDecimal valorLiquidado = dao.somaLiquidacaoPagas(solicitacaoFinanceira);
        if (valorLiquidado != null) {
            if (solicitacaoFinanceira.getValor().compareTo(valorLiquidado) == 0) {
                solicitacaoFinanceira.setSituacaoSolicitacao(SituacaoSolicitacao.Pago);
                this.salvarouAtualizar(solicitacaoFinanceira);
                return true; //Pode pagar
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean checarLiquidacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        BigDecimal valorLiquidado = dao.somaLiquidacaoPagas(solicitacaoFinanceira);
        if (valorLiquidado != null) {
            return solicitacaoFinanceira.getValor().compareTo(valorLiquidado) == 0 && solicitacaoFinanceira.getSituacaoSolicitacao() != SituacaoSolicitacao.Pago;
        } else {
            return false;
        }
    }

    public BigDecimal saldoAnterior(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            return dao.saldoAnterior(solicitacaoFinanceira);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }

    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria unidadeOrcamentaria, Convenio convenio, Credor credor, Date dataInicio, Date dataFinal) throws Exception {
        if (convenio == null) {
            // Trazer todos as solicitações que possuem convenio
            if (credor == null) {
                // sem credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidadeOrcamentaria);
                } else {
                    return dao.listarConvenio(unidadeOrcamentaria, dataInicio, dataFinal);
                }
            } else {
                // com credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidadeOrcamentaria, credor);
                } else {
                    // com data
                    return dao.listarConvenio(unidadeOrcamentaria, credor, dataInicio, dataFinal);
                }
            }
        } else {
            if (credor == null) {
                // sem credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidadeOrcamentaria, convenio);
                } else {
                    return dao.listarConvenio(unidadeOrcamentaria, convenio, dataInicio, dataFinal);
                }
            } else {
                // com credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidadeOrcamentaria, convenio, credor);
                } else {
                    // com data
                    return dao.listarConvenio(unidadeOrcamentaria, convenio, credor, dataInicio, dataFinal);
                }
            }
        }
        //return null;
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> unidades, Convenio convenio, Credor credor, Date dataInicio, Date dataFinal) throws Exception {
        if (convenio == null) {
            // Trazer todos as solicitações que possuem convenio
            if (credor == null) {
                // sem credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidades);
                } else {
                    return dao.listarConvenio(unidades, dataInicio, dataFinal);
                }
            } else {
                // com credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidades, credor);
                } else {
                    // com data
                    return dao.listarConvenio(unidades, credor, dataInicio, dataFinal);
                }
            }
        } else {
            if (credor == null) {
                // sem credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidades, convenio);
                } else {
                    return dao.listarConvenio(unidades, convenio, dataInicio, dataFinal);
                }
            } else {
                // com credor
                if (dataInicio == null || dataFinal == null) {
                    // sem data
                    return dao.listarConvenio(unidades, convenio, credor);
                } else {
                    // com data
                    return dao.listarConvenio(unidades, convenio, credor, dataInicio, dataFinal);
                }
            }
        }
    }

    public List<SolicitacaoFinanceira> listarFiltrosTesouraria(UnidadeOrcamentaria unidadeOrcamentaria, Credor credor, List<UnidadeOrcamentaria> unidadeOrcamentarias, SituacaoSolicitacao situacao, Date dataInicio, Date dataFinal) throws Exception {
        return dao.listarFiltrosUnidadeOrcamentariaTesouraria(unidadeOrcamentarias, unidadeOrcamentaria, credor, situacao, dataInicio, dataFinal);
    }

    public List<SolicitacaoFinanceira> listarFiltrosTesourariaPagamento(UnidadeOrcamentaria unidadeOrcamentaria, Credor credor, List<UnidadeOrcamentaria> unidadeOrcamentarias, SituacaoSolicitacao situacao, Date dataInicio, Date dataFinal, Date datapi, Date datapf) throws Exception {
        return dao.listarFiltrosUnidadeOrcamentariaTesourariaPagamentos(unidadeOrcamentarias, unidadeOrcamentaria, credor, situacao, dataInicio, dataFinal, datapi, datapf);
    }

    public Date getDataEmpenho(SolicitacaoFinanceira sol) {
        return gestorEmpenhoSolicitacaoDao.buscarDataEmpenho(sol.getId(), sol.getEmpenho());
    }

    public List<DTOInformacao> listarInformacao(AreaAdministrativa areaAdministrativa, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        return dao.listarInformacao(areaAdministrativa, unidadeOrcamentarias);
    }

    public List<SolicitacaoFinanceira> listar(Dotacao dotacao) throws Exception {
        return dao.listar(dotacao);
    }

    public List<SolicitacaoFinanceira> listarContratoOrdemCompra(Contrato c) {
        return dao.listarContratoOrdemCompra(c);
    }

    public List<SolicitacaoFinanceira> listarContratoSemOrdemCompra(Contrato c) {
        return dao.listarContratoSemOrdemCompra(c);
    }

//    public List<SolicitacaoFinanceira> listar(Contrato c) {
//        return dao.listarContratoOrdemCompra(c);
//    }
}
