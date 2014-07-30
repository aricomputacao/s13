/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.PendenciaDAO;
import br.com.siafi.modelo.Pendencia;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class PendenciaController extends Controller<Pendencia, String> implements Serializable{

    @EJB
    private PendenciaDAO dao;
    
    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }
    
}
