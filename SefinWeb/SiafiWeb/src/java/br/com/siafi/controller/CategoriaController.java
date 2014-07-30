/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.CategoriaDAO;
import br.com.siafi.modelo.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 18/04/2013 - Controler da classe
 * Categoria
 *
 * @author Gilmário
 */
@Stateless
public class CategoriaController extends Controller<Categoria, Long> implements Serializable {

    @EJB
    private CategoriaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Categoria t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<Categoria> listaCategoriaPorCota(UnidadeOrcamentaria uo) throws Exception {
        return dao.listaCategoriaPorCota(uo);
    }

//    public Categoria categoriaPorTexto(List<String> valores) throws Exception {
//        Categoria c = new Categoria();
//        c.setId(Long.parseLong(valores.get(0)));
//        c.setNome(valores.get(1));
//        return c;
//    }
    public List<Categoria> listarCategoriasNormais() throws Exception {
        return dao.listarCategoriasNormais();
    }

}
