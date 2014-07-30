/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.modelo.dto;

import br.com.siafi.modelo.CreditoAdicionalDetalhe;
import br.com.siafi.modelo.Dotacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Arquivo do projeto SiafiModelos criando em 19/09/2013
 *
 * @author ari
 */
public class DotacaoDto implements Serializable {

    private Dotacao dotacao;
    private List<CreditoAdicionalDetalhe> adicionalDetalhes;
    private BigDecimal valorUtilizado;
    private BigDecimal saldo;

    //CADTIPO (S - SUPLEMENTAÇÃO,X - ESPECIAL, E - EXTRAORDINÁRIO, A - ANULAÇÃO)
    public BigDecimal calcularSaldo() {
        saldo = dotacao.getValor();
        for (CreditoAdicionalDetalhe creditoAdicionalDetalhe : adicionalDetalhes) {
            if (creditoAdicionalDetalhe.getTipo().equals("S") || creditoAdicionalDetalhe.getTipo().equals("X") || creditoAdicionalDetalhe.getTipo().equals("E")) {
                setSaldo(saldo.add(creditoAdicionalDetalhe.getValor()));
            } else {
                setSaldo(saldo.subtract(creditoAdicionalDetalhe.getValor()));
            }
        }
        if (valorUtilizado == BigDecimal.ZERO || valorUtilizado == null) {
            return saldo;
        } else {
            return saldo.subtract(valorUtilizado);

        }
    }

    public DotacaoDto() {
        this.adicionalDetalhes = new ArrayList<>();
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Dotacao getDotacao() {
        return dotacao;
    }

    public void setDotacao(Dotacao dotacao) {
        this.dotacao = dotacao;
    }

    public BigDecimal getValorUtilizado() {
        return valorUtilizado;
    }

    public void setValorUtilizado(BigDecimal valorUtilizado) {
        this.valorUtilizado = valorUtilizado;
    }

    public BigDecimal getSaldo() {
        return calcularSaldo();
    }

    public List<CreditoAdicionalDetalhe> getAdicionalDetalhes() {
        return adicionalDetalhes;
    }

    public void setAdicionalDetalhes(List<CreditoAdicionalDetalhe> adicionalDetalhes) {
        this.adicionalDetalhes = adicionalDetalhes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.dotacao);
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
        final DotacaoDto other = (DotacaoDto) obj;
        return Objects.equals(this.dotacao, other.dotacao);
    }
}
