/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.ElementoDespesa;
import br.com.siafi.modelo.Orcamento;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 - Dao do modelo Projeto
 * Atividade
 *
 * @author Gilmário
 */
@Stateless
public class ProjetoAtividadeDAO extends DAO<ProjetoAtividade, String> implements Serializable {

    public ProjetoAtividadeDAO() {
        super(ProjetoAtividade.class);
    }

    public List<ProjetoAtividade> listarProjetoAtividadeUnidadeOrcamentaria(UnidadeOrcamentaria uo) throws Exception {
        TypedQuery<ProjetoAtividade> q;
        q = getEm().createQuery("SELECT p FROM ProjetoAtividade p WHERE p.unidadeOrcamentaria = :uo", ProjetoAtividade.class);
        q.setParameter("uo", uo);
        return q.getResultList();
    }

    public List<ProjetoAtividade> listarProjetoAtividadeUnidadeOrcamentariaElemento(UnidadeOrcamentaria uo, ElementoDespesa elementoDespesa, Orcamento o) throws Exception {
        TypedQuery<ProjetoAtividade> q;
        q = getEm().createQuery("SELECT d.projetoAtividade FROM Dotacao d WHERE d.projetoAtividade.unidadeOrcamentaria = :uo AND d.naturezaDespesa.elementoDespesa =:elemento AND d.orcamento =:o", ProjetoAtividade.class);
        q.setParameter("uo", uo);
        q.setParameter("elemento", elementoDespesa);
        q.setParameter("o", o);
        return q.getResultList();
    }

    public List<ProjetoAtividade> listar(String tipoAcao, String nome, UnidadeOrcamentaria unidadeOrcamentaria, List<UnidadeOrcamentaria> unidadeOrcamentarias) {
        String sql = "SELECT p FROM ProjetoAtividade p WHERE p.nome like :nome ";
        if (unidadeOrcamentaria != null) {
            sql += " AND p.unidadeOrcamentaria =:unidade ";
        }
        if (unidadeOrcamentarias != null && !unidadeOrcamentarias.isEmpty()) {
            sql += " AND p.unidadeOrcamentaria IN (:unidades) ";
        }
        if (tipoAcao != null && !"".equals(tipoAcao)) {
            sql += " AND p.acao =:acao AND p.tipo =:tipo ";
        }
        Query q = getEm().createQuery(sql);
        q.setParameter("nome", "%" + nome + "%");
        if (unidadeOrcamentaria != null) {
            q.setParameter("unidade", unidadeOrcamentaria);
        }
        if (unidadeOrcamentarias != null && !unidadeOrcamentarias.isEmpty()) {
            q.setParameter("unidades", unidadeOrcamentarias);
        }
        if (tipoAcao != null && !"".equals(tipoAcao)) {
            String tipo = tipoAcao.substring(0, 1);
            String acao = tipoAcao.substring(1, 4);
            q.setParameter("tipo", tipo);
            q.setParameter("acao", acao);
        }
        return q.getResultList();
    }
}
