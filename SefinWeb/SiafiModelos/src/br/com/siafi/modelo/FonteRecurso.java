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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto Siafi - Criado em 11/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "fonte_recurso", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FonteRecurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "for_id")
    private String id;
    @Column(name = "for_nome")
    private String nome;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tfr_id", referencedColumnName = "tfr_id", nullable = false)
    private TipoFonteRecurso tipoFonteRecurso;

    public FonteRecurso() {
    }

    public FonteRecurso(String id) {
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

    public TipoFonteRecurso getTipoFonteRecurso() {
        return tipoFonteRecurso;
    }

    public void setTipoFonteRecurso(TipoFonteRecurso tipoFonteRecurso) {
        this.tipoFonteRecurso = tipoFonteRecurso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final FonteRecurso other = (FonteRecurso) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "FonteRecurso{" + "id=" + id + ", nome=" + nome + '}';
    }
}
