/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.FuncaoController;
import br.com.siafi.modelo.Funcao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Bean gerenciado do Projeto SiafiWeb criado em 18/06/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class FuncaoMB extends BeanGenerico<Funcao> implements Serializable {

    private Funcao funcao;
    private List<Funcao> listaFuncao;
    @EJB
    private FuncaoController funcaoControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;

    /**
     * Creates a new instance of FuncaoMb
     */
    public FuncaoMB() {
        super(Funcao.class);
        this.listaFuncao = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        this.funcao = (Funcao) beanNavegacao.getRegistroMapa("funcao", new Funcao());
    }

    public void listar() {
        listaFuncao = listar(funcaoControler);
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public List<Funcao> getListaFuncao() {
        return listaFuncao;
    }

    public void setListaFuncao(List<Funcao> listaFuncao) {
        this.listaFuncao = listaFuncao;
    }

}
