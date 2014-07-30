/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.PendenciaController;
import br.com.siafi.modelo.Pendencia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class PendenciaMB extends BeanGenerico<Pendencia> implements Serializable {

    @EJB
    private PendenciaController controller;
    private Pendencia pendencia;
    private List<Pendencia> listaPendencias;

    public PendenciaMB() {
        super(Pendencia.class);
    }

    @PostConstruct
    private void iniciar() {
        pendencia = new Pendencia();
        listaPendencias = new ArrayList<>();
    }

    public void listar() {
        try {
            listaPendencias = controller.listarTodos("credorNome", getCampoBusca(), getValorBusca());
       //listaPendencias = controller.listarTodos("credorNome");
        } catch (Exception ex) {
            Logger.getLogger(PendenciaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remover(Pendencia p) {
        try {
            pendencia = controller.gerenciar(p.getDocumento());
            controller.excluir(pendencia);
            listaPendencias.remove(pendencia);
            MenssagemUtil.addMessageInfo(getMsg("exclusao"));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro_exclusao"), ex, this.getClass().getName());
            Logger.getLogger(PendenciaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pendencia getPendencia() {
        return pendencia;
    }

    public void setPendencia(Pendencia pendencia) {
        this.pendencia = pendencia;
    }

    public List<Pendencia> getListaPendencias() {
        return listaPendencias;
    }

    public void setListaPendencias(List<Pendencia> listaPendencias) {
        this.listaPendencias = listaPendencias;
    }

}
