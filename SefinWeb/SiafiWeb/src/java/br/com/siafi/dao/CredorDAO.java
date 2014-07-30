/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Licitacao;
import br.com.siafi.modelo.Pendencia;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto SiafiWeb criada em 26/06/2013
 *
 * @author: ari
 */
@Stateless
public class CredorDAO extends DAO<Credor, Integer> implements Serializable {

    public CredorDAO() {
        super(Credor.class);
    }

    public List<Credor> listarOutros(String campo, String valor) throws Exception {
        return getEm().createQuery("SELECT a FROM Credor a WHERE a." + campo + " like :valor ").setParameter("valor", "%" + valor.toUpperCase() + "%").getResultList();
    }

    public List<Credor> listarTodos(Licitacao licitacao) throws Exception {
        return getEm().createQuery("SELECT DISTINCT i.credor FROM ItemLicitacao i WHERE i.licitacao = :licitacao").setParameter("licitacao", licitacao).getResultList();
    }

    public List<Credor> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria un, String nome) throws Exception {
        TypedQuery<Credor> q = getEm().createQuery("SELECT DISTINCT i.credor FROM ItemLicitacao i WHERE i.licitacao in (SELECT ld.licitacao FROM LicitacaoUnidadeOrcamentaria ld WHERE  ld.unidadeOrcamentaria=:un ) AND i.credor.pessoa.nome like :nome", Credor.class);
        q.setParameter("un", un);
        q.setParameter("nome", nome + "%");
        return q.getResultList();
    }

    public Pendencia credorComPendencia(String documento) throws Exception {
        return getEm().find(Pendencia.class, documento);

    }
}
