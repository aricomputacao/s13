/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto ******* - Criado em 06/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "subfuncao", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subfuncao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Length(min = 3, max = 3)
    @Column(name = "sub_id", nullable = false, length = 3)
    private String id;
    @Length(min = 1, max = 80)
    @Column(name = "sub_nome", nullable = false, length = 80)
    private String nome;
    @Length(max = 2000)
    @Column(name = "sub_uso", length = 2000)
    private String uso;

    public Subfuncao() {
    }

    public Subfuncao(String id) {
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

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Subfuncao other = (Subfuncao) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Subfuncao{" + "codigo=" + id + ", nome=" + nome + '}';
    }
}
