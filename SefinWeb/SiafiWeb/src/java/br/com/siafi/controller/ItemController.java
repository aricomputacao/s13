/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.GrupoItemDAO;
import br.com.siafi.dao.ItemDAO;
import br.com.siafi.dao.UnidadeMedidaDAO;
import br.com.siafi.modelo.Item;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SIAFI - Criado em 27/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class ItemController extends Controller<Item, Long> implements Serializable {

    @EJB
    private ItemDAO dao;
    @EJB
    private br.com.gestor.dao.ItemDAO gestorItemDao;
    @EJB
    private GrupoItemDAO grupoItemDao;
    @EJB
    private UnidadeMedidaDAO unidadeMedidaDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Item> lista = gestorItemDao.listarImportacao();
        for (br.com.gestor.modelo.Item i : lista) {
            System.out.println(i.toString());
            Item item = new Item();
            item.setEspecificacao(i.getEspecificacao());
            item.setGrupoItem(grupoItemDao.carregar(i.getGrupoItem().getId()));
            item.setId(i.getId());
            item.setNome(i.getNome());
            item.setUnidadeMedida(unidadeMedidaDao.carregar(i.getUnidadeMedida().getId()));
            dao.atualizar(item);
        }
        gestorItemDao.marcarImportados("ITD");
    }

}
