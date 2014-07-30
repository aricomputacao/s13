/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.ProgramaController;
import br.com.siafi.modelo.Programa;
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
public class ProgramaMB extends BeanGenerico<Programa> implements Serializable {

    private Programa programa;
    private List<Programa> listaProgramas;
    @EJB
    private ProgramaController programaControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;

    /**
     * Creates a new instance of ProgramaMb
     */
    public ProgramaMB() {
        super(Programa.class);

        this.listaProgramas = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        this.programa = (Programa) beanNavegacao.getRegistroMapa("programa", new Programa());
    }

    public void listar() {
        listaProgramas = listar(programaControler);
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public List<Programa> getListaProgramas() {
        return listaProgramas;
    }

    public void setListaProgramas(List<Programa> listaProgramas) {
        this.listaProgramas = listaProgramas;
    }

}
