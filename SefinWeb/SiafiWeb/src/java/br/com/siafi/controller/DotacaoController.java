/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.dao.EmpenhoSolicitacaoDAO;
import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.DotacaoDAO;
import br.com.guardiao.dao.ExercicioDAO;
import br.com.siafi.dao.NaturezaDespesaDAO;
import br.com.siafi.dao.OrcamentoDAO;
import br.com.siafi.dao.ProjetoAtividadeDAO;
import br.com.siafi.dao.SolicitacaoFinanceiraDAO;
import br.com.siafi.modelo.Dotacao;
import br.com.sefin.modelo.dto.DotacaoDto;
import br.com.siafi.modelo.ElementoDespesa;
import br.com.guardiao.modelo.Exercicio;
import br.com.siafi.dao.CreditoAdicionalDetalheDAO;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.CreditoAdicionalDetalhe;
import br.com.siafi.modelo.DotacalPk;
import br.com.siafi.modelo.NaturezaDespesa;
import br.com.siafi.modelo.Orcamento;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class DotacaoController extends Controller<Dotacao, String> implements Serializable {

    @EJB
    private DotacaoDAO dao;
    @EJB
    private OrcamentoDAO orcamentoDao;
    @EJB
    private ExercicioDAO exercicioDao;
    @EJB
    private NaturezaDespesaDAO natDao;
    @EJB
    private ProjetoAtividadeDAO paDao;
    @EJB
    private br.com.gestor.dao.DotacaoDAO dotacaoGestorDao;
    @EJB
    private EmpenhoSolicitacaoDAO emprenhoDao;
    @EJB
    private CreditoAdicionalDetalheDAO adicionalDetalheDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Dotacao> lista = dotacaoGestorDao.listarImportacao();
        Orcamento o = orcamentoDao.orcamentoAtual(exercicioDao.buscarUniqueResult("padrao", true));
        for (br.com.gestor.modelo.Dotacao dotacao : lista) {
            DotacalPk dotacalPk = new DotacalPk();
            System.out.println(dotacao.toString());
            Dotacao dotacaoSiafi = new Dotacao();
            System.out.println(dotacao.getId().toString());
            dotacalPk.setId(dotacao.getProjetoAtividade() + dotacao.getNaturezaDespesa() + o.getId());
            dotacalPk.setId_reduzido(dotacao.getId().toString());
            dotacaoSiafi.setDotacalPk(dotacalPk);
            dotacaoSiafi.setNaturezaDespesa(natDao.carregar(dotacao.getNaturezaDespesa()));
            dotacaoSiafi.setProjetoAtividade(paDao.carregar(dotacao.getProjetoAtividade()));
            dotacaoSiafi.setOrcamento(o);
            dotacaoSiafi.setValor(dotacao.getDotValor());
            dao.atualizar(dotacaoSiafi);
        }
        dotacaoGestorDao.marcarImportados("DOT");
    }

    @Override
    public void salvarouAtualizar(Dotacao t) throws Exception {
        if (t.getDotacalPk() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public BigDecimal saldo(Dotacao dotacao) throws Exception {
        // Adicionar Suplementações
        BigDecimal suplmen = adicionalDetalheDao.totalAdicionado(dotacao);
        // Subtrair as Anulações
        BigDecimal anulacoes = adicionalDetalheDao.totalAnulado(dotacao);
        // Sobtrair solicitações a partir de aprovadas
        BigDecimal utilizado = dao.saldoUtilizado(dotacao);
        return dotacao.getValor().add(suplmen).subtract(anulacoes).subtract(utilizado);
    }

    public BigDecimal saldo(Dotacao dotacao, String numeroEmpenho, Integer ano) throws Exception {
        // Adicionar Suplementações
        BigDecimal suplmen = adicionalDetalheDao.totalAdicionado(dotacao);
        System.out.println(suplmen);
        // Subtrair as Anulações
        BigDecimal anulacoes = adicionalDetalheDao.totalAnulado(dotacao);
        System.out.println(anulacoes);
        // Sobtrair solicitações a partir de aprovadas
        BigDecimal utilizado = dao.saldoUtilizadoGestor(dotacao, numeroEmpenho, ano);
        System.out.println(utilizado);
        return dotacao.getValor().add(suplmen).subtract(anulacoes).subtract(utilizado);
    }

    public Dotacao dotacaoProjetoAtividadeNatureza(ProjetoAtividade pa, NaturezaDespesa nd, Orcamento o) throws Exception {
        return dotacaoProjetoAtividadeNatureza(pa, nd, o);
    }

    public List<DotacaoDto> dotacaoProjetoAtividadeOrcamento(ProjetoAtividade pa, Orcamento o, ElementoDespesa ed) throws Exception {
        List<DotacaoDto> dotacaoDtos = new ArrayList<>();
        List<Dotacao> dotacaos;
        DotacaoDto dto;
        dotacaos = dao.dotacaoProjetoAtividadeOrcamento(pa, o, ed);
        for (Dotacao dotacao : dotacaos) {
            List<CreditoAdicionalDetalhe> adicionalDetalhes = adicionalDetalheDao.listaCreditoAdicioalDetalhe(dotacao);
            dto = new DotacaoDto();
            if (!adicionalDetalhes.isEmpty()) {
                dto.setAdicionalDetalhes(adicionalDetalhes);
            }
            dto.setValorUtilizado(BigDecimal.ZERO);
            dto.setDotacao(dotacao);
            dto.setValorUtilizado(dao.somaSolicitacaoDotacao(dotacao));
            dotacaoDtos.add(dto);
        }
        return dotacaoDtos;
    }

    public List<Dotacao> dotacaoElementoDespesaOrcamento(Orcamento o, ElementoDespesa elementoDespesa) throws Exception {
        return dao.dotacaoElementoDespesaOrcamento(o, elementoDespesa);
    }

    public Dotacao dotacaoPorCodigoSimples(String cod, Orcamento o) throws Exception {
        return dao.dotacaoPorCodigoSimples(cod, o);
    }

    public List<DotacaoDto> gerarDTO(List<Dotacao> dotacaos) throws Exception {
        List<DotacaoDto> dotacaoDtos = new ArrayList<>();
        for (Dotacao dotacao : dotacaos) {
            DotacaoDto dto = new DotacaoDto();
            List<CreditoAdicionalDetalhe> adicionalDetalhes = adicionalDetalheDao.listaCreditoAdicioalDetalhe(dotacao);
            if (!adicionalDetalhes.isEmpty()) {
                dto.setAdicionalDetalhes(adicionalDetalhes);
            }
            dto.setValorUtilizado(BigDecimal.ZERO);
            dto.setDotacao(dotacao);
            dto.setValorUtilizado(dao.somaSolicitacaoDotacao(dotacao));
            dotacaoDtos.add(dto);
        }
        return dotacaoDtos;
    }

    public List<ProjetoAtividade> listarProjetoPorElementoUnidadeOrcamentaria(Orcamento o, CentroCusto despesa, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        if (o != null && despesa != null && unidadeOrcamentaria != null) {
            return dao.listarProjetoPorElementoUnidadeOrcamentaria(o, despesa, unidadeOrcamentaria);
        } else {
            return new ArrayList<>();
        }
    }

    public List<Dotacao> listar(UnidadeOrcamentaria unidadeOrcamentaria, List<UnidadeOrcamentaria> unidadeOrcamentarias, Exercicio exercicio, NaturezaDespesa naturezaDespesa, ProjetoAtividade projetoAtividade) throws Exception {
        return dao.listar(unidadeOrcamentaria, unidadeOrcamentarias, exercicio, naturezaDespesa, projetoAtividade);
    }

    public BigDecimal saldoDotacaoGestor(Date dataEmpenho, String numeroEmpenho, String dotReduzido) {
        return emprenhoDao.saldoDotacao(dataEmpenho, numeroEmpenho, dotReduzido);
    }

}
