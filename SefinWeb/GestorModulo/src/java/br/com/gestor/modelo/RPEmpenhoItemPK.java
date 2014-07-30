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
public class RPEmpenhoItemPK implements Serializable {

    @Column(name = "RPIRPEMPENHO")
    private Integer rpEmpenho;
    @Column(name = "RPIITEM")
    private Integer item;

    public RPEmpenhoItemPK() {
    }

    public Integer getRpEmpenho() {
        return rpEmpenho;
    }

    public void setRpEmpenho(Integer rpEmpenho) {
        this.rpEmpenho = rpEmpenho;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.rpEmpenho);
        hash = 29 * hash + Objects.hashCode(this.item);
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
        final RPEmpenhoItemPK other = (RPEmpenhoItemPK) obj;
        if (!Objects.equals(this.rpEmpenho, other.rpEmpenho)) {
            return false;
        }
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        return true;
    }
}
