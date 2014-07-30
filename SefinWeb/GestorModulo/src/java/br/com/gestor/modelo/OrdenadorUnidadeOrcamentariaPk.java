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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gilmario
 */
@Embeddable
public class OrdenadorUnidadeOrcamentariaPk implements Serializable {

    @Column(name = "UOOORGAO")
    private String idorgao;
    @Column(name = "UOOUNIDADE")
    private String idunidade;
    @Column(name = "UOOPESSOA")
    private Integer pessoa;
    @Temporal(TemporalType.DATE)
    @Column(name = "UOOINICIO")
    private Date inicio;

    public String getIdorgao() {
        return idorgao;
    }

    public void setIdorgao(String idorgao) {
        this.idorgao = idorgao;
    }

    public String getIdunidade() {
        return idunidade;
    }

    public void setIdunidade(String idunidade) {
        this.idunidade = idunidade;
    }

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.idorgao);
        hash = 23 * hash + Objects.hashCode(this.idunidade);
        hash = 23 * hash + Objects.hashCode(this.pessoa);
        hash = 23 * hash + Objects.hashCode(this.inicio);
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
        final OrdenadorUnidadeOrcamentariaPk other = (OrdenadorUnidadeOrcamentariaPk) obj;
        if (!Objects.equals(this.idorgao, other.idorgao)) {
            return false;
        }
        if (!Objects.equals(this.idunidade, other.idunidade)) {
            return false;
        }
        if (!Objects.equals(this.pessoa, other.pessoa)) {
            return false;
        }
        return Objects.equals(this.inicio, other.inicio);
    }

}
