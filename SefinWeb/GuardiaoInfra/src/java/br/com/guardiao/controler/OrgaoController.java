/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.OrgaoDAO;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.OrgaoPk;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 * Arquivo do projeto GuardiaoInfra criando em 24/09/2013
 *
 * @author ari
 */
@Stateless
public class OrgaoController extends Controller<Orgao, String> implements Serializable {

    @EJB
    private OrgaoDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Orgao t) throws Exception {
        if (dao.checarOrgao(t.getId())) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public boolean checarOrgao(OrgaoPk pk) throws Exception {
        return dao.checarOrgao(pk);
    }

    public Orgao buscarNome(String string) throws Exception {
        return dao.buscarNome(string);
    }

    public List<Orgao> listarAtivos() throws Exception {
        return dao.listarAtivos();
    }

}
