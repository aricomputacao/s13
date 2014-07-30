/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.CreditoAdicionalDetalheDAO;
import br.com.siafi.dao.DotacaoDAO;
import br.com.siafi.modelo.CreditoAdicional;
import br.com.siafi.modelo.CreditoAdicionalDetalhe;
import br.com.siafi.modelo.CreditoAdicionalDetalhePk;
import br.com.siafi.modelo.Dotacao;
import br.com.siafi.modelo.Orcamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class CreditoAdicionalDetalheController extends Controller<CreditoAdicionalDetalhe, CreditoAdicionalDetalhePk> implements Serializable {

    @EJB
    private CreditoAdicionalDetalheDAO dao;
    @EJB
    private br.com.gestor.dao.CreditoAdicionalDetalheDAO gestorDao;
    @EJB
    private DotacaoDAO dotacaoDao;
    @EJB
    private OrcamentoController odao;
    @EJB
    private FonteRecursoController fonteRecursoControler;

    @Override
    public void salvarouAtualizar(CreditoAdicionalDetalhe t) throws Exception {
        dao.atualizar(t);
    }

    public BigDecimal totalAdicionado(Dotacao dotacao) throws Exception {
        return dao.totalAdicionado(dotacao);
    }

    public BigDecimal totalAnulado(Dotacao dotacao) throws Exception {
        return dao.totalAnulado(dotacao);
    }

    public void importar() throws Exception {
        gestorDao.corrigeImportacao();
        List<br.com.gestor.modelo.CreditoAdicionalDetalhe> lista = gestorDao.listarImportacao();
        Orcamento o = odao.getOrcamentoVigente();
        for (br.com.gestor.modelo.CreditoAdicionalDetalhe j : lista) {
            System.out.println(j.toString());
            System.out.println(j.getDotacao());
            CreditoAdicionalDetalhe i = new CreditoAdicionalDetalhe();
            CreditoAdicionalDetalhePk detalhePk = new CreditoAdicionalDetalhePk();

            i.setAnulacao(j.getAnulacao());
            i.setCredito(j.getCredito());
            i.setCreditoAdicional(new CreditoAdicional(j.getId().getCreditoAdicional()));
            i.setDotacao(dotacaoDao.dotacaoPorCodigoSimples(j.getDotacao().toString(), o));
            i.setExcesso(j.getExcesso());
            i.setFonte(j.getFonte());
            if (i.getFonteRecurso() != null) {
                i.setFonteRecurso(fonteRecursoControler.carregar(j.getFonteRecurso()));
            }

            detalhePk.setCodigo(j.getId().getCodigo());
            detalhePk.setCreditoAdicionalId(j.getId().getCreditoAdicional());
            i.setId(detalhePk);

            i.setSeqDia(j.getSeqdia());
            i.setSubTipo(j.getSubTipo());
            i.setTipo(j.getTipo());
            i.setValor(j.getValor());
            dao.atualizar(i);
        }
        gestorDao.marcarImportados("CAD");
    }

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<CreditoAdicionalDetalhe> listar(Dotacao dotacao) throws Exception {
        return dao.listar(dotacao);
    }
}
