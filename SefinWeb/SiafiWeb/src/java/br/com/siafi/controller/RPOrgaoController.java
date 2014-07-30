/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.RPOrgaoDAO;
import br.com.siafi.modelo.RpOrgao;
import br.com.siafi.modelo.RpOrgaoPk;
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
public class RPOrgaoController extends Controller<RpOrgao, RpOrgaoPk> implements Serializable {

    @EJB
    private RPOrgaoDAO dao;
    @EJB
    private br.com.gestor.dao.RPOrgaoDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.RPOrgao> lista = gestorDao.listarImportacao();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.RPOrgao r : lista) {
            System.out.println(r.toString());
            RpOrgao orgao = new RpOrgao();
            orgao.setNome(r.getNome());
            RpOrgaoPk pk = new RpOrgaoPk();
            pk.setCodigo(r.getId().getCodigo());
            pk.setExercicio(r.getId().getExercicio());
            orgao.setOrgaoPk(pk);
            dao.atualizar(orgao);
        }
        gestorDao.marcarImportados("RPO");
        System.out.println("RpOrgao Concluído");
    }

}
