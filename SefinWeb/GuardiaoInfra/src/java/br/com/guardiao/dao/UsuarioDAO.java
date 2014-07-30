/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

/**
 * Classe do Projeto guardiao - Criado em 22/03/2013 - Classe dao da entidade
 * Usuario
 *
 * @author Gilmário
 */
@Stateless
public class UsuarioDAO extends DAO<Usuario, String> implements Serializable {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario login(String nome, String senha) throws Exception {
        return (Usuario) getEm().createQuery("from Usuario where senha=:senha and pessoa.nome=:nome").setParameter("senha", senha).setParameter("nome", nome).getSingleResult();
    }

    public boolean verificaId(String doc) throws Exception {
        try {
            return carregar(doc) == null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean existe(String doc) throws Exception {
        try {
            return carregar(doc) != null;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Usuario> listarTodos(String nome) throws Exception {
        return getEm().createQuery("from Usuario a where a.pessoa.nome like :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Usuario> listarPorArea(AreaAdministrativa areaAdministrativa) throws Exception {
        return getEm().createQuery("SELECT u FROM Usuario u WHERE u.areaAdministrativa =:area").setParameter("area", areaAdministrativa).getResultList();
    }

    public List<Usuario> listar(Orgao orgao, String campo, String valor) throws Exception {
        return getEm().createQuery("SELECT u FROM Usuario u WHERE u.orgao =:orgao AND u." + campo + " like :valor ").setParameter("valor", "%" + valor + "%").setParameter("orgao", orgao).setMaxResults(100).getResultList();
    }
}
