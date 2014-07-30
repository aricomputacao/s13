/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.sefin.enumerated.TipoFluxo;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Classe do Projeto SiafiModelos criada em 16/04/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "centro_custo", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CentroCusto implements Serializable, Comparable<CentroCusto> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cet_id", nullable = false)
    private Integer id;
    @Column(name = "cet_nome", nullable = false)
    private String nome;
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "eld_id", referencedColumnName = "eld_id")
    private ElementoDespesa elementoDespesa;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "cet_fluxo")
    private TipoFluxo fluxo;

    public TipoFluxo getFluxo() {
        return fluxo;
    }

    public void setFluxo(TipoFluxo fluxo) {
        this.fluxo = fluxo;
    }

    public ElementoDespesa getElementoDespesa() {
        return elementoDespesa;
    }

    public void setElementoDespesa(ElementoDespesa elementoDespesa) {
        this.elementoDespesa = elementoDespesa;
    }

    public CentroCusto() {
    }

    public CentroCusto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final CentroCusto other = (CentroCusto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "CentroCusto{" + "id=" + id + ", nome=" + nome + ", elementoDespesa=" + elementoDespesa + '}';
    }

    @Override
    public int compareTo(CentroCusto o) {
        return this.getElementoDespesa().getId().compareTo(o.getElementoDespesa().getId());
    }
}
