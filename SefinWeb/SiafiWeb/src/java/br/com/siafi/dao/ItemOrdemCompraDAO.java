/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.sefin.modelo.dto.ItemOrdemContratoResumoDTO;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.ItemOrdemCompra;
import br.com.siafi.modelo.ItemOrdemCompraPk;
import br.com.siafi.modelo.OrdemCompra;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author ari
 */
@Stateless
public class ItemOrdemCompraDAO extends DAO<ItemOrdemCompra, ItemOrdemCompraPk> implements Serializable {

    public ItemOrdemCompraDAO() {
        super(ItemOrdemCompra.class);
    }

    public List<ItemOrdemCompra> litarPorOrdemCompra(OrdemCompra ordemCompra) throws Exception {
        Query q = getEm().createQuery("SELECT i FROM ItemOrdemCompra i WHERE i.ordemCompra = :ordem ORDER BY i.id ");
        q.setParameter("ordem", ordemCompra);
        return q.getResultList();
    }

    public void excluirItem(ItemOrdemCompra ioc) throws Exception {
        Query q = getEm().createQuery("delete FROM ItemOrdemCompra i WHERE i.itemOrdemCompraPk = :pk ");
        q.setParameter("pk", ioc.getItemOrdemCompraPk());
        q.executeUpdate();
    }

    public void limpar(OrdemCompra ordemCompra, Integer maxItem) throws Exception {
        Query q = getEm().createQuery("delete FROM ItemOrdemCompra i WHERE i.itemOrdemCompraPk.ordem > :ordem AND i.ordemCompra = :ordemCompra");
        q.setParameter("ordemCompra", ordemCompra);
        q.setParameter("ordem", maxItem);
        q.executeUpdate();
    }

    public List<ItemOrdemCompra> litarPorOrdemCompra(ItemOrdemCompraPk id) {
        return getEm().createQuery("SELECT i FROM ItemOrdemCompra i WHERE i.itemOrdemCompraPk =:id").setParameter("id", id).getResultList();
    }

    public List<ItemOrdemContratoResumoDTO> listarResumo(Contrato c) {
        return getEm().createQuery("SELECT new br.com.sefin.modelo.dto.ItemOrdemContratoResumoDTO(io.itemLicitacao.item,SUM(io.quantidade), io.itemLicitacao) FROM ItemOrdemCompra io WHERE io.ordemCompra.contrato =:c AND io.ordemCompra.status <> 'Cancelada' GROUP BY io.itemLicitacao.item, io.itemLicitacao ", ItemOrdemContratoResumoDTO.class).setParameter("c", c).getResultList();
    }
}
