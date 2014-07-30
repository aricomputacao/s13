/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
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
@Table(name = "RPORGAO", catalog = "", schema = "")
public class RPOrgao implements Serializable {

    @EmbeddedId
    private RPOrgaoPK id;
    @Column(name = "RPONOME")
    private String nome;
    @Column(name = "RPOEXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public RPOrgao() {
    }

    public RPOrgaoPK getId() {
        return id;
    }

    public void setId(RPOrgaoPK id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final RPOrgao other = (RPOrgao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
