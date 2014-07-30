/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Arquivo do projeto SiafiModelos criando em 16/10/2013
 *
 * @author ari
 */
@Embeddable
public class RpUnidadeOrcamentariaPk implements Serializable {

    @NotNull
    @Column(name = "rp_codigo", nullable = false)
    private String orgao;
    @NotNull
    @Column(name = "rp_exercicio", nullable = false)
    private Integer exercicio;
    @NotNull
    @Column(name = "rp_undidade_id", nullable = false, length = 2)
    private String id;

    public RpUnidadeOrcamentariaPk() {
    }

    public RpUnidadeOrcamentariaPk(String orgao, Integer exercicio, String id) {
        this.orgao = orgao;
        this.exercicio = exercicio;
        this.id = id;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.orgao);
        hash = 31 * hash + Objects.hashCode(this.exercicio);
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
        final RpUnidadeOrcamentariaPk other = (RpUnidadeOrcamentariaPk) obj;
        if (!Objects.equals(this.orgao, other.orgao)) {
            return false;
        }
        if (!Objects.equals(this.exercicio, other.exercicio)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
