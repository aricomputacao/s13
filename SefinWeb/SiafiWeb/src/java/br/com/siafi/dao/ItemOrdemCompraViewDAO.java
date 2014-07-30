/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.sefin.view.ItemOrdemCompraView;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 09/07/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class ItemOrdemCompraViewDAO extends DAO<ItemOrdemCompraView, Long> implements Serializable {

    public ItemOrdemCompraViewDAO() {
        super(ItemOrdemCompraView.class);
    }

    public List<ItemOrdemCompraView> listarPorLicitacao(Integer licitacao) throws Exception {
        return getEm().createQuery("SELECT i FROM ItemOrdemCompraView i WHERE i.licitacao = :licitacao ").setParameter("licitacao", licitacao).getResultList();
    }
}
