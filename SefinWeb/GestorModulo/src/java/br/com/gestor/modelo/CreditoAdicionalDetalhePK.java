/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author gilmario
 */
@Embeddable
public class CreditoAdicionalDetalhePK implements Serializable {

    @Column(name = "CADCOD")
    private Integer codigo;
    @Column(name = "CADCREDITOADICIONAL")
    private Integer creditoAdicional;

    public CreditoAdicionalDetalhePK() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCreditoAdicional() {
        return creditoAdicional;
    }

    public void setCreditoAdicional(Integer creditoAdicional) {
        this.creditoAdicional = creditoAdicional;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.codigo);
        hash = 79 * hash + Objects.hashCode(this.creditoAdicional);
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
        final CreditoAdicionalDetalhePK other = (CreditoAdicionalDetalhePK) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.creditoAdicional, other.creditoAdicional)) {
            return false;
        }
        return true;
    }
}
