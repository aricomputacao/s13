/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sae.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.CriptografiaSenha;
import br.com.guardiao.modelo.Util;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sae.controller.AcessoCredorController;
import br.com.sae.modelo.AcessoCredor;
import br.com.sae.modelo.enumerated.Perfil;
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
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class AcessoCredorMB extends BeanGenerico<AcessoCredor> implements Serializable {

    @Inject
    private BeanUtilitario beanUtilitario;

    @EJB
    private AcessoCredorController controller;
    private AcessoCredor acessoCredor;
    private List<AcessoCredor> listaAcessoCredores;
    private CriptografiaSenha cs;
    private boolean docCPF;

    public AcessoCredorMB() {
        super(AcessoCredor.class);
    }

    @PostConstruct
    public void novo() {
        cs = new CriptografiaSenha();
        acessoCredor = (AcessoCredor) beanUtilitario.getRegistroDoMap("acesso_credor", new AcessoCredor());
        listaAcessoCredores = new ArrayList<>();
    }

    public void salvar() {

        try {
            acessoCredor.setSenha(cs.criptografarSenha(acessoCredor.getDocumento()));
            controller.salvarouAtualizar(acessoCredor);
            MenssagemUtil.addMessageInfo(beanUtilitario.getMsg("cadastro"));
            novo();
        } catch (PersistenceException ex) {
            Logger.getLogger(AcessoCredorMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EJBException ex) {
            Logger.getLogger(AcessoCredorMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoCredorMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listar() {
        try {
            listaAcessoCredores = controller.listarTodos("documento", getCampoBusca(), getValorBusca());
        } catch (PersistenceException ex) {
            Logger.getLogger(AcessoCredorMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EJBException ex) {
            Logger.getLogger(AcessoCredorMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AcessoCredorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ativaDesativaAcesso(AcessoCredor ac) {
        ac.setAtivo(!ac.isAtivo());
        try {
            controller.salvarouAtualizar(ac);
            MenssagemUtil.addMessageInfo("Situação do Acesso  alterado.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(beanUtilitario.getMsg("erro_desconhecido"), ex, this.getClass().getName());
        }
    }

    public void validate(FacesContext fc, UIComponent uic, Object value) {
        String doc = value.toString().replaceAll("[^0-9]", "");
        try {
            if ((Util.CPF(doc) == false) && (Util.validaCNPJ(doc) == false)) {
                ((UIInput) uic).setValid(false);
                MenssagemUtil.addMessageErro("Documento invalido");
                return;
            }

            if (controller.carregar(doc) != null) {
                ((UIInput) uic).setValid(false);
                MenssagemUtil.addMessageErro("Já existe um usuario cadastrado com esse documento.");

            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e);
        }

    }

    public Perfil[] getPerfis() {
        return Perfil.values();
    }

    public AcessoCredor getAcessoCredor() {
        return acessoCredor;
    }

    public void setAcessoCredor(AcessoCredor acessoCredor) {
        this.acessoCredor = acessoCredor;
    }

    public List<AcessoCredor> getListaAcessoCredores() {
        return listaAcessoCredores;
    }

    public void setListaAcessoCredores(List<AcessoCredor> listaAcessoCredores) {
        this.listaAcessoCredores = listaAcessoCredores;
    }

    public boolean isDocCPF() {
        return docCPF;
    }

    public void setDocCPF(boolean docCPF) {
        this.docCPF = docCPF;
    }

}
