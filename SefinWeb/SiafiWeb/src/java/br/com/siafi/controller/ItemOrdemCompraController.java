/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.sefin.modelo.dto.ItemOrdemContratoResumoDTO;
import br.com.siafi.dao.ItemOrdemCompraDAO;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.ItemOrdemCompra;
import br.com.siafi.modelo.ItemOrdemCompraPk;
import br.com.siafi.modelo.OrdemCompra;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class ItemOrdemCompraController extends Controller<ItemOrdemCompra, ItemOrdemCompraPk> implements Serializable {

    @EJB
    private ItemOrdemCompraDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(ItemOrdemCompra t) throws Exception {
        if (t.getItemOrdemCompraPk() != null) {
            dao.atualizar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void removerItensOrdemCancelada(OrdemCompra compra) throws Exception {
        List<ItemOrdemCompra> ordemCompras = listarPorOrdemCompra(compra);
        for (ItemOrdemCompra it : ordemCompras) {
            excluir(it);
        }
    }

    public List<ItemOrdemCompra> listarPorOrdemCompra(OrdemCompra ordemCompra) throws Exception {
        return dao.litarPorOrdemCompra(ordemCompra);
    }

    public void limparOrdemCompra(OrdemCompra ordemCompra, Integer maxItem) throws Exception {
        dao.limpar(ordemCompra, maxItem);
    }

    public List<ItemOrdemContratoResumoDTO> listarResumo(Contrato c) {
        return dao.listarResumo(c);
    }

}
