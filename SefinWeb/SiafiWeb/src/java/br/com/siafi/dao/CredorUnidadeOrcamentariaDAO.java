/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.CredorUnidadeOrcamentaria;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless
public class CredorUnidadeOrcamentariaDAO extends DAO<CredorUnidadeOrcamentaria, Long> implements Serializable {
    
    public CredorUnidadeOrcamentariaDAO() {
        super(CredorUnidadeOrcamentaria.class);
    }
    
    public List<CredorUnidadeOrcamentaria> credorUnidadeOrcamentarias(List<UnidadeOrcamentaria> uos) {
        return getEm().createQuery("SELECT c from CredorUnidadeOrcamentaria c WHERE c.unidadeOrcamentaria in (:uos)")
                .setParameter("uos", uos)
                .getResultList();
    }
    
    public CredorUnidadeOrcamentaria buscaCredorUnidadeOrcamentaria(UnidadeOrcamentaria uo) {
        TypedQuery<CredorUnidadeOrcamentaria> q;
        q = getEm().createQuery("SELECT c from CredorUnidadeOrcamentaria c WHERE c.unidadeOrcamentaria = :uo", CredorUnidadeOrcamentaria.class);
        q.setParameter("uo", uo);
        if (q.getResultList().isEmpty()) {
            return new CredorUnidadeOrcamentaria();
        }else{
            return q.getSingleResult();

        }
        
        
    }
}
