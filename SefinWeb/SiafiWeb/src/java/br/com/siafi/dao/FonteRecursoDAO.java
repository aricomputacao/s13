/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.FonteRecurso;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 11/06/2013 - Dao do modelo Fonte Recurso
 *
 * @author Gilmário
 */
@Stateless
public class FonteRecursoDAO extends DAO<FonteRecurso, String> implements Serializable {

    public FonteRecursoDAO() {
        super(FonteRecurso.class);
    }

    public List<FonteRecurso> listar(UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return getEm().createQuery("SELECT DISTINCT c.fonteRecurso FROM Conta c WHERE c.unidadeOrcamentaria=:un ").setParameter("un", unidadeOrcamentaria).getResultList();
    }
}
