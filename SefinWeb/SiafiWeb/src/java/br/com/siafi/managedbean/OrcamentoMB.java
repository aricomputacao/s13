/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.controler.ExercicioController;
import br.com.siafi.controller.OrcamentoController;
import br.com.guardiao.modelo.Exercicio;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.modelo.Orcamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Bean gerenciado do Projeto SiafiWeb criado em 13/06/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class OrcamentoMB extends BeanGenerico<Orcamento> implements Serializable {

    @EJB
    private OrcamentoController orcamentoControler;
    @EJB
    private ExercicioController exercicioControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private Orcamento orcamentoSelecionado;
    private List<Orcamento> listaOrcamento;

    /**
     * Creates a new instance of OrcamentoMb
     */
    public OrcamentoMB() {
        super(Orcamento.class);
        this.listaOrcamento = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        this.orcamentoSelecionado = (Orcamento) beanNavegacao.getRegistroMapa("orcamento", new Orcamento());
    }

    public List<Exercicio> getListExercicios() {
        try {
            return exercicioControler.listarTodos("ano");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao consulta exercicios", ex, this.getClass().getName());
        }
        return null;
    }

    public void salvar() {
        try {
            orcamentoControler.salvarouAtualizar(orcamentoSelecionado);
            MenssagemUtil.addMessageInfo("Orçamento cadastrado com sucesso. \r\n" + orcamentoSelecionado.getExercicio().getAno().toString());
            novo();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao salvar registro", ex, this.getClass().getName());
        }
    }

    public void listar() {
        listaOrcamento = listar(orcamentoControler);
    }

    public void novo() {
        orcamentoSelecionado = new Orcamento();
    }

    public Orcamento getOrcamentoSelecionado() {
        return orcamentoSelecionado;
    }

    public void setOrcamentoSelecionado(Orcamento orcamentoSelecionado) {
        this.orcamentoSelecionado = orcamentoSelecionado;
    }

    public List<Orcamento> getListaOrcamento() {
        return listaOrcamento;
    }

    public void setListaOrcamento(List<Orcamento> listaOrcamento) {
        this.listaOrcamento = listaOrcamento;
    }
}
