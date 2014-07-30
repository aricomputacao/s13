package br.com.gestor.dao;

import br.com.gestor.modelo.Dotacao;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorDotacaoDao")
public class DotacaoDAO extends GestorDAO<Dotacao> implements Serializable {

    public DotacaoDAO() {
        super(Dotacao.class);
    }

    public Dotacao carregarDotacao(Integer id) {
        TypedQuery q;
        q = getEm().createQuery("SELECT d FROM Dotacao d WHERE d.id = :id", Dotacao.class);
        q.setParameter("id", id);
        return (Dotacao) q.getSingleResult();
    }

}
