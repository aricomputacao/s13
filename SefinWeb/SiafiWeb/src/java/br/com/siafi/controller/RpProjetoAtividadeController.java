/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.dao.RPProjetoAtividadeDAO;
import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.RPUnidadeOrcamentariaDAO;
import br.com.siafi.dao.RpProjetoAtividadeDAO;
import br.com.siafi.modelo.Programa;
import br.com.siafi.modelo.RpProjetoAtividade;
import br.com.siafi.modelo.RpUnidadeOrcamentariaPk;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 *
 * @author gilmario
 */
@Stateless
public class RpProjetoAtividadeController extends Controller<RpProjetoAtividade, String> implements Serializable {

    @EJB
    private RpProjetoAtividadeDAO projetoAtividadeDao;
    @EJB
    private RPProjetoAtividadeDAO gestorDao;
    @EJB
    private RPUnidadeOrcamentariaDAO orcamentariaDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(projetoAtividadeDao);
    }

    public void importar() throws SQLException, EJBException, PersistenceException, Exception {
        List<br.com.gestor.modelo.RPProjetoAtividade> lista = gestorDao.listarImportacao();

        System.out.println(lista.size());
        for (br.com.gestor.modelo.RPProjetoAtividade j : lista) {
            System.out.println(j.getNome());
            System.out.println(j.getId().getFuncao());
            RpProjetoAtividade i = new RpProjetoAtividade();
            i.setAbreviacao(j.getNome());
            i.setAcao(j.getId().getCodigo().substring(1).replace(" ", ""));

            i.setDataAbertura(null);

            i.setFuncao(j.getId().getFuncao());
            i.setId(j.getId().getFuncao() + j.getId().getSubFuncao() + j.getId().getPrograma() + j.getId().getCodigo());
            i.setNome(j.getNome());
            i.setPrograma(new Programa(j.getId().getPrograma()));
            i.setSubfuncao(j.getId().getSubFuncao());
            i.setTipo(j.getTipo().toString());

            RpUnidadeOrcamentariaPk unidadeOrcamentariaPk = new RpUnidadeOrcamentariaPk();
            unidadeOrcamentariaPk.setOrgao(j.getId().getOrgao());
            unidadeOrcamentariaPk.setExercicio(j.getId().getExercicio());
            unidadeOrcamentariaPk.setId(j.getId().getUnidade());
            //  RpUnidadeOrcamentaria rpUnidadeOrcamentaria = new RpUnidadeOrcamentaria();
            i.setUnidadeOrcamentaria(orcamentariaDao.carregar(unidadeOrcamentariaPk));

            System.out.println(j.getId().getOrgao().concat(" - ").concat(j.getId().getUnidade()).concat(j.getId().getExercicio().toString()));

            projetoAtividadeDao.atualizar(i);

        }
        gestorDao.marcarImportados("RPP");
        System.out.println("RpUnidade Concluído");
    }

}
