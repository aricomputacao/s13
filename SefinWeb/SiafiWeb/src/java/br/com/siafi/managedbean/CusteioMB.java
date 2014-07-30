/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.ExercicioController;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.CategoriaDespesaController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.CategoriaDespesa;
import br.com.sefin.modelo.dto.CusteioDto;
import br.com.guardiao.modelo.Exercicio;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 * Arquivo do projeto SiafiWeb criando em 30/07/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class CusteioMB implements Serializable {

    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private SolicitacaoFinanceiraController controler;
    @EJB
    private ExercicioController exercicioControler;
    @EJB
    private CategoriaDespesaController categoriaDespesaControler;
    private CartesianChartModel graficoPrincipal;
    private CartesianChartModel graficoMensalUnidadeOrcamentaria;
    private PieChartModel graficoUnidadeOrcamentaria;
    private List<SolicitacaoFinanceira> solicitacoes;
    private List<CusteioDto> listCusteio;
    private List<Exercicio> listaExercicios;
    private List<CategoriaDespesa> listaCategoriaDespesa;
    private List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras;
    private CategoriaDespesa categoriaDespesaSelecionada;
    private Exercicio exercicioSelcionado;
    private CusteioDto custeioDtoSelecioando;
    private BigDecimal totalGeral;
    private BigDecimal vlJan;
    private BigDecimal vlFev;
    private BigDecimal vlMar;
    private BigDecimal vlAbr;
    private BigDecimal vlMai;
    private BigDecimal vlJun;
    private BigDecimal vlJul;
    private BigDecimal vlAgo;
    private BigDecimal vlSet;
    private BigDecimal vlOut;
    private BigDecimal vlNov;
    private BigDecimal vlDez;
   

    @PostConstruct
    private void init() {
        try {
            listaCategoriaDespesa = categoriaDespesaControler.listarTodos("nome");
            listaExercicios = exercicioControler.listarTodos("ano");
            exercicioSelcionado = listaExercicios.get(0);
            categoriaDespesaSelecionada = listaCategoriaDespesa.get(0);
            listaSolicitacaoFinanceiras = new ArrayList<>();
            graficoPrincipal = new CartesianChartModel();
            graficoMensalUnidadeOrcamentaria = new CartesianChartModel();
            graficoUnidadeOrcamentaria = new PieChartModel();
            inicializaGraficos();
           
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
        }
    }

    public void geraLista() {

        try {
            totalGeral = BigDecimal.ZERO;
            solicitacoes = controler.listarSolicitacoesCusteio(exercicioSelcionado, categoriaDespesaSelecionada, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias());
            listCusteio = controler.listarCusteioDtoPorUnidadeOrc(solicitacoes);
            for (CusteioDto dto : listCusteio) {
                totalGeral = totalGeral.add(dto.getTotal());
            }
            totalCompetencia(listCusteio);
            geraGraficoPrincipal(listCusteio);
            geraGraficoPizza(listCusteio);

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar relatório", ex, this.getClass().getName());
        }
    }

    public void listarCompetencia(int mes) {
        listaSolicitacaoFinanceiras.clear();
        for (SolicitacaoFinanceira solicitacaoFinanceira : custeioDtoSelecioando.getSolicitacaoFinanceiras()) {
            if (solicitacaoFinanceira.getMesCompetencia().ordinal() == mes) {
                listaSolicitacaoFinanceiras.add(solicitacaoFinanceira);
            }
        }
    }

    public void totalCompetencia(List<CusteioDto> dtos) {
        vlJan = BigDecimal.ZERO;
        vlFev = BigDecimal.ZERO;
        vlMar = BigDecimal.ZERO;
        vlAbr = BigDecimal.ZERO;
        vlMai = BigDecimal.ZERO;
        vlJun = BigDecimal.ZERO;
        vlJul = BigDecimal.ZERO;
        vlAgo = BigDecimal.ZERO;
        vlSet = BigDecimal.ZERO;
        vlOut = BigDecimal.ZERO;
        vlNov = BigDecimal.ZERO;
        vlDez = BigDecimal.ZERO;
        for (CusteioDto custeioDto : dtos) {
            vlJan = vlJan.add(custeioDto.getJaneiro());
            vlFev = vlFev.add(custeioDto.getFevereiro());
            vlMar = vlMar.add(custeioDto.getMarco());
            vlAbr = vlAbr.add(custeioDto.getAbril());
            vlMai = vlMai.add(custeioDto.getMaio());
            vlJun = vlJun.add(custeioDto.getJunho());
            vlJul = vlJul.add(custeioDto.getJulho());
            vlAgo = vlAgo.add(custeioDto.getAgosto());
            vlSet = vlSet.add(custeioDto.getSetembro());
            vlOut = vlOut.add(custeioDto.getOutubro());
            vlNov = vlNov.add(custeioDto.getNovembro());
            vlDez = vlDez.add(custeioDto.getDezembro());

        }

    }

    private void inicializaGraficos() {
        ChartSeries serie = new ChartSeries();
        serie.setLabel("");
        serie.set("0", 0);
        graficoPrincipal.addSeries(serie);

        graficoUnidadeOrcamentaria.set("", 0);
        graficoMensalUnidadeOrcamentaria.addSeries(serie);
    }

    private void geraGraficoPrincipal(List<CusteioDto> custeios) {
        graficoPrincipal.clear();
        for (CusteioDto c : custeios) {
            ChartSeries serie = new ChartSeries();
            serie.setLabel(c.getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getIdOrgao() + c.getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getId() + " - " + c.getUnidadeOrcamentaria().getAbreviacao());
            serie.set(exercicioSelcionado.getAno().toString(), c.getTotal());
            graficoPrincipal.addSeries(serie);
        }
    }

    private void geraGraficoPizza(List<CusteioDto> custeioDtos) {
        graficoUnidadeOrcamentaria.clear();
        for (CusteioDto c : custeioDtos) {
            graficoUnidadeOrcamentaria.set(c.getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getIdOrgao() + c.getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getId() + " - " + c.getUnidadeOrcamentaria().getAbreviacao(), c.getTotal());
        }
    }

    private void geraGraficoMesUnidadeOrcamentaria(Integer indice) {
        graficoMensalUnidadeOrcamentaria.clear();
        custeioDtoSelecioando = listCusteio.get(indice);
        String und;
        und = custeioDtoSelecioando.getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getIdOrgao() + custeioDtoSelecioando.getUnidadeOrcamentaria().getUnidadeOrcamentariaPK().getId() + " - " + custeioDtoSelecioando.getUnidadeOrcamentaria().getAbreviacao();

        ChartSeries serieJan = new ChartSeries();
        serieJan.setLabel("Janeiro");
        serieJan.set(und, custeioDtoSelecioando.getJaneiro());
        graficoMensalUnidadeOrcamentaria.addSeries(serieJan);
        ChartSeries serieFev = new ChartSeries();
        serieFev.setLabel("Fevereiro");
        serieFev.set(und, custeioDtoSelecioando.getFevereiro());
        graficoMensalUnidadeOrcamentaria.addSeries(serieFev);
        ChartSeries serieMar = new ChartSeries();
        serieMar.setLabel("Março");
        serieMar.set(und, custeioDtoSelecioando.getMarco());
        graficoMensalUnidadeOrcamentaria.addSeries(serieMar);
        ChartSeries serieAbr = new ChartSeries();
        serieAbr.setLabel("Abril");
        serieAbr.set(und, custeioDtoSelecioando.getAbril());
        graficoMensalUnidadeOrcamentaria.addSeries(serieAbr);
        ChartSeries serieMai = new ChartSeries();
        serieMai.setLabel("Maio");
        serieMai.set(und, custeioDtoSelecioando.getMaio());
        graficoMensalUnidadeOrcamentaria.addSeries(serieMai);
        ChartSeries serieJun = new ChartSeries();
        serieJun.setLabel("Junho");
        serieJun.set(und, custeioDtoSelecioando.getJunho());
        graficoMensalUnidadeOrcamentaria.addSeries(serieJun);
        ChartSeries serieJul = new ChartSeries();
        serieJul.setLabel("Julho");
        serieJul.set(und, custeioDtoSelecioando.getJulho());
        graficoMensalUnidadeOrcamentaria.addSeries(serieJul);

        ChartSeries serieAgo = new ChartSeries();
        serieAgo.setLabel("Agostro");
        serieAgo.set(und, custeioDtoSelecioando.getAgosto());
        graficoMensalUnidadeOrcamentaria.addSeries(serieAgo);
        ChartSeries serieSet = new ChartSeries();
        serieSet.setLabel("Setembro");
        serieSet.set(und, custeioDtoSelecioando.getSetembro());
        graficoMensalUnidadeOrcamentaria.addSeries(serieSet);
        ChartSeries serieOut = new ChartSeries();
        serieOut.setLabel("Outubro");
        serieOut.set(und, custeioDtoSelecioando.getOutubro());
        graficoMensalUnidadeOrcamentaria.addSeries(serieOut);
        ChartSeries serieNov = new ChartSeries();
        serieNov.setLabel("Novembro");
        serieNov.set(und, custeioDtoSelecioando.getNovembro());
        graficoMensalUnidadeOrcamentaria.addSeries(serieNov);
        ChartSeries serieDez = new ChartSeries();
        serieDez.setLabel("Dezembro");
        serieDez.set(und, custeioDtoSelecioando.getDezembro());
        graficoMensalUnidadeOrcamentaria.addSeries(serieDez);

    }

    public void selecionaUnidadeOrcamentaria(ItemSelectEvent event) {
        geraGraficoMesUnidadeOrcamentaria(event.getItemIndex());
    }

    public void impressao() {
        try {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listCusteio, m, "WEB-INF/relatorios/relatorio_custeio.jasper", "Relatório de Custeio");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        }
    }

   

    public List<CusteioDto> getListCusteio() {
        return listCusteio;
    }

    public void setListCusteio(List<CusteioDto> listCusteio) {
        this.listCusteio = listCusteio;
    }

    public List<Exercicio> getListaExercicios() {
        return listaExercicios;
    }

    public void setListaExercicios(List<Exercicio> listaExercicios) {
        this.listaExercicios = listaExercicios;
    }

    public List<CategoriaDespesa> getListaCategoriaDespesa() {
        return listaCategoriaDespesa;
    }

    public void setListaCategoriaDespesa(List<CategoriaDespesa> listaCategoriaDespesa) {
        this.listaCategoriaDespesa = listaCategoriaDespesa;
    }

    public CategoriaDespesa getCategoriaDespesaSelecionada() {
        return categoriaDespesaSelecionada;
    }

    public void setCategoriaDespesaSelecionada(CategoriaDespesa categoriaDespesaSelecionada) {
        this.categoriaDespesaSelecionada = categoriaDespesaSelecionada;
    }

    public Exercicio getExercicioSelcionado() {
        return exercicioSelcionado;
    }

    public void setExercicioSelcionado(Exercicio exercicioSelcionado) {
        this.exercicioSelcionado = exercicioSelcionado;
    }

    public List<SolicitacaoFinanceira> getListaSolicitacaoFinanceiras() {
        return listaSolicitacaoFinanceiras;
    }

    public void setListaSolicitacaoFinanceiras(List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras) {
        this.listaSolicitacaoFinanceiras = listaSolicitacaoFinanceiras;
    }

    public CusteioDto getCusteioDtoSelecioando() {
        return custeioDtoSelecioando;
    }

    public void setCusteioDtoSelecioando(CusteioDto custeioDtoSelecioando) {
        this.custeioDtoSelecioando = custeioDtoSelecioando;
    }

    public BigDecimal getTotalGeral() {
        return totalGeral;
    }

    public void setTotalGeral(BigDecimal totalGeral) {
        this.totalGeral = totalGeral;
    }

    public BigDecimal getVlJan() {
        return vlJan;
    }

    public void setVlJan(BigDecimal vlJan) {
        this.vlJan = vlJan;
    }

    public BigDecimal getVlFev() {
        return vlFev;
    }

    public void setVlFev(BigDecimal vlFev) {
        this.vlFev = vlFev;
    }

    public BigDecimal getVlMar() {
        return vlMar;
    }

    public void setVlMar(BigDecimal vlMar) {
        this.vlMar = vlMar;
    }

    public BigDecimal getVlAbr() {
        return vlAbr;
    }

    public void setVlAbr(BigDecimal vlAbr) {
        this.vlAbr = vlAbr;
    }

    public BigDecimal getVlMai() {
        return vlMai;
    }

    public void setVlMai(BigDecimal vlMai) {
        this.vlMai = vlMai;
    }

    public BigDecimal getVlJun() {
        return vlJun;
    }

    public void setVlJun(BigDecimal vlJun) {
        this.vlJun = vlJun;
    }

    public BigDecimal getVlJul() {
        return vlJul;
    }

    public void setVlJul(BigDecimal vlJul) {
        this.vlJul = vlJul;
    }

    public BigDecimal getVlAgo() {
        return vlAgo;
    }

    public void setVlAgo(BigDecimal vlAgo) {
        this.vlAgo = vlAgo;
    }

    public BigDecimal getVlSet() {
        return vlSet;
    }

    public void setVlSet(BigDecimal vlSet) {
        this.vlSet = vlSet;
    }

    public BigDecimal getVlOut() {
        return vlOut;
    }

    public void setVlOut(BigDecimal vlOut) {
        this.vlOut = vlOut;
    }

    public BigDecimal getVlNov() {
        return vlNov;
    }

    public void setVlNov(BigDecimal vlNov) {
        this.vlNov = vlNov;
    }

    public BigDecimal getVlDez() {
        return vlDez;
    }

    public void setVlDez(BigDecimal vlDez) {
        this.vlDez = vlDez;
    }

    public CategoriaDespesaController getCategoriaDespesaControler() {
        return categoriaDespesaControler;
    }

    public void setCategoriaDespesaControler(CategoriaDespesaController categoriaDespesaControler) {
        this.categoriaDespesaControler = categoriaDespesaControler;
    }

    public CartesianChartModel getGraficoPrincipal() {
        return graficoPrincipal;
    }

    public void setGraficoPrincipal(CartesianChartModel graficoPrincipal) {
        this.graficoPrincipal = graficoPrincipal;
    }

    public CartesianChartModel getGraficoMensalUnidadeOrcamentaria() {
        return graficoMensalUnidadeOrcamentaria;
    }

    public void setGraficoMensalUnidadeOrcamentaria(CartesianChartModel graficoMensalUnidadeOrcamentaria) {
        this.graficoMensalUnidadeOrcamentaria = graficoMensalUnidadeOrcamentaria;
    }

    public PieChartModel getGraficoUnidadeOrcamentaria() {
        return graficoUnidadeOrcamentaria;
    }

    public void setGraficoUnidadeOrcamentaria(PieChartModel graficoUnidadeOrcamentaria) {
        this.graficoUnidadeOrcamentaria = graficoUnidadeOrcamentaria;
    }

    
    
    
}
