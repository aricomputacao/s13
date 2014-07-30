/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sae.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sae.controller.CredorController;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class CredorMB extends BeanGenerico<Credor> implements Serializable {

    @EJB
    private CredorController controller;
    private List<Credor> listaCredores;

    public CredorMB() {
        super(Credor.class);
    }

    @PostConstruct

    public void novo() {
        listaCredores = new ArrayList<>();
    }

    public void listar() {
        try {
            listaCredores = controller.listarTodos("id", getCampoBusca(), getValorBusca().toUpperCase());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public List<Credor> getListaCredores() {
        return listaCredores;
    }

    public void setListaCredores(List<Credor> listaCredores) {
        this.listaCredores = listaCredores;
    }

}
