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
@Table(name = "grupo_natureza_despesa", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrupoNaturezaDespesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "gnd_id", nullable = false)
    private String id;
    @Length(max = 100)
    @Column(name = "gnd_nome", nullable = false, length = 100)
    private String nome;
    @Length(max = 2000)
    @Column(name = "gnd_especificacao", nullable = false, length = 2000)
    private String especificacao;

    public GrupoNaturezaDespesa() {
    }

    public GrupoNaturezaDespesa(String id) {
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
        if (!(object instanceof GrupoNaturezaDespesa)) {
            return false;
        }
        GrupoNaturezaDespesa other = (GrupoNaturezaDespesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.siafi.modelo.GrupoNaturezaDespesa[ id=" + id + " ]";
    }
}
