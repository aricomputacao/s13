/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.UnidadeAdministrativaDAO;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeAdministrativa;
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
public class UnidadeAdministrativaController extends Controller<UnidadeAdministrativa, String> implements Serializable {

    @EJB
    private UnidadeAdministrativaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(UnidadeAdministrativa t) throws Exception {
        dao.atualizar(t);
    }

    public boolean checarOrgao(Orgao o, List<Orgao> orgaos) throws Exception {
        return dao.checarOrgao(o, orgaos);
    }

    public boolean checarUnidade(String id) throws Exception {
        return dao.checarUnidade(id);
    }

    public boolean existe(String id) throws Exception {
        return dao.existe(id);
    }

}
