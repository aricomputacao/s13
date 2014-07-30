package br.com.gestor.dao;

import br.com.gestor.modelo.FonteRecurso;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorFonteRecursoDao")
public class FonteRecursoDAO extends GestorDAO<FonteRecurso> implements Serializable {

    public FonteRecursoDAO() {
        super(FonteRecurso.class);
    }
}
