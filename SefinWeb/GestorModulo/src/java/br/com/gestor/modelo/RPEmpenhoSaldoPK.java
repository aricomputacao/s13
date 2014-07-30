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
public class RPEmpenhoSaldoPK implements Serializable {

    @Column(name = "RPSRPEMPENHO")
    private Integer rpEmpenho;
    @Column(name = "RPSTIPO")
    private String tipo;

    public RPEmpenhoSaldoPK() {
    }

    public Integer getRpEmpenho() {
        return rpEmpenho;
    }

    public void setRpEmpenho(Integer rpEmpenho) {
        this.rpEmpenho = rpEmpenho;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.rpEmpenho);
        hash = 53 * hash + Objects.hashCode(this.tipo);
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
        final RPEmpenhoSaldoPK other = (RPEmpenhoSaldoPK) obj;
        if (!Objects.equals(this.rpEmpenho, other.rpEmpenho)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        return true;
    }
}
