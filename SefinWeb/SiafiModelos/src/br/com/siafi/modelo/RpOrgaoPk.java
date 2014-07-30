/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

/**
 * Arquivo do projeto SiafiModelos criando em 17/10/2013
 *
 * @author ari
 */
@Embeddable
public class RpOrgaoPk implements Serializable {

    @NotNull
    @Column(name = "rp_codigo",nullable = false)
    private String codigo;
    @NotNull
    @Column(name = "rp_exercicio",nullable = false)
    private Integer exercicio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.codigo);
        hash = 97 * hash + Objects.hashCode(this.exercicio);
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
        final RpOrgaoPk other = (RpOrgaoPk) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.exercicio, other.exercicio)) {
            return false;
        }
        return true;
    }
    
}
