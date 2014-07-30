/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.DAO;
import java.io.Serializable;
import java.util.List;

/**
 * Classe do Projeto guardiao - Criado em 16/04/2013 - Controler generico para
 * servir de base para os outros controlers
 *
 * @param <T>
 * @param <PK>
 * @author Gilmário
 */
public abstract class Controller<T, PK extends Serializable> implements Serializable {

    private DAO dao;

    /**
     *
     */
    protected abstract void inicializaDAO();

    /**
     *
     * @param dao
     */
    protected void setDAO(DAO dao) {
        this.dao = dao;
    }

    /**
     *
     * @return
     */
    protected DAO getDAO() {
        return dao;
    }

    /**
     *
     * @param t
     * @throws Exception
     */
    public void salvar(T t) throws Exception {
        dao.salvar(t);
    }

    /**
     *
     * @param t
     * @throws Exception
     */
    public void atualizar(T t) throws Exception {
        dao.atualizar(t);
    }

    /**
     *
     * @param t
     * @throws Exception
     */
    public void salvarouAtualizar(T t) throws Exception {
        dao.atualizar(t);
    }

    /**
     *
     * @param t
     * @throws Exception
     */
    public void excluir(T t) throws Exception {
        dao.excluir(t);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public T carregar(PK id) throws Exception {
        return (T) dao.carregar(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public T gerenciar(PK id) throws Exception {
        return (T) dao.gerenciar(id);
    }

    /**
     *
     * @param ordem
     * @param campo
     * @param valor
     * @return
     * @throws Exception
     */
    public List<T> listarTodos(String ordem, String campo, String valor) throws Exception {
        return dao.listarTodos(ordem, campo, valor);
    }

    /**
     *
     * @param ordem
     * @return
     * @throws Exception
     */
    public List<T> listarTodos(String ordem) throws Exception {
        return dao.listarTodos(ordem);
    }
}
