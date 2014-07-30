/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.CargoDAO;
import br.com.guardiao.modelo.Cargo;
import java.io.Serializable;
import java.sql.SQLException;
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
public class CargoController extends Controller<Cargo, Long> implements Serializable {

    @EJB
    private CargoDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Cargo t) throws SQLException, PersistenceException, EJBException, Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

}
