/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.CategoriaDespesaDAO;
import br.com.siafi.dao.ElementoDespesaDAO;
import br.com.siafi.dao.GrupoNaturezaDespesaDAO;
import br.com.siafi.dao.ModalidadeAplicacaoDespesaDAO;
import br.com.siafi.dao.NaturezaDespesaDAO;
import br.com.siafi.dao.SubElementoDespesaDAO;
import br.com.siafi.modelo.NaturezaDespesa;
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
public class NaturezaDespesaController extends Controller<NaturezaDespesa, String> implements Serializable {

    @EJB
    private NaturezaDespesaDAO dao;
    @EJB
    private ElementoDespesaDAO eledao;
    @EJB
    private SubElementoDespesaDAO subeldao;
    @EJB
    private NaturezaDespesaDAO natdao;
    @EJB
    private GrupoNaturezaDespesaDAO gpdao;
    @EJB
    private CategoriaDespesaDAO catdao;
    @EJB
    private ModalidadeAplicacaoDespesaDAO mddao;
    @EJB
    private br.com.gestor.dao.NaturezaDespesaDAO gestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(NaturezaDespesa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.NaturezaDespesa> lista = gestorDao.listarTodos();
        for (br.com.gestor.modelo.NaturezaDespesa naturezaDespesa : lista) {
            System.out.println(naturezaDespesa.toString());
            NaturezaDespesa n = new NaturezaDespesa();
            String categoria = naturezaDespesa.getId().substring(0, 1);
            String elemento = naturezaDespesa.getId().substring(4, 6);
            String grupo = naturezaDespesa.getId().substring(1, 2);
            String modalidade = naturezaDespesa.getId().substring(2, 4);
            String subelemento = naturezaDespesa.getId().substring(6);
            n.setCategoriaDespesa(catdao.carregar(categoria));
            if (!elemento.equals("00")) {
                n.setElementoDespesa(eledao.carregar(elemento));
            }
            if (!grupo.equals("00")) {
                n.setGrupoNaturezaDespesa(gpdao.carregar(grupo));
            }
            if (!modalidade.equals("00")) {
                n.setModalidadeAplicacaoDespesa(mddao.carregar(modalidade));
            }
            if (!subelemento.equals("00")) {
                n.setSubElementoDespesa(subeldao.carregar(subelemento));
            }
            n.setId(naturezaDespesa.getId());
            n.setNome(naturezaDespesa.getNome());
            n.setUso(naturezaDespesa.getUso());
            dao.atualizar(n);
        }
    }

}
