/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Sistema;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Classe do Projeto guardiao - Criado em 20/03/2013 - Classe Dao da Entidade
 * Módulo
 *
 * @author Gilmário
 */
@Stateless
public class ModuloDAO extends DAO<Modulo, Long> implements Serializable {

    public ModuloDAO() {
        super(Modulo.class);
    }

    /**
     *
     * @param sis
     * @return
     */
    public List<Modulo> listar(Sistema sis) throws Exception {
        return getEm().createQuery("SELECT m FROM Modulo m WHERE m.sistema =:sistema").setParameter("sistema", sis).getResultList();
    }

    public Modulo buscarUnique(Sistema sis, String mnemonico) throws Exception {
        return getEm().createQuery("SELECT m FROM Modulo m WHERE m.sistema =:sistema AND m.mnemonico =:mnemonico", Modulo.class).setParameter("sistema", sis).setParameter("mnemonico", mnemonico).getSingleResult();
    }

    public List<Modulo> listar(Sistema s, String campo, String valor) throws Exception {
        String sql = "SELECT m FROM Modulo m WHERE " + campo + " like :valor ";
        if (s != null) {
            sql += " AND m.sistema =:s ";
        }
        Query q = getEm().createQuery(sql);
        q.setParameter("valor", valor + "%");
        if (s != null) {
            q.setParameter("s", s);
        }
        return q.getResultList();
    }
}
