/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.UsuarioDAO;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.CriptografiaSenha;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 24/04/2013
 *
 * @author: ari
 */
@Stateless
public class UsuarioController extends Controller<Usuario, String> implements Serializable {

    @EJB
    private UsuarioDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void alterarSenha(Usuario u, String senhaAtual, String novaSenha, String confirmeSenha) throws Exception {
        CriptografiaSenha cs = new CriptografiaSenha();
        if (u.getSenha().equals(cs.criptografarSenha(senhaAtual))) {
            if (novaSenha.equals(confirmeSenha)) {
                u.setSenha(novaSenha);
                dao.atualizar(u);
            }
        }

    }

    public List<Usuario> listarPorArea(AreaAdministrativa areaAdministrativa) throws Exception {
        return dao.listarPorArea(areaAdministrativa);
    }

    @Override
    public void salvarouAtualizar(Usuario t) throws Exception {
        dao.atualizar(t);
    }

    public List<Usuario> listar(Orgao orgao, String campo, String valor) throws Exception {
        return dao.listar(orgao, campo, valor);
    }

}
