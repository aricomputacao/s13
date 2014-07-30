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
public class RPProjetoAtividadePK implements Serializable {

    @Column(name = "RPPORGAO")
    private String orgao;
    @Column(name = "RPPUNIDADE")
    private String unidade;
    @Column(name = "RPPFUNCAO")
    private String funcao;
    @Column(name = "RPPSUBFUNCAO")
    private String subFuncao;
    @Column(name = "RPPPROGRAMA")
    private String programa;
    @Column(name = "RPPCOD")
    private String codigo;
    @Column(name = "RPPEXERCICIO")
    private Integer exercicio;

    public RPProjetoAtividadePK() {
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSubFuncao() {
        return subFuncao;
    }

    public void setSubFuncao(String subFuncao) {
        this.subFuncao = subFuncao;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

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
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.orgao);
        hash = 29 * hash + Objects.hashCode(this.unidade);
        hash = 29 * hash + Objects.hashCode(this.funcao);
        hash = 29 * hash + Objects.hashCode(this.subFuncao);
        hash = 29 * hash + Objects.hashCode(this.programa);
        hash = 29 * hash + Objects.hashCode(this.codigo);
        hash = 29 * hash + Objects.hashCode(this.exercicio);
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
        final RPProjetoAtividadePK other = (RPProjetoAtividadePK) obj;
        if (!Objects.equals(this.orgao, other.orgao)) {
            return false;
        }
        if (!Objects.equals(this.unidade, other.unidade)) {
            return false;
        }
        if (!Objects.equals(this.funcao, other.funcao)) {
            return false;
        }
        if (!Objects.equals(this.subFuncao, other.subFuncao)) {
            return false;
        }
        if (!Objects.equals(this.programa, other.programa)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.exercicio, other.exercicio)) {
            return false;
        }
        return true;
    }
}
