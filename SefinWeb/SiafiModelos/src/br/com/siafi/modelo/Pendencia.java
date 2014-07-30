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
 * Arquivo do projeto SiafiModelos criando em 25/07/2013
 * @author ari
 */
@Entity
@Table(name = "pendencia",schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Pendencia implements Serializable{

    @Id
    @Column( name = "pen_id",nullable = false)
    private String documento;
    @Column(name = "pen_credor_nome",nullable = false,length = 1024)
    private String credorNome;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCredorNome() {
        return credorNome;
    }

    public void setCredorNome(String credorNome) {
        this.credorNome = credorNome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.documento);
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
        final Pendencia other = (Pendencia) obj;
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pendencia{" + "documento=" + documento + ", credorNome=" + credorNome + '}';
    }
    
    
}
