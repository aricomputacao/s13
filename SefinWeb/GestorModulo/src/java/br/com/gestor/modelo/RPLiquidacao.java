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
@Table(name = "RPLIQUIDACAO", catalog = "", schema = "")
public class RPLiquidacao implements Serializable {

    @Id
    @Column(name = "RPLCOD")
    private Integer codigo;
    @Column(name = "RPLDATA")
    @Temporal(TemporalType.DATE)
    private Date rplData;
    @Column(name = "RPLVALOR")
    private BigDecimal valor;
    @Column(name = "RPLTIPO")
    private String tipo;
    @Column(name = "RPLTIPONOTA")
    private String tipoNota;
    @Column(name = "RPLSUBTIPONOTA")
    private String subTipoNota;
    @Column(name = "RPLNUMERONOTA")
    private String numeroNota;
    @Column(name = "RPLSERIENOTA")
    private Integer serieNota;
    @Column(name = "RPLSERIESELO")
    private Integer serieSelo;
    @Column(name = "RPLNUMEROSELO")
    private String numeroSelo;
    @Column(name = "RPLNUMEROFORMULARIO")
    private String numeroFormulario;
    @Column(name = "RPLLIMITENOTA")
    @Temporal(TemporalType.DATE)
    private Date limiteNota;
    @Temporal(TemporalType.DATE)
    @Column(name = "RPLDATANOTA")
    private Date dataNota;
    @Column(name = "RPLRPEMPENHO")
    private Integer rpEmpenho;
    @Column(name = "RPLTIPOTCM")
    private String tipoTcm;
    @Column(name = "RPLSTATUS")
    private String status;
    @Column(name = "RPLEXPORTADO")
    private Boolean exportado;

    public RPLiquidacao() {
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }
    
    

    public RPLiquidacao(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getRplData() {
        return rplData;
    }

    public void setRplData(Date rplData) {
        this.rplData = rplData;
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

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public String getSubTipoNota() {
        return subTipoNota;
    }

    public void setSubTipoNota(String subTipoNota) {
        this.subTipoNota = subTipoNota;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public Integer getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(Integer serieNota) {
        this.serieNota = serieNota;
    }

    public Integer getSerieSelo() {
        return serieSelo;
    }

    public void setSerieSelo(Integer serieSelo) {
        this.serieSelo = serieSelo;
    }

    public String getNumeroSelo() {
        return numeroSelo;
    }

    public void setNumeroSelo(String numeroSelo) {
        this.numeroSelo = numeroSelo;
    }

    public String getNumeroFormulario() {
        return numeroFormulario;
    }

    public void setNumeroFormulario(String numeroFormulario) {
        this.numeroFormulario = numeroFormulario;
    }

    public Date getLimiteNota() {
        return limiteNota;
    }

    public void setLimiteNota(Date limiteNota) {
        this.limiteNota = limiteNota;
    }

    public Date getDataNota() {
        return dataNota;
    }

    public void setDataNota(Date dataNota) {
        this.dataNota = dataNota;
    }

    public Integer getRpEmpenho() {
        return rpEmpenho;
    }

    public void setRpEmpenho(Integer rpEmpenho) {
        this.rpEmpenho = rpEmpenho;
    }

    public String getTipoTcm() {
        return tipoTcm;
    }

    public void setTipoTcm(String tipoTcm) {
        this.tipoTcm = tipoTcm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.codigo);
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
        final RPLiquidacao other = (RPLiquidacao) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
}
