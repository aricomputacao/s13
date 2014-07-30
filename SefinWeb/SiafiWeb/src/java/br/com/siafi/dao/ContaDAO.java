/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Conta;
import br.com.siafi.modelo.FonteRecurso;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Arquivo do projeto SiafiWeb criando em 29/07/2013
 *
 * @author ari
 */
@Stateless
public class ContaDAO extends DAO<Conta, Integer> implements Serializable {

    public ContaDAO() {
        super(Conta.class);
    }

    public List<Conta> listarNumero(Integer numero) throws Exception {
        TypedQuery<Conta> q;
        q = getEm().createQuery("SELECT con FROM Conta con WHERE con.numero = :numero", Conta.class);
        q.setParameter("numero", numero);
        return q.getResultList();
    }

    public List<Conta> listarUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        TypedQuery<Conta> q;
        q = getEm().createQuery("SELECT con FROM Conta con WHERE con.unidadeOrcamentaria = :unidadeOrcamentaria", Conta.class);
        q.setParameter("unidadeOrcamentaria", unidadeOrcamentaria);
        return q.getResultList();
    }

    public List<Conta> listarPorFonte(FonteRecurso fonteRecurso, UnidadeOrcamentaria un) throws Exception {
        return getEm().createQuery("SELECT c FROM Conta c WHERE c.fonteRecurso=:fonte AND c.unidadeOrcamentaria =:un ").setParameter("fonte", fonteRecurso).setParameter("un", un).getResultList();
    }
}
