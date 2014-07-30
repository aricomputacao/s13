/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.CategoriaDespesaController;
import br.com.siafi.modelo.CategoriaDespesa;
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
public class CategoriaDespesaMB extends BeanGenerico<CategoriaDespesa> implements Serializable {

    @EJB
    private CategoriaDespesaController controler;
    private List<CategoriaDespesa> lista;

    public CategoriaDespesaMB() {
        super(CategoriaDespesa.class);
        lista = new ArrayList<>();
    }

    public List<CategoriaDespesa> getLista() {
        return lista;
    }

    public void setLista(List<CategoriaDespesa> lista) {
        this.lista = lista;
    }

    public void listar() {
        lista = listar(controler);
    }
}
