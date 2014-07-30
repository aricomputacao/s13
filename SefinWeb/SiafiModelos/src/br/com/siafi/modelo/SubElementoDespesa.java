/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto Siafi - Criado em 06/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "sub_elemento_despesa", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubElementoDespesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Length(max = 2, min = 2)
    @Column(name = "sed_id", nullable = false)
    private String id;
    @Length(max = 100)
    @Column(name = "sed_nome", nullable = false, length = 100)
    private String nome;
    @Length(max = 2000)
    @Column(name = "sed_especificacao", length = 2000)
    private String especificacao;

    public SubElementoDespesa() {
    }

    public SubElementoDespesa(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubElementoDespesa)) {
            return false;
        }
        SubElementoDespesa other = (SubElementoDespesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.siafi.modelo.SubElementoDespesa[ id=" + id + " ]";
    }
}
