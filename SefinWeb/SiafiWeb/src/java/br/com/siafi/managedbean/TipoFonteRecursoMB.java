/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.TipoFonteRecursoController;
import br.com.siafi.modelo.TipoFonteRecurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class TipoFonteRecursoMB extends BeanGenerico<TipoFonteRecurso> implements Serializable {

    @EJB
    private TipoFonteRecursoController tipoFonteRecursoControler;
    private List<TipoFonteRecurso> lista;

    public TipoFonteRecursoMB() {
        super(TipoFonteRecurso.class);
        lista = new ArrayList<>();
    }

    @PostConstruct
    public void iniciar() {
        try {
            lista = tipoFonteRecursoControler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao consultar", ex, this.getClass().getName());
        }
    }

    public void listar() {
        lista = listar(tipoFonteRecursoControler);
    }

    public List<TipoFonteRecurso> getLista() {
        return lista;
    }

    public void setLista(List<TipoFonteRecurso> lista) {
        this.lista = lista;
    }
}
