/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.UnidadeMedidaController;
import br.com.siafi.modelo.UnidadeMedida;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Classe do Projeto SIAFI - Criado em 28/06/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class UnidadeMedidaMB extends BeanGenerico<UnidadeMedida> implements Serializable {

    @EJB
    private UnidadeMedidaController controler;
    private List<UnidadeMedida> lista;

    public UnidadeMedidaMB() {
        super(UnidadeMedida.class);
        lista = new ArrayList<>();
    }

    public void listar() {
        lista = listar(controler);
    }

    public List<UnidadeMedida> getLista() {
        return lista;
    }

    public void setLista(List<UnidadeMedida> lista) {
        this.lista = lista;
    }
}
