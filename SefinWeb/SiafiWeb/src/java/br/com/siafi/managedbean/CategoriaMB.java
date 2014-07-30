/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.CategoriaController;
import br.com.siafi.controller.CentroCustoController;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.CentroCusto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Classe do Projeto Siafi - Criado em 22/04/2013 - bean gerenciador do modelo
 * Categoria
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class CategoriaMB extends BeanGenerico<Categoria> implements Serializable {

    @EJB
    private CategoriaController controller;
    @EJB
    private CentroCustoController despesaControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private Categoria categoria;
    private List<Categoria> listaCategoria;

    /**
     *
     */
    public CategoriaMB() {
        super(Categoria.class);
        listaCategoria = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        categoria = (Categoria) beanNavegacao.getRegistroMapa("categoria", new Categoria());
    }

    public void novo() {
        categoria = new Categoria();
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(categoria);
            categoria = new Categoria();
            MenssagemUtil.addMessageInfo("Categoria salva com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar categoria", ex, this.getClass().getName());
        }
    }

    public void excluir() {
        try {
            controller.excluir(categoria);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao excluir categoria", ex, this.getClass().getName());
        }
    }

    public void listar() {
        listaCategoria = listar(controller);
    }

    public void selecionar() {
        try {
            categoria = controller.carregar(categoria.getId());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar categoria", ex, this.getClass().getName());
        }
    }

    /**
     *
     * @return
     */
    public List<CentroCusto> getListDespesa() {
        try {
            return despesaControler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar despesas", ex, this.getClass().getName());
            return new ArrayList<>();
        }
    }

    /**
     *
     * @return
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return
     */
    public List<Categoria> getListaCategoria() {
        return listaCategoria;
    }

    /**
     *
     * @param listaCategoria
     */
    public void setListaCategoria(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }
}
