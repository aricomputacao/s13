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

/**
 * Classe do Projeto SiafiModelos criada em 26/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "tipo_licitacao", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TipoLicitacao implements Serializable {

    @Id
    @Column(name = "tip_lic_id")
    private Integer id;
    @Column(name = "tip_lic_nome", nullable = false)
    private String nome;

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

    @Override
    public int hashCode() {
        int hash = 3;
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
        final TipoLicitacao other = (TipoLicitacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoLicitacao{" + "nome=" + nome + '}';
    }
}
