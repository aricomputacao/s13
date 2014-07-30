/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.TarefaDAO;
import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.Tarefa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Classe do Projeto Siafi - Criado em 03/05/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class TarefaController extends Controller<Tarefa, Long> implements Serializable {

    @EJB
    private TarefaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Tarefa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<Tarefa> listarPorSistema(String ordem, String valor, String sigla) throws Exception {
        return dao.listarPorSistema(ordem, valor, sigla);
    }

    public List<Tarefa> listarPorModulo(Modulo modulo) throws Exception {
        return dao.listarPorModulo(modulo);
    }

    public List<Tarefa> listarPorSistema(Sistema s) throws Exception {
        return dao.listarPorSistema(s);
    }

    public List<Tarefa> listarPorModulo(Sistema sistema, Modulo modulo, String campoBusca, String valorBusca) throws Exception {
        return dao.listar(sistema, modulo, campoBusca, valorBusca);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Boolean existeTarefa(String mnemonico) throws Exception {
        return dao.buscarUniqueResult("mnemonico", mnemonico) == null;
    }

}
