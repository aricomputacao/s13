/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.dao.RPUnidadeDAO;
import br.com.guardiao.controler.Controller;
import br.com.guardiao.dao.OrgaoDAO;
import br.com.siafi.dao.RPOrgaoDAO;
import br.com.siafi.dao.RPUnidadeOrcamentariaDAO;
import br.com.siafi.modelo.RpOrgaoPk;
import br.com.siafi.modelo.RpUnidadeOrcamentaria;
import br.com.siafi.modelo.RpUnidadeOrcamentariaPk;
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
public class RPUnidadeOrcamentariaController extends Controller<RpUnidadeOrcamentaria, RpUnidadeOrcamentariaPk> implements Serializable {

    @EJB
    private RPUnidadeOrcamentariaDAO dao;
    @EJB
    private RPUnidadeDAO gestorDao;
    @EJB
    private OrgaoDAO orgaoDao;
    @EJB
    private RPOrgaoDAO rPOrgaoDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.RPUnidade> lista = gestorDao.listarImportacao();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.RPUnidade j : lista) {
            System.out.println(j.getNome());
            RpUnidadeOrcamentaria i = new RpUnidadeOrcamentaria();

            i.setOrgaoAtual(orgaoDao.orgaoAtivo(j.getOrgaoAtual()));
            i.setNome(j.getNome());

            RpUnidadeOrcamentariaPk pk = new RpUnidadeOrcamentariaPk();
            pk.setOrgao(j.getRpUnidadePK().getOrgao());
            pk.setId(j.getRpUnidadePK().getUnidade());
            pk.setExercicio(j.getRpUnidadePK().getExercicio());

            RpOrgaoPk rpOrgaoPk = new RpOrgaoPk();
            rpOrgaoPk.setCodigo(j.getRpUnidadePK().getOrgao());
            rpOrgaoPk.setExercicio(j.getRpUnidadePK().getExercicio());
            i.setRpOrgao(rPOrgaoDao.carregar(rpOrgaoPk));
            i.setOrcamentariaPk(pk);
            dao.atualizar(i);
        }
        gestorDao.marcarImportados("RPU");
        System.out.println("RpUnidade Concluído");
    }

}
