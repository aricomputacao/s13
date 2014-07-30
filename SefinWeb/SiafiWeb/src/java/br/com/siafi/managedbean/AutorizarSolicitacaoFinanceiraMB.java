/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Classe do Projeto Siafi - Criado em 27/05/2013 - Autorizar Solicitação
 * Finaceira
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class AutorizarSolicitacaoFinanceiraMB extends BeanGenerico<SolicitacaoFinanceira> implements Serializable {

    @EJB
    private SolicitacaoFinanceiraController solicitacaoControler;
    @EJB
    private UsuarioMB usuarioMb;
    private SolicitacaoFinanceira solicitacaoFinanceira;
    private List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private List<UnidadeOrcamentaria> listaUnidadeOrcamentarias;
    private BigDecimal valorAutorizado;
    private SituacaoSolicitacao situacao;

    public AutorizarSolicitacaoFinanceiraMB() {
        super(SolicitacaoFinanceira.class);
        solicitacaoFinanceira = new SolicitacaoFinanceira();
        listaSolicitacaoFinanceiras = new ArrayList<>();
        unidadeOrcamentaria = new UnidadeOrcamentaria();
        listaUnidadeOrcamentarias = new ArrayList<>();
        valorAutorizado = new BigDecimal(BigInteger.ZERO);
        situacao = SituacaoSolicitacao.Pendente;
    }

    @PostConstruct
    private void carregarListaUnidadeOrcamentaria() {
        listaUnidadeOrcamentarias = usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias();
    }

    public void salvar() {
        try {
            solicitacaoFinanceira.setSituacaoSolicitacao(situacao);
            solicitacaoFinanceira.setUsuarioAutorizou(usuarioMb.getUsuarioSelecionado());
            solicitacaoFinanceira.setDataAutorizacao(new Date());
            if (solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
                if (solicitacaoFinanceira.getValor().compareTo(valorAutorizado) >= 0) {
                    solicitacaoFinanceira.setValor(valorAutorizado);
                    solicitacaoControler.salvarouAtualizar(solicitacaoFinanceira);
                    listaSolicitacaoFinanceiras.remove(solicitacaoFinanceira);
                    situacao = SituacaoSolicitacao.Pendente;
                    solicitacaoControler.encaminharFluxoContabilidade(solicitacaoFinanceira);
                    solicitacaoFinanceira = new SolicitacaoFinanceira();
                    valorAutorizado = new BigDecimal(BigInteger.ZERO);
                    MenssagemUtil.addMessageInfo("Solicitação aprovada");
                } else {
                    MenssagemUtil.addMessageErro("O valor autorizado não pode ser maior que o valor solicitado");
                }
            } else if (solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Negado)) {
                solicitacaoControler.salvarouAtualizar(solicitacaoFinanceira);
                listaSolicitacaoFinanceiras.remove(solicitacaoFinanceira);
                solicitacaoFinanceira = new SolicitacaoFinanceira();
                situacao = SituacaoSolicitacao.Pendente;
                valorAutorizado = new BigDecimal(BigInteger.ZERO);
                MenssagemUtil.addMessageInfo("Solicitação não aprovada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao autorizar solicitação", ex, this.getClass().getName());
        }
    }

    public void listar() {
        try {
            listaSolicitacaoFinanceiras = solicitacaoControler.listarSolicitacaoSituacao(unidadeOrcamentaria, SituacaoSolicitacao.Pendente, getValorBusca(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias());
            if (listaSolicitacaoFinanceiras.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma solicitação encontrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar solicitações", ex, this.getClass().getName());
        }
    }

    public List<Mes> getListaMeses() {
        return Arrays.asList(Mes.values());
    }

    public void novo() {
        solicitacaoFinanceira = new SolicitacaoFinanceira();
        situacao = SituacaoSolicitacao.Pendente;
        valorAutorizado = new BigDecimal(BigInteger.ZERO);
    }

    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
    }

    public List<SolicitacaoFinanceira> getListaSolicitacaoFinanceiras() {
        return listaSolicitacaoFinanceiras;
    }

    public void setListaSolicitacaoFinanceiras(List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras) {
        this.listaSolicitacaoFinanceiras = listaSolicitacaoFinanceiras;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<UnidadeOrcamentaria> getListaUnidadeOrcamentarias() {
        return listaUnidadeOrcamentarias;
    }

    public void setListaUnidadeOrcamentarias(List<UnidadeOrcamentaria> listaUnidadeOrcamentarias) {
        this.listaUnidadeOrcamentarias = listaUnidadeOrcamentarias;
    }

    public BigDecimal getValorAutorizado() {
        return valorAutorizado;
    }

    public void setValorAutorizado(BigDecimal valorAutorizado) {
        this.valorAutorizado = valorAutorizado;
    }

    public SituacaoSolicitacao getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoSolicitacao situacao) {
        this.situacao = situacao;
    }
}
