/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

/**
 * Arquivo do projeto SiafiModelos criando em 25/10/2013
 *
 * @author ari
 */
@Embeddable
public class DotacalPk implements Serializable {

    @Column(name = "dot_id", nullable = false)
    private String id;
    @Column(name = "dot_id_reduzido")
    private String id_reduzido;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_reduzido() {
        return id_reduzido;
    }

    public void setId_reduzido(String id_reduzido) {
        this.id_reduzido = id_reduzido;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.id_reduzido);
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
        final DotacalPk other = (DotacalPk) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.id_reduzido, other.id_reduzido)) {
            return false;
        }
        return true;
    }
    
}
