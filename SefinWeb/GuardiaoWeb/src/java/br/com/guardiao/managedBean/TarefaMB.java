/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.ModuloController;
import br.com.guardiao.controler.TarefaController;
import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Tarefa;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Classe do Projeto guardiao - Criado em 22/03/2013 - Bean Gerenciador das
 * tarefas dos modulos
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class TarefaMB extends BeanGenerico<Tarefa> implements Serializable {

    @EJB
    private TarefaController tarefaControler;
    private Tarefa tarefaSelecionada;
    @EJB
    private ModuloController moduloControler;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private String acao;
    private String modelo;
    private List<Tarefa> tarefas;
    private String mnTarefa;
    private Modulo modulo;
    private Sistema sistema;

    /**
     *
     */
    public TarefaMB() {
        super(Tarefa.class);

    }

    @PostConstruct
    private void init() {
        tarefaSelecionada = (Tarefa) navegacaoGuardiao.getRegistroMapa("tarefa", new Tarefa());
        tarefas = new ArrayList<>();
        acao = "";
        modelo = "";
        mnTarefa = "";
        if (tarefaSelecionada.getId() != null) {
            acao = tarefaSelecionada.getMnemonico().substring(tarefaSelecionada.getMnemonico().length() - 4, tarefaSelecionada.getMnemonico().length() - 2);
            modelo = tarefaSelecionada.getMnemonico().substring(tarefaSelecionada.getMnemonico().length() - 2, tarefaSelecionada.getMnemonico().length());
            mnTarefa = acao + modelo;
        }
    }

    /**
     *
     * @return
     */
    public Tarefa getTarefaSelecionada() {
        return tarefaSelecionada;
    }

    /**
     *
     * @param tarefaSelecionada
     */
    public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
        this.tarefaSelecionada = tarefaSelecionada;
    }

    public void listar() {
        try {
            tarefas = tarefaControler.listarPorModulo(sistema, modulo, getCampoBusca(), getValorBusca());
            if (tarefas.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("consultar Tarefas", ex, this.getClass().getName());
        }
    }

    /**
     *
     * @return
     */
    public List<Modulo> getListModulos() {
        try {
            gerarMnemonico();
            return moduloControler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao atualizar lista de Módulos", ex, this.getClass().getName());
        }
        return null;
    }

    public List<Modulo> listarModulos(Sistema sis) {
        try {
            return moduloControler.listar(sis);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar de Módulos", ex, this.getClass().getName());
        }
        return new ArrayList<>();
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void gerarMnemonico() {
        if (!"".equals(acao)) {
            mnTarefa = acao + modelo;
            tarefaSelecionada.setMnemonico(tarefaSelecionada.getModulo().getSistema().getMnemonico() + tarefaSelecionada.getModulo().getMnemonico() + mnTarefa);
        }

    }

    public void salvar() {
        try {
            tarefaControler.salvarouAtualizar(this.tarefaSelecionada);
            tarefaSelecionada = new Tarefa();
            acao = "";
            modelo = "";
            mnTarefa = "";
            MenssagemUtil.addMessageInfo("Tarefa cadastrada com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar Tarefas", ex, this.getClass().getName());
        }
    }

    public String getMnTarefa() {
        return mnTarefa;
    }

    public void setMnTarefa(String mnTarefa) {
        this.mnTarefa = mnTarefa;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

}
