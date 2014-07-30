/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.EncaminhamentoLiquidacaoController;
import br.com.siafi.controller.SolicitacaoLiquidacaoController;
import br.com.siafi.modelo.Credor;
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
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class SolicitacaiLiquidacaoMB extends BeanGenerico<SolicitacaoLiquidacao> implements Serializable {

    @Inject
    private BeanNavegacaoMB beanNavegacao;
    @EJB
    private UsuarioMB usuarioMB;
    @EJB
    private SolicitacaoLiquidacaoController controller;
    @EJB
    private EncaminhamentoLiquidacaoController encaminhamentoLiquidacaoController;
    private List<SolicitacaoLiquidacao> listaSolicitacaoLiquidacaos;
    private SolicitacaoLiquidacao solicitacaoLiquidacao;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private List<UnidadeOrcamentaria> listaUnidadeOrcamentarias;
    private Credor credor;
    private String idUnidade;

    public SolicitacaiLiquidacaoMB() {
        super(SolicitacaoLiquidacao.class);
    }

    @PostConstruct
    public void iniciar() {
        solicitacaoLiquidacao = (SolicitacaoLiquidacao) beanNavegacao.getRegistroMapa("solicitacao_liquidacao", new SolicitacaoLiquidacao());
        listaSolicitacaoLiquidacaos = new ArrayList<>();
        listaUnidadeOrcamentarias = usuarioMB.getUsuarioSelecionado().getUnidadeOrcamentarias();
        credor = new Credor();

    }

    public void salvar() {
        try {
            controller.addProcessoLiquidacao(solicitacaoLiquidacao, usuarioMB.getUsuarioSelecionado());
            iniciar();
            MenssagemUtil.addMessageInfo(getMsg("cadastro"));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Falha ao inserir registro!", ex, this.getClass().getName());

        }
    }

    public void atualizarLiquidacao() {
        try {

            controller.atualizar(solicitacaoLiquidacao);
            MenssagemUtil.addMessageInfo(getMsg("atualizar"));
            solicitacaoLiquidacao = new SolicitacaoLiquidacao();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Falha ao editar", ex, this.getClass().getName());

        }
    }

    public void excluir(SolicitacaoLiquidacao i) {
        try {

            controller.excluirProcesso(i);
            listaSolicitacaoLiquidacaos.remove(i);
            MenssagemUtil.addMessageInfo(getMsg("exclusao"));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro_exclusao"));
        }
    }

    public void impressaoEncaminhamentos() {
        try {
            List<EncaminhamentoLiquidacao> els = new ArrayList<>();
            for (SolicitacaoLiquidacao so : listaSolicitacaoLiquidacaos) {
                EncaminhamentoLiquidacao e = encaminhamentoLiquidacaoController.ultimoEncaminhamento(so);
                els.add(e);
            }

            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(els, m, "WEB-INF/relatorios/rel_encaminhamento_liquidacao.jasper", "Relatório de Encaminhamentos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaiLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void impressaoEncaminhamento(SolicitacaoLiquidacao l) {
        try {
            EncaminhamentoLiquidacao e = encaminhamentoLiquidacaoController.ultimoEncaminhamento(l);
            List<EncaminhamentoLiquidacao> els = new ArrayList<>();
            els.add(e);
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(els, m, "WEB-INF/relatorios/rel_encaminhamento_liquidacao.jasper", "Relatório de Encaminhamentos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaiLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void impressao() {
        try {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaSolicitacaoLiquidacaos, m, "WEB-INF/relatorios/rel_solicitacao_liquidacao.jasper", "Relatório de Liquidações");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaiLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void impressaoEncaminhamentos(List<EncaminhamentoLiquidacao> l) {
        try {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(l, m, "WEB-INF/relatorios/relatorio_encaminhamentos.jasper", "Relatório de Encaminhamentos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        }

    }

    public void listarPorSolicitacao() {
        listaSolicitacaoLiquidacaos = controller.listarPorSolicitacao(solicitacaoLiquidacao.getSolicitacaoFinanceira());
    }

    public void listarPorNumeroSolicitacao() {
        listaSolicitacaoLiquidacaos = controller.listarPorSolicitacao(getCampoBusca(), listaUnidadeOrcamentarias, unidadeOrcamentaria, credor);
        credor = new Credor();
    }

    /**
     * Metodo para gerar lista apenas com os processos que estão na tesouraria
     */
    public void listarPorNumeroSolicitacaoTesouraria() {
        listaSolicitacaoLiquidacaos = controller.listarPorSolicitacaoTesouraria(getCampoBusca(), listaUnidadeOrcamentarias, unidadeOrcamentaria, credor);
        credor = new Credor();
    }

    public List<SolicitacaoLiquidacao> getListaSolicitacaoLiquidacaos() {
        return listaSolicitacaoLiquidacaos;
    }

    public void setListaSolicitacaoLiquidacaos(List<SolicitacaoLiquidacao> listaSolicitacaoLiquidacaos) {
        this.listaSolicitacaoLiquidacaos = listaSolicitacaoLiquidacaos;
    }

    public SolicitacaoLiquidacao getSolicitacaoLiquidacao() {
        return solicitacaoLiquidacao;
    }

    public void setSolicitacaoLiquidacao(SolicitacaoLiquidacao solicitacaoLiquidacao) {
        this.solicitacaoLiquidacao = solicitacaoLiquidacao;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public void selecionaSecretaria() {
        if (idUnidade.length() == 4) {
            for (UnidadeOrcamentaria u : usuarioMB.getUsuarioSelecionado().getUnidadeOrcamentarias()) {
                if (idUnidade.equals(u.getUnidadeOrcamentariaPK().toString())) {
                    unidadeOrcamentaria = u;
                    break;
                } else {
                    unidadeOrcamentaria = null;
                }
            }
            if (unidadeOrcamentaria == null) {
                idUnidade = "";
                MenssagemUtil.addMessageInfo("Nenhuma secretária encontrada");
            }
        } else {
            unidadeOrcamentaria = null;
        }
    }

    public void alteraUnidadeOrcamentaria() {
        if (unidadeOrcamentaria != null) {
            idUnidade = unidadeOrcamentaria.getUnidadeOrcamentariaPK().toString();
        }
    }

    public String getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
    }

}
