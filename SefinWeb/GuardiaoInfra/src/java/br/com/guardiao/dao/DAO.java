/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Classe do Projeto guardiao - Criado em 11/04/2013 -
 *
 * @author Gilmário
 * @param <T>
 * @param <PK>
 */
public abstract class DAO<T, PK extends Serializable> implements Serializable {

    @PersistenceContext(unitName = "GuardiaoPU")
    private EntityManager em;
    private final Class<T> entityClass;

    protected EntityManager getEm() {
        return em;
    }

    public DAO(Class classe) {
        this.entityClass = classe;
    }

    public void salvar(T t) throws Exception {
        em.persist(t);
    }

    public void excluir(T t) throws Exception {
        em.remove(t);
    }

    public void atualizar(T t) throws Exception {
        em.merge(t);
    }

    public T buscarUniqueResult(String campo, Object valor) throws Exception {
        return (T) em.createQuery("from " + getEntityClass().getName() + " where " + campo + "= :valor").setParameter("valor", valor).getSingleResult();
    }

    public T carregar(PK id) throws Exception {
        return (T) em.find(getEntityClass(), id);
    }

    public T gerenciar(PK id) throws Exception {
        return (T) em.getReference(getEntityClass(), id);
    }

    public List<T> listarTodos(String ordem) throws Exception {
        return em.createQuery("FROM " + getEntityClass().getName() + " order by :ordem")
                .setParameter("ordem", ordem).getResultList();
    }

    // Usar somente para classes de Auditoria
    public List<T> listarAuditoria(String campo, PK id) throws Exception {
        return getEm().createQuery("SELECT a FROM " + getEntityClass().getName() + " a WHERE " + "a." + campo + "=:id order by a.entidadeRevisional.dataRevisao ").setParameter("id", id).getResultList();
    }

    public List<T> listarTodos(String ordem, String campo, String valor) throws Exception {
        return em.createQuery("SELECT a FROM " + getEntityClass().getName() + " a WHERE a." + campo + " like :valor order by a." + ordem).setParameter("valor", valor + "%").getResultList();
    }

    public void comitaTransacao() throws Exception {
        getEm().flush();
    }

    public List<T> pesquisaAutoComplete(String ordem, String campo, String sugest, int numeroResultados) throws Exception {
        return getEm().createQuery("SELECT t FROM " + getEntityClass().getName() + " t WHERE t." + campo + " like :sugest ORDER BY t." + campo).setParameter("sugest", sugest + "%").setMaxResults(numeroResultados).getResultList();
    }

    //Para ser utilizado nas importações ou operaçoes em lote
    public void limpaEntityManager() throws Exception {
        getEm().clear();
    }

//    public void limpaCacheRegion(T t, String region) throws Exception {
//        List<CacheManager> allCacheManagers = CacheManager.ALL_CACHE_MANAGERS;
//        for (CacheManager cacheManager : allCacheManagers) {
//            Cache cache = cacheManager.getCache(region);
//            cache.removeAll();
//
//        }
//    }
    protected Class getEntityClass() {
        return entityClass;
    }
}
