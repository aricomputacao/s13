/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.RpLiquidacaoController;
import br.com.siafi.modelo.Conta;
import br.com.siafi.modelo.FonteRecurso;
import br.com.siafi.modelo.RpLiquidacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class AutorizarRpLiquidacaoMB extends BeanGenerico<RpLiquidacao> implements Serializable {

    @EJB
    private RpLiquidacaoController controller;
    private FonteRecurso fonteRecurso;
    private List<FonteRecurso> listFonteRecurso;
    private List<Conta> listConta;
    private Conta conta;
    private List<RpLiquidacao> listaLiquidacaos;
    private RpLiquidacao liquidacao;

    public AutorizarRpLiquidacaoMB() {
        super(RpLiquidacao.class);
    }

    @PostConstruct
    public void iniciar() {
        listaLiquidacaos = new ArrayList<>();
        liquidacao = new RpLiquidacao();
        listarLiquidacaoLiberar();
    }

    public void listarLiquidacaoLiberar() {
        try {
            listaLiquidacaos = controller.listarLiquidacaoLiberar();
            if (listaLiquidacaos.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao listar liquidações", ex, this.getClass().getName());
        }
    }

    public void autorizarLiquidacao() {
        try {
            liquidacao.setLiberado(Boolean.TRUE);
            controller.salvarouAtualizar(liquidacao);
            listarLiquidacaoLiberar();
            MenssagemUtil.addMessageInfo("pagamento de liquidação autorizado");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao autorizar pagamento de liquidação", ex, this.getClass().getName());
        }
    }

    public FonteRecurso getFonteRecurso() {
        return fonteRecurso;
    }

    public void setFonteRecurso(FonteRecurso fonteRecurso) {
        this.fonteRecurso = fonteRecurso;
    }

    public List<FonteRecurso> getListFonteRecurso() {
        return listFonteRecurso;
    }

    public void setListFonteRecurso(List<FonteRecurso> listFonteRecurso) {
        this.listFonteRecurso = listFonteRecurso;
    }

    public List<Conta> getListConta() {
        return listConta;
    }

    public void setListConta(List<Conta> listConta) {
        this.listConta = listConta;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public List<RpLiquidacao> getListaLiquidacaos() {
        return listaLiquidacaos;
    }

    public void setListaLiquidacaos(List<RpLiquidacao> listaLiquidacaos) {
        this.listaLiquidacaos = listaLiquidacaos;
    }

    public RpLiquidacao getLiquidacao() {
        return liquidacao;
    }

    public void setLiquidacao(RpLiquidacao liquidacao) {
        this.liquidacao = liquidacao;
    }
}
