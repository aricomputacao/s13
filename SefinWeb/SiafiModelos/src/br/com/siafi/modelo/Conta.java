/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.NotAudited;

/**
 * Arquivo do projeto SiafiModelos criando em 29/07/2013
 *
 * @author ari
 */
@Entity
@Table(name = "conta", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conta implements Serializable {

    @Id
    @Column(name = "con_id", nullable = false)
    private Integer id;
    @Column(name = "con_nome", nullable = false)
    private String nome;
    @Column(name = "con_numero", nullable = false)
    private Integer numero;
    @Column(name = "con_nomeclatura")
    private String nomeclatura;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "uno_id", nullable = false, referencedColumnName = "uno_id"),
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    @NotAudited
    private UnidadeOrcamentaria unidadeOrcamentaria;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "for_id", referencedColumnName = "for_id", nullable = false)
    private FonteRecurso fonteRecurso;
    @Column(name = "con_saldo", nullable = false)
    private BigDecimal saldo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNomeclatura() {
        return nomeclatura;
    }

    public void setNomeclatura(String nomeclatura) {
        this.nomeclatura = nomeclatura;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public FonteRecurso getFonteRecurso() {
        return fonteRecurso;
    }

    public void setFonteRecurso(FonteRecurso fonteRecurso) {
        this.fonteRecurso = fonteRecurso;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final Conta other = (Conta) obj;
        return Objects.equals(this.id, other.id);
    }
}
