/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Permissao;
import br.com.guardiao.modelo.Tarefa;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Classe do Projeto guardiao - Criado em 26/03/2013 - Classe dao da permissão
 *
 * @author Gilmário
 */
@Stateless
public class PermissaoDAO extends DAO<Permissao, Long> implements Serializable {

    public PermissaoDAO() {
        super(Permissao.class);
    }

    // Buscar uma permissão de um usuario e tarefa especificos
    public Permissao busca(Usuario u, Tarefa t) throws Exception {
        Query q = getEm().createQuery("select p from Permissao p where p.usuario = :usu and p.tarefa = :tar");
        q.setParameter("usu", u);
        q.setParameter("tar", t);
        return (Permissao) q.getSingleResult();
    }

    // retornar todas as permissões de um determinado usuario
    public List<Permissao> listarTodos(Usuario u) throws Exception {
        Query q = getEm().createQuery("select p from Permissao p where p.usuario = :usu");
        q.setParameter("usu", u);
        return q.getResultList();
    }

    // Deletar todas as permissões do usuario
    public void deletarPermissoes(Usuario u) throws Exception {
        getEm().createQuery("delete from Permissao p where p.usuario =:u").setParameter("u", u).executeUpdate();
    }
}
