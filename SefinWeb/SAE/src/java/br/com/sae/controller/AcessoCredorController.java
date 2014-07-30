/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sae.controller;

import br.com.guardiao.controler.Controller;
import br.com.sae.dao.AcessoCredorDAO;
import br.com.sae.modelo.AcessoCredor;
import java.io.Serializable;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 *
 * @author ari
 */
@Stateless
public class AcessoCredorController extends Controller<AcessoCredor, String> implements Serializable{
    @EJB
    private AcessoCredorDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
    

    @Override
    public void salvarouAtualizar(AcessoCredor t) throws SQLException, PersistenceException, EJBException, Exception {
        if (t.getDocumento() != null) {
            dao.atualizar(t);
        }else{
            dao.salvar(t);
        }
    }
    
   
    
}
