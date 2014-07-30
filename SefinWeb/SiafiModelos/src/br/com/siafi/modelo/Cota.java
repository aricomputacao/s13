/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 * Classe do Projeto Siafi - Criado em 07/05/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "cota", schema = "siafi", uniqueConstraints
        = @UniqueConstraint(columnNames = {"uno_id", "org_id", "exe_ano", "cet_id", "cat_id"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class Cota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cot_id", nullable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cet_id", referencedColumnName = "cet_id", nullable = false)
    private CentroCusto despesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cat_id", referencedColumnName = "cat_id", nullable = false)
    private Categoria categoria;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "uno_id", nullable = false, referencedColumnName = "uno_id"),
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    private UnidadeOrcamentaria unidadeOrcamentaria;
    @Column(name = "cot_ativo", nullable = false)
    private boolean ativo;
    @Column(name = "cot_autorizado_automatico", nullable = false)
    private boolean autorizadoAutomatico;
    @Column(name = "cot_valor", nullable = false)
    @Min(value = 1)
    private BigDecimal valor;

    public Cota() {
    }

    public Cota(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CentroCusto getDespesa() {
        return despesa;
    }

    public void setDespesa(CentroCusto despesa) {
        this.despesa = despesa;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isAutorizadoAutomatico() {
        return autorizadoAutomatico;
    }

    public void setAutorizadoAutomatico(boolean autorizadoAutomatico) {
        this.autorizadoAutomatico = autorizadoAutomatico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Cota other = (Cota) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
