/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "credito_adicional", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditoAdicional implements Serializable {

    @Id
    @Column(nullable = false, name = "cad_id")
    private Integer id;
    @Column(name = "cad_lei")
    private Integer lei;
    @Column(name = "cad_numero_decreto")
    private String numeroDecreto;
    @Column(name = "cad_data_decreto")
    @Temporal(TemporalType.DATE)
    private Date dataDecreto;
    @Column(name = "cad_status")
    private String status;

    public CreditoAdicional() {
    }

    public CreditoAdicional(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLei() {
        return lei;
    }

    public void setLei(Integer lei) {
        this.lei = lei;
    }

    public String getNumeroDecreto() {
        return numeroDecreto;
    }

    public void setNumeroDecreto(String numeroDecreto) {
        this.numeroDecreto = numeroDecreto;
    }

    public Date getDataDecreto() {
        return dataDecreto;
    }

    public void setDataDecreto(Date dataDecreto) {
        this.dataDecreto = dataDecreto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final CreditoAdicional other = (CreditoAdicional) obj;
        return Objects.equals(this.id, other.id);
    }
}
