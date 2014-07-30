/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.GrupoItemDAO;
import br.com.siafi.modelo.GrupoItem;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto ******* - Criado em 27/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class GrupoItemController extends Controller<GrupoItem, Integer> implements Serializable {

    @EJB
    private GrupoItemDAO dao;
    @EJB
    private br.com.gestor.dao.GrupoItemDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(GrupoItem t) throws Exception {

    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.GrupoItem> gestorGrupoItems = gestorDao.listarImportacao();
        for (br.com.gestor.modelo.GrupoItem grupoItemGestor : gestorGrupoItems) {
            System.out.println(grupoItemGestor.toString());
            GrupoItem grupoItem = new GrupoItem();
            grupoItem.setId(grupoItemGestor.getId());
            grupoItem.setNome(grupoItemGestor.getNome());
            dao.atualizar(grupoItem);
            //grupoItemGestor.setExportado(Boolean.TRUE);
            //gestorDao.atualizar(grupoItemGestor);
        }
        gestorDao.marcarImportados("GID");
    }

}
