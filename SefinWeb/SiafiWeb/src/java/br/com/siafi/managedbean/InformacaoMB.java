/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.modelo.dto.DTOInformacao;
import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Mostrar as informações na tela de login relativas ao protocolo de processos
 * do SIAFI
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class InformacaoMB implements Serializable {

    @EJB
    private EncaminhamentoController encaminhamentoControler;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    private List<DTOInformacao> dtos;
    private Long encaminhamentosNaoRecebidos;
    private Long encaminhamentosEncaminhados;
    private Long totalProcessos;

    public InformacaoMB() {

    }

    @PostConstruct
    public void contar() {
        try {
            encaminhamentosNaoRecebidos = encaminhamentoControler.contarNRecebidos(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias());
            encaminhamentosEncaminhados = encaminhamentoControler.contarEncaminhados(usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), null, usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), null);
            ajustaDTO();
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e);
            dtos = new ArrayList<>();
            encaminhamentosNaoRecebidos = 0L;
            encaminhamentosEncaminhados = 0L;
        }
    }

    private void ajustaDTO() throws Exception {
        totalProcessos = 0L;
        dtos = solicitacaoFinanceiraControler.listarInformacao(usuarioMb.getUsuarioSelecionado().getAreaAdministrativa(), usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias());
        for (DTOInformacao i : dtos) {
            totalProcessos += i.getQuantidade();
        }
    }

    public Long getEncaminhamentosNaoRecebidos() {
        return encaminhamentosNaoRecebidos;
    }

    public void setEncaminhamentosNaoRecebidos(Long encaminhamentosNaoRecebidos) {
        this.encaminhamentosNaoRecebidos = encaminhamentosNaoRecebidos;
    }

    public List<DTOInformacao> getDtos() {
        return dtos;
    }

    public void setDtos(List<DTOInformacao> dtos) {
        this.dtos = dtos;
    }

    public Long getEncaminhamentosEncaminhados() {
        return encaminhamentosEncaminhados;
    }

    public void setEncaminhamentosEncaminhados(Long encaminhamentosEncaminhados) {
        this.encaminhamentosEncaminhados = encaminhamentosEncaminhados;
    }

    public Long getTotalProcessos() {
        return totalProcessos;
    }

    public void setTotalProcessos(Long totalProcessos) {
        this.totalProcessos = totalProcessos;
    }

}
