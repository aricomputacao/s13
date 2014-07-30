package br.com.gestor.dao;

import br.com.gestor.modelo.Funcao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorFuncaoDao")
public class FuncaoDAO extends GestorDAO<Funcao> implements Serializable {

    public FuncaoDAO() {
        super(Funcao.class);
    }
}
