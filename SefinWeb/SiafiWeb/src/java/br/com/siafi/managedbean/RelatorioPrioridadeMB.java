/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.FonteRecursoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.FonteRecurso;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;

/**
 * @Sistema SiafiWeb
 * @Data 31/07/2013
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class RelatorioPrioridadeMB implements Serializable {

    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private FonteRecursoController fonteRecursoControler;
    private List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras;
    private FonteRecurso fonteRecurso;
    private List<FonteRecurso> listaFonteRecurso;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Date dataInicio;
    private Date dataFinal;

    @PostConstruct
    private void init() {
        try {
            listaSolicitacaoFinanceiras = new ArrayList<>();
            listaFonteRecurso = fonteRecursoControler.listarTodos("nome");
            fonteRecurso = listaFonteRecurso.get(0);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
        }
    }

    public List<SolicitacaoFinanceira> getListaSolicitacaoFinanceiras() {
        return listaSolicitacaoFinanceiras;
    }

    public void setListaSolicitacaoFinanceiras(List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras) {
        this.listaSolicitacaoFinanceiras = listaSolicitacaoFinanceiras;
    }

    public FonteRecurso getFonteRecurso() {
        return fonteRecurso;
    }

    public void setFonteRecurso(FonteRecurso fonteRecurso) {
        this.fonteRecurso = fonteRecurso;
    }

    public List<FonteRecurso> getListaFonteRecurso() {
        return listaFonteRecurso;
    }

    public void setListaFonteRecurso(List<FonteRecurso> listaFonteRecurso) {
        this.listaFonteRecurso = listaFonteRecurso;
    }

    public void listar() {
        try {
            listaSolicitacaoFinanceiras = solicitacaoFinanceiraControler.listarPorFonteRecurso(unidadeOrcamentaria, fonteRecurso, dataInicio, dataFinal, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias());
            if (listaSolicitacaoFinanceiras.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum resultado encontrado");
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", e, this.getClass().getName());
        }
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
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

    public void impressao() {
        try {
            if (listaSolicitacaoFinanceiras.isEmpty()) {
                MenssagemUtil.addMessageInfo("Não hé nenhum registro para impressão");
            } else {
                String titulo = "Relatório de Solicitações Financeiras - Fonte de Recusro";
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaSolicitacaoFinanceiras, m, "WEB-INF/relatorios/relatorio_solicitacao.jasper", titulo);
                RelatorioSession.setBytesRelatorioInSession(rel);
            }
        } catch (JRException e) {
            MenssagemUtil.addMessageErro("Erro ao gerar relatório", e, this.getClass().getName());
        }
    }
}
