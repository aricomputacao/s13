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
 * Classe do Projeto SiafiModelos criada em 25/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "licitacao", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Licitacao implements Serializable {

    @Id
    @Column(name = "lic_id", nullable = false)
    private Integer id;
    @Temporal(TemporalType.DATE)
    @Column(name = "lic_data", nullable = false)
    private Date data;
    @Column(name = "lic_numero", nullable = false, unique = true)
    private String numero;
    @Column(name = "lic_objetivo", length = 2000)
    private String objetivo;
    @Column(name = "lic_valor_orcado")
    private BigDecimal valorOrcado;
    @Column(name = "lic_valor_limite")
    private BigDecimal valorLimite;
    @Column(name = "lic_valor_original")
    private BigDecimal valorOriginal;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tip_lic_id", referencedColumnName = "tip_lic_id", nullable = false)
    private TipoLicitacao tipoLicitacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "mod_lic_id", referencedColumnName = "mod_lic_id", nullable = false)
    private ModalidadeLicitacao modalidadeLicitacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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

    public BigDecimal getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(BigDecimal valorLimite) {
        this.valorLimite = valorLimite;
    }

    public TipoLicitacao getTipoLicitacao() {
        return tipoLicitacao;
    }

    public void setTipoLicitacao(TipoLicitacao tipoLicitacao) {
        this.tipoLicitacao = tipoLicitacao;
    }

    public ModalidadeLicitacao getModalidadeLicitacao() {
        return modalidadeLicitacao;
    }

    public void setModalidadeLicitacao(ModalidadeLicitacao modalidadeLicitacao) {
        this.modalidadeLicitacao = modalidadeLicitacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
}
