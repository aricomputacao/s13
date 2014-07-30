/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.Tarefa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Classe do Projeto guardiao - Criado em 22/03/2013 - Classe de dao da entidade
 * Tarefa
 *
 * @author Gilmário
 */
@Stateless
public class TarefaDAO extends DAO<Tarefa, Long> implements Serializable {

    public TarefaDAO() {
        super(Tarefa.class);
    }

    // Listar todas as tarefas de um modulo
    public List<Tarefa> listarPorModulo(Modulo modulo) throws Exception {
        try {
            List<Tarefa> tarefas = getEm().createQuery("from Tarefa where modulo =:modulo ").setParameter("modulo", modulo).getResultList();
            return tarefas;
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    public List<Tarefa> listarPorSistema(String ordem, String mnemonico, String sigla) throws Exception {
        try {
            List<Tarefa> tarefas = getEm().createQuery("from Tarefa t where t.modulo.sistema.sigla =:sigla and t.mnemonico like :mnemonico order by :ordem")
                    .setParameter("mnemonico", "%" + mnemonico + "%")
                    .setParameter("ordem", ordem).setParameter("sigla", sigla).setMaxResults(10).getResultList();
            return tarefas;
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    public boolean existe(Long id) throws Exception {
        try {
            return carregar(id) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Tarefa> listarPorSistema(Sistema sistema) throws Exception {
        return getEm().createQuery("SELECT t FROM Tarefa t WHERE t.modulo.sistema =:sistema ORDER BY t.modulo, t.nome").setParameter("sistema", sistema).getResultList();
    }

    public List<Tarefa> listar(Sistema sistema, Modulo modulo, String campo, String valor) throws Exception {
        String sql = " SELECT t FROM Tarefa t WHERE t." + campo + " like :valor ";
        if (modulo != null) {
            sql += " AND t.modulo=:modulo ";
        }
        if (sistema != null) {
            sql += " AND t.modulo.sistema=:sistema ";
        }
        Query q = getEm().createQuery(sql);
        q.setParameter("valor", valor + "%");
        if (modulo != null) {
            q.setParameter("modulo", modulo);
        }
        if (sistema != null) {
            q.setParameter("sistema", sistema);
        }
        return q.getResultList();

    }
}
