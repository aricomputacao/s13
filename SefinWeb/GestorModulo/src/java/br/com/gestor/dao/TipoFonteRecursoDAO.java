package br.com.gestor.dao;

import br.com.gestor.modelo.TipoFonteRecurso;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorTipoFonteRecursoDao")
public class TipoFonteRecursoDAO extends GestorDAO<TipoFonteRecurso> implements Serializable {

    public TipoFonteRecursoDAO() {
        super(TipoFonteRecurso.class);
    }
}
