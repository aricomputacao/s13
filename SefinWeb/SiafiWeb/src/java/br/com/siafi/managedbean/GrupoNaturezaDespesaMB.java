/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.GrupoNaturezaDespesaController;
import br.com.siafi.modelo.GrupoNaturezaDespesa;
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
public class GrupoNaturezaDespesaMB extends BeanGenerico<GrupoNaturezaDespesa> implements Serializable {

    @EJB
    private GrupoNaturezaDespesaController controler;
    private List<GrupoNaturezaDespesa> lista;

    public GrupoNaturezaDespesaMB() {
        super(GrupoNaturezaDespesa.class);
        lista = new ArrayList<>();
    }

    public List<GrupoNaturezaDespesa> getLista() {
        return lista;
    }

    public void setLista(List<GrupoNaturezaDespesa> lista) {
        this.lista = lista;
    }

    public void listar() {
        lista = listar(controler);
    }

}
