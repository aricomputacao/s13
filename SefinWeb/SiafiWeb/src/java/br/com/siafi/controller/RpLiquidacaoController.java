/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.dao.RPLiquidacaoDAO;
import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.RpEmpenhoDAO;
import br.com.siafi.dao.RpLiquidacaoDAO;
import br.com.siafi.modelo.RpLiquidacao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class RpLiquidacaoController extends Controller<RpLiquidacao, Integer> implements Serializable {

    @EJB
    private RpLiquidacaoDAO liquidacaoDao;
    @EJB
    private RPLiquidacaoDAO gestorDao;
    @EJB
    private RpEmpenhoDAO rPEmpenhoDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(liquidacaoDao);
    }

    public List<RpLiquidacao> listarLiquidacaoLiberar() throws Exception {
        return liquidacaoDao.listarLiquidacaoLiberar();
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.RPLiquidacao> lista = gestorDao.listarImportacao();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.RPLiquidacao j : lista) {

            System.out.println(j.getCodigo());
            RpLiquidacao i = new RpLiquidacao();
            i.setCodigo(j.getCodigo());
            i.setDataLiquidacao(j.getRplData());
            i.setNumeroNota(j.getNumeroNota());
            i.setRpEmpenho(rPEmpenhoDao.carregar(j.getRpEmpenho()));
            i.setStatus(j.getStatus());
            i.setTipo(j.getTipo());
            i.setValor(j.getValor());
            liquidacaoDao.atualizar(i);
        }
        gestorDao.marcarImportados("RPL");
        System.out.println("RpLiquidacao Concluído");
    }

}
