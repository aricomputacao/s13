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
 * Classe do Projeto SIAFI - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "LICITACAO", catalog = "", schema = "")
public class Licitacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "LICCOD")
    private Integer id;
    @Column(name = "LICDATA")
    @Temporal(TemporalType.DATE)
    private Date dataLicitacao;
    @Column(name = "LICNUMERO")
    private String numero;
    @Column(name = "LICOBJETO")
    private String objetivo;
    @Column(name = "LICVALORORCADO")
    private BigDecimal valorOrcado;
    @Column(name = "LICVALORLIMITE")
    private BigDecimal valorLimit;
    @Column(name = "LICTIPO")
    private String tipoLicitacao;
    @Column(name = "LICMODALIDADE")
    private String modalidade;
    @Column(name = "LICEXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public Licitacao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataLicitacao() {
        return dataLicitacao;
    }

    public void setDataLicitacao(Date dataLicitacao) {
        this.dataLicitacao = dataLicitacao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public BigDecimal getValorOrcado() {
        return valorOrcado;
    }

    public void setValorOrcado(BigDecimal valorOrcado) {
        this.valorOrcado = valorOrcado;
    }

    public BigDecimal getValorLimit() {
        return valorLimit;
    }

    public void setValorLimit(BigDecimal valorLimit) {
        this.valorLimit = valorLimit;
    }

    public String getTipoLicitacao() {
        return tipoLicitacao;
    }

    public void setTipoLicitacao(String tipoLicitacao) {
        this.tipoLicitacao = tipoLicitacao;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
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
        final Licitacao other = (Licitacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Licitacao{" + "id=" + id + ", dataLicitacao=" + dataLicitacao + ", numero=" + numero + ", objetivo=" + objetivo + ", valorOrcado=" + valorOrcado + ", valorLimit=" + valorLimit + ", tipoLicitacao=" + tipoLicitacao + ", modalidade=" + modalidade + ", exportado=" + exportado + '}';
    }
}
