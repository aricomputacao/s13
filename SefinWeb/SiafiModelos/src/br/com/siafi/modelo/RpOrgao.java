/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Arquivo do projeto SiafiModelos criando em 17/10/2013
 * @author ari
 */
@Entity
@Table(name = "rp_orgao",schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RpOrgao implements Serializable{
    @EmbeddedId
    private RpOrgaoPk orgaoPk;
    
    
    @NotNull
    @Column(name = "rp_nome",nullable = false)
    private String nome;

    public RpOrgaoPk getOrgaoPk() {
        return orgaoPk;
    }

    public void setOrgaoPk(RpOrgaoPk orgaoPk) {
        this.orgaoPk = orgaoPk;
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
        hash = 79 * hash + Objects.hashCode(this.orgaoPk);
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
        final RpOrgao other = (RpOrgao) obj;
        if (!Objects.equals(this.orgaoPk, other.orgaoPk)) {
            return false;
        }
        return true;
    }
    
    

}
