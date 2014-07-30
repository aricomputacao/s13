/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.GrupoItemController;
import br.com.siafi.modelo.GrupoItem;
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
public class GrupoItemMB extends BeanGenerico<GrupoItem> implements Serializable {

    @EJB
    private GrupoItemController controler;
    private List<GrupoItem> lista;

    public GrupoItemMB() {
        super(GrupoItem.class);
        lista = new ArrayList<>();
    }

    public void listar() {
        lista = listar(controler);
    }

    public List<GrupoItem> getLista() {
        return lista;
    }

    public void setLista(List<GrupoItem> lista) {
        this.lista = lista;
    }
}
