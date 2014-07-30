/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "CREDITOADICIONAL", catalog = "", schema = "")
public class CreditoAdicional implements Serializable {

    @Id
    @Column(name = "CRACOD")
    private Integer codigo;
    @Column(name = "CRALEI")
    private Integer lei;
    @Column(name = "CRANUMERODECRETO")
    private String numeroDecreto;
    @Column(name = "CRADATADECRETO")
    @Temporal(TemporalType.DATE)
    private Date dataDecreto;
    @Column(name = "CRASTATUS")
    private String status;
    @Column(name = "CRASTATUSMSG")
    private String statusMsg;
    @Column(name = "CRAEXPORTADO")
    private Boolean exportado;

    public CreditoAdicional() {
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Boolean isExportado() {
        return exportado;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.codigo);
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
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
}
