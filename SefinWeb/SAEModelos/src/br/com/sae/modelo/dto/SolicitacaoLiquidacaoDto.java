/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sae.modelo.dto;

import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.Encaminhamento;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.CreditCardNumber;

/**
 *
 * @author ari
 */
public class SolicitacaoLiquidacaoDto implements Serializable {

    private SolicitacaoFinanceira solicitacaoFinanceira;
    private List<Encaminhamento> encaminhamentos;
    private List<EmpenhoDetalhe> empenhoDetalhes;
    private BigDecimal valorPago;
    private BigDecimal restoPagar;

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

}
