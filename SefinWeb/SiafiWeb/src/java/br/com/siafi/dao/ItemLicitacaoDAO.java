/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Item;
import br.com.siafi.modelo.ItemLicitacao;
import br.com.siafi.modelo.Licitacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto SiafiWeb criada em 26/06/2013
 *
 * @author: ari
 */
@Stateless
public class ItemLicitacaoDAO extends DAO<ItemLicitacao, Long> implements Serializable {

    public ItemLicitacaoDAO() {
        super(ItemLicitacao.class);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ItemLicitacao buscarPorLicitacaoCredorItemAditivo(Licitacao l, Credor c, Item i, String aditivo) throws Exception {
        Query q = getEm().createQuery("SELECT c FROM ItemLicitacao c WHERE c.aditivo=:aditivo AND c.licitacao=:licitacao AND c.credor=:credor AND c.item=:item");
        q.setParameter("item", i);
        q.setParameter("aditivo", aditivo);
        q.setParameter("licitacao", l);
        q.setParameter("credor", c);
        return (ItemLicitacao) q.getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public ItemLicitacao buscarPorLicitacaoCredorItemAditivoLote(Licitacao l, Credor c, Item i, String aditivo, String lote) {
        try {
            Query q = getEm().createQuery("SELECT c FROM ItemLicitacao c WHERE c.aditivo=:aditivo AND c.licitacao=:licitacao AND c.credor=:credor AND c.item=:item AND c.lote=:lote");
            q.setParameter("item", i);
            q.setParameter("aditivo", aditivo);
            q.setParameter("licitacao", l);
            q.setParameter("credor", c);
            q.setParameter("lote", lote);
            return (ItemLicitacao) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception ee) {
            return null;
        }
    }

    public List<Licitacao> listarPorCredor(Credor credor) throws Exception {
        TypedQuery<Licitacao> q = getEm().createQuery("SELECT DISTINCT c.licitacao FROM ItemLicitacao c WHERE c.credor=:credor", Licitacao.class);
        q.setParameter("credor", credor);
        return q.getResultList();
    }

    public List<Credor> listarCredores(String nome) throws Exception {
        TypedQuery<Credor> q = getEm().createQuery("SELECT DISTINCT c.credor FROM ItemLicitacao c WHERE c.credor.pessoa.nome like :nome", Credor.class);
        q.setParameter("nome", "%" + nome + "%");
        return q.getResultList();
    }

    public List<ItemLicitacao> listaPorLicitacao(Licitacao licitacao) throws Exception {
        return getEm().createQuery("SELECT c FROM ItemLicitacao c WHERE c.licitacao=:licitacao ORDER BY c.credor ").setParameter("licitacao", licitacao).getResultList();
    }
}
