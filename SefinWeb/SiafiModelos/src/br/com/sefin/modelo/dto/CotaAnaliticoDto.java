/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.modelo.dto;

import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Arquivo do projeto SiafiModelos criando em 06/08/2013
 *
 * @author ari
 */
public class CotaAnaliticoDto implements Serializable {

    private List<SolicitacaoFinanceira> solicitacaoFinanceiras;
    private Cota cota;
    private BigDecimal saldoRelativo;
    private BigDecimal saldo;

    public List<SolicitacaoFinanceira> getSolicitacaoFinanceiras() {
        return solicitacaoFinanceiras;
    }

    public void setSolicitacaoFinanceiras(List<SolicitacaoFinanceira> solicitacaoFinanceiras) {
        this.solicitacaoFinanceiras = solicitacaoFinanceiras;
    }

    public Cota getCota() {
        return cota;
    }

    public void setCota(Cota cota) {
        this.cota = cota;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldoRelativo() {
        return saldoRelativo;
    }

    public void setSaldoRelativo(BigDecimal saldoRelativo) {
        this.saldoRelativo = saldoRelativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.cota);
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
        final CotaAnaliticoDto other = (CotaAnaliticoDto) obj;
        if (!Objects.equals(this.cota, other.cota)) {
            return false;
        }
        return true;
    }

    public void calculaSaldo() {
        this.saldo = saldoRelativo;
        for (SolicitacaoFinanceira financeira : getSolicitacaoFinanceiras()) {
            if (financeira.getValor() != BigDecimal.ZERO && financeira.getValor() != null) {
                saldo = saldo.subtract(financeira.getValor());

            }

        }
    }
}
