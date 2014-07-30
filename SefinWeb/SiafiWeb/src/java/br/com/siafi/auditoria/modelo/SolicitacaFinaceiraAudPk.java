/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.auditoria.modelo;

import br.com.sefin.auditoria.modelo.EntidadeRevisional;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author gilmario
 */
@Embeddable
public class SolicitacaFinaceiraAudPk implements Serializable {

    @Column(name = "sol_id", nullable = false, length = 10)
    private String id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "rev", nullable = false, referencedColumnName = "eti_id")
    private EntidadeRevisional entidadeRevisional;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntidadeRevisional getEntidadeRevisional() {
        return entidadeRevisional;
    }

    public void setEntidadeRevisional(EntidadeRevisional entidadeRevisional) {
        this.entidadeRevisional = entidadeRevisional;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.entidadeRevisional);
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
        final SolicitacaFinaceiraAudPk other = (SolicitacaFinaceiraAudPk) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.entidadeRevisional, other.entidadeRevisional)) {
            return false;
        }
        return true;
    }
}
