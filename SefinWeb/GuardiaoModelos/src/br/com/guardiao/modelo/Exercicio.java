/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * Classe do Projeto guardiao criada em 16/04/2013
 *
 * @author allan
 */
@Entity
@Table(name = "exercicio", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class Exercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "exe_ano", nullable = false)
    private Integer ano;
    @Column(name = "exe_descricao", length = 128)
    private String descricao;
    @Column(name = "exe_data_inicial", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInicial;
    @Column(name = "exe_data_final", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataFinal;
    @Column(name = "exe_padrao")
    private boolean padrao;
    @Column(name = "exe_ativo", nullable = false)
    private boolean ativo;

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.ano);
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
        final Exercicio other = (Exercicio) obj;
        if (!Objects.equals(this.ano, other.ano)) {
            return false;
        }
        return true;
    }

    public Exercicio() {
    }

    public Exercicio(int ano) {
        this.ano = ano;
    }
}
