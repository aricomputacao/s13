/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.ContaController;
import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.controller.FonteRecursoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.dataiterativa.EmpenhoDetalheDataList;
import br.com.siafi.modelo.Conta;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.FonteRecurso;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @Sistema SiafiWeb
 * @Data 25/07/2013
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class AutorizarPagamentoLiquidacaoMB implements Serializable {

    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private EmpenhoDetalheController empenhoDetalheControler;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private ContaController contaControler;
    @EJB
    private FonteRecursoController fonteRecursoControler;
    private EmpenhoDetalhe empenhoDetalhe;
    private UnidadeOrcamentaria unidadeOrcamentaria;

    private FonteRecurso fonteRecurso;
    private List<FonteRecurso> listFonteRecurso;
    private List<Conta> listConta;
    private List<UnidadeOrcamentaria> unidades;
    private List<SolicitacaoFinanceira> listaSolicitacao;
    private List<EmpenhoDetalhe> listaEmpenhoDetalhes;
    private List<EmpenhoDetalhe> empenhoDetalhesList;
    private EmpenhoDetalheDataList empenhoDetalheDataList;

    private Conta conta;
    private boolean mantemFonteRecurso;

    public AutorizarPagamentoLiquidacaoMB() {
        listConta = new ArrayList<>();
        listFonteRecurso = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            empenhoDetalhesList = new ArrayList<>();
            unidades = new ArrayList<>();
            unidadeOrcamentaria = new UnidadeOrcamentaria();
            // verificar se o usuario possue unidades orçamentarias
            if (usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias() != null) {
                // Selecionar as unidades orçamentarias que presisão de autorização
                for (UnidadeOrcamentaria uo : usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias()) {
                    if (uo.isPendenteAutorizacao()) {
                        unidades.add(uo);
                    }
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(AutorizarPagamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
            MenssagemUtil.addMessageErro(ex);
        }
    }

    public void listarSolicitacoes() {
        try {

            // Listar as solicitação pendetes que estão Liquidadas na tesouraria ou tesouraria Saude e se o solicitação está em
            // uma delas
            listaSolicitacao = solicitacaoFinanceiraControler.listarPendentesPagamento(unidadeOrcamentaria, unidades);
            // importar para o siafi todas os detalhes de pagamento pendentes(Empenho datalhe ou liquidações ) das solicitações selecionadas acima
            listaEmpenhoDetalhes = empenhoDetalheControler.importarListarEmpenhosDetalhesPendentes(listaSolicitacao);

            empenhoDetalheDataList = new EmpenhoDetalheDataList(listaEmpenhoDetalhes);

        } catch (Exception ex) {
            Logger.getLogger(AutorizarPagamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
            MenssagemUtil.addMessageErro(ex);
        }
    }

    public void autorizarPagamento() {
        try {
            for (EmpenhoDetalhe e : empenhoDetalhesList) {

                e.setDataAutorizacao(new Date());
                autorizarGestor(e.getId(), e.getDataAutorizacao());
                empenhoDetalheControler.salvarouAtualizar(e);
                listaEmpenhoDetalhes.remove(e);
                MenssagemUtil.addMessageInfo("Pagamento Autorizado");
            }
            empenhoDetalheDataList = new EmpenhoDetalheDataList(listaEmpenhoDetalhes);
        } catch (Exception ex) {
            Logger.getLogger(AutorizarPagamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
            MenssagemUtil.addMessageErro(ex);
        }

    }

    public List<EmpenhoDetalhe> getListaEmpenhoDetalhes() {
        return listaEmpenhoDetalhes;
    }

    public void setListaEmpenhoDetalhes(List<EmpenhoDetalhe> listaEmpenhoDetalhes) {
        this.listaEmpenhoDetalhes = listaEmpenhoDetalhes;
    }

    public EmpenhoDetalhe getEmpenhoDetalhe() {
        return empenhoDetalhe;
    }

    public void setEmpenhoDetalhe(EmpenhoDetalhe empenhoDetalhe) {
        this.empenhoDetalhe = empenhoDetalhe;
    }

    // Autorizar o pagamento e exportar para o gestor
//    public void autorizarPagamento() {
//        try {
//            // Carrega e Atualiza Empenho Detalhe no sistema gestor com a data de autorização
////            if (conta.getSaldo().compareTo(empenhoDetalhe.getValor()) >= 0) {
//            empenhoDetalhe.setConta(conta);
//            empenhoDetalhe.setDataAutorizacao(new Date());
//            autorizarGestor(empenhoDetalhe.getId(), empenhoDetalhe.getDataAutorizacao());
//            empenhoDetalheControler.salvarouAtualizar(empenhoDetalhe);
//            MenssagemUtil.addMessageInfo("Pagamento Autorizado");
//            //          } else {
//            //            MenssagemUtil.addMessageWarn("Não há saldo na conta para efetuar o pagamento.");
//            //      }
//
//        } catch (Exception ex) {
//            Logger.getLogger(AutorizarPagamentoLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
//            MenssagemUtil.addMessageErro(ex);
//        }
//    }
    private void autorizarGestor(Integer id, Date dataAutorizacao) {
        try {
            empenhoDetalheControler.exportar(id, dataAutorizacao);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao autorizar pagamento", ex, this.getClass().getName());
        }
    }

    public FonteRecurso getFonteRecurso() {
        return fonteRecurso;
    }

    public void setFonteRecurso(FonteRecurso fonteRecurso) {
        this.fonteRecurso = fonteRecurso;
    }

    public void selecionaEmpenhoDetalhe(EmpenhoDetalhe empenhoDetalhe) {
        try {
            this.empenhoDetalhe = empenhoDetalhe;
            listFonteRecurso = fonteRecursoControler.listar(empenhoDetalhe.getSolicitacaoFinanceira().getCota().getUnidadeOrcamentaria());
            manterFonteRecurso(empenhoDetalhe.getSolicitacaoFinanceira());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao selecionar empenho", ex, this.getClass().getName());
        }

    }

    public List<FonteRecurso> getListFonteRecurso() {
        return listFonteRecurso;
    }

    public void setListFonteRecurso(List<FonteRecurso> listFonteRecurso) {
        this.listFonteRecurso = listFonteRecurso;
    }

    public List<Conta> getListConta() {
        return listConta;
    }

    public void setListConta(List<Conta> listConta) {
        this.listConta = listConta;
    }

    public void listarContas() {
        try {
            listConta = contaControler.listarPorFonte(fonteRecurso, empenhoDetalhe.getSolicitacaoFinanceira().getCota().getUnidadeOrcamentaria());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao listar contas", ex, this.getClass().getName());
        }
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    /**
     * Manter a Fonte de recurso unica por solicitação fianceira independete da
     * quantidade de pagamentos
     *
     * @param solicitacaoFinanceira
     * @return
     */
    private void manterFonteRecurso(SolicitacaoFinanceira solicitacaoFinanceira) {
        try {
            List<EmpenhoDetalhe> listEmpenhoDetalhe = empenhoDetalheControler.listarPorSolicitacao(solicitacaoFinanceira);
            if (!listEmpenhoDetalhe.isEmpty()) {
                for (EmpenhoDetalhe e : listEmpenhoDetalhe) {
                    if (e.getConta() != null) {
                        fonteRecurso = e.getConta().getFonteRecurso();
                        listarContas();
                        mantemFonteRecurso = true;
                        break;
                    } else {
                        mantemFonteRecurso = false;
                    }
                }
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao listar empenhos", ex, this.getClass().getName());
        }
    }

    public boolean isMantemFonteRecurso() {
        return mantemFonteRecurso;
    }

    public void setMantemFonteRecurso(boolean mantemFonteRecurso) {
        this.mantemFonteRecurso = mantemFonteRecurso;
    }

    public List<UnidadeOrcamentaria> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadeOrcamentaria> unidades) {
        this.unidades = unidades;
    }

    public List<SolicitacaoFinanceira> getListaSolicitacao() {
        return listaSolicitacao;
    }

    public void setListaSolicitacao(List<SolicitacaoFinanceira> listaSolicitacao) {
        this.listaSolicitacao = listaSolicitacao;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<EmpenhoDetalhe> getEmpenhoDetalhesList() {
        return empenhoDetalhesList;
    }

    public void setEmpenhoDetalhesList(List<EmpenhoDetalhe> empenhoDetalhesList) {
        this.empenhoDetalhesList = empenhoDetalhesList;
    }

    public EmpenhoDetalheDataList getEmpenhoDetalheDataList() {
        return empenhoDetalheDataList;
    }

    public void setEmpenhoDetalheDataList(EmpenhoDetalheDataList empenhoDetalheDataList) {
        this.empenhoDetalheDataList = empenhoDetalheDataList;
    }

}
