/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto Siafi - Criado em 16/04/2013 - Dao do modelo Categoria
 *
 * @author Gilmário
 */
@Stateless
public class CategoriaDAO extends DAO<Categoria, Long> implements Serializable {

    /**
     *
     */
    public CategoriaDAO() {
        super(Categoria.class);
    }

    public List<Categoria> listaCategoriaPorCota(UnidadeOrcamentaria uo) throws Exception {
        TypedQuery<Categoria> qry = getEm().createQuery("SELECT DISTINCT c.categoria from Cota  c WHERE c.unidadeOrcamentaria = :uo ", Categoria.class);
        qry.setParameter("uo", uo);
        return qry.getResultList();

    }

    public List<Categoria> listarCategoriasNormais() throws Exception {
        return getEm().createQuery("SELECT c FROM Categoria c WHERE c.nome != 'EXTRA' ").getResultList();
    }
}
