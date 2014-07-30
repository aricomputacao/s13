/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Classe do Projeto Siafi - Criado em 24/06/2013 -
 *
 * @author Gilmário
 */
public class GestorDAO<T> implements Serializable {

    @PersistenceContext(unitName = "GestorModuloPU")
    private EntityManager em;
    private Class classe;

    public GestorDAO(Class classe) {
        this.classe = classe;
    }

    public List<T> listarImportacao() {
        return em.createQuery("SELECT a FROM " + getClasse().getName() + " a where a.exportado <> true ").getResultList();
    }

    public void marcarImportados(String s) {
        em.createQuery("UPDATE " + getClasse().getName() + " SET " + s + "EXPORTADO = TRUE WHERE " + s + "EXPORTADO = FALSE").executeUpdate();
    }

    public T buscarUniqueResult(String campo, Object valor) {
        Query q = em.createQuery("SELECT c from " + getClasse().getName() + " c where " + campo + "= :valor");
        q.setParameter("valor", valor);
        try {
            return (T) q.getSingleResult();
        } catch (Exception no) {
            return null;
        }
    }

    public T carregar(Serializable id) {
        return (T) em.find(getClasse(), id);
    }

    public T gerenciar(Serializable id) {
        return (T) em.getReference(getClasse(), id);
    }

    public void atualizar(T t) {
        em.merge(t);
    }

    protected EntityManager getEm() {
        return em;
    }

    protected void setEm(EntityManager em) {
        this.em = em;
    }

    public List<T> listarTodos(String ordem) {
        return em.createQuery("SELECT a FROM " + getClasse().getName() + " a ORDER BY " + ordem).getResultList();
    }

    public List<T> listarTodos() {
        return em.createQuery("SELECT a FROM " + getClasse().getName() + " a ").getResultList();
    }

    protected Class getClasse() {
        return classe;
    }

    protected void setClasse(Class classe) {
        this.classe = classe;
    }
}
