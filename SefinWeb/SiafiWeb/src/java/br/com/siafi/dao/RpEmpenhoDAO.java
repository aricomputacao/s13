/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.RpEmpenho;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author gilmario
 */
@Stateless
public class RpEmpenhoDAO extends DAO<RpEmpenho, Integer> implements Serializable {

    public RpEmpenhoDAO() {
        super(RpEmpenho.class);
    }

    public RpEmpenho carregarEmpenhoCod(Integer cod) throws Exception {
        TypedQuery<RpEmpenho> q;
        q = getEm().createQuery("SELECT e from RpEmpenho e where e.id = :cod", RpEmpenho.class);
        q.setParameter("cod", cod);
        return q.getSingleResult();
    }

    public List<RpEmpenho> EmpenhoUnidade() throws Exception {
        TypedQuery<RpEmpenho> q;
        q = getEm().createQuery("SELECT e from RpEmpenho e ", RpEmpenho.class);
        //q.setParameter("cod", cod);
        return q.getResultList();
    }
}
