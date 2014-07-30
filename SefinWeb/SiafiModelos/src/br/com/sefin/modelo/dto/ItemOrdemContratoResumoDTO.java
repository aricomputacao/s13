/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.modelo.dto;

import br.com.siafi.modelo.Item;
import br.com.siafi.modelo.ItemLicitacao;
import java.math.BigDecimal;

/**
 *
 * @author gilmario
 */
public class ItemOrdemContratoResumoDTO {

    private Item item;
    private BigDecimal quantidade;
    private ItemLicitacao itemLicitacao;

    public ItemOrdemContratoResumoDTO(Item item, BigDecimal quantidade, ItemLicitacao itemLicitacao) {
        this.item = item;
        this.quantidade = quantidade;
        this.itemLicitacao = itemLicitacao;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
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

}
