/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.Exercicio;
import br.com.sefin.enumerated.Mes;
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
import org.hibernate.envers.Audited;

/**
 * @Sistema SiafiModelos
 * @Data 05/08/2013
 * @author gilmario
 */
@Entity
@Table(name = "cota_controle", schema = "siafi", uniqueConstraints =
        @UniqueConstraint(columnNames = {"cco_competencia", "exe_ano", "cot_id"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class CotaControle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cco_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cot_id", referencedColumnName = "cot_id", nullable = false)
    private Cota cota;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "exe_ano", referencedColumnName = "exe_ano", nullable = false)
    private Exercicio exercicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cco_data_cadastro")
    private Date dataCadastrada;
    @Column(name = "cco_competencia")
    private Mes competencia;
    @Column(name = "cco_valor")
    private BigDecimal valor;

    public CotaControle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cota getCota() {
        return cota;
    }

    public void setCota(Cota cota) {
        this.cota = cota;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Date getDataCadastrada() {
        return dataCadastrada;
    }

    public void setDataCadastrada(Date dataCadastrada) {
        this.dataCadastrada = dataCadastrada;
    }

    public Mes getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Mes competencia) {
        this.competencia = competencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final CotaControle other = (CotaControle) obj;
        if (!Objects.equals(this.id, other.getId())) {
            return false;
        }
        return true;
    }
}
