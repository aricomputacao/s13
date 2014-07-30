/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import br.com.gestor.modelo.EmpenhoSolicitacaoItem;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * @Sistema GestorModulo
 * @Data 17/07/2013
 * @author gilmario
 */
@Stateless(name = "gestorempenhoSolicitacaoItensDao")
public class EmpenhoSolicitacaoItemDAO extends GestorDAO<EmpenhoSolicitacaoItem> implements Serializable {

    public EmpenhoSolicitacaoItemDAO() {
        super(EmpenhoSolicitacaoItem.class);
    }

    public void salvar(EmpenhoSolicitacaoItem empenhoSolicitacaoItem) {
        getEm().merge(empenhoSolicitacaoItem);
    }
    
    public List<EmpenhoSolicitacaoItem> buscarPorSolicitacao(String sol){
        TypedQuery<EmpenhoSolicitacaoItem> q;
        q = getEm().createQuery("SELECT i from EmpenhoSolicitacaoItem i WHERE i.solicitacaoItemPk.numeroSolicitacao = :sol", EmpenhoSolicitacaoItem.class);
        q.setParameter("sol", sol);
        
        return q.getResultList();
    }
    
    public void remover(EmpenhoSolicitacaoItem item){
        getEm().remove(item);
    }
}
