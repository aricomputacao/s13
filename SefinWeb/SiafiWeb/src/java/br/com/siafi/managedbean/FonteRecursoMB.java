/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.FonteRecursoController;
import br.com.siafi.modelo.FonteRecurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Classe do Projeto ******* - Criado em 18/06/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class FonteRecursoMB extends BeanGenerico<FonteRecurso> implements Serializable {

    @EJB
    private FonteRecursoController controler;
    private List<FonteRecurso> lista;

    public FonteRecursoMB() {
        super(FonteRecurso.class);
        lista = new ArrayList<>();
    }

    @PostConstruct
    public void iniciar() {
        try {
            lista = controler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
        }
    }

    public List<FonteRecurso> getLista() {
        return lista;
    }

    public void setLista(List<FonteRecurso> lista) {
        this.lista = lista;
    }
}
