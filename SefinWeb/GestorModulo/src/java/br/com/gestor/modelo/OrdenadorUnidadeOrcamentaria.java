/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
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
@Table(name = "UND_ORC_ORD", schema = "", catalog = "")
public class OrdenadorUnidadeOrcamentaria implements Serializable {

    @EmbeddedId
    private OrdenadorUnidadeOrcamentariaPk id;
    @Column(name = "UOONOME")
    private String nome;
    @Column(name = "UOOFINAL")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    public OrdenadorUnidadeOrcamentaria() {
    }

    public OrdenadorUnidadeOrcamentariaPk getId() {
        return id;
    }

    public void setId(OrdenadorUnidadeOrcamentariaPk id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final OrdenadorUnidadeOrcamentaria other = (OrdenadorUnidadeOrcamentaria) obj;
        return Objects.equals(this.id, other.id);
    }

}
