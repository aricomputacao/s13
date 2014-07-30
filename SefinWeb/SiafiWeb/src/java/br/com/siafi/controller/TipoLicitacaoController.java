/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.TipoLicitacaoDAO;
import br.com.siafi.modelo.TipoLicitacao;
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
public class TipoLicitacaoController extends Controller<TipoLicitacao, Integer> implements Serializable {

    @EJB
    private TipoLicitacaoDAO tipoLicitacaoDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(tipoLicitacaoDao);
    }

}
