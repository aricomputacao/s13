/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.ModalidadeLicitacaoDAO;
import br.com.siafi.modelo.ModalidadeLicitacao;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 28/06/2013
 *
 * @author: ari
 */
@Stateless
public class ModalidadeLicitacaoController extends Controller<ModalidadeLicitacao, Integer> implements Serializable {

    @EJB
    private ModalidadeLicitacaoDAO modalidadeLicitacaoDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(modalidadeLicitacaoDao);
    }

}
