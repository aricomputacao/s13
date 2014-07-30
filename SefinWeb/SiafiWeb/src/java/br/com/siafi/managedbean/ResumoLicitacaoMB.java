/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.sefin.modelo.dto.CredorItemDTO;
import br.com.guardiao.modelo.Pessoa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.view.ItemOrdemCompraView;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.ItemLicitacaoController;
import br.com.siafi.controller.ItemOrdemCompraViewController;
import br.com.siafi.controller.LicitacaoController;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.ItemLicitacao;
import br.com.siafi.modelo.Licitacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Gilmario
 */
@ManagedBean
@ViewScoped
public class ResumoLicitacaoMB implements Serializable {

    @EJB
    private LicitacaoController licitacaoControler;
    @EJB
    private ItemLicitacaoController itemLicitacaoControler;
    @EJB
    private CredorController credorControler;
    @EJB
    private ItemOrdemCompraViewController itemOrdemCompraViewControler;
    @EJB
    private UsuarioMB usuarioMb;
    private List<Licitacao> licitacaosList;
    private Licitacao licitacao;
    private List<Credor> credoresList;
    private List<UnidadeOrcamentaria> unidadeOrcamentariasList;
    private List<ItemLicitacao> itemLicitacaosList;
    private String numero;
    private Date dataInicio;
    private Date dataFinal;
    private BigDecimal saldo;
    private List<CredorItemDTO> credorItemList;
    private List<ItemOrdemCompraView> itemOrdemCompraViewList;
    private Credor credor;
    private UnidadeOrcamentaria unidadeOrcamentaria;

    public ResumoLicitacaoMB() {
    }

    @PostConstruct
    private void inicializa() {
        credor = new Credor();
        credor.setPessoa(new Pessoa());
        saldo = BigDecimal.ZERO;
        itemOrdemCompraViewList = new ArrayList<>();
        licitacaosList = new ArrayList<>();
        credoresList = new ArrayList<>();
        unidadeOrcamentariasList = new ArrayList<>();
        itemLicitacaosList = new ArrayList<>();
    }

    public void selecionaLicitacao(Licitacao lic) {
        try {
            licitacao = lic;
            unidadeOrcamentariasList = licitacaoControler.listarUnidadesPorLicitacao(licitacao);
            credoresList = credorControler.listarTodos(licitacao);
            itemLicitacaosList = itemLicitacaoControler.listarPorLicitacao(licitacao);
            credorItemList = montarCredorListItem(itemLicitacaosList);
            itemOrdemCompraViewList = itemOrdemCompraViewControler.listarPorLicitacao(licitacao.getId());
            buscaSaldo(licitacao);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionat licitação", ex, this.getClass().getName());
        }
    }

    private List<CredorItemDTO> montarCredorListItem(List<ItemLicitacao> itemLicitacaos) {
        List<CredorItemDTO> credorItems;
        credorItems = new ArrayList<>();
        Integer id = 0;
        CredorItemDTO ci = new CredorItemDTO();
        for (ItemLicitacao i : itemLicitacaos) {
            Credor c = i.getCredor();
            if (c.getId() != id) {
                id = c.getId();
                ci = new CredorItemDTO();
                ci.setCredor(c);
                credorItems.add(ci);
            }
            ci.getItens().add(i);
        }
        return credorItems;
    }

    public void listar() {
        try {
            licitacao = null;
            if (credor.getId() != null) {
                if (unidadeOrcamentaria != null) {
                    licitacaosList = licitacaoControler.listarUnidadeNumeroCredor(unidadeOrcamentaria, numero, credor);
                } else {
                    licitacaosList = licitacaoControler.listarUnidadeNumeroCredor(usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), numero, credor);
                }
            } else {
                if (unidadeOrcamentaria != null) {
                    licitacaosList = licitacaoControler.listarUnidadeOrcamentariaNumero(unidadeOrcamentaria, numero);
                } else {
                    licitacaosList = licitacaoControler.listarUnidadeOrcamentariaNumero(usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), numero);
                }
            }
            if (licitacaosList.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma licitação encontrada.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    private void buscaSaldo(Licitacao lic) throws Exception {
        saldo = licitacaoControler.calcularSaldo(lic);
    }

    public List<Licitacao> getLicitacaosList() {
        return licitacaosList;
    }

    public void setLicitacaosList(List<Licitacao> licitacaosList) {
        this.licitacaosList = licitacaosList;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public List<Credor> getCredoresList() {
        return credoresList;
    }

    public void setCredoresList(List<Credor> credoresList) {
        this.credoresList = credoresList;
    }

    public List<UnidadeOrcamentaria> getUnidadeOrcamentariasList() {
        return unidadeOrcamentariasList;
    }

    public void setUnidadeOrcamentariasList(List<UnidadeOrcamentaria> unidadeOrcamentariasList) {
        this.unidadeOrcamentariasList = unidadeOrcamentariasList;
    }

    public List<ItemLicitacao> getItemLicitacaosList() {
        return itemLicitacaosList;
    }

    public void setItemLicitacaosList(List<ItemLicitacao> itemLicitacaosList) {
        this.itemLicitacaosList = itemLicitacaosList;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<CredorItemDTO> getCredorItemList() {
        return credorItemList;
    }

    public void setCredorItemList(List<CredorItemDTO> credorItemList) {
        this.credorItemList = credorItemList;
    }

    public List<ItemOrdemCompraView> getItemOrdemCompraViewList() {
        return itemOrdemCompraViewList;
    }

    public void setItemOrdemCompraViewList(List<ItemOrdemCompraView> itemOrdemCompraViewList) {
        this.itemOrdemCompraViewList = itemOrdemCompraViewList;
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
}
