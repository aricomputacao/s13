/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import br.com.sefin.enumerated.Vinculo;
import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.controller.SolicitacaoLiquidacaoController;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ProcessarLiquidacaoMb implements Serializable {

    @EJB
    private EmpenhoDetalheController empenhoDetalheControler;
    @EJB
    private SolicitacaoLiquidacaoController solicitacaoLiquidacaoController;
    @EJB
    private AreaAdministrativaController administrativaController;
    private List<EmpenhoDetalhe> empenhoDetalhes;
    private SolicitacaoLiquidacao solicitacaoLiquidacao;

    @PostConstruct
    private void iniciar() {
        empenhoDetalhes = new ArrayList<>();
        solicitacaoLiquidacao = new SolicitacaoLiquidacao();
    }

    public void importatEmpenhoDetalhe() {
        try {
            empenhoDetalhes = empenhoDetalheControler.listaSemPagamento();
            for (EmpenhoDetalhe em : empenhoDetalhes) {
                if ((!em.getSolicitacaoFinanceira().getVinculo().equals(Vinculo.Folha_de_Pagamento)) && (!em.getSolicitacaoFinanceira().getVinculo().equals(Vinculo.Obrigações))) {
                    SolicitacaoLiquidacao sl = new SolicitacaoLiquidacao();
                    sl.setAreaAdministrativa(administrativaController.administrativaTipo(TipoAreaAdm.Tesouraria));
                    sl.setDocumento(em.getSolicitacaoFinanceira().getNotaFiscal());
                    sl.setSolicitacaoFinanceira(em.getSolicitacaoFinanceira());
                    sl.setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao.Aprovado);
                    sl.setValor(em.getValor());
                    solicitacaoLiquidacaoController.salvar(sl);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProcessarLiquidacaoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
