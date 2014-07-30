/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "RPEMPENHOSALDO", catalog = "", schema = "")
public class RPEmpenhoSaldo implements Serializable {

    @EmbeddedId
    private RPEmpenhoSaldoPK id;
    @Column(name = "RPSDATA")
    @Temporal(TemporalType.DATE)
    private Date dataSaldo;
    @Column(name = "RPSVALOR")
    private BigDecimal valor;

    public RPEmpenhoSaldo() {
    }

    public RPEmpenhoSaldoPK getId() {
        return id;
    }

    public void setId(RPEmpenhoSaldoPK id) {
        this.id = id;
    }

    public Date getDataSaldo() {
        return dataSaldo;
    }

    public void setDataSaldo(Date dataSaldo) {
        this.dataSaldo = dataSaldo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final RPEmpenhoSaldo other = (RPEmpenhoSaldo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
