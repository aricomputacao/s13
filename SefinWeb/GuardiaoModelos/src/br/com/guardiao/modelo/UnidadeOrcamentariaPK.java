/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe do Projeto guardiao criada em 20/03/2013 - Classe representa a chave
 * primario composta da tabela Unidade Orçamentaria;
 *
 * @author: ari
 */
@Embeddable
public class UnidadeOrcamentariaPK implements Serializable {

    @Column(name = "uno_id", nullable = false, length = 2)
    private String id;
    @Column(nullable = false, name = "org_id", length = 2)
    private String idOrgao;
    @Column(nullable = false, name = "exe_ano")
    private Integer exercicio_ano;

    public UnidadeOrcamentariaPK() {
    }

    public UnidadeOrcamentariaPK(String id, String idOrgao, Integer exercicio_ano) {
        this.id = id;
        this.idOrgao = idOrgao;
        this.exercicio_ano = exercicio_ano;
    }

    public Integer getExercicio_ano() {
        return exercicio_ano;
    }

    public void setExercicio_ano(Integer exercicio_ano) {
        this.exercicio_ano = exercicio_ano;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.idOrgao);
        hash = 29 * hash + Objects.hashCode(this.exercicio_ano);
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
        final UnidadeOrcamentariaPK other = (UnidadeOrcamentariaPK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.idOrgao, other.idOrgao)) {
            return false;
        }
        return Objects.equals(this.exercicio_ano, other.exercicio_ano);
    }

    public String getIdOrgao() {
        return idOrgao;
    }

    public void setIdOrgao(String idOrgao) {
        this.idOrgao = idOrgao;
    }

    @Override
    public String toString() {
        return idOrgao + id;
    }

}
