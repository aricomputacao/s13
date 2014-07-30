/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.CentroCusto;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 07/05/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class CotaDAO extends DAO<Cota, Long> implements Serializable {

    public CotaDAO() {
        super(Cota.class);
    }

    public List<Cota> listarPorUnidadeOrc(UnidadeOrcamentaria u) throws Exception {
        return getEm().createQuery("SELECT c from Cota c where c.unidadeOrcamentaria=:u AND c.categoria.nome != :e order by c.categoria.nome,c.despesa.nome").setParameter("u", u).setParameter("e", "EXTRA").getResultList();
    }

    public List<Cota> listarPorUnidadeOrcCategoria(UnidadeOrcamentaria unidadeOrcamentaria, Categoria categoria) throws Exception {
        return getEm().createQuery("from Cota where unidadeOrcamentaria=:u AND categoria=:c order by categoria").setParameter("u", unidadeOrcamentaria).setParameter("c", categoria).getResultList();
    }

    public List<Cota> listarPorCategoria(Categoria categoria) throws Exception {
        return getEm().createQuery("from Cota where categoria=:c order by categoria").setParameter("c", categoria).getResultList();
    }

    public Cota busca(UnidadeOrcamentaria uo, Categoria c, CentroCusto d) throws Exception {
        return (Cota) getEm().createQuery("SELECT a from Cota a where a.categoria=:c and a.unidadeOrcamentaria = :uo and a.despesa = :d")
                .setParameter("c", c)
                .setParameter("uo", uo)
                .setParameter("d", d).getSingleResult();

    }

    public List<Cota> listarPorUnidadeOrc(List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        return getEm().createQuery("from Cota where unidadeOrcamentaria IN (:u) AND categoria.nome != 'EXTRA' order by categoria").setParameter("u", unidadeOrcamentarias).getResultList();
    }

    public List<Cota> listarPorUnidadeOrcCategoria(List<UnidadeOrcamentaria> unidadeOrcamentarias, Categoria categoria) throws Exception {
        return getEm().createQuery("from Cota where unidadeOrcamentaria IN (:u) AND categoria=:c order by categoria").setParameter("u", unidadeOrcamentarias).setParameter("c", categoria).getResultList();
    }

}
