/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sae.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sae.dao.ConsultaDAO;
import br.com.sefin.modelo.dto.SolicitacaoSaldoDto;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 *
 * @author ari
 */
@Stateless
public class ConsultaController extends Controller<SolicitacaoFinanceira, Long> implements Serializable {

    @EJB
    private ConsultaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<SolicitacaoFinanceira> listarSolicitacaoCredor(String doc, Date dtIni, Date dtFim) throws PersistenceException, EJBException, Exception {
        return dao.listarSolicitacaoCredor(doc, dtIni, dtFim);
    }

    public List<SolicitacaoFinanceira> listarSolicitacaoUnidadeOrcamentariaCredor(UnidadeOrcamentaria uo, String doc, Date dtIni, Date dtFim) throws PersistenceException, EJBException, Exception {
        return dao.listarSolicitacaoUnidadeOrcamentariaCredor(uo, doc, dtIni, dtFim);
    }

    public List<SolicitacaoSaldoDto> solicitacaoSaldoDtos(UnidadeOrcamentaria orcamentaria, String doc, Date dtIni, Date dtFim) throws EJBException, Exception {
        List<SolicitacaoFinanceira> financeiras;
        List<SolicitacaoSaldoDto> dtos = new ArrayList<>();

        if (orcamentaria == null) {
            financeiras = dao.listarSolicitacaoCredor(doc, dtIni, dtFim);

        } else {
            financeiras = dao.listarSolicitacaoUnidadeOrcamentariaCredor(orcamentaria, doc, dtIni, dtFim);
        }

        for (SolicitacaoFinanceira sol : financeiras) {
            SolicitacaoSaldoDto dto = new SolicitacaoSaldoDto();
            List<EmpenhoDetalhe> detalhes;
            detalhes = dao.listarLiquidacaoPorSolicitacao(sol);
            dto.setEmpenhoDetalhes(detalhes);
            dto.setSolicitacaoFinanceira(sol);
            dtos.add(dto);
        }
        return dtos;

    }
}
