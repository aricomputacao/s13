/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.modelo.dto;

import br.com.sefin.enumerated.SituacaoSolicitacao;
import java.io.Serializable;

/**
 *
 * @author gilmario
 */
public class DTOInformacao implements Serializable {

    private Long quantidade;
    private SituacaoSolicitacao situacaoSolicitacao;

    public DTOInformacao(SituacaoSolicitacao situacaoSolicitacao, Long quantidade) {
        this.situacaoSolicitacao = situacaoSolicitacao;
        this.quantidade = quantidade;
    }

    public SituacaoSolicitacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

}
