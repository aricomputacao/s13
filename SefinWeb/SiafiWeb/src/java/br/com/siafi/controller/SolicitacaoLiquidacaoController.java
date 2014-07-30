/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.Usuario;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.siafi.dao.SolicitacaoLiquidacaoDAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class SolicitacaoLiquidacaoController extends Controller<SolicitacaoLiquidacao, Long> implements Serializable {

    @EJB
    private SolicitacaoLiquidacaoDAO dao;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraController;
    @EJB
    private EncaminhamentoLiquidacaoController encaminhamentoLiquidacaoController;

    @PostConstruct
    @Override
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) {
        return dao.listarPorSolicitacao(solicitacaoFinanceira);
    }

    public List<SolicitacaoLiquidacao> listarPorSolicitacao(String numero, List<UnidadeOrcamentaria> uos, UnidadeOrcamentaria uo, Credor c) {
        if (c.getId() != null) {
            if (numero.equals("") || numero.equals(null)) {
                if (uo != null) {
                    return dao.listarPorSolicitacao(uo, c);
                } else {
                    return dao.listarPorSolicitacao(uos, c);

                }
            } else {
                if (uo != null) {
                    return dao.listarPorSolicitacao(numero, uo, c);
                } else {
                    return dao.listarPorSolicitacao(numero, uos, c);

                }

            }
        } else {
            if (numero.equals("") || numero.equals(null)) {
                if (uo != null) {
                    return dao.listarPorSolicitacao(uo);
                } else {
                    return dao.listarPorSolicitacao(uos);

                }
            } else {
                if (uo != null) {
                    return dao.listarPorSolicitacao(numero, uo);
                } else {
                    return dao.listarPorSolicitacao(numero, uos);

                }

            }
        }

    }

    /**
     * Lista apenas os processos que estão na tesouraria
     *
     * @param numero
     * @param uos
     * @param uo
     * @param c
     * @return
     */
    public List<SolicitacaoLiquidacao> listarPorSolicitacaoTesouraria(String numero, List<UnidadeOrcamentaria> uos, UnidadeOrcamentaria uo, Credor c) {
        if (c.getId() != null) {
            if (numero.equals("") || numero.equals(null)) {
                if (uo != null) {
                    return dao.listarPorSolicitacaoTesouraria(uo, c);
                } else {
                    return dao.listarPorSolicitacaoTesouraria(uos, c);

                }
            } else {
                if (uo != null) {
                    return dao.listarPorSolicitacaoTesouraria(numero, uo, c);
                } else {
                    return dao.listarPorSolicitacaoTesouraria(numero, uos, c);

                }

            }
        } else {
            if (numero.equals("") || numero.equals(null)) {
                if (uo != null) {
                    return dao.listarPorSolicitacaoTesouraria(uo);
                } else {
                    return dao.listarPorSolicitacaoTesouraria(uos);

                }
            } else {
                if (uo != null) {
                    return dao.listarPorSolicitacaoTesouraria(numero, uo);
                } else {
                    return dao.listarPorSolicitacaoTesouraria(numero, uos);

                }

            }
        }

    }

    public void addProcessoLiquidacao(SolicitacaoLiquidacao solicitacaoLiquidacao, Usuario u) throws Exception {
        solicitacaoLiquidacao.setAreaAdministrativa(u.getAreaAdministrativa());
        if (u.getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria)) {
            solicitacaoLiquidacao.setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Aprovado);
        } else {
            solicitacaoLiquidacao.setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Aberto);
        }
        //Concatena a nota da liquidação com a nota registrada no processo
        if (solicitacaoLiquidacao.getSolicitacaoFinanceira().getNotaFiscal() != null) {
            if (!solicitacaoLiquidacao.getSolicitacaoFinanceira().getNotaFiscal().equals(solicitacaoLiquidacao.getDocumento())) {
                SolicitacaoFinanceira s = solicitacaoLiquidacao.getSolicitacaoFinanceira();
                s.setNotaFiscal(s.getNotaFiscal().concat(",".concat(solicitacaoLiquidacao.getDocumento())));
                solicitacaoFinanceiraController.atualiza(s);
            }
        }
        solicitacaoLiquidacao.setUsuario(u);
        salvar(solicitacaoLiquidacao);
    }

    public void excluirProcesso(SolicitacaoLiquidacao sl) throws Exception {
        List<EncaminhamentoLiquidacao> encaminhamentoLiquidacaos = encaminhamentoLiquidacaoController.listar(sl);
        for (EncaminhamentoLiquidacao encaminhamentoLiquidacao : encaminhamentoLiquidacaos) {
            encaminhamentoLiquidacao = encaminhamentoLiquidacaoController.carregar(encaminhamentoLiquidacao.getId());
            encaminhamentoLiquidacaoController.excluir(encaminhamentoLiquidacao);
        }
        
        sl = dao.carregar(sl.getId());
        dao.excluir(sl);
    }

}
