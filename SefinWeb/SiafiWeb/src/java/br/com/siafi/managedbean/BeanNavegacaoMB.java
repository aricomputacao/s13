/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.util.NavegacaoGenerico;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Classe do Projeto Siafi - Criado em 26/04/2013 - Classe de Utilitario de
 * navegação das páginas.
 *
 * @author Gilmário
 */
@Named
@SessionScoped
public class BeanNavegacaoMB extends NavegacaoGenerico implements Serializable {

    public BeanNavegacaoMB() {
        super();
    }

    @Override
    @PostConstruct
    protected void setarExtensao() {
        setExtensao("xhtml");
        setMnemonicoSistema("SAF");
    }

}
