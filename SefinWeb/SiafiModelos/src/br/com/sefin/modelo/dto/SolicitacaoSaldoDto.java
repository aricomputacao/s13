/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.modelo.dto;

import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.Encaminhamento;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ari
 */
public class SolicitacaoSaldoDto implements Serializable {

    private SolicitacaoFinanceira solicitacaoFinanceira;
    private List<EmpenhoDetalhe> empenhoDetalhes;
    private Encaminhamento encaminhamento;
    private BigDecimal valorPago;
    private BigDecimal restoPagar;

    public SolicitacaoSaldoDto(){
        valorPago = BigDecimal.ZERO;
        restoPagar = BigDecimal.ZERO;
    }
    
    public BigDecimal getValorPago() {
        valorPago = BigDecimal.ZERO;
        for (EmpenhoDetalhe empDet : empenhoDetalhes) {
            if (empDet.getDataPagamento() != null) {
                valorPago = valorPago.add(empDet.getValor());
            }
        }
        return valorPago;
    }

    public BigDecimal getRestoPagar() {
        restoPagar = BigDecimal.ZERO;   
        restoPagar = solicitacaoFinanceira.getValor().subtract(valorPago);
        return restoPagar;
    }

    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
    }

    public List<EmpenhoDetalhe> getEmpenhoDetalhes() {
        return empenhoDetalhes;
    }

    public void setEmpenhoDetalhes(List<EmpenhoDetalhe> empenhoDetalhes) {
        this.empenhoDetalhes = empenhoDetalhes;
    }

    public Encaminhamento getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(Encaminhamento encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.solicitacaoFinanceira);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SolicitacaoSaldoDto other = (SolicitacaoSaldoDto) obj;
        if (!Objects.equals(this.solicitacaoFinanceira, other.solicitacaoFinanceira)) {
            return false;
        }
        return true;
    }

}
