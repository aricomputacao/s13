package br.com.gestor.dao;

import br.com.gestor.modelo.SubFuncao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorSubFuncaoDao")
public class SubFuncaoDAO extends GestorDAO<SubFuncao> implements Serializable {

    public SubFuncaoDAO() {
        super(SubFuncao.class);
    }
}
