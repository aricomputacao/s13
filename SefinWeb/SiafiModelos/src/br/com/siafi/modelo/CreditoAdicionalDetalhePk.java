/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author gilmario
 */
@Embeddable
public class CreditoAdicionalDetalhePk implements Serializable {

    @Column(name = "cdd_id", nullable = false)
    private Integer codigo;
    @Column(name = "cdd_credito_adicional_id", nullable = false)
    private Integer creditoAdicionalId;

    public CreditoAdicionalDetalhePk() {
    }

    public CreditoAdicionalDetalhePk(Integer codigo, Integer creditoAdicionalId) {
        this.codigo = codigo;
        this.creditoAdicionalId = creditoAdicionalId;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCreditoAdicionalId() {
        return creditoAdicionalId;
    }

    public void setCreditoAdicionalId(Integer creditoAdicionalId) {
        this.creditoAdicionalId = creditoAdicionalId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigo);
        hash = 37 * hash + Objects.hashCode(this.creditoAdicionalId);
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
        final CreditoAdicionalDetalhePk other = (CreditoAdicionalDetalhePk) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.creditoAdicionalId, other.creditoAdicionalId)) {
            return false;
        }
        return true;
    }
}
