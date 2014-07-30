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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto SiafiModelos criada em 25/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "aditivo", schema = "siafi", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"adi_cod", "con_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Aditivo implements Serializable {

    @Id
    @Column(name = "adi_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "adi_cod")
    private String codigo;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "con_id", nullable = false, referencedColumnName = "con_id")
    private Contrato contrato;
    @Column(name = "adi_valor", nullable = false)
    private BigDecimal valor;
    @Column(name = "adi_data_publicacao")
    @Temporal(TemporalType.DATE)
    private Date dataPublicacao;
    @Column(name = "adi_data_inicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Column(name = "adi_data_fim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @Column(name = "adi_objetivo", length = 2000)
    private String objetivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
