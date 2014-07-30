/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.LicitacaoDAO;
import br.com.siafi.dao.ModalidadeLicitacaoDAO;
import br.com.siafi.dao.TipoLicitacaoDAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Licitacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 28/06/2013
 *
 * @author: ari
 */
@Stateless
public class LicitacaoController extends Controller<Licitacao, Integer> implements Serializable {

    @EJB
    private LicitacaoDAO licitacaoDao;
    @EJB
    private TipoLicitacaoDAO tipoLicitacaoDao;
    @EJB
    private ModalidadeLicitacaoDAO modalidadeLicitacaoDao;
    @EJB
    private br.com.gestor.dao.LicitacaoDAO licitacaoGestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(licitacaoDao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Licitacao> licitacaosGestor = licitacaoGestorDao.listarImportacao();
        for (br.com.gestor.modelo.Licitacao licitacao : licitacaosGestor) {
            System.out.println(licitacao.toString());
            Licitacao licitacaoSiafi = licitacaoDao.carregar(licitacao.getId());
            if (licitacaoSiafi == null) {
                licitacaoSiafi = new Licitacao();
                licitacaoSiafi.setId(licitacao.getId());
                licitacaoSiafi.setModalidadeLicitacao(modalidadeLicitacaoDao.carregar(licitacao.getModalidade()));
                licitacaoSiafi.setData(licitacao.getDataLicitacao());
                licitacaoSiafi.setNumero(licitacao.getNumero());
                licitacaoSiafi.setObjetivo(licitacao.getObjetivo());
                licitacaoSiafi.setTipoLicitacao(tipoLicitacaoDao.carregar(licitacao.getTipoLicitacao()));
                licitacaoSiafi.setValorLimite(licitacao.getValorLimit());
                licitacaoSiafi.setValorOrcado(licitacao.getValorOrcado());
                licitacaoSiafi.setValorOriginal(licitacao.getValorOrcado());
                licitacaoDao.atualizar(licitacaoSiafi);
            }
        }
        licitacaoGestorDao.marcarImportados("LIC");
    }

    public List<Licitacao> listarUnidadeOrcamentariaCredor(UnidadeOrcamentaria uo, Credor credor) throws Exception {
        return licitacaoDao.listarUnidadeOrcamentariaCredor(uo, credor);
    }

    public List<Licitacao> listarUnidadeOrcamentariaCredorLimpas(UnidadeOrcamentaria uo, Credor credor) throws Exception {
        return licitacaoDao.listarUnidadeOrcamentariaCredorLimpas(uo, credor);
    }

    public List<Licitacao> listarLicitacaoUnidadeOrcamentariaCredorLimpas(UnidadeOrcamentaria uo, Credor credor) throws Exception {
        return licitacaoDao.listarLicitacaoUnidadeOrcCredor(uo, credor);
    }

    public List<Licitacao> listarUnidadeOrcamentariaNumero(UnidadeOrcamentaria uo, String numero) throws Exception {
        return licitacaoDao.listarUnidadeOrcamentariaNumero(uo, numero);
    }

    public List<Licitacao> listarUnidadeOrcamentariaNumero(List<UnidadeOrcamentaria> uo, String numero) throws Exception {
        return licitacaoDao.listarUnidadeOrcamentariaNumero(uo, numero);
    }

    public List<Licitacao> listar(UnidadeOrcamentaria uo, String numero, Credor credor) throws Exception {
        if (credor.getId() == null) {
            return licitacaoDao.listarUnidadeOrcamentariaNumero(uo, numero);
        } else if (credor.getId() != null) {
            return licitacaoDao.listarUnidadeOrcamentariaCredorNumero(uo, credor, numero);
        } else {
            return licitacaoDao.listarPorUnidadeOrcamentaria(uo);
        }
    }

    public List<Licitacao> listar(List<UnidadeOrcamentaria> uo, String numero, Credor credor) throws Exception {
        if (credor.getId() == null) {
            return licitacaoDao.listarUnidadeOrcamentariaNumero(uo, numero);
        } else if (credor.getId() != null) {
            return licitacaoDao.listarUnidadeOrcamentariaCredorNumero(uo, credor, numero);
        } else {
            return licitacaoDao.listarPorUnidadeOrcamentaria(uo);
        }
    }

    public List<Licitacao> listarUnidadeNumeroCredor(UnidadeOrcamentaria uo, String numero, Credor credor) throws Exception {
        return licitacaoDao.listarUnidadeOrcamentariaCredorNumero(uo, credor, numero);
    }

    public List<Licitacao> listarUnidadeNumeroCredor(List<UnidadeOrcamentaria> uo, String numero, Credor credor) throws Exception {
        return licitacaoDao.listarUnidadeOrcamentariaCredorNumero(uo, credor, numero);
    }

    public List<Licitacao> lisitacaoPorOrdemComprea(Licitacao licitacao) throws Exception {

        return licitacaoDao.listarPorOrdemCompra(licitacao);

    }

    public List<UnidadeOrcamentaria> listarUnidadesPorLicitacao(Licitacao licitacao) throws Exception {
        return licitacaoDao.listarPorLicitacao(licitacao);
    }

    public BigDecimal calcularSaldo(Licitacao lic) throws Exception {
        BigDecimal valorUtilizado = licitacaoDao.calcularValorUtilizado(lic);
        BigDecimal valorTotal = licitacaoDao.calcularValorTotal(lic);
        if (valorUtilizado == null) {
            valorUtilizado = BigDecimal.ZERO;
        }
        if (valorTotal == null) {
            valorTotal = BigDecimal.ZERO;
        }
        return valorTotal.subtract(valorUtilizado);
    }

}
