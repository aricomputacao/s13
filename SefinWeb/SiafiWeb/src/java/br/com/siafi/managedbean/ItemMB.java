/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.ItemController;
import br.com.siafi.modelo.Item;
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
public class ItemMB extends BeanGenerico<Item> implements Serializable {

    @EJB
    private ItemController controler;
    private List<Item> lista;

    public ItemMB() {
        super(Item.class);
        lista = new ArrayList<>();
    }

    public void listar() {
        lista = listar(controler);
    }

    public List<Item> getLista() {
        return lista;
    }

    public void setLista(List<Item> lista) {
        this.lista = lista;
    }
}
