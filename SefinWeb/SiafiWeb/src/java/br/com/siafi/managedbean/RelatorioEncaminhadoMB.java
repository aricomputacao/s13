/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.controler.OrgaoController;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.util.DataUtil;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Encaminhamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;

/**
 * O Objetivo desta classe é prover uma consulta que mostre os encaminhamentos
 * feitos por uma determinada area adinistrativa em um determinado período. Como
 * cada Area administrativa já é especifica de uma determinada secretária, e
 * como cada usuário possue apenas uma secretária eu vou utilizar o usuário
 * logado para selecioar a localização dos encaminhamento
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class RelatorioEncaminhadoMB implements Serializable {

    @EJB
    private EncaminhamentoController controler;
    @EJB
    private AreaAdministrativaController areaController;
    @EJB
    private OrgaoController orgaoControler;
    @EJB
    UsuarioMB usuarioMb;
    private Date dataInicio;
    private Date dataFinal;
    private AreaAdministrativa destino;
    private Credor credor;
    private List<Encaminhamento> encaminhamentosList;
    private Orgao orgao;

    public RelatorioEncaminhadoMB() {
        /**
         * Definir as datas default
         */
        dataInicio = new Date();
        dataFinal = new Date();
        encaminhamentosList = new ArrayList<>();
    }

    /**
     * Funçoes Principais
     */
    public void listar() {
        try {
            encaminhamentosList = controler.listaEncaminhamentosDataDestino(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), dataInicio, DataUtil.setDataComHoraMaxima(dataFinal), destino, credor);
            if (encaminhamentosList.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma resultado encontrado!");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public void impressao() {
        try {
            if (encaminhamentosList.isEmpty()) {
                MenssagemUtil.addMessageInfo("Não hé nenhum registro para impressão");
            } else {
                String titulo = "Relatório de Processos encaminhados por período";
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(encaminhamentosList, m, "WEB-INF/relatorios/processo_encaminhado_periodo.jasper", titulo);
                RelatorioSession.setBytesRelatorioInSession(rel);
            }
        } catch (JRException e) {
            MenssagemUtil.addMessageErro("Erro ao gerar relatório", e, this.getClass().getName());
        }
    }

    public List<AreaAdministrativa> areas() {
        try {
            return areaController.listarPorAreaAdm(orgao);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao consultar area", ex, this.getClass().getName());
        }
        return null;
    }

    public List<Orgao> getListaOrgaos() {
        try {
            return orgaoControler.listarAtivos();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao consultar Orgãos", ex, this.getClass().getName());
        }
        return null;
    }

    /**
     * Getters and setters
     *
     */
    /**
     *
     * @return
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     *
     * @param dataInicio
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     *
     * @return
     */
    public Date getDataFinal() {
        return dataFinal;
    }

    /**
     *
     * @param dataFinal
     */
    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public AreaAdministrativa getDestino() {
        return destino;
    }

    public void setDestino(AreaAdministrativa destino) {
        this.destino = destino;
    }

    public List<Encaminhamento> getEncaminhamentosList() {
        return encaminhamentosList;
    }

    public void setEncaminhamentosList(List<Encaminhamento> encaminhamentosList) {
        this.encaminhamentosList = encaminhamentosList;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

}
