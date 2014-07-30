package br.com.gestor.dao;

import br.com.gestor.modelo.Programa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorProgramaDao")
public class ProgramaDAO extends GestorDAO<Programa> implements Serializable {

    public ProgramaDAO() {
        super(Programa.class);
    }
}
