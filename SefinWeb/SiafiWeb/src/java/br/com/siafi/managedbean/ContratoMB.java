/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.modelo.dto.ItemOrdemContratoResumoDTO;
import br.com.siafi.controller.ContratoController;
import br.com.siafi.controller.ItemOrdemCompraController;
import br.com.siafi.controller.OrdemCompraController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.OrdemCompra;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ContratoMB extends BeanGenerico<Contrato> implements Serializable {

    @EJB
    private ContratoController contratoControler;
    @EJB
    private OrdemCompraController ordemCompraController;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraController;
    @EJB
    private ItemOrdemCompraController itemOrdemCompraController;
    private List<Contrato> contratos;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private String numero;
    private Credor credor;
    private List<OrdemCompra> ordemCompraList;
    private List<SolicitacaoFinanceira> solicitacaoFinanceiraList;
    private Contrato contrato;
    private List<ItemOrdemContratoResumoDTO> resumoItens;

    /**
     * Creates a new instance of ContratoMb
     */
    public ContratoMB() {
        super(Contrato.class);
        contratos = new ArrayList<>();
        unidadeOrcamentaria = new UnidadeOrcamentaria();
        credor = new Credor();
        ordemCompraList = new ArrayList<>();
        solicitacaoFinanceiraList = new ArrayList<>();
        resumoItens = new ArrayList<>();
    }

    public void contratoNumero() {
        try {
            contratos = contratoControler.listarPorUnidadeOrcamentariaNumeroCredor(numero, unidadeOrcamentaria, credor);
            if (contratos.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum contrato encotrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao listar contratos", ex, this.getClass().getName());
        }
    }

    public void selecionarContrato(Contrato c) {
        contrato = c;
        ordemCompraList = ordemCompraController.listar(c);
        solicitacaoFinanceiraList = solicitacaoFinanceiraController.listarContratoOrdemCompra(c);
        solicitacaoFinanceiraList.addAll(solicitacaoFinanceiraController.listarContratoSemOrdemCompra(c));
        resumoItens = itemOrdemCompraController.listarResumo(c);
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;

    }

    public List<OrdemCompra> getOrdemCompraList() {
        return ordemCompraList;
    }

    public void setOrdemCompraList(List<OrdemCompra> ordemCompraList) {
        this.ordemCompraList = ordemCompraList;
    }

    public List<SolicitacaoFinanceira> getSolicitacaoFinanceiraList() {
        return solicitacaoFinanceiraList;
    }

    public void setSolicitacaoFinanceiraList(List<SolicitacaoFinanceira> solicitacaoFinanceiraList) {
        this.solicitacaoFinanceiraList = solicitacaoFinanceiraList;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<ItemOrdemContratoResumoDTO> getResumoItens() {
        return resumoItens;
    }

    public void setResumoItens(List<ItemOrdemContratoResumoDTO> resumoItens) {
        this.resumoItens = resumoItens;
    }

}
