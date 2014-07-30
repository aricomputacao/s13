/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.FonteRecursoDAO;
import br.com.siafi.dao.TipoFonteRecursoDAO;
import br.com.siafi.modelo.FonteRecurso;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 11/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class FonteRecursoController extends Controller<FonteRecurso, String> implements Serializable {

    @EJB
    private FonteRecursoDAO dao;
    @EJB
    private TipoFonteRecursoDAO tpdao;
    @EJB
    private br.com.gestor.dao.FonteRecursoDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(FonteRecurso t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.FonteRecurso> lista = gestorDao.listarTodos();
        for (br.com.gestor.modelo.FonteRecurso fonteRecurso : lista) {
            System.out.println(fonteRecurso);
            FonteRecurso f = new FonteRecurso();
            f.setId(fonteRecurso.getId());
            f.setNome(fonteRecurso.getNome());
            f.setTipoFonteRecurso(tpdao.carregar(fonteRecurso.getTipoFonteRecurso().getId()));
            dao.atualizar(f);
        }
    }

    public List<FonteRecurso> listar(UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return dao.listar(unidadeOrcamentaria);
    }

}
