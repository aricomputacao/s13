/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.SubFuncaoController;
import br.com.siafi.modelo.Subfuncao;
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
public class SubFuncaoMB extends BeanGenerico<Subfuncao> implements Serializable {

    private Subfuncao subfuncao;
    private List<Subfuncao> listaSubfuncaos;
    @EJB
    private SubFuncaoController subFuncaoControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;

    /**
     * Creates a new instance of SubFuncaoMb
     */
    public SubFuncaoMB() {
        super(Subfuncao.class);
        this.listaSubfuncaos = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        this.subfuncao = (Subfuncao) beanNavegacao.getRegistroMapa("subFuncao", new Subfuncao());
    }

    public void listar() {
        listaSubfuncaos = listar(subFuncaoControler);
    }

    public Subfuncao getSubfuncao() {
        return subfuncao;
    }

    public void setSubfuncao(Subfuncao subfuncao) {
        this.subfuncao = subfuncao;
    }

    public List<Subfuncao> getListaSubfuncaos() {
        return listaSubfuncaos;
    }

    public void setListaSubfuncaos(List<Subfuncao> listaSubfuncaos) {
        this.listaSubfuncaos = listaSubfuncaos;
    }
}
