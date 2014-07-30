/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.dao.UnidadeOrcamentariaDAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.ContaDAO;
import br.com.siafi.dao.FonteRecursoDAO;
import br.com.siafi.modelo.Conta;
import br.com.siafi.modelo.FonteRecurso;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Arquivo do projeto SiafiWeb criando em 29/07/2013
 *
 * @author ari
 */
@Stateless
public class ContaController extends Controller<Conta, Integer> implements Serializable {

    @EJB
    private ContaDAO contaDao;
    @EJB
    private br.com.gestor.dao.ContaDAO gestorContaDao;
    @EJB
    private FonteRecursoDAO fonteRecursoDao;
    @EJB
    private UnidadeOrcamentariaDAO unidadeOrcamentariaDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(contaDao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Conta> gestorContas = gestorContaDao.listarImportacao();
        for (br.com.gestor.modelo.Conta gestorConta : gestorContas) {
            UnidadeOrcamentaria unidadeOrcamentaria;
            FonteRecurso fonteRecurso;
            Conta siafiConta = new Conta();
            fonteRecurso = fonteRecursoDao.carregar(gestorConta.getFonteRecurso());
            unidadeOrcamentaria = unidadeOrcamentariaDao.unidadeAtiva(gestorConta.getOrgao(), gestorConta.getUnidadeAdministrativa());
            siafiConta.setFonteRecurso(fonteRecurso);
            siafiConta.setUnidadeOrcamentaria(unidadeOrcamentaria);
            siafiConta.setId(gestorConta.getId());
            siafiConta.setNome(gestorConta.getNome());
            siafiConta.setNomeclatura(gestorConta.getNomeclatura());
            siafiConta.setNumero(gestorConta.getNumero());
            siafiConta.setSaldo(gestorConta.getSaldoInicial());
            contaDao.atualizar(siafiConta);
        }
        gestorContaDao.marcarImportados("CON");
    }

    @Override
    public void salvarouAtualizar(Conta t) throws Exception {
        //
    }

    public List<Conta> listarNumero(Integer numero, UnidadeOrcamentaria uo) throws Exception {
        if (numero != 0) {
            return contaDao.listarNumero(numero);

        } else if (uo != null) {
            return contaDao.listarUnidadeOrcamentaria(uo);
        } else {
            return contaDao.listarTodos("id");
        }

    }

    public List<Conta> listarPorFonte(FonteRecurso fonteRecurso, UnidadeOrcamentaria un) throws Exception {
        return contaDao.listarPorFonte(fonteRecurso, un);
    }

}
