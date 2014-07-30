/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbeanaud;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.auditoria.modelo.SolicitacaoFinanceiraAud;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.SolicitacaoFinanceiraAudController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.managedbean.UsuarioMB;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class SolicitacaoFinanceiraAudMB extends BeanGenerico<SolicitacaoFinanceiraAud> implements Serializable {

    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private SolicitacaoFinanceiraAudController audController;
    @EJB
    private CredorController credorControler;
    private List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras;
    private SolicitacaoFinanceira solicitacaoFinanceira;
    private SolicitacaoFinanceiraAud solicitacaoFinanceiraAud;
    private List<SolicitacaoFinanceiraAud> listaSolicitacaoFinanceiraAud;
    private List<SituacaoSolicitacao> listaSituacaoSolicitacaos;

    private List<Credor> credores;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private SituacaoSolicitacao situacaoSolicitacao;
    private Credor credor;
    private Date dataInicial;
    private Date dataFinal;
    private String numero;
    private int tabAtiva;
    private boolean filtroData;
    private boolean filtroSituacao;
    private boolean filtroNumero;
    private String nomeCredor;
    private String empenho;
    private Mes competenciaConsulta;
    private Integer anoConsulta;

    public SolicitacaoFinanceiraAudMB() {
        super(SolicitacaoFinanceiraAud.class);
    }

    @PostConstruct
    public void init() {
        listaSolicitacaoFinanceiras = new ArrayList<>();
        credor = new Credor();
        solicitacaoFinanceira = new SolicitacaoFinanceira();
        listaSituacaoSolicitacaos = new ArrayList<>();
        anoConsulta = 2014;
        numero = "";
        empenho = "";
        //iniciaData();
        setTabAtiva(0);
    }

    private void iniciaData() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        dataInicial = c.getTime();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        dataFinal = c.getTime();
    }

    public void listarSolicitacoes() {
        setTabAtiva(0);
        try {
            listaSolicitacaoFinanceiras = solicitacaoFinanceiraControler.listarFiltros(dataInicial, dataFinal, numero, listaSituacaoSolicitacaos, unidadeOrcamentaria, getCampoOrdem(), credor, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), empenho, anoConsulta, competenciaConsulta);

            if (listaSolicitacaoFinanceiras.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma Solicitaçãoencontrada");
            }
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaoFinanceiraAudMB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            dataFinal = null;
            dataInicial = null;
            numero = null;
            empenho = null;
            situacaoSolicitacao = null;
            credor = new Credor();
            nomeCredor = "";
        }

    }

    public void listarAlteracoes() {
        if (solicitacaoFinanceira != null) {
            try {
                listaSolicitacaoFinanceiraAud = audController.listarAuditoria("id", solicitacaoFinanceira.getId());
                if (listaSolicitacaoFinanceiraAud.isEmpty()) {
                    MenssagemUtil.addMessageInfo("Nenhum resultado encontrado");
                }
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
            }
        }
    }

    public void listarCredores() {
        try {
            credores = credorControler.listarOutros("pessoa.nome", nomeCredor);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public List<SolicitacaoFinanceira> getListaSolicitacaoFinanceiras() {
        return listaSolicitacaoFinanceiras;
    }

    public void setListaSolicitacaoFinanceiras(List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras) {
        this.listaSolicitacaoFinanceiras = listaSolicitacaoFinanceiras;
    }

    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
        setTabAtiva(1);
    }

    public SolicitacaoFinanceiraAud getSolicitacaoFinanceiraAud() {
        return solicitacaoFinanceiraAud;
    }

    public void setSolicitacaoFinanceiraAud(SolicitacaoFinanceiraAud solicitacaoFinanceiraAud) {
        this.solicitacaoFinanceiraAud = solicitacaoFinanceiraAud;
    }

    public List<SolicitacaoFinanceiraAud> getListaSolicitacaoFinanceiraAud() {
        return listaSolicitacaoFinanceiraAud;
    }

    public void setListaSolicitacaoFinanceiraAud(List<SolicitacaoFinanceiraAud> listaSolicitacaoFinanceiraAud) {
        this.listaSolicitacaoFinanceiraAud = listaSolicitacaoFinanceiraAud;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getTabAtiva() {
        return tabAtiva;
    }

    public void setTabAtiva(int tabAtiva) {
        this.tabAtiva = tabAtiva;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public SituacaoSolicitacao[] getSituacoesSolicitacao() {
        return SituacaoSolicitacao.values();
    }

    public List<Credor> getCredores() {
        return credores;
    }

    public void setCredores(List<Credor> credores) {
        this.credores = credores;
    }

    public SituacaoSolicitacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public boolean isFiltroData() {
        return filtroData;
    }

    public void setFiltroData(boolean filtroData) {
        this.filtroData = filtroData;
    }

    public boolean isFiltroSituacao() {
        return filtroSituacao;
    }

    public void setFiltroSituacao(boolean filtroSituacao) {
        this.filtroSituacao = filtroSituacao;
    }

    public boolean isFiltroNumero() {
        return filtroNumero;
    }

    public void setFiltroNumero(boolean filtroNumero) {
        this.filtroNumero = filtroNumero;
    }

    public String getNomeCredor() {
        return nomeCredor;
    }

    public void setNomeCredor(String nomeCredor) {
        this.nomeCredor = nomeCredor.toUpperCase();
    }

    public List<Mes> getListaMeses() {
        return Arrays.asList(Mes.values());
    }

    public List<SituacaoSolicitacao> getListaSituacaoSolicitacaos() {
        return listaSituacaoSolicitacaos;
    }

    public void setListaSituacaoSolicitacaos(List<SituacaoSolicitacao> listaSituacaoSolicitacaos) {
        this.listaSituacaoSolicitacaos = listaSituacaoSolicitacaos;
    }

    public String getEmpenho() {
        return empenho;
    }

    public void setEmpenho(String empenho) {
        this.empenho = empenho;
    }

    public Mes getCompetenciaConsulta() {
        return competenciaConsulta;
    }

    public void setCompetenciaConsulta(Mes competenciaConsulta) {
        this.competenciaConsulta = competenciaConsulta;
    }

    public Integer getAnoConsulta() {
        return anoConsulta;
    }

    public void setAnoConsulta(Integer anoConsulta) {
        this.anoConsulta = anoConsulta;
    }

}
