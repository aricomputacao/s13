/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.UnidadeOrcamentariaDAO;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.UnidadeOrcamentariaPK;
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
public class UnidadeOrcamentariaController extends Controller<UnidadeOrcamentaria, UnidadeOrcamentariaPK> implements Serializable {

    @EJB
    private UnidadeOrcamentariaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(UnidadeOrcamentaria t) throws Exception {
        dao.atualizar(t);
    }

    public List<UnidadeOrcamentaria> listar(Orgao orgao) throws Exception {
        return dao.listar(orgao);
    }

    public List<UnidadeOrcamentaria> listarAtivos() throws Exception {
        return dao.listarAtivos();
    }

    public UnidadeOrcamentaria buscarId(String string) throws Exception {
        return dao.buscarId(string);
    }

    public UnidadeOrcamentaria buscarAtivo(String numero) throws Exception {
        return dao.unidadeAtiva(numero.substring(0, 2), numero.substring(2, 4));
    }

}
