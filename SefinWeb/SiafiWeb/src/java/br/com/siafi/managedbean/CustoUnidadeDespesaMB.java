/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.Mes;
import br.com.siafi.controller.CentroCustoController;
import br.com.guardiao.controler.ExercicioController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.CentroCustoDto;
import br.com.sefin.modelo.dto.CusteioDto;
import br.com.guardiao.modelo.Exercicio;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class CustoUnidadeDespesaMB implements Serializable {

    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private CentroCustoController centroCustoControler;
    @EJB
    private ExercicioController exercicioControler;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private CusteioDto custeioDto;
    private List<Exercicio> exercicioList;
    private Exercicio exercicio;
    private List<CentroCustoDto> centroCustoDtoList;
    private List<CentroCustoDto> centroCustoDtoLista;
    private Mes mes;
    private PieChartModel graficoDespesaMensal;
    private CartesianChartModel graficoDescritivoCusteioMensal;
    private BigDecimal totalDetalhe;

    @PostConstruct
    public void init() {
        try {
            //
            centroCustoDtoList = new ArrayList<>();
            centroCustoDtoLista = new ArrayList<>();
            custeioDto = new CusteioDto();
            exercicioList = exercicioControler.listarTodos("ano");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
        }
    }

    // Limpar dados existentes
    private void limpaConsulta() {
        custeioDto = new CusteioDto();
        centroCustoDtoList.clear();
        centroCustoDtoLista.clear();
        graficoDespesaMensal = null;
        graficoDescritivoCusteioMensal = null;

    }

    public void montaCusteio() {
        try {
            limpaConsulta();
            List<SolicitacaoFinanceira> solicitacaoFinanceiras = solicitacaoFinanceiraControler.listarSolicitacoesCusteio(exercicio, unidadeOrcamentaria);
            if (solicitacaoFinanceiras.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nã há Informações sobre esse período.");
            } else {
                custeioDto = solicitacaoFinanceiraControler.listarCusteioDtoPorUnidadeOrc(solicitacaoFinanceiras).get(0);
                geraGraficoPizza();
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao montar custeio", ex, this.getClass().getName());
        }
    }

    public List<Exercicio> getExercicioList() {
        return exercicioList;
    }

    public void setExercicioList(List<Exercicio> exercicioList) {
        this.exercicioList = exercicioList;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public CusteioDto getCusteioDto() {
        return custeioDto;
    }

    public void setCusteioDto(CusteioDto custeioDto) {
        this.custeioDto = custeioDto;
    }

    public void selecionaMesTabela(Integer m) {
        try {
            mes = Mes.values()[m];
            centroCustoDtoLista = centroCustoControler.listarUnidadeOrcamentariaMesDespesa(exercicio, unidadeOrcamentaria, mes);
//        geraGraficoDetalhe(centroCustoDtoList);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao selecionar tabela", ex, this.getClass().getName());
        }
    }

    public void selecionaMes(Integer m) {
        try {
            mes = Mes.values()[m];
            centroCustoDtoList = centroCustoControler.listarUnidadeOrcamentariaMesDespesa(exercicio, unidadeOrcamentaria, mes);
            geraGraficoDetalhe(centroCustoDtoList);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar mes", ex, this.getClass().getName());
        }
    }

    private void geraGraficoDetalhe(List<CentroCustoDto> centroCustoList) {
        graficoDescritivoCusteioMensal = new CartesianChartModel();
        totalDetalhe = BigDecimal.ZERO;
        for (CentroCustoDto centroCustoDto : centroCustoList) {
            ChartSeries serie = new ChartSeries(centroCustoDto.getCentroCusto().getNome());
            serie.set(mes.toString(), centroCustoDto.getTotal());
            graficoDescritivoCusteioMensal.addSeries(serie);
            totalDetalhe = totalDetalhe.add(centroCustoDto.getTotal());
        }
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<CentroCustoDto> getCentroCustoDtoList() {
        return centroCustoDtoList;
    }

    public void setCentroCustoDtoList(List<CentroCustoDto> centroCustoDtoList) {
        this.centroCustoDtoList = centroCustoDtoList;
    }

    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    private void geraGraficoPizza() {
        graficoDespesaMensal = new PieChartModel();
        graficoDespesaMensal.set("Janeiro", custeioDto.getJaneiro());
        graficoDespesaMensal.set("Favereiro", custeioDto.getFevereiro());
        graficoDespesaMensal.set("Março", custeioDto.getMarco());
        graficoDespesaMensal.set("Abril", custeioDto.getAbril());
        graficoDespesaMensal.set("Maio", custeioDto.getMaio());
        graficoDespesaMensal.set("Junho", custeioDto.getJunho());
        graficoDespesaMensal.set("Julho", custeioDto.getJulho());
        graficoDespesaMensal.set("Agosto", custeioDto.getAgosto());
        graficoDespesaMensal.set("Setembro", custeioDto.getSetembro());
        graficoDespesaMensal.set("Outubro", custeioDto.getOutubro());
        graficoDespesaMensal.set("Novembro", custeioDto.getNovembro());
        graficoDespesaMensal.set("Dezembro", custeioDto.getDezembro());
    }

    public PieChartModel getGraficoDespesaMensal() {
        return graficoDespesaMensal;
    }

    public void setGraficoDespesaMensal(PieChartModel graficoDespesaMensal) {
        this.graficoDespesaMensal = graficoDespesaMensal;
    }

    public CartesianChartModel getGraficoDescritivoCusteioMensal() {
        return graficoDescritivoCusteioMensal;
    }

    public void setGraficoDescritivoCusteioMensal(CartesianChartModel graficoDescritivoCusteioMensal) {
        this.graficoDescritivoCusteioMensal = graficoDescritivoCusteioMensal;
    }

    public BigDecimal getTotalDetalhe() {
        return totalDetalhe;
    }

    public void setTotalDetalhe(BigDecimal totalDetalhe) {
        this.totalDetalhe = totalDetalhe;
    }

    public void selecionaDet(ItemSelectEvent event) {
        selecionaMes(event.getItemIndex());
    }

    public List<CentroCustoDto> getCentroCustoDtoLista() {
        return centroCustoDtoLista;
    }

    public void setCentroCustoDtoLista(List<CentroCustoDto> centroCustoDtoLista) {
        this.centroCustoDtoLista = centroCustoDtoLista;
    }
}
