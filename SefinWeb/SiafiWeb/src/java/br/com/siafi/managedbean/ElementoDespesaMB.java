/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.ElementoDespesaController;
import br.com.siafi.modelo.ElementoDespesa;
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
public class ElementoDespesaMB extends BeanGenerico<ElementoDespesa> implements Serializable {

    @EJB
    private ElementoDespesaController controler;
    private List<ElementoDespesa> lista;

    public ElementoDespesaMB() {
        super(ElementoDespesa.class);
        lista = new ArrayList<>();
    }

    public List<ElementoDespesa> getLista() {
        return lista;
    }

    public void setLista(List<ElementoDespesa> lista) {
        this.lista = lista;
    }

    public void listar() {
        lista = listar(controler);
    }

}
