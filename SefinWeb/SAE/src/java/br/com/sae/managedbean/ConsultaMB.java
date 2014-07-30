/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sae.managedbean;

import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.sae.controller.ConsultaController;
import br.com.sefin.modelo.dto.SolicitacaoSaldoDto;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ConsultaMB extends BeanGenerico<SolicitacaoFinanceira> implements Serializable {

    @Inject
    private BeanUtilitario beanUtilitario;
    @EJB
    private ConsultaController controller;
    @EJB
    private UnidadeOrcamentariaController orcamentariaController;
    private List<SolicitacaoSaldoDto> listaSolicitacaoSaldoDtos;
    private List<UnidadeOrcamentaria> listaUnidadeOrcamentarias;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Date dataInicial;
    private Date dataFinal;

    public ConsultaMB() {
        super(SolicitacaoFinanceira.class);
    }

    @PostConstruct
    private void Iniciar() {
        try {
            dataInicial = new Date();
            dataFinal = new Date();
            listaSolicitacaoSaldoDtos = new ArrayList<>();
            listaUnidadeOrcamentarias = orcamentariaController.listarAtivos();
            unidadeOrcamentaria = new UnidadeOrcamentaria();
        } catch (PersistenceException ex) {
            Logger.getLogger(ConsultaMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EJBException ex) {
            Logger.getLogger(ConsultaMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ConsultaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultar() {
        try {
            listaSolicitacaoSaldoDtos = controller.solicitacaoSaldoDtos(unidadeOrcamentaria, beanUtilitario.getUsuarioLogado().getDocumento(), dataInicial, dataFinal);
        } catch (Exception ex) {
            Logger.getLogger(ConsultaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void impressao() {
        try {
            Map<String, Object> m = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaSolicitacaoSaldoDtos, m, "WEB-INF/relatorios/relatorio_solicitacao.jasper", "Relatório de Processos");
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (JRException ex) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
        }
    }

    public List<SolicitacaoSaldoDto> getListaSolicitacaoSaldoDtos() {
        return listaSolicitacaoSaldoDtos;
    }

    public void setListaSolicitacaoSaldoDtos(List<SolicitacaoSaldoDto> listaSolicitacaoSaldoDtos) {
        this.listaSolicitacaoSaldoDtos = listaSolicitacaoSaldoDtos;
    }

    public List<UnidadeOrcamentaria> getListaUnidadeOrcamentarias() {
        return listaUnidadeOrcamentarias;
    }

    public void setListaUnidadeOrcamentarias(List<UnidadeOrcamentaria> listaUnidadeOrcamentarias) {
        this.listaUnidadeOrcamentarias = listaUnidadeOrcamentarias;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
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

}
