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
 * Classe do Projeto ******* - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Embeddable
public class AditivoPK implements Serializable {

    @Column(name = "ADCADITIVO")
    private String id;
    @Column(name = "ADCCONTRATO")
    private Integer idContrato;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.idContrato);
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
        final AditivoPK other = (AditivoPK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.idContrato, other.idContrato)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AditivoPK{" + "id=" + id + ", idContrato=" + idContrato + '}';
    }
}
