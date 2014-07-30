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
@Table(name = "SUBFUNCOES", catalog = "", schema = "")
public class SubFuncao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "SUBCODIGO", nullable = false)
    private String id;
    @Length(max = 100)
    @Column(name = "SUBNOME", nullable = false, length = 100)
    private String nome;
    @Length(max = 2000)
    @Column(name = "SUBUSO", length = 2000)
    private String uso;

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
        final SubFuncao other = (SubFuncao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SubFuncao{" + "id=" + id + ", nome=" + nome + ", uso=" + uso + '}';
    }
}
