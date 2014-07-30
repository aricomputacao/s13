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
 * Classe do Projeto SiafiModelos criada em 25/06/2013
 * 
 * @author: ari
 */
@Entity
@Table(name="tipo_credor" ,schema="siafi")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TipoCredor implements Serializable{

    @Id
    @Column(name="tip_cre_id",nullable=false)
    private Integer id;
    @Column(name="tip_cre_nome",nullable=false)
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
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final TipoCredor other = (TipoCredor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "TipoCredor{" + "nome=" + nome + '}';
    }

    
    
}
