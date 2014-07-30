/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.siafi.controller.ModalidadeAplicacaoDepesaController;
import br.com.siafi.modelo.ModalidadeAplicacaoDespesa;
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
public class ModalidadeAplicacaoDespesaMB extends BeanGenerico<ModalidadeAplicacaoDespesa> implements Serializable {

    @EJB
    private ModalidadeAplicacaoDepesaController controler;
    private List<ModalidadeAplicacaoDespesa> lista;

    public ModalidadeAplicacaoDespesaMB() {
        super(ModalidadeAplicacaoDespesa.class);
        lista = new ArrayList<>();
    }

    public List<ModalidadeAplicacaoDespesa> getLista() {
        return lista;
    }

    public void setLista(List<ModalidadeAplicacaoDespesa> lista) {
        this.lista = lista;
    }

    public void listar() {
        lista = listar(controler);
    }

}
