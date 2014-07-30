package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.sefin.modelo.dto.SolicitacaoSaldoDto;
import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.Encaminhamento;
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
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class RelatorioProcessoTesourariaMB implements Serializable {

    @Inject
    private UsuarioMB usuarioMb;
    @EJB
    private SolicitacaoFinanceiraController financeiraControler;
    @EJB
    private EmpenhoDetalheController detalheControler;
    @EJB
    private EncaminhamentoController encaminhamentoControler;

    private List<SolicitacaoFinanceira> solicitacaoFinanceiras;
    private List<EmpenhoDetalhe> empenhoDetalhes;
    private List<SolicitacaoSaldoDto> solicitacaoSaldoDtos;
    private SolicitacaoFinanceira solicitacaoFinanceira;
    private Encaminhamento encaminhamento;
    private Credor credor;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private SituacaoSolicitacao situacaoSolicitacao;
    private Date dataInicio;
    private Date dataFinal;
    private Date dataPagamentoInicio;
    private Date dataPagamentoFinal;

    @PostConstruct
    private void iniciar() {
        solicitacaoFinanceira = new SolicitacaoFinanceira();
        solicitacaoFinanceiras = new ArrayList<>();
        encaminhamento = new Encaminhamento();
        unidadeOrcamentaria = new UnidadeOrcamentaria();
        solicitacaoSaldoDtos = new ArrayList<>();
    }

    public void listarFiltrosTesouraria() {
        try {
            solicitacaoFinanceiras = financeiraControler.listarFiltrosTesouraria(unidadeOrcamentaria, credor, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), situacaoSolicitacao, dataInicio, dataFinal);
            if (solicitacaoFinanceiras.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma Solicitação encontrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        } finally {
            credor = null;
        }
    }

    public void processarRelatorioTesouraria(List<SolicitacaoFinanceira> financeiras) {
        try {
            solicitacaoSaldoDtos = new ArrayList<>();
            for (SolicitacaoFinanceira sol : financeiras) {
                SolicitacaoSaldoDto dto = new SolicitacaoSaldoDto();
                empenhoDetalhes = detalheControler.listarPorSolicitacao(sol);
                encaminhamento = encaminhamentoControler.buscaUltimoEncaminhamentoTesouraria(sol);
                dto.setEmpenhoDetalhes(empenhoDetalhes);
                dto.setEncaminhamento(encaminhamento);
                dto.setSolicitacaoFinanceira(sol);
                solicitacaoSaldoDtos.add(dto);
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar processaemnto de solicitações", ex, this.getClass().getName());
        }
    }

    public void impressaoRelatorioTesouraria() {

        processarRelatorioTesouraria(solicitacaoFinanceiras);
        if (!solicitacaoSaldoDtos.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(solicitacaoSaldoDtos, m, "WEB-INF/relatorios/relatorio_solicitacao_tesouraria.jasper", "Resumo de Pagamentos");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public EmpenhoDetalheController getDetalheControler() {
        return detalheControler;
    }

    public void setDetalheControler(EmpenhoDetalheController detalheControler) {
        this.detalheControler = detalheControler;
    }

    public List<SolicitacaoFinanceira> getSolicitacaoFinanceiras() {
        return solicitacaoFinanceiras;
    }

    public void setSolicitacaoFinanceiras(List<SolicitacaoFinanceira> solicitacaoFinanceiras) {
        this.solicitacaoFinanceiras = solicitacaoFinanceiras;
    }

    public List<EmpenhoDetalhe> getEmpenhoDetalhes() {
        return empenhoDetalhes;
    }

    public void setEmpenhoDetalhes(List<EmpenhoDetalhe> empenhoDetalhes) {
        this.empenhoDetalhes = empenhoDetalhes;
    }

    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
    }

    public Encaminhamento getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(Encaminhamento encaminhamento) {
        this.encaminhamento = encaminhamento;
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

    public List<SituacaoSolicitacao> getListaSituacaoSol() {
        List<SituacaoSolicitacao> sits = new ArrayList<>();
        sits.add(SituacaoSolicitacao.Liquidado);
        sits.add(SituacaoSolicitacao.DocumentaçãoLiberada);
        sits.add(SituacaoSolicitacao.Pago);
        return sits;
    }

    public SituacaoSolicitacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
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

    public Date getDataPagamentoInicio() {
        return dataPagamentoInicio;
    }

    public void setDataPagamentoInicio(Date dataPagamentoInicio) {
        this.dataPagamentoInicio = dataPagamentoInicio;
    }

    public Date getDataPagamentoFinal() {
        return dataPagamentoFinal;
    }

    public void setDataPagamentoFinal(Date dataPagamentoFinal) {
        this.dataPagamentoFinal = dataPagamentoFinal;
    }

}
