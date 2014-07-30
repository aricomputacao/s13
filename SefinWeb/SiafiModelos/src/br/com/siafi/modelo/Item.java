/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto SiafiModelos criada em 25/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "item", schema = "siafi", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"grp_ite_id", "ite_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item implements Serializable {

    @Id
    @Column(name = "ite_id", nullable = false)
    private Long id;
    @Column(name = "ite_nome", nullable = false)
    private String nome;
    @Column(name = "ite_especificacao", length = 2000)
    private String especificacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "und_med_id", nullable = false, referencedColumnName = "und_med_id")
    private UnidadeMedida unidadeMedida;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "grp_ite_id", nullable = false, referencedColumnName = "grp_ite_id")
    private GrupoItem grupoItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public GrupoItem getGrupoItem() {
        return grupoItem;
    }

    public void setGrupoItem(GrupoItem grupoItem) {
        this.grupoItem = grupoItem;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Item other = (Item) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", nome=" + nome + '}';
    }
}
