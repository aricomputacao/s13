/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
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
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "aditivo_convenio", schema = "siafi", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cov_id", "adc_numero"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class AditivoConvenio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adc_id", nullable = false)
    private Integer id;
    @Length(min = 2, max = 2)
    @Column(name = "adc_numero", nullable = false, length = 2)
    private String numero;
    @JoinColumn(name = "cov_id", referencedColumnName = "cov_id", nullable = false)
    @ManyToOne
    private Convenio convenio;
    @Column(nullable = false, name = "adc_valor_repasse")
    private BigDecimal valorRepasse;
    @Temporal(TemporalType.DATE)
    @Column(name = "adc_data_final", nullable = false)
    private Date dataFinal;
    @Column(name = "adc_data_cadastro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public BigDecimal getValorRepasse() {
        return valorRepasse;
    }

    public void setValorRepasse(BigDecimal valorRepasse) {
        this.valorRepasse = valorRepasse;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public AditivoConvenio() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.numero);
        hash = 19 * hash + Objects.hashCode(this.convenio);
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
        final AditivoConvenio other = (AditivoConvenio) obj;
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.convenio, other.convenio)) {
            return false;
        }
        return true;
    }
}
