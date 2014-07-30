/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.UsuarioController;

import br.com.guardiao.modelo.CriptografiaSenha;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class UltilMB implements Serializable {

    @Inject
    private UsuarioMB usuarioMb;
    @EJB
    private UsuarioController usuarioControler;
    private String senhaAtual;
    private String novaSenha;
    private String confirmeSenha;

    public void validateSenhaAtual(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        CriptografiaSenha cs = new CriptografiaSenha();
        senhaAtual = (String) value;
        if (!usuarioMb.getUsuarioSelecionado().getSenha().equals(cs.criptografarSenha(senhaAtual))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha atual incorreta!", ""));
        }
    }

    public void alterarSenha() {
        try {
            usuarioControler.alterarSenha(usuarioMb.getUsuarioSelecionado(), senhaAtual, novaSenha, confirmeSenha);
            MenssagemUtil.addMessageInfo("Senha alterada!");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Falha ao inserir registro!", ex, this.getClass().getName());
        } finally {
            novaSenha = "";
            confirmeSenha = "";
            senhaAtual = "";

        }
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

}
