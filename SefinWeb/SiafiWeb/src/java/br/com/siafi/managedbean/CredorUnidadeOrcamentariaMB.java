/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.CredorUnidadeOrcamentariaController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.dataiterativa.CredorDataList;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.CredorUnidadeOrcamentaria;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CredorUnidadeOrcamentariaMB extends BeanGenerico<CredorUnidadeOrcamentaria> implements Serializable {

    @Inject
    private UsuarioMB usuarioMb;
    @EJB
    private CredorUnidadeOrcamentariaController controller;
    @EJB
    private CredorController credorController;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraController;
    
    private CredorUnidadeOrcamentaria credorUnidadeOrcamentaria;
    private CredorDataList credorDataList;
    private List<Credor> listaCredores;
    private List<Credor> listaCredores2;
    private List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras;
    private String idUnidade;
    private List<CredorUnidadeOrcamentaria> listaCredorUnidadeOrcamentarias;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Credor credor;
    private Mes mes;

    public CredorUnidadeOrcamentariaMB() {
        super(CredorUnidadeOrcamentaria.class);
    }

    @PostConstruct
    public void iniciar() {
        try {
            credorUnidadeOrcamentaria = new CredorUnidadeOrcamentaria();
            listaCredores = new ArrayList<>();
            listaCredores2 = new ArrayList<>();
            listaSolicitacaoFinanceiras = new ArrayList<>();
            listaCredorUnidadeOrcamentarias = new ArrayList<>();
            credorDataList = new CredorDataList(credorController.listarTodos("pessoa.nome"));
            credor = new Credor();
        } catch (Exception ex) {
            Logger.getLogger(CredorUnidadeOrcamentariaMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void salvar() {
        try {
            controller.salvarCredorUnidade(credorUnidadeOrcamentaria, unidadeOrcamentaria, listaCredores2);
            MenssagemUtil.addMessageInfo(getMsg("cadastro"));
            iniciar();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro"));
            Logger.getLogger(CredorUnidadeOrcamentariaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirCredor(Credor item) {
        try {
            credorUnidadeOrcamentaria.getCredores().remove(item);
            controller.atualizar(credorUnidadeOrcamentaria);
            MenssagemUtil.addMessageInfo(getMsg("exclusao"));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(getMsg("erro_exclusao"));
            Logger.getLogger(CredorUnidadeOrcamentariaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCredor() {
        for (Credor c : listaCredores) {
            if (!credorUnidadeOrcamentaria.getCredores().contains(c)) {
                try {
                    addCredor(c);
                    controller.salvarCredorUnidade(credorUnidadeOrcamentaria, unidadeOrcamentaria, listaCredores2);
                } catch (Exception ex) {
                    MenssagemUtil.addMessageErro(getMsg("erro"));
                    Logger.getLogger(CredorUnidadeOrcamentariaMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        MenssagemUtil.addMessageInfo(getMsg("cadastro"));

    }

    public void removerCredores() {
        try {
            listaCredores2 = new ArrayList<>();
            credorUnidadeOrcamentaria.getCredores().clear();
            credorUnidadeOrcamentaria.setCredores(new ArrayList<Credor>());
            controller.atualizar(credorUnidadeOrcamentaria);
            MenssagemUtil.addMessageInfo(getMsg("exclusao"));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro_exclusao");
            Logger.getLogger(CredorUnidadeOrcamentariaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selecionaSecretaria() {
        listaCredores2.clear();
        if (idUnidade.length() == 4) {
            for (UnidadeOrcamentaria u : usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias()) {
                if (idUnidade.equals(u.getUnidadeOrcamentariaPK().toString())) {
                    unidadeOrcamentaria = u;
                    credorUnidadeOrcamentaria = controller.buscaCredorUnidadeOrcamentaria(u);
                    if (credorUnidadeOrcamentaria.getId() == null) {
                        credorUnidadeOrcamentaria.setCredores(new ArrayList<Credor>());
                    } else {
                        listaCredores2 = credorUnidadeOrcamentaria.getCredores();

                    }
                    if (credorUnidadeOrcamentaria.getId() == null) {
                        unidadeOrcamentaria = u;
                    }
                    break;
                } else {
                    unidadeOrcamentaria = null;

                }
            }
            if (unidadeOrcamentaria == null) {
                idUnidade = "";
                MenssagemUtil.addMessageInfo("Nenhuma secretária encontrada");
            }
        } else {
            unidadeOrcamentaria = null;
        }

    }

    public void consultarProcessos() {
        try {
            List<SituacaoSolicitacao> l = new ArrayList<>();
            listaSolicitacaoFinanceiras = solicitacaoFinanceiraController.listarFiltros(credor, 2014, mes);
            if (listaSolicitacaoFinanceiras.isEmpty()) {
                MenssagemUtil.addMessageWarn(getMsg("lista_vazia"));
            }
        } catch (Exception ex) {
            Logger.getLogger(CredorUnidadeOrcamentariaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    
      public void impressao() {
        try {
        
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaSolicitacaoFinanceiras, m, "WEB-INF/relatorios/rel_acompanhamento_und_credor.jasper", "Relatório de Encaminhamentos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        } catch (Exception ex) {
            Logger.getLogger(SolicitacaiLiquidacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ajustaCodigo() {
        listaCredores2.clear();
        if (unidadeOrcamentaria == null) {
            idUnidade = "";
        } else {
            credorUnidadeOrcamentaria = controller.buscaCredorUnidadeOrcamentaria(unidadeOrcamentaria);
            if (credorUnidadeOrcamentaria.getId() == null) {
                credorUnidadeOrcamentaria.setCredores(new ArrayList<Credor>());
            } else {
                listaCredores2 = credorUnidadeOrcamentaria.getCredores();
            }
            idUnidade = unidadeOrcamentaria.getUnidadeOrcamentariaPK().toString();
        }
    }

    public List<Mes> getListaMeses() {
        return Arrays.asList(Mes.values());
    }

    public void addCredor(Credor c) {
        listaCredores2.add(c);
    }

    public void delCredor(CredorDataList c) {
        credorUnidadeOrcamentaria.getCredores().remove(c);
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public List<CredorUnidadeOrcamentaria> getListaCredorUnidadeOrcamentarias() {
        return listaCredorUnidadeOrcamentarias;
    }

    public void setListaCredorUnidadeOrcamentarias(List<CredorUnidadeOrcamentaria> listaCredorUnidadeOrcamentarias) {
        this.listaCredorUnidadeOrcamentarias = listaCredorUnidadeOrcamentarias;
    }

    public CredorUnidadeOrcamentaria getCredorUnidadeOrcamentaria() {
        return credorUnidadeOrcamentaria;
    }

    public void setCredorUnidadeOrcamentaria(CredorUnidadeOrcamentaria credorUnidadeOrcamentaria) {
        this.credorUnidadeOrcamentaria = credorUnidadeOrcamentaria;
    }

    public CredorDataList getCredorDataList() {
        return credorDataList;
    }

    public void setCredorDataList(CredorDataList credorDataList) {
        this.credorDataList = credorDataList;
    }

    public List<Credor> getListaCredores() {
        return listaCredores;
    }

    public void setListaCredores(List<Credor> listaCredores) {
        this.listaCredores = listaCredores;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public String getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
    }

    public List<Credor> getListaCredores2() {
        return listaCredores2;
    }

    public void setListaCredores2(List<Credor> listaCredores2) {
        this.listaCredores2 = listaCredores2;
    }

    public List<SolicitacaoFinanceira> getListaSolicitacaoFinanceiras() {
        return listaSolicitacaoFinanceiras;
    }

    public void setListaSolicitacaoFinanceiras(List<SolicitacaoFinanceira> listaSolicitacaoFinanceiras) {
        this.listaSolicitacaoFinanceiras = listaSolicitacaoFinanceiras;
    }



    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

}
