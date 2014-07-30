/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.sefin.view.ItemOrdemCompraView;
import br.com.siafi.dao.ItemOrdemCompraViewDAO;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 09/07/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class ItemOrdemCompraViewController implements Serializable {

    @EJB
    private ItemOrdemCompraViewDAO dao;

    public List<ItemOrdemCompraView> listarPorLicitacao(Integer licitacao) throws Exception {
        return dao.listarPorLicitacao(licitacao);
    }
}
