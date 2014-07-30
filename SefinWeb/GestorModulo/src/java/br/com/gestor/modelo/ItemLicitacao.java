/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Classe do Projeto Siafi - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "LICITACAOITEM", catalog = "", schema = "")
public class ItemLicitacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ItemLicitacaoPK id;
    @Column(name = "LIIQUANTIDADE")
    private BigDecimal quantidade;
    @Column(name = "LIIVALOR")
    private BigDecimal valor;
    @Column(name = "LIITOTAL")
    private BigDecimal total;
    @Column(name = "LIICOD")
    private Integer codigo;
    @Column(name = "LIIEXPORTADO")
    private Boolean exportado;

    
    public ItemLicitacao() {
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    
    
    public ItemLicitacao(ItemLicitacaoPK id) {
        this.id = id;
    }

    public ItemLicitacaoPK getId() {
        return id;
    }

    public void setId(ItemLicitacaoPK id) {
        this.id = id;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
        hash = 13 * hash + Objects.hashCode(this.id);
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

    @Override
    public String toString() {
        return "ItemLicitacao{" + "id=" + id + ", quantidade=" + quantidade + ", valor=" + valor + ", total=" + total + ", codigo=" + codigo + ", exportado=" + exportado + '}';
    }

}
