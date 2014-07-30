/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "PROGRAMAS", catalog = "", schema = "")
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PROCODIGO", nullable = false)
    private String id;
    @Length(max = 100)
    @Column(name = "PRONOME", nullable = false, length = 100)
    private String nome;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
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
        final Programa other = (Programa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Programa{" + "id=" + id + ", nome=" + nome + '}';
    }
}
