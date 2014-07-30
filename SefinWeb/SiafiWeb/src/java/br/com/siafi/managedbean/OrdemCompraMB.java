/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sefin.enumerated.StatusOrdemCompra;
import br.com.sefin.view.ItemOrdemCompraView;
import br.com.siafi.controller.AditivoController;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.ItemLicitacaoController;
import br.com.siafi.controller.ItemOrdemCompraController;
import br.com.siafi.controller.ItemOrdemCompraViewController;
import br.com.siafi.controller.LicitacaoController;
import br.com.siafi.controller.OrdemCompraController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.Aditivo;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.ItemLicitacao;
import br.com.siafi.modelo.ItemOrdemCompra;
import br.com.siafi.modelo.ItemOrdemCompraPk;
import br.com.siafi.modelo.Licitacao;
import br.com.siafi.modelo.OrdemCompra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;

/**
 * Classe do Projeto Siafi - Criado em 09/07/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class OrdemCompraMB extends BeanGenerico<OrdemCompra> implements Serializable {

    @EJB
    private OrdemCompraController controler;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private ItemOrdemCompraController itemOrdemCompraControler;
    @EJB
    private LicitacaoController licitacaoControler;
    @EJB
    private ItemLicitacaoController itemLicitacaoControler;
    @EJB
    private ItemOrdemCompraViewController itemOrdemCompraViewControler;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoControler;
    @EJB
    private AditivoController aditivoControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private String numeroLicitacao;
    private OrdemCompra ordemCompra;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private List<OrdemCompra> ordemCompraLista;
    private List<Licitacao> licitacaoLista;
    private List<ItemOrdemCompra> itensLista;
    private List<ItemLicitacao> itensLicitacaoList;
    private List<Aditivo> listaAditivos;
    private List<ItemOrdemCompraView> listItemOrdemCompraView;
    private Credor credor;
    private Licitacao licitacao;
    private ItemOrdemCompra itemOrdemCompra;
    private ItemLicitacao itemLicitacao;
    private ItemOrdemCompraView itemOrdemCompraView;
    private Aditivo aditivo;
    private Date dateInicial;
    private Date dataFinal;
    private boolean salvo;
    private StatusOrdemCompra statusOrdemCompra;
    private BigDecimal valorInitario;
    /**
     * Controle de quantidade *
     */
    private BigDecimal quantidade;

    public OrdemCompraMB() {
        super(OrdemCompra.class);
    }

    public void novo() {
        numeroLicitacao = "";
        quantidade = BigDecimal.ONE;
        itemOrdemCompra = new ItemOrdemCompra();
        itemLicitacao = new ItemLicitacao();
        itensLista = new ArrayList<>();
        itensLicitacaoList = new ArrayList<>();
        ordemCompraLista = new ArrayList<>();
        licitacaoLista = new ArrayList<>();
        ordemCompra = new OrdemCompra();
        ordemCompra.setStatus(StatusOrdemCompra.Pendente);
        ordemCompra.setDataEmissao(new Date());
        ordemCompra.setEmissor(usuarioMb.getUsuarioSelecionado());
        ordemCompra.setValorTotal(BigDecimal.ZERO);
        licitacao = new Licitacao();
        credor = new Credor();
        aditivo = new Aditivo();
        selecionaUnidadeOrcamentariaDef();
        salvo = false;
        listItemOrdemCompraView = new ArrayList<>();
        valorInitario = BigDecimal.ZERO;
    }

    public void atualizaItemLic(Long id) {
        try {
            itemLicitacao = itemLicitacaoControler.carregar(id);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao atualizar Licitação", ex, this.getClass().getName());
        }
    }

    @PostConstruct
    private void inicializa() {
        novo();
        ordemCompra = (OrdemCompra) beanNavegacao.getRegistroMapa("ordem_compra", new OrdemCompra());
        if (ordemCompra.getId() != null) {
            credor = ordemCompra.getCredor();
            unidadeOrcamentaria = ordemCompra.getUnidadeOrcamentaria();
            listarItensOrdemCompra();
            setLicitacao(ordemCompra.getLicitacao());
            aditivo = ordemCompra.getContrato().getAditivosList().get(ordemCompra.getContrato().getAditivosList().size() - 1);
            listarContratos();

        } else {
            ordemCompra.setStatus(StatusOrdemCompra.Pendente);
            ordemCompra.setDataEmissao(new Date());
            ordemCompra.setEmissor(usuarioMb.getUsuarioSelecionado());
            ordemCompra.setValorTotal(BigDecimal.ZERO);
            licitacao = new Licitacao();
            credor = new Credor();
            selecionaUnidadeOrcamentariaDef();
        }
        listaAditivos = new ArrayList<>();
    }

    private void selecionaUnidadeOrcamentariaDef() {
        if (usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias() != null && !usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias().isEmpty()) {
            unidadeOrcamentaria = usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias().get(0);
        }
    }

    public void cancelarSolicitacao() {
        try {
            controler.cancelarOrdemCompra(ordemCompra);
            MenssagemUtil.addMessageInfo("Ordem de compra cancelada com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Cancelar Ordem de compra.", ex, this.getClass().getName());
        }
    }

    public void salvar() {
        try {
            ordemCompra.setValorTotal(calcValorTotal());

            if (valida()) {
                ordemCompra.setCredor(credor);
                ordemCompra.setLicitacao(licitacao);
                ordemCompra.setUnidadeOrcamentaria(unidadeOrcamentaria);
                controler.salvarouAtualizar(ordemCompra);
                for (ItemOrdemCompra io : itensLista) {
                    io.setOrdemCompra(ordemCompra);
                    io.getItemOrdemCompraPk().setIdOrdemCompra(ordemCompra.getId());
                    controler.salvarouAtualizar(io);
                }
                itemOrdemCompraControler.limparOrdemCompra(ordemCompra, itensLista.size());
                MenssagemUtil.addMessageInfo("A ordem de compra foi emitida com sucesso!");
                MenssagemUtil.addMessageInfo("Id: " + ordemCompra.getId().toString());
                salvo = true;
//                novo();
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro salvar ordem de compra", ex, this.getClass().getName());
        }
    }

    public void editar() {
        salvo = false;
    }

    public boolean valida() throws Exception {
        if (credor.getId() == null) {
            MenssagemUtil.addMessageWarn("Selecione um credor.");
            return false;
        } else if (ordemCompra.getValorTotal().equals(BigDecimal.ZERO)) {
            MenssagemUtil.addMessageWarn("voce deve incluir pelo menos um item.");
            return false;
        } else if (licitacao.getId() == null) {
            MenssagemUtil.addMessageWarn("Selecione uma licitação.");
            return false;
        } else {
            return controler.checarSaldoContrato(aditivo, ordemCompra);
        }
    }

    public void listar() {
        try {
            ordemCompraLista = controler.listar(unidadeOrcamentaria, dateInicial, dataFinal, statusOrdemCompra);
            if (ordemCompraLista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar ordem de compra", ex, this.getClass().getName());
        }
    }

    public void listarContratos() {
        try {
            listaAditivos = aditivoControler.listarAditivosValidosOrdemCompra(null, unidadeOrcamentaria, credor);
        } catch (Exception ex) {
            listaAditivos = new ArrayList<>();
            MenssagemUtil.addMessageErro("Erro ao Listar contratos.", ex, this.getClass().getName());
        }
    }

    public BigDecimal mostraSaldoAditivo(Aditivo a) {
        try {
            return solicitacaoControler.saldoAditivo(a);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao encontrar o aditivo.", ex, this.getClass().getName());
            return BigDecimal.ZERO;
        }
    }

    public void limparLicitacao() {
        licitacao = new Licitacao();
        licitacaoLista.clear();
        itemLicitacao = new ItemLicitacao();
        itemOrdemCompraView = new ItemOrdemCompraView();
        itensLicitacaoList.clear();
        listItemOrdemCompraView.clear();
        itensLista.clear();

    }

    public void addItem() {
        boolean contemItem = false;
        if (itemOrdemCompraView.saldo().compareTo(quantidade) >= 0) {
            ItemOrdemCompraPk itemOrdemCompraPk = new ItemOrdemCompraPk();
            itemOrdemCompra.setItemOrdemCompraPk(itemOrdemCompraPk);
            itemOrdemCompra.getItemOrdemCompraPk().setOrdem(itensLista.size() + 1);
            itemOrdemCompra.setQuantidade(quantidade);
            itemOrdemCompra.setItemLicitacao(itemLicitacao);
            itemOrdemCompra.setValorUnitario(valorInitario);
            for (ItemOrdemCompra ioc : itensLista) {
                if (ioc.getItemLicitacao().equals(itemOrdemCompra.getItemLicitacao())) {
                    contemItem = true;
                }
            }
            if (!contemItem) {
                itensLista.add(itemOrdemCompra);
            } else {
                MenssagemUtil.addMessageWarn("você já incluir esse item");
            }
            itemOrdemCompra = new ItemOrdemCompra();
            quantidade = BigDecimal.ONE;
            valorInitario = BigDecimal.ZERO;
            itemLicitacao = new ItemLicitacao();
            ordemCompra.setValorTotal(calcValorTotal());
            itemOrdemCompraView = new ItemOrdemCompraView();
        } else {
            MenssagemUtil.addMessageWarn("A quantidade é maior que o saldo disponível");
        }
    }

    public void removeItem(ItemOrdemCompra ioc) {
        try {
            itensLista.remove(ioc);
            reordenaTabelaItens();
            ordemCompra.setValorTotal(calcValorTotal());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao remover item da ordem de compra", ex, this.getClass().getName());
        }
    }

    private void reordenaTabelaItens() {
        for (ItemOrdemCompra i : itensLista) {
            i.getItemOrdemCompraPk().setOrdem(itensLista.indexOf(i) + 1);
        }
    }

    private BigDecimal calcValorTotal() {
        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        for (ItemOrdemCompra io : itensLista) {
            BigDecimal soma = io.getQuantidade().multiply(io.getValorUnitario());
            total = total.add(soma);
        }
        return total;
    }

    public void buscarLicitacao() {
        try {
            licitacaoLista = licitacaoControler.listarLicitacaoUnidadeOrcamentariaCredorLimpas(unidadeOrcamentaria, credor);
            if (licitacaoLista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma licitacao encontrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao consultar solicitações", ex, this.getClass().getName());
        }
    }

    public void impressaoOrdemCompra() {
        try {
            itensLista = itemOrdemCompraControler.listarPorOrdemCompra(ordemCompra);
            if (!itensLista.isEmpty()) {
                try {
                    Map<String, Object> m = new HashMap<>();
                    m.put("ordem", ordemCompra);
                    byte[] rel = new AssistentedeRelatorio().relatorioemByte(itensLista, m, "WEB-INF/relatorios/ordem_compra.jasper", "Ordem de Compra");
                    RelatorioSession.setBytesRelatorioInSession(rel);
                } catch (JRException ex) {
                    MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
                }
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao consultar itens da ordem de compra", ex, this.getClass().getName());
        }
    }

    public void setLicitacao(Licitacao licitacao) {
        try {
            this.licitacao = licitacao;
            itensLicitacaoList = itemLicitacaoControler.listarPorLicitacao(licitacao);
            listItemOrdemCompraView = itemOrdemCompraViewControler.listarPorLicitacao(licitacao.getId());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao setar licitação", ex, this.getClass().getName());
        }
    }

    public OrdemCompra getOrdemCompra() {
        return ordemCompra;
    }

    public void setOrdemCompra(OrdemCompra ordemCompra) {
        this.ordemCompra = ordemCompra;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<OrdemCompra> getOrdemCompraLista() {
        return ordemCompraLista;
    }

    public void setOrdemCompraLista(List<OrdemCompra> ordemCompraLista) {
        this.ordemCompraLista = ordemCompraLista;
    }

    public List<Licitacao> getLicitacaoLista() {
        return licitacaoLista;
    }

    public void setLicitacaoLista(List<Licitacao> licitacaoLista) {
        this.licitacaoLista = licitacaoLista;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public List<ItemOrdemCompra> getItensLista() {
        return itensLista;
    }

    public void setItensLista(List<ItemOrdemCompra> itensLista) {
        this.itensLista = itensLista;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public StatusOrdemCompra getStatusCancelada() {
        return StatusOrdemCompra.Cancelada;
    }

    public String getNumeroLicitacao() {
        return numeroLicitacao;
    }

    public void setNumeroLicitacao(String numeroLicitacao) {
        this.numeroLicitacao = numeroLicitacao;
    }

    public List<ItemLicitacao> getItensLicitacaoList() {
        return itensLicitacaoList;
    }

    public void setItensLicitacaoList(List<ItemLicitacao> itensLicitacaoList) {
        this.itensLicitacaoList = itensLicitacaoList;
    }

    public ItemOrdemCompra getItemOrdemCompra() {
        return itemOrdemCompra;
    }

    public void setItemOrdemCompra(ItemOrdemCompra itemOrdemCompra) {
        this.itemOrdemCompra = itemOrdemCompra;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public ItemLicitacao getItemLicitacao() {
        return itemLicitacao;
    }

    public void setItemLicitacao(ItemLicitacao itemLicitacao) {
        this.itemLicitacao = itemLicitacao;
    }

    public List<ItemOrdemCompraView> getListItemOrdemCompraView() {
        return listItemOrdemCompraView;
    }

    public void setListItemOrdemCompraView(List<ItemOrdemCompraView> listItemOrdemCompraView) {
        this.listItemOrdemCompraView = listItemOrdemCompraView;
    }

    public ItemOrdemCompraView getItemOrdemCompraView() {
        return itemOrdemCompraView;
    }

    public void setItemOrdemCompraView(ItemOrdemCompraView itemOrdemCompraView) {
        this.itemOrdemCompraView = itemOrdemCompraView;
        this.valorInitario = itemOrdemCompraView.getValor();
    }

    public void listarItensOrdemCompra() {
        try {
            itensLista = itemOrdemCompraControler.listarPorOrdemCompra(ordemCompra);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao consultar itens da ordem de compra", ex, this.getClass().getName());
        }
    }

    public Date getDateInicial() {
        return dateInicial;
    }

    public void setDateInicial(Date dateInicial) {
        this.dateInicial = dateInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public List<Aditivo> getListaAditivos() {
        return listaAditivos;
    }

    public void setListaAditivos(List<Aditivo> listaAditivos) {
        this.listaAditivos = listaAditivos;
    }

    public Aditivo getAditivo() {
        return aditivo;
    }

    public void setAditivo(Aditivo aditivo) {
        this.aditivo = aditivo;
    }

    public boolean podeEditar(OrdemCompra ordem) {
        return StatusOrdemCompra.Pendente.equals(ordem.getStatus());
    }

    public boolean isSalvo() {
        return salvo;
    }

    public void setSalvo(boolean salvo) {
        this.salvo = salvo;
    }

    public List<StatusOrdemCompra> getListStatusOrdemCompra() {
        return Arrays.asList(StatusOrdemCompra.values());
    }

    public StatusOrdemCompra getStatusOrdemCompra() {
        return statusOrdemCompra;
    }

    public void setStatusOrdemCompra(StatusOrdemCompra statusOrdemCompra) {
        this.statusOrdemCompra = statusOrdemCompra;
    }

    public BigDecimal getValorInitario() {
        return valorInitario;
    }

    public void setValorInitario(BigDecimal valorInitario) {
        this.valorInitario = valorInitario;
    }

}
