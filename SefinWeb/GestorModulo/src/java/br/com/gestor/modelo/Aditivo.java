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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe do Projeto Siafi - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "ADITIVOCONTRATO", catalog = "", schema = "")
public class Aditivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private AditivoPK aditivoPK;
    @Column(name = "ADCVALOR")
    private BigDecimal valor;
    @Temporal(TemporalType.DATE)
    @Column(name = "ADCASSINATURA")
    private Date assinatura;
    @Temporal(TemporalType.DATE)
    @Column(name = "ADCPUBLICACAO")
    private Date publicacao;
    @Temporal(TemporalType.DATE)
    @Column(name = "ADCINICIO")
    private Date inicio;
    @Temporal(TemporalType.DATE)
    @Column(name = "ADCFINAL")
    private Date fim;
    @Column(name = "ADCOBJETO", length = 2000)
    private String objetivo;
    @Column(name = "ADCMODALIDADE")
    private String modalidade;
    @Column(name = "ADCEXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public Aditivo() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.aditivoPK);
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
        final Aditivo other = (Aditivo) obj;
        return Objects.equals(this.aditivoPK, other.aditivoPK);
    }

    public AditivoPK getAditivoPK() {
        return aditivoPK;
    }

    public void setAditivoPK(AditivoPK aditivoPK) {
        this.aditivoPK = aditivoPK;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Date assinatura) {
        this.assinatura = assinatura;
    }

    public Date getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Date publicacao) {
        this.publicacao = publicacao;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    @Override
    public String toString() {
        return "Aditivo{" + "aditivoPK=" + aditivoPK + ", valor=" + valor + ", assinatura=" + assinatura + ", publicacao=" + publicacao + ", inicio=" + inicio + ", fim=" + fim + ", objetivo=" + objetivo + ", exportado=" + exportado + '}';
    }
}
