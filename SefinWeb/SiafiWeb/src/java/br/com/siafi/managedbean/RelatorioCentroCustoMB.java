/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.CentroCustoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.CentroCustoDto;
import br.com.siafi.modelo.CentroCustoUnidadeOrcamentariaDto;
import br.com.sefin.modelo.dto.CusteioDto;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class RelatorioCentroCustoMB implements Serializable {

    @EJB
    private SolicitacaoFinanceiraController financeiraControler;
    @EJB
    private CentroCustoController centroCustoControler;
    @EJB
    private UsuarioMB usuarioMb;
    private List<CentroCustoDto> centroCustoDtos;
    private List<CentroCustoUnidadeOrcamentariaDto> centroCustoUnidadeOrcamentariaDtos;
    private List<SolicitacaoFinanceira> financeirasUnidadeOrcamentaria;
    private List<SolicitacaoFinanceira> financeirasDespesa;
    private List<CentroCustoDto> tabela;
    private List<CentroCustoDto> cenCustoDtos;
    private List<CentroCustoDto> centroCustoDtosUnidadeOrcamentaria;
    private List<CentroCustoDto> custoDtos;
    private CartesianChartModel graficoUnidadeOrcamentariaCentroCusto;
    private PieChartModel graficoPizzaUnidadeOrcamentaria;
    private CartesianChartModel graficoCentroCustoUnidadeOrcamentaria;
    private PieChartModel graficoPizzaUnidadeOrcamentariaTotalDespesa;
    private CentroCustoDto custoDto;
    private CusteioDto custeioDto;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Date dtIni;
    private Date dtFim;
    private String cores;

    public RelatorioCentroCustoMB() {

        inicializaGraficos();
        financeirasDespesa = new ArrayList<>();
        financeirasUnidadeOrcamentaria = new ArrayList<>();
        tabela = new ArrayList<>();
        custoDtos = new ArrayList<>();
        custoDto = new CentroCustoDto();
        centroCustoDtosUnidadeOrcamentaria = new ArrayList<>();
        cores = "696969,2F4F4F,708090,000080,6495ED,483D8B,4169E1,1E90FF,00CED1,006400,556B2F,3CB371,7CFC00,BDB76B,CD5C5C,B22222,FF69B4,FF00FF,00E5EE,00EE76,EE6363,CD6600,CD8C95,FF83FA,9F79EE,363636,BCEE68,bc8f8f,a0522d,ff69b4,afeeee,f0e68c,CCCC99,C0C0C0,556B2F,ADD8E6,4682B4,B0C4DE";

    }

    private void inicializaGraficos() {
        graficoUnidadeOrcamentariaCentroCusto = new CartesianChartModel();
        graficoPizzaUnidadeOrcamentaria = new PieChartModel();
        graficoCentroCustoUnidadeOrcamentaria = new CartesianChartModel();
        graficoPizzaUnidadeOrcamentariaTotalDespesa = new PieChartModel();

        ChartSeries serie = new ChartSeries();
        serie.setLabel("");
        serie.set("0", 0);
        graficoUnidadeOrcamentariaCentroCusto.addSeries(serie);
        graficoPizzaUnidadeOrcamentaria.set("", 0);
        graficoCentroCustoUnidadeOrcamentaria.addSeries(serie);
        graficoPizzaUnidadeOrcamentariaTotalDespesa.set("0", 0);

        unidadeOrcamentaria = new UnidadeOrcamentaria();
    }

    public void gerarGraficos() {
        try {
            financeirasDespesa = financeiraControler.listaSolicitacaoDataDespesa(dtIni, dtFim, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), unidadeOrcamentaria);
            financeirasUnidadeOrcamentaria = financeiraControler.listaSolicitacaoUnidadeOrcamentaria(dtIni, dtFim, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias());
            tabelaCentroCusto();
//            geraGraficoPizza();
            geraGraficoPizzaUnidadeOrcamentaria();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar Grafico", ex, this.getClass().getName());
        }
    }
    //1

    public void geraGraficoPizza() {
        try {
            graficoPizzaUnidadeOrcamentaria.clear();
            centroCustoDtos = centroCustoControler.listarCusteioDtoPorUnidadeOrc(financeirasDespesa);
            for (CentroCustoDto c : centroCustoDtos) {
                if (c.getCentroCusto().getNome().length() > 30) {
                    graficoPizzaUnidadeOrcamentaria.set(c.getCentroCusto().getNome().substring(0, 15), c.getTotal());

                } else {
                    graficoPizzaUnidadeOrcamentaria.set(c.getCentroCusto().getNome(), c.getTotal());

                }
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar Grafico pizza", ex, this.getClass().getName());
        }

    }
    //1.2

    private void geraGraficoUnidadeOrcamentariaCentroCusto(Integer indice) {
        try {
            custoDto = centroCustoDtos.get(indice);
            centroCustoUnidadeOrcamentariaDtos = financeiraControler.listarCentroCustoUnidadeOrcamentaria(custoDto.getCentroCusto(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias());
            graficoUnidadeOrcamentariaCentroCusto.clear();

            for (CentroCustoUnidadeOrcamentariaDto centroCustoUnidadeOrcamentariaDto : centroCustoUnidadeOrcamentariaDtos) {
                ChartSeries s = new ChartSeries();
                s.setLabel(centroCustoUnidadeOrcamentariaDto.getUnidadeOrcamentaria().getNome().substring(0, 20));
                s.set(custoDto.getCentroCusto().getNome(), centroCustoUnidadeOrcamentariaDto.getTotal());
                graficoUnidadeOrcamentariaCentroCusto.addSeries(s);
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar Grafico de custos", ex, this.getClass().getName());
        }
    }

    public void listarFinanceiraUnidadeOrc(ItemSelectEvent event) {
        try {
            UnidadeOrcamentaria uo = centroCustoUnidadeOrcamentariaDtos.get(event.getSeriesIndex()).getUnidadeOrcamentaria();
            financeirasDespesa = financeiraControler.listarPorCentroCustoUnidadeOrcamentaria(custoDto.getCentroCusto(), uo);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao consultar despesas", ex, this.getClass().getName());
        }
    }

    //Grafico 2
    public void geraGraficoPizzaUnidadeOrcamentaria() {
        try {
            graficoPizzaUnidadeOrcamentariaTotalDespesa.clear();
            centroCustoDtosUnidadeOrcamentaria = centroCustoControler.listarUnidadeOrcPorCusteioDto(financeirasUnidadeOrcamentaria);
            custoDtos = centroCustoControler.processarCentroCusto(centroCustoDtosUnidadeOrcamentaria);
            for (CentroCustoDto centroCustoDto : custoDtos) {
                graficoPizzaUnidadeOrcamentariaTotalDespesa.set(centroCustoDto.getOrcamentaria().getUnidadeOrcamentariaPK().getIdOrgao().concat(centroCustoDto.getOrcamentaria().getUnidadeOrcamentariaPK().getId()) + "-" + centroCustoDto.getOrcamentaria().getNome().substring(0, 20), centroCustoDto.getTotal());
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar Grafico", ex, this.getClass().getName());
        }
    }

    //2.1
    public void geraGraficoCentroCustoUnidadeorcamentaria(Integer indice) {
        try {
            //centroCustoDtos = custeioDtos.get(indice);
            CentroCustoDto ccd = custoDtos.get(indice);
            graficoCentroCustoUnidadeOrcamentaria.clear();
            cenCustoDtos = financeiraControler.listarCentroCustoDto(ccd.getOrcamentaria());
            for (CentroCustoDto centroCustoDto : cenCustoDtos) {
                ChartSeries s = new ChartSeries();
                if (centroCustoDto.getCentroCusto().getNome().length() > 30) {
                    s.setLabel(centroCustoDto.getCentroCusto().getNome().substring(0, 29));
                } else {
                    s.setLabel(centroCustoDto.getCentroCusto().getNome());

                }
                s.set(centroCustoDto.getOrcamentaria().getNome(), centroCustoDto.getTotal());
                graficoCentroCustoUnidadeOrcamentaria.addSeries(s);
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar Grafico", ex, this.getClass().getName());
        }
    }

    public void tabelaCentroCusto() {
        try {
            tabela = centroCustoControler.listarCusteioDtoTabela(financeirasDespesa);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar consulta", ex, this.getClass().getName());
        }

    }

    public void listarFinanceiraDespesa(ItemSelectEvent event) {
        try {
            custoDto = cenCustoDtos.get(event.getSeriesIndex());
            financeirasDespesa = financeiraControler.listarPorCentroCustoUnidadeOrcamentaria(custoDto.getCentroCusto(), custoDto.getOrcamentaria());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao gerar consulta", ex, this.getClass().getName());
        }
    }

    public void selecionaUnidadeOrcamentaria(ItemSelectEvent event) {
        financeirasDespesa.clear();
        geraGraficoCentroCustoUnidadeorcamentaria(event.getItemIndex());
    }

    public void selecionaCentroCusto(ItemSelectEvent event) {
        financeirasDespesa.clear();
        geraGraficoUnidadeOrcamentariaCentroCusto(event.getItemIndex());
    }

    public List<CentroCustoDto> getCentroCustoDtos() {
        return centroCustoDtos;
    }

    public void setCentroCustoDtos(List<CentroCustoDto> centroCustoDtos) {
        this.centroCustoDtos = centroCustoDtos;
    }

    public Date getDtIni() {
        return dtIni;
    }

    public void setDtIni(Date dtIni) {
        this.dtIni = dtIni;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public CartesianChartModel getGraficoUnidadeOrcamentariaCentroCusto() {
        return graficoUnidadeOrcamentariaCentroCusto;
    }

    public void setGraficoUnidadeOrcamentariaCentroCusto(CartesianChartModel graficoUnidadeOrcamentariaCentroCusto) {
        this.graficoUnidadeOrcamentariaCentroCusto = graficoUnidadeOrcamentariaCentroCusto;
    }

    public PieChartModel getGraficoPizzaUnidadeOrcamentaria() {
        return graficoPizzaUnidadeOrcamentaria;
    }

    public void setGraficoPizzaUnidadeOrcamentaria(PieChartModel graficoPizzaUnidadeOrcamentaria) {
        this.graficoPizzaUnidadeOrcamentaria = graficoPizzaUnidadeOrcamentaria;
    }

    public CentroCustoDto getCustoDto() {
        return custoDto;
    }

    public void setCustoDto(CentroCustoDto custoDto) {
        this.custoDto = custoDto;
    }

    public CartesianChartModel getGraficoCentroCustoUnidadeOrcamentaria() {
        return graficoCentroCustoUnidadeOrcamentaria;
    }

    public void setGraficoCentroCustoUnidadeOrcamentaria(CartesianChartModel graficoCentroCustoUnidadeOrcamentaria) {
        this.graficoCentroCustoUnidadeOrcamentaria = graficoCentroCustoUnidadeOrcamentaria;
    }

    public PieChartModel getGraficoPizzaUnidadeOrcamentariaTotalDespesa() {
        return graficoPizzaUnidadeOrcamentariaTotalDespesa;
    }

    public void setGraficoPizzaUnidadeOrcamentariaTotalDespesa(PieChartModel graficoPizzaUnidadeOrcamentariaTotalDespesa) {
        this.graficoPizzaUnidadeOrcamentariaTotalDespesa = graficoPizzaUnidadeOrcamentariaTotalDespesa;
    }

    public CusteioDto getCusteioDto() {
        return custeioDto;
    }

    public void setCusteioDto(CusteioDto custeioDto) {
        this.custeioDto = custeioDto;
    }

    public List<SolicitacaoFinanceira> getFinanceirasUnidadeOrcamentaria() {
        return financeirasUnidadeOrcamentaria;
    }

    public void setFinanceirasUnidadeOrcamentaria(List<SolicitacaoFinanceira> financeirasUnidadeOrcamentaria) {
        this.financeirasUnidadeOrcamentaria = financeirasUnidadeOrcamentaria;
    }

    public List<CentroCustoDto> getTabela() {
        return tabela;
    }

    public void setTabela(List<CentroCustoDto> tabela) {
        this.tabela = tabela;
    }

    public List<SolicitacaoFinanceira> getFinanceirasDespesa() {
        return financeirasDespesa;
    }

    public void setFinanceirasDespesa(List<SolicitacaoFinanceira> financeirasDespesa) {
        this.financeirasDespesa = financeirasDespesa;
    }

    public void imprimirResultado() {
        try {
            Map<String, Object> m = new HashMap<>();
            m.put("dtIni", dtIni);
            m.put("dtFim", dtFim);
            if (unidadeOrcamentaria != null) {
                m.put("unidade", unidadeOrcamentaria.getUnidadeOrcamentariaPK().getIdOrgao().concat(unidadeOrcamentaria.getUnidadeOrcamentariaPK().getId().concat("-".concat(unidadeOrcamentaria.getAbreviacao()))));
            }

            byte[] rel = new AssistentedeRelatorio().relatorioemByte(tabela, m, "WEB-INF/relatorios/relatorio_despesas.jasper", "Relatório de Resumo das despesas");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        }
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

}
