/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.service;

import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.PersistenceException;

/**
 *
 * @author gilmario
 */
//@WebService()
public class Servico {

    @EJB
    private SolicitacaoFinanceiraController controler;

    public Servico() {
    }

    public void Hello() {

    }

    //@WebMethod
    public String dizOi() {
        List<SolicitacaoFinanceira> lista;
        String reste = "";
        BigDecimal t = new BigDecimal(BigInteger.ZERO);
        try {
            lista = controler.listarTodos("id");

            for (SolicitacaoFinanceira s : lista) {
                t = t.add(s.getValor());
            }
        } catch (PersistenceException ex) {
            Logger.getLogger(Servico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EJBException ex) {
            Logger.getLogger(Servico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Servico.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Total de Solicitações ae Agora : " + t.toString();
    }

}
