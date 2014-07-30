/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.ExercicioController;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.controler.Controller;
import br.com.sefin.enumerated.Mes;
import br.com.siafi.dao.CotaControleDAO;
import br.com.siafi.dao.CotaDAO;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.CentroCusto;
import br.com.sefin.modelo.dto.CotaAnaliticoDto;
import br.com.siafi.modelo.CotaControle;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 07/05/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class CotaController extends Controller<Cota, Long> implements Serializable {

    @EJB
    private CotaDAO dao;
    @EJB
    private CotaControleDAO controleDao;
    @EJB
    private ExercicioController exercicioControler;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Cota t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<Cota> listarUnidadeOrcCategoria(UnidadeOrcamentaria unidadeOrcamentaria, Categoria categoria) throws Exception {
        if (unidadeOrcamentaria != null && categoria == null) {
            return dao.listarPorUnidadeOrc(unidadeOrcamentaria);
        } else if (unidadeOrcamentaria == null && categoria != null) {
            return dao.listarPorCategoria(categoria);
        } else if (unidadeOrcamentaria != null && categoria != null) {
            return dao.listarPorUnidadeOrcCategoria(unidadeOrcamentaria, categoria);
        } else {
            return dao.listarTodos("despesa");
        }

    }

    public Cota busca(UnidadeOrcamentaria uo, Categoria c, CentroCusto d) throws Exception {
        return dao.busca(uo, c, d);
    }

    public List<Cota> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria orcamentaria) throws Exception {
        return dao.listarPorUnidadeOrc(orcamentaria);
    }

    public List<CotaAnaliticoDto> CotaAnaliticoDto(List<SolicitacaoFinanceira> financeiras, List<Cota> cotas, Mes competencia) throws Exception {
        List<CotaAnaliticoDto> dtos = new ArrayList<>();
        List<CotaAnaliticoDto> dtoReturn = new ArrayList<>();
        CotaAnaliticoDto analiticoDto = new CotaAnaliticoDto();
        CotaControle cotaControle;
        BigDecimal saldoRelativo;
        analiticoDto.setSolicitacaoFinanceiras(new ArrayList<SolicitacaoFinanceira>());
        analiticoDto.setCota(new Cota());
        for (SolicitacaoFinanceira s : financeiras) {

            if (analiticoDto.getCota() == s.getCota()) {
                analiticoDto.getSolicitacaoFinanceiras().add(s);
            } else {
                saldoRelativo = BigDecimal.ZERO;
                cotaControle = controleDao.valorAtualCota(s.getCota(), competencia, exercicioControler.exercicioVigente());
                if (cotaControle != null) {
                    saldoRelativo = cotaControle.getValor();
                }
                analiticoDto = new CotaAnaliticoDto();
                analiticoDto.setSolicitacaoFinanceiras(new ArrayList<SolicitacaoFinanceira>());
                analiticoDto.getSolicitacaoFinanceiras().add(s);
                analiticoDto.setSaldoRelativo(saldoRelativo);
                analiticoDto.setCota(s.getCota());
                dtos.add(analiticoDto);
            }
        }
        for (Cota c : cotas) {
            int cont = 0;
            for (CotaAnaliticoDto ca : dtos) {
                if (ca.getCota().getId() == c.getId()) {
                    cont = 1;
                }
            }
            if (cont == 0) {
                saldoRelativo = BigDecimal.ZERO;
                analiticoDto = new CotaAnaliticoDto();
                analiticoDto.setSolicitacaoFinanceiras(new ArrayList<SolicitacaoFinanceira>());
                cotaControle = controleDao.valorAtualCota(c, competencia, exercicioControler.exercicioVigente());
                if (cotaControle != null) {
                    saldoRelativo = cotaControle.getValor();
                }
                analiticoDto.setSaldoRelativo(saldoRelativo);
                analiticoDto.setCota(c);
                dtoReturn.add(analiticoDto);
            }
        }

        for (CotaAnaliticoDto dt : dtoReturn) {
            dtos.add(dt);
        }

        for (CotaAnaliticoDto cotaAnaliticoDto : dtos) {
            cotaAnaliticoDto.calculaSaldo();
        }

        return dtos;
    }

    public List<Cota> listarUnidadeOrcamentariaCategoria(UnidadeOrcamentaria unidadeOrcamentaria, Categoria categoria) throws Exception {
        if (categoria == null) {
            return dao.listarPorUnidadeOrc(unidadeOrcamentaria);
        } else {
            return dao.listarPorUnidadeOrcCategoria(unidadeOrcamentaria, categoria);
        }

    }

    public List<Cota> listarUnidadeOrcamentariaCategoria(List<UnidadeOrcamentaria> unidadeOrcamentarias, Categoria categoria) throws Exception {
        if (categoria == null) {
            return dao.listarPorUnidadeOrc(unidadeOrcamentarias);
        } else {
            return dao.listarPorUnidadeOrcCategoria(unidadeOrcamentarias, categoria);
        }
    }

}
