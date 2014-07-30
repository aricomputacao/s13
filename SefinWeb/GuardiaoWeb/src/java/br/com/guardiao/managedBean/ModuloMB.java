/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.ModuloController;
import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Bean gerenciado do Projeto guardião criado em 22/03/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ModuloMB extends BeanGenerico<Modulo> implements Serializable {

    @EJB
    private ModuloController moduloControler;
    @EJB
    private SistemaController sistemaControler;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private Modulo moduloSelecionado;
    private List<Modulo> modulos;
    private List<Modulo> modulosPai;
    private Sistema sistema;

    public ModuloMB() {
        super(Modulo.class);
    }

    @PostConstruct
    private void init() {
        moduloSelecionado = (Modulo) navegacaoGuardiao.getRegistroMapa("modulo", new Modulo());
        if (moduloSelecionado.getId() != null) {
            sistema = moduloSelecionado.getSistema();
        }
        modulos = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public List<Sistema> lstSistemas() {
        try {
            return sistemaControler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
        return null;
    }

    /**
     *
     * @param nome
     * @return
     */
    public List<Sistema> listarSistemas(String nome) {
        try {
            return sistemaControler.listarNome(nome);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
        return null;
    }

    public void listaMoculosPai() {
        if (sistema != null) {
            try {
                modulosPai = moduloControler.listar(sistema);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao listar modulo pai", ex, this.getClass().getName());
            }
        }
    }

    public Modulo getModuloSelecionado() {
        return moduloSelecionado;
    }

    public void setModuloSelecionado(Modulo moduloSelecionado) {
        this.moduloSelecionado = moduloSelecionado;
    }

    public void listar() {
        try {
            modulos = moduloControler.listar(sistema, getCampoBusca(), getValorBusca());
            if (modulos.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum resultado encontrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar modulo pai", ex, this.getClass().getName());
        }
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public List<Modulo> getModulosPai() {
        return modulosPai;
    }

    public void setModulosPai(List<Modulo> modulosPai) {
        this.modulosPai = modulosPai;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public void salvar() {
        try {
            moduloSelecionado.setSistema(sistema);
            moduloControler.salvarouAtualizar(moduloSelecionado);
            moduloSelecionado = new Modulo();
            sistema = new Sistema();
            MenssagemUtil.addMessageInfo("Módulo cadastrado com seucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar", ex, this.getClass().getName());
        }
    }

}
