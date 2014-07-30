/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.util.NavegacaoGenerico;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author gilmario
 */
@Named
@SessionScoped
public class NavegacaoGuardiaoMB extends NavegacaoGenerico implements Serializable {

    public NavegacaoGuardiaoMB() {
        super();
    }

    @Override
    @PostConstruct
    protected void setarExtensao() {
        setExtensao("xhtml");
        setMnemonicoSistema("GRD");
    }

}
