/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto SiafiModelos criada em 25/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "item_licitacao", schema = "siafi", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"lic_id", "cre_id", "ite_id", "ite_lic_aditivo", "ite_lote"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ItemLicitacao implements Serializable {

    @Id
    @Column(name = "ite_lic_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lic_id", nullable = false, referencedColumnName = "lic_id")
    private Licitacao licitacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cre_id", nullable = false, referencedColumnName = "cre_id")
    private Credor credor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ite_id", nullable = false, referencedColumnName = "ite_id")
    private Item item;
    @Column(name = "ite_lic_aditivo")
    private String aditivo;
    @Column(name = "ite_lic_quantidade", scale = 4, precision = 19)
    private BigDecimal quantidade;
    @Column(name = "ite_lic_valor", scale = 4, precision = 19)
    private BigDecimal valor;
    @Column(name = "ite_lic_valor_total", scale = 4, precision = 19)
    private BigDecimal valorTotal;
    @Column(name = "ite_lote")
    private String lote;
    @Column(name = "ite_codigo")
    private Integer codigo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getAditivo() {
        return aditivo;
    }

    public void setAditivo(String aditivo) {
        this.aditivo = aditivo;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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
        final ItemLicitacao other = (ItemLicitacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
