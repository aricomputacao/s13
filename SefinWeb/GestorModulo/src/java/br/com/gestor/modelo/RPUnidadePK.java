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
public class RPUnidadePK implements Serializable {

    @Column(name = "RPUORGAO")
    private String orgao;
    @Column(name = "RPUUNIDADE")
    private String unidade;
    @Column(name = "RPUEXERCICIO")
    private Integer exercicio;

    public RPUnidadePK() {
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.orgao);
        hash = 53 * hash + Objects.hashCode(this.unidade);
        hash = 53 * hash + Objects.hashCode(this.exercicio);
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
        final RPUnidadePK other = (RPUnidadePK) obj;
        if (!Objects.equals(this.orgao, other.orgao)) {
            return false;
        }
        if (!Objects.equals(this.unidade, other.unidade)) {
            return false;
        }
        if (!Objects.equals(this.exercicio, other.exercicio)) {
            return false;
        }
        return true;
    }
}
