/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.SistemaDAO;
import br.com.guardiao.modelo.Sistema;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Arquivo do projeto GuardiaoInfra criando em 24/09/2013
 *
 * @author ari
 */
@Stateless
public class SistemaController extends Controller<Sistema, Long> implements Serializable {

    @EJB
    private SistemaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Sistema t) throws Exception {
        if (t.getId() != null) {
            dao.atualizar(t);
        } else {
            dao.salvar(t);
        }
    }

    public Sistema buscarMnemonico(String mn) throws Exception {
        return dao.buscarMnemonico(mn);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Sistema existeSistema(String mn) throws Exception {
        return dao.buscarMnemonico(mn);
    }

    public List<Sistema> listarNome(String nome) throws Exception {
        return dao.listarNome(nome);
    }

}
