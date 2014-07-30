/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.TipoFonteRecurso;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 11/06/2013 - Dao do modelo Tipo Fonte
 * Recurso
 *
 * @author Gilmário
 */
@Stateless
public class TipoFonteRecursoDAO extends DAO<TipoFonteRecurso, String> implements Serializable {

    public TipoFonteRecursoDAO() {
        super(TipoFonteRecurso.class);
    }
}
