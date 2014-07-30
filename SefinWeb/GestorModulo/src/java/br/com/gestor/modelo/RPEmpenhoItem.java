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
 *
 * @author gilmario
 */
@Entity
@Table(name = "RPEMPENHOITEM", catalog = "", schema = "")
public class RPEmpenhoItem implements Serializable {

    @EmbeddedId
    private RPEmpenhoItemPK id;
    @Column(name = "RPIQUANTIDADE")
    private BigDecimal quantidade;
    @Column(name = "RPIVALORUNITARIO")
    private BigDecimal valorUnitario;
    @Column(name = "RPIVALORTOTALITEM")
    private BigDecimal valorTotalItem;

    public RPEmpenhoItem() {
    }

    public RPEmpenhoItemPK getId() {
        return id;
    }

    public void setId(RPEmpenhoItemPK id) {
        this.id = id;
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

    public BigDecimal getValorTotalItem() {
        return valorTotalItem;
    }

    public void setValorTotalItem(BigDecimal valorTotalItem) {
        this.valorTotalItem = valorTotalItem;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final RPEmpenhoItem other = (RPEmpenhoItem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
