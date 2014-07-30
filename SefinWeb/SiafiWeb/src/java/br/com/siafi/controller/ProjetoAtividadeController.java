/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.dao.UnidadeOrcamentariaDAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.FuncaoDAO;
import br.com.siafi.dao.ProgramaDAO;
import br.com.siafi.dao.ProjetoAtividadeDAO;
import br.com.siafi.dao.SubFuncaoDAO;
import br.com.siafi.modelo.ElementoDespesa;
import br.com.siafi.modelo.Orcamento;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class ProjetoAtividadeController extends Controller<ProjetoAtividade, String> implements Serializable {

    @EJB
    private ProjetoAtividadeDAO dao;
    @EJB
    private UnidadeOrcamentariaDAO uodao;
    @EJB
    private FuncaoDAO fdao;
    @EJB
    private SubFuncaoDAO sdao;
    @EJB
    private ProgramaDAO pdao;
    @EJB
    private br.com.gestor.dao.ProjetoAtividadeDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(ProjetoAtividade t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.ProjetoAtividade> lista = gestorDao.listarTodos();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.ProjetoAtividade projetoAtividade : lista) {
            System.out.println(projetoAtividade.toString());
            ProjetoAtividade p = new ProjetoAtividade();
            p.setAbreviacao(projetoAtividade.getAbreviacao());
            p.setAcao(projetoAtividade.getAcao());
            p.setDataAbertura(projetoAtividade.getDataAbertura());
            p.setFuncao(fdao.carregar(projetoAtividade.getFuncao()));
            p.setId(projetoAtividade.getId());
            p.setNome(projetoAtividade.getNome());
            p.setPrograma(pdao.carregar(projetoAtividade.getPrograma()));
            p.setSubfuncao(sdao.carregar(projetoAtividade.getSubfuncao()));
            p.setTipo(projetoAtividade.getTipo().toString());
            System.out.println(projetoAtividade.getOrgao() + projetoAtividade.getUnidadeOrcamentaria());
            p.setUnidadeOrcamentaria(uodao.unidadeAtiva(projetoAtividade.getOrgao(), projetoAtividade.getUnidadeOrcamentaria()));
            dao.atualizar(p);
        }
    }

    public List<ProjetoAtividade> listarProjetoAtividadeUnidadeOrcamentaria(UnidadeOrcamentaria uo) throws Exception {
        return dao.listarProjetoAtividadeUnidadeOrcamentaria(uo);
    }

    public List<ProjetoAtividade> listarProjetoAtividadeUnidadeOrcamentariaElemento(UnidadeOrcamentaria unidadeOrcamentaria, ElementoDespesa elementoDespesa, Orcamento o) throws Exception {
        return dao.listarProjetoAtividadeUnidadeOrcamentariaElemento(unidadeOrcamentaria, elementoDespesa, o);
    }

    public List<ProjetoAtividade> listar(String tipoAcao, String valorBusca, UnidadeOrcamentaria unidadeOrcamentaria, List<UnidadeOrcamentaria> unidadeOrcamentarias) {
        return dao.listar(tipoAcao, valorBusca, unidadeOrcamentaria, unidadeOrcamentarias);
    }

}
