/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.controler.Controller;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.siafi.dao.EncaminhamentoLiquidacaoDAO;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class EncaminhamentoLiquidacaoController extends Controller<EncaminhamentoLiquidacao, Long> implements Serializable {

    @EJB
    private EncaminhamentoLiquidacaoDAO dao;
    @EJB
    private AreaAdministrativaController areaController;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<EncaminhamentoLiquidacao> listar(SolicitacaoLiquidacao solicitacaoLiquidacao) throws Exception {
        return dao.listar(solicitacaoLiquidacao);
    }

    public AreaAdministrativa selecionaDestino(SolicitacaoLiquidacao sl) throws Exception {
        //Controladoria
        if (sl.getStatusSolicitacaoLiquidacao().equals(StatusSolicitacaoLiquidacao.Aberto) && sl.getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro)) {
            return areaController.administrativaTipo(TipoAreaAdm.Controladoria);// Controladoria
            //Contabilidade
        } else if (sl.getStatusSolicitacaoLiquidacao().equals(StatusSolicitacaoLiquidacao.Aberto) && sl.getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Controladoria)) {
            return areaController.administrativaTipo(TipoAreaAdm.Contabilidade);// Contabilidade
//            Financeiro
        } else if (sl.getStatusSolicitacaoLiquidacao().equals(StatusSolicitacaoLiquidacao.Aprovado) && sl.getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
            return areaController.pegarAreaFinanceiraOrgao(sl.getSolicitacaoFinanceira().getCota().getUnidadeOrcamentaria().getOrgao(), TipoAreaAdm.Financeiro) ;
        } else if (sl.getStatusSolicitacaoLiquidacao().equals(StatusSolicitacaoLiquidacao.Aprovado) && sl.getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro)) {
            return areaController.administrativaTipo(TipoAreaAdm.Tesouraria);// Tesouraria
        } else {
            throw new Exception("Não foi possivel localizar um destino correto");
        }
    }

    public EncaminhamentoLiquidacao ultimoEncaminhamento(SolicitacaoLiquidacao sl) throws Exception {
        return dao.ultimoEncaminhamento(sl);
    }

    public List<EncaminhamentoLiquidacao> listarReceber(AreaAdministrativa administrativa, List<UnidadeOrcamentaria> unidadesOrcamentarias, UnidadeOrcamentaria orcamentaria, StatusSolicitacaoLiquidacao situacao) throws Exception {
        return dao.listarReceber(administrativa, unidadesOrcamentarias, orcamentaria, situacao);
    }

    public List<SolicitacaoLiquidacao> listarSolicitacoes(UnidadeOrcamentaria unidadeOrcamentaria, StatusSolicitacaoLiquidacao statusSolicitacaoLiquidacao, AreaAdministrativa areaAdministrativa) {
        return dao.listarSolicitacoes(unidadeOrcamentaria, statusSolicitacaoLiquidacao, areaAdministrativa);
    }
    /**
     * Metodo para pegar encaminhamentos das liquidações da tesouraria para contabilidade
     * @param dataEnc
     * @param origem
     * @param destino
     * @return 
     * @throws java.lang.Exception 
     */
    public List<EncaminhamentoLiquidacao> encaminhamentoLiquidacaos(Date dataEnc,AreaAdministrativa origem,AreaAdministrativa destino) throws Exception{
        return dao.listar(origem,destino,dataEnc);
    }

    

}
