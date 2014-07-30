/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Sistema Siafi
 * @Data 17/07/2013
 * @author gilmario
 */
@Entity
@Table(name = "FEEMPITE", catalog = "", schema = "")
public class EmpenhoSolicitacaoItem implements Serializable {

    @EmbeddedId
    private EmpenhoSolicitacaoItemPk solicitacaoItemPk;
    @Column(name = "ITE_QUANTIDADE")
    private BigDecimal quantidade;
    @Column(name = "ITE_PRECO")
    private BigDecimal valorUnitario;
    @Column(name = "ITE_TOTAL")
    private BigDecimal valorTotal;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.solicitacaoItemPk);
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
        final EmpenhoSolicitacaoItem other = (EmpenhoSolicitacaoItem) obj;
        if (!Objects.equals(this.solicitacaoItemPk, other.solicitacaoItemPk)) {
            return false;
        }
        return true;
    }

    public EmpenhoSolicitacaoItemPk getSolicitacaoItemPk() {
        return solicitacaoItemPk;
    }

    public void setSolicitacaoItemPk(EmpenhoSolicitacaoItemPk solicitacaoItemPk) {
        this.solicitacaoItemPk = solicitacaoItemPk;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "EmpenhoSolicitacaoItem{" + "solicitacaoItemPk=" + solicitacaoItemPk + ", quantidade=" + quantidade + ", valorUnitario=" + valorUnitario + ", valorTotal=" + valorTotal + '}';
    }
}
