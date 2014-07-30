/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "RPPAGAMENTO", catalog = "", schema = "")
public class RPPagamento implements Serializable {

    @Id
    @Column(name = "RPGCOD")
    private Integer codigo;
    @Column(name = "RPGRPEMPENHO")
    private Integer rpEmpenho;
    @Column(name = "RPGDATA")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    @Column(name = "RPGDOC")
    private String doc;
    @Column(name = "RPGVALOR")
    private BigDecimal valor;
    @Column(name = "RPGHISTORICO")
    private String historico;
    @Column(name = "RPGSUBEMPENHO")
    private String subEmpenho;
    @Column(name = "RPGSTATUS")
    private String status;
    @Column(name = "RPGSTATUSMSG")
    private String statusMsg;
    @Column(name = "RPGTIPO")
    private String tipo;

    public RPPagamento() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getRpEmpenho() {
        return rpEmpenho;
    }

    public void setRpEmpenho(Integer rpEmpenho) {
        this.rpEmpenho = rpEmpenho;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getSubEmpenho() {
        return subEmpenho;
    }

    public void setSubEmpenho(String subEmpenho) {
        this.subEmpenho = subEmpenho;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        final RPPagamento other = (RPPagamento) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
}
