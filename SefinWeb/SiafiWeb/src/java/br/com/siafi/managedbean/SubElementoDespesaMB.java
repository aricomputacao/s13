/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.SubElementoDespesaController;
import br.com.siafi.modelo.SubElementoDespesa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Classe do Projeto SIAFI - Criado em 18/06/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class SubElementoDespesaMB extends BeanGenerico<SubElementoDespesa> implements Serializable {

    @EJB
    private SubElementoDespesaController controler;
    private List<SubElementoDespesa> lista;

    public SubElementoDespesaMB() {
        super(SubElementoDespesa.class);
        lista = new ArrayList<>();
    }

    public List<SubElementoDespesa> getLista() {
        return lista;
    }

    public void setLista(List<SubElementoDespesa> lista) {
        this.lista = lista;
    }

    public void listar() {
        lista = listar(controler);
    }

}
