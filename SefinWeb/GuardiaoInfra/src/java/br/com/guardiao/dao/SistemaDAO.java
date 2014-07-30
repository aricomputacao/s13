/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto guardiao - Criado em 20/03/2013 - DAO Da classe Sistema
 *
 * @author Gilmário
 */
@Stateless
public class SistemaDAO extends DAO<Sistema, Long> implements Serializable {

    public SistemaDAO() {
        super(Sistema.class);
    }

    // Selecionar um sistema com um nome especifico
    public Sistema buscarNome(String nome) throws Exception {
        Query qry = getEm().createQuery("SELECT s FROM Sistema s where s.nome = :nome", Sistema.class);
        qry.setParameter("nome", nome);
        Sistema s = (Sistema) qry.getSingleResult();
        return s;
    }

    // Listar sistemas pelo nome que contenha uma palavra chave
    public List<Sistema> listarNome(String nome) throws Exception {
        TypedQuery<Sistema> qry;
        qry = getEm().createQuery("SELECT s FROM Sistema s where s.nome like :nome", Sistema.class);
        qry.setParameter("nome", "%" + nome + "%");
        return qry.getResultList();
    }

    // Listar sistemas que o usuario não tem acesso.
    public List<Sistema> listarTodosEx(Usuario usu) throws Exception {
        Query qry = getEm().createQuery("FROM Sistema s where s not in (:sistemas)", Sistema.class);
        qry.setParameter("sistemas", usu.getSistemas());
        return qry.getResultList();
    }

    public Sistema buscarMnemonico(String mn) throws Exception {
        Query qry = getEm().createQuery("SELECT s FROM Sistema s where s.mnemonico = :mn", Sistema.class);
        qry.setParameter("mn", mn);
        return (Sistema) qry.getSingleResult();
    }
}
