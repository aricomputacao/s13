/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sae.controller;

import br.com.guardiao.controler.Controller;
import br.com.sae.dao.CredorDAO;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class CredorController extends Controller<Credor, Integer> implements Serializable{

    @EJB
    private CredorDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
   
}
