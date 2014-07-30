/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.controller.ConvenioController;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.Convenio;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class RelatorioSolicitacaoConvenioMB implements Serializable {

    @EJB
    private CredorController credorControler;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private ConvenioController convenioController;
    @EJB
    private UsuarioMB usuarioMb;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private List<Convenio> listaConvenios;
    private Convenio convenio;
    private Credor credor;
    private Date dataInicio;
    private Date dataFinal;
    private String numeroConvenio;
    private List<SituacaoSolicitacao> situacoesSelecionadas;
    private List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras;
    private List<SelectItem> situacoesDisponiveis;

    public RelatorioSolicitacaoConvenioMB() {
    }

    @PostConstruct
    private void init() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        dataInicio = c.getTime();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        dataFinal = c.getTime();
        listaConvenios = new ArrayList<>();
        listaSolicitacaoFinanceiras = new ArrayList<>();
        situacoesDisponiveis = new ArrayList<>();
        for (SituacaoSolicitacao s : SituacaoSolicitacao.values()) {
            situacoesDisponiveis.add(new SelectItem(s, s.name()));
        }
        situacoesSelecionadas = new ArrayList<>();
    }

    public void impressao() {
        if (!listaSolicitacaoFinanceiras.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaSolicitacaoFinanceiras, m, "WEB-INF/relatorios/relatorio_solicitacao_convenio.jasper", "Relatório de Solicitações Financeiras por Convênio");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public void listar() {
        for (SituacaoSolicitacao s : situacoesSelecionadas) {
            System.out.println(s);
        }
        if (unidadeOrcamentaria != null) {
            try {
                listaSolicitacaoFinanceiras = solicitacaoFinanceiraControler.listarConvenio(unidadeOrcamentaria, convenio, credor, dataInicio, dataFinal);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
            }
        } else {
            try {
                listaSolicitacaoFinanceiras = solicitacaoFinanceiraControler.listarConvenio(usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), convenio, credor, dataInicio, dataFinal);
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
            }
        }

        if (listaSolicitacaoFinanceiras.isEmpty()) {
            MenssagemUtil.addMessageInfo("Nenhuma solicitação encontrada.");
        }
    }

    public void listaConvenio() {
        try {
            listaConvenios = convenioController.listarTodos("numero", "numero", numeroConvenio);
            if (listaConvenios.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum convenio encontrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
            Logger.getLogger(RelatorioSolicitacaoConvenioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<Convenio> getListaConvenios() {
        return listaConvenios;
    }

    public void setListaConvenios(List<Convenio> listaConvenios) {
        this.listaConvenios = listaConvenios;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public List<SolicitacaoFinanceira> getListaSolicitacaoFinanceiras() {
        return listaSolicitacaoFinanceiras;
    }

    public void setListaSolicitacaoFinanceiras(List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras) {
        this.listaSolicitacaoFinanceiras = listaSolicitacaoFinanceiras;
    }

    public String getNumeroConvenio() {
        return numeroConvenio;
    }

    public void setNumeroConvenio(String numeroConvenio) {
        this.numeroConvenio = numeroConvenio;
    }

    public List<SelectItem> getSituacoesDisponiveis() {
        return situacoesDisponiveis;
    }

    public List<SituacaoSolicitacao> getSituacoesSelecionadas() {
        return situacoesSelecionadas;
    }

    public void setSituacoesSelecionadas(List<SituacaoSolicitacao> situacoesSelecionadas) {
        this.situacoesSelecionadas = situacoesSelecionadas;
    }
}
