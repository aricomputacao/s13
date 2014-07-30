/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.auditoria.modelo.SolicitacaoFinanceiraAud;
import br.com.siafi.dao.SolicitacaoFinanceiraAudDAO;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class SolicitacaoFinanceiraAudController extends Controller<SolicitacaoFinanceiraAud, String> implements Serializable {

    @EJB
    private SolicitacaoFinanceiraAudDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    // Usar somente para classes de Auditoria
    public List<SolicitacaoFinanceiraAud> listarAuditoria(String campo, String id) throws Exception {
        return dao.listarAuditoria(campo, id);
    }

}
