/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.HistoricoPadraoController;
import br.com.siafi.modelo.HistoricoPadrao;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class HistoricoPadraoMB extends BeanGenerico<HistoricoPadrao> implements Serializable {

    @EJB
    private HistoricoPadraoController controler;
    private HistoricoPadrao historicoPadrao;
    private List<HistoricoPadrao> historicoPadraoList;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private String nome;

    public HistoricoPadraoMB() {
        super(HistoricoPadrao.class);
    }

    @PostConstruct
    private void init() {
        novo();
        nome = "";
    }

    public void salvar() {
        try {
            if (historicoPadrao.getUnidadeOrcamentaria() == null) {
                historicoPadrao.setUnidadeOrcamentaria(unidadeOrcamentaria);
            }
            controler.salvarouAtualizar(historicoPadrao);
            MenssagemUtil.addMessageInfo("Registro salvo.");
            novo();
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Ocorreu um erro ao salvar esse registro.", e, this.getClass().getName());
        }
    }

    public void excluir() {
        try {
            controler.excluir(historicoPadrao);
            MenssagemUtil.addMessageInfo("Registro excluído com sucesso!");
            listar();// Recarregar a lista caso seja nescessário.
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Ocorreu um erro ao excluir esse registro.", e, this.getClass().getName());
        }
    }

    public void listar() {
        if (unidadeOrcamentaria != null) {
            try {
                historicoPadraoList = controler.listar(unidadeOrcamentaria, nome);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao listar registros.");
                Logger.getLogger(HistoricoPadraoMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            MenssagemUtil.addMessageErro("Selecione uma unidade orçamentária.");
        }

    }

    public void novo() {
        historicoPadrao = new HistoricoPadrao();
    }

    public HistoricoPadrao getHistoricoPadrao() {
        return historicoPadrao;
    }

    public void setHistoricoPadrao(HistoricoPadrao historicoPadrao) {
        this.historicoPadrao = historicoPadrao;
    }

    public List<HistoricoPadrao> getHistoricoPadraoList() {
        return historicoPadraoList;
    }

    public void setHistoricoPadraoList(List<HistoricoPadrao> historicoPadraoList) {
        this.historicoPadraoList = historicoPadraoList;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
        nome = "";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
