/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.ItemLicitacao;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Classe do Projeto ******* - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorItemLicitacaoDao")
public class ItemLicitacaoDAO extends GestorDAO<ItemLicitacao> implements Serializable {

    public ItemLicitacaoDAO() {
        super(ItemLicitacao.class);
    }

    public void atualizarItemLicitacaLoteNulo() {
        Query q;
        q = getEm().createNativeQuery("update licitacaoitem   set liilote = 0 where liilote is null");
        q.executeUpdate();
    }

    public void atualizarItemLicitacaAditivoNulo() {
        Query q;
        q = getEm().createNativeQuery("update licitacaoitem   set liiaditivo = 0 where liiaditivo is null");
        q.executeUpdate();
    }
}
