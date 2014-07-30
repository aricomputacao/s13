/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.DotacaoController;
import br.com.guardiao.controler.ExercicioController;
import br.com.siafi.modelo.Dotacao;
import br.com.sefin.modelo.dto.DotacaoDto;
import br.com.guardiao.modelo.Exercicio;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.CreditoAdicionalDetalheController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.CreditoAdicionalDetalhe;
import br.com.siafi.modelo.NaturezaDespesa;
import br.com.siafi.modelo.ProjetoAtividade;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
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
 * Bean gerenciado do Projeto SiafiWeb criado em 18/06/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class DotacaoMB extends BeanGenerico<Dotacao> implements Serializable {

    private Dotacao dotacao;
    private List<Dotacao> listaDotacaos;
    private List<Exercicio> listaExercicios;
    private List<DotacaoDto> listaDotacaoDtos;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Exercicio exercicio;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private DotacaoController dotacaoControler;
    @EJB
    private ExercicioController exercicioControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraController;
    @EJB
    private CreditoAdicionalDetalheController creditoAdicionalDetalheController;
    private List<CreditoAdicionalDetalhe> listaCreditosAdicionaisDetalhes;
    private List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras;
    private NaturezaDespesa naturezaDespesa;
    private ProjetoAtividade projetoAtividade;

    public DotacaoMB() {
        super(Dotacao.class);

        this.listaDotacaos = new ArrayList<>();
    }

    @PostConstruct
    public void novo() {
//        this.dotacao = (Dotacao) beanNavegacao.getRegistroMapa("dotacao", new Dotacao());
        try {
            listaExercicios = exercicioControler.listarTodos("ano");
            exercicio = exercicioControler.exercicioVigente();
            unidadeOrcamentaria = new UnidadeOrcamentaria();
            listaDotacaoDtos = new ArrayList<>();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao listar exercicios", ex, this.getClass().getName());
        }

    }

    public void listar() {
        try {
//            listaDotacaoDtos = dotacaoControler.dotacaoUnidadeOrcamamentaria(unidadeOrcamentaria, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), exercicio);
            listaDotacaos = dotacaoControler.listar(unidadeOrcamentaria, usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), exercicio, naturezaDespesa, projetoAtividade);
            listaDotacaoDtos = dotacaoControler.gerarDTO(listaDotacaos);
            if (listaDotacaoDtos.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma dotação encontrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public void impressao() {
        if (!listaDotacaoDtos.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaDotacaoDtos, m, "WEB-INF/relatorios/relatorio_dotacao.jasper", "Relatório de Dotações Orçamentarias");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public void seleciona(Dotacao d) {
        try {
            dotacao = d;
            listaSolicitacaoFinanceiras = solicitacaoFinanceiraController.listar(dotacao);
            listaCreditosAdicionaisDetalhes = creditoAdicionalDetalheController.listar(dotacao);
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao selecionar dotação ", e, getClass().getName());
        }
    }

    public Dotacao getDotacao() {
        return dotacao;
    }

    public void setDotacao(Dotacao dotacao) {
        this.dotacao = dotacao;
    }

    public List<Dotacao> getListaDotacaos() {
        return listaDotacaos;
    }

    public void setListaDotacaos(List<Dotacao> listaDotacaos) {
        this.listaDotacaos = listaDotacaos;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<Exercicio> getListaExercicios() {
        return listaExercicios;
    }

    public void setListaExercicios(List<Exercicio> listaExercicios) {
        this.listaExercicios = listaExercicios;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public List<DotacaoDto> getListaDotacaoDtos() {
        return listaDotacaoDtos;
    }

    public void setListaDotacaoDtos(List<DotacaoDto> listaDotacaoDtos) {
        this.listaDotacaoDtos = listaDotacaoDtos;
    }

    public List<CreditoAdicionalDetalhe> getListaCreditosAdicionaisDetalhes() {
        return listaCreditosAdicionaisDetalhes;
    }

    public void setListaCreditosAdicionaisDetalhes(List<CreditoAdicionalDetalhe> listaCreditosAdicionaisDetalhes) {
        this.listaCreditosAdicionaisDetalhes = listaCreditosAdicionaisDetalhes;
    }

    public List<SolicitacaoFinanceira> getListaSolicitacaoFinanceiras() {
        return listaSolicitacaoFinanceiras;
    }

    public void setListaSolicitacaoFinanceiras(List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras) {
        this.listaSolicitacaoFinanceiras = listaSolicitacaoFinanceiras;
    }

    public NaturezaDespesa getNaturezaDespesa() {
        return naturezaDespesa;
    }

    public void setNaturezaDespesa(NaturezaDespesa naturezaDespesa) {
        this.naturezaDespesa = naturezaDespesa;
    }

    public ProjetoAtividade getProjetoAtividade() {
        return projetoAtividade;
    }

    public void setProjetoAtividade(ProjetoAtividade projetoAtividade) {
        this.projetoAtividade = projetoAtividade;
    }

}
