/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe do Projeto Siafi - Criado em 09/07/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "item_ordem_compra_view", schema = "siafi")
public class ItemOrdemCompraView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ite_lic_id")
    private Long itemLicitacao;
    @Column(name = "ite_id")
    private Long idItem;
    @Column(name = "ite_nome", length = 255)
    private String nome;
    @Column(name = "grp_ite_id")
    private Integer grupoItemId;
    @Column(name = "und_med_id")
    private Integer unidade;
    @Column(name = "ite_especificacao", length = 2000)
    private String especificacao;
    @Column(name = "ite_lic_quantidade", precision = 19, scale = 2)
    private BigDecimal quantidade;
    @Column(name = "ite_lic_valor", precision = 19, scale = 2)
    private BigDecimal valor;
    @Column(name = "lic_id")
    private Integer licitacao;
    @Column(name = "cre_id")
    private Integer credor;
    @Column(name = "ite_ord_comp_quantidade")
    private BigDecimal qtdUtilizada;

    public ItemOrdemCompraView() {
    }

    public BigDecimal saldo() {
        if (qtdUtilizada == null) {
            return quantidade;
        } else {
            return quantidade.subtract(qtdUtilizada);
        }
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getGrupoItemId() {
        return grupoItemId;
    }

    public void setGrupoItemId(Integer grupoItemId) {
        this.grupoItemId = grupoItemId;
    }

    public Integer getUnidade() {
        return unidade;
    }

    public void setUnidade(Integer unidade) {
        this.unidade = unidade;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public Long getItemLicitacao() {
        return itemLicitacao;
    }

    public void setItemLicitacao(Long itemLicitacao) {
        this.itemLicitacao = itemLicitacao;
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

    public Integer getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Integer licitacao) {
        this.licitacao = licitacao;
    }

    public Integer getCredor() {
        return credor;
    }

    public void setCredor(Integer credor) {
        this.credor = credor;
    }

    public BigDecimal getQtdUtilizada() {
        return qtdUtilizada;
    }

    public void setQtdUtilizada(BigDecimal qtdUtilizada) {
        this.qtdUtilizada = qtdUtilizada;
    }
}
