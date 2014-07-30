/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.ModalidadeAplicacaoDespesaDAO;
import br.com.siafi.modelo.ModalidadeAplicacaoDespesa;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class ModalidadeAplicacaoDepesaController extends Controller<ModalidadeAplicacaoDespesa, String> implements Serializable {

    @EJB
    private ModalidadeAplicacaoDespesaDAO dao;
    @EJB
    private br.com.gestor.dao.ModalidadeAplicacaoDespesaDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.ModalidadeAplicacaoDespesa> lista = gestorDao.listarTodos();
        for (br.com.gestor.modelo.ModalidadeAplicacaoDespesa modalidadeAplicacaoDespesa : lista) {
            System.out.println(modalidadeAplicacaoDespesa.toString());
            ModalidadeAplicacaoDespesa m = new ModalidadeAplicacaoDespesa();
            m.setId(modalidadeAplicacaoDespesa.getId());
            m.setNome(modalidadeAplicacaoDespesa.getNome());
            m.setEspecificacao(modalidadeAplicacaoDespesa.getEspecificacao());
            dao.atualizar(m);
        }
    }

}
