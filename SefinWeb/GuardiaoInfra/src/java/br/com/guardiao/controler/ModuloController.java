/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.ModuloDAO;
import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Sistema;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author gilmario
 */
@Stateless
public class ModuloController extends Controller<Modulo, Long> implements Serializable {

    @EJB
    private ModuloDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Modulo t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<Modulo> listar(Sistema s, String campo, String valor) throws Exception {
        return dao.listar(s, campo, valor);
    }

    public List<Modulo> listar(Sistema s) throws Exception {
        return dao.listar(s);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Modulo existeModulo(Sistema sistema, String mnemonico) throws Exception {
        return dao.buscarUnique(sistema, mnemonico);
    }

}
