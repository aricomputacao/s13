/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gilmario
 */
@Embeddable
public class RPEmpenhoAnulacaoPK implements Serializable {

    @Column(name = "RPARPEMPENHO")
    private Integer RPEmpenho;
    @Column(name = "RPADATA")
    @Temporal(TemporalType.DATE)
    private Date rpaData;
    @Column(name = "RPATIPOCANCELAMENTO")
    private String tipoCancelamento;
    @Column(name = "RPATIPOSALDO")
    private String tipoSaldo;

    public RPEmpenhoAnulacaoPK() {
    }

    public Integer getRPEmpenho() {
        return RPEmpenho;
    }

    public void setRPEmpenho(Integer RPEmpenho) {
        this.RPEmpenho = RPEmpenho;
    }

    public Date getRpaData() {
        return rpaData;
    }

    public void setRpaData(Date rpaData) {
        this.rpaData = rpaData;
    }

    public String getTipoCancelamento() {
        return tipoCancelamento;
    }

    public void setTipoCancelamento(String tipoCancelamento) {
        this.tipoCancelamento = tipoCancelamento;
    }

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.RPEmpenho);
        hash = 41 * hash + Objects.hashCode(this.rpaData);
        hash = 41 * hash + Objects.hashCode(this.tipoCancelamento);
        hash = 41 * hash + Objects.hashCode(this.tipoSaldo);
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
        final RPEmpenhoAnulacaoPK other = (RPEmpenhoAnulacaoPK) obj;
        if (!Objects.equals(this.RPEmpenho, other.RPEmpenho)) {
            return false;
        }
        if (!Objects.equals(this.rpaData, other.rpaData)) {
            return false;
        }
        if (!Objects.equals(this.tipoCancelamento, other.tipoCancelamento)) {
            return false;
        }
        if (!Objects.equals(this.tipoSaldo, other.tipoSaldo)) {
            return false;
        }
        return true;
    }
}
