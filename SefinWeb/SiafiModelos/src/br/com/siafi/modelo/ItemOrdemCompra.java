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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 *
 * @author ari
 */
@Entity
@Table(name = "item_ordem_compra", schema = "siafi", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ord_comp_id", "ite_ord_comp_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class ItemOrdemCompra implements Serializable {

    @EmbeddedId
    private ItemOrdemCompraPk itemOrdemCompraPk;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ord_comp_id", nullable = false, referencedColumnName = "ord_comp_id")
    private OrdemCompra ordemCompra;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ite_lic_id", nullable = false, referencedColumnName = "ite_lic_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private ItemLicitacao itemLicitacao;
    @Column(name = "ite_ord_comp_quantidade", precision = 19, scale = 4)
    private BigDecimal quantidade;
    @Column(name = "ite_ord_comp_valor_unitario", precision = 19, scale = 4, nullable = true)
    private BigDecimal valorUnitario;

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public OrdemCompra getOrdemCompra() {
        return ordemCompra;
    }

    public void setOrdemCompra(OrdemCompra ordemCompra) {
        this.ordemCompra = ordemCompra;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public ItemLicitacao getItemLicitacao() {
        return itemLicitacao;
    }

    public void setItemLicitacao(ItemLicitacao itemLicitacao) {
        this.itemLicitacao = itemLicitacao;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public ItemOrdemCompraPk getItemOrdemCompraPk() {
        return itemOrdemCompraPk;
    }

    public void setItemOrdemCompraPk(ItemOrdemCompraPk itemOrdemCompraPk) {
        this.itemOrdemCompraPk = itemOrdemCompraPk;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.itemOrdemCompraPk);
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
        final ItemOrdemCompra other = (ItemOrdemCompra) obj;
        return this.itemOrdemCompraPk.equals(other.itemOrdemCompraPk);
    }
}
