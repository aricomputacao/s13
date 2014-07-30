/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.PermissaoDAO;
import br.com.guardiao.modelo.Permissao;
import br.com.guardiao.modelo.Tarefa;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class PermissaoController extends Controller<Permissao, Long> implements Serializable {

    @EJB
    private PermissaoDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Permissao t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<Permissao> listarTodos(Usuario u) throws Exception {
        return dao.listarTodos(u);
    }

    public void deletarPermissoes(Usuario u) throws Exception {
        dao.deletarPermissoes(u);
    }

    public Permissao busca(Usuario u, Tarefa t) throws Exception {
        return dao.busca(u, t);
    }

}
