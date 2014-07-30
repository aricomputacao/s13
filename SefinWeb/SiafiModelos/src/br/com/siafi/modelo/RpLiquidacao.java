/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rp_liquidacao", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RpLiquidacao implements Serializable {

    @Id
    @Column(name = "rpl_id")
    private Integer codigo;
    @Column(name = "rpl_dataLiquidacao")
    @Temporal(TemporalType.DATE)
    private Date dataLiquidacao;
    @Column(name = "rpl_valor")
    private BigDecimal valor;
    @Column(name = "rpl_tipo")
    private String tipo;
    @Column(name = "rpl_numero_nota")
    private String numeroNota;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "rem_id", referencedColumnName = "rem_id", nullable = false)
    private RpEmpenho rpEmpenho;
    @Column(name = "rpl_status")
    private String status;
    @Column(name = "rpl_liberado", nullable = false)
    private Boolean liberado;

    public RpLiquidacao() {
    }

    public RpLiquidacao(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getDataLiquidacao() {
        return dataLiquidacao;
    }

    public void setDataLiquidacao(Date dataLiquidacao) {
        this.dataLiquidacao = dataLiquidacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public RpEmpenho getRpEmpenho() {
        return rpEmpenho;
    }

    public void setRpEmpenho(RpEmpenho rpEmpenho) {
        this.rpEmpenho = rpEmpenho;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getLiberado() {
        return liberado;
    }

    public void setLiberado(Boolean liberado) {
        this.liberado = liberado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final RpLiquidacao other = (RpLiquidacao) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
}
