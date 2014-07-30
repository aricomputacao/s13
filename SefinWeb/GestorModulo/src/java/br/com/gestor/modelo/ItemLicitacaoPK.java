/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe do Projeto ******* - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Embeddable
public class ItemLicitacaoPK implements Serializable {

    @Column(name = "LIILICITANTE")
    private Integer credor;
    @Column(name = "LIILICITACAO")
    private Integer licitacao;
    @Column(name = "LIIITEM")
    private Long item;
    @Column(name = "LIIADITIVO")
    private String aditvo;
    @Column(name = "LIILOTE")
    private Long lote;

    public Integer getCredor() {
        return credor;
    }

    public void setCredor(Integer credor) {
        this.credor = credor;
    }

    public Integer getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Integer licitacao) {
        this.licitacao = licitacao;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public String getAditvo() {
        return aditvo;
    }

    public void setAditvo(String aditvo) {
        this.aditvo = aditvo;
    }

    public Long getLote() {
        return lote;
    }

    public void setLote(Long lote) {
        this.lote = lote;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.credor);
        hash = 97 * hash + Objects.hashCode(this.licitacao);
        hash = 97 * hash + Objects.hashCode(this.item);
        hash = 97 * hash + Objects.hashCode(this.aditvo);
        hash = 97 * hash + Objects.hashCode(this.lote);
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
        final ItemLicitacaoPK other = (ItemLicitacaoPK) obj;
        if (!Objects.equals(this.credor, other.credor)) {
            return false;
        }
        if (!Objects.equals(this.licitacao, other.licitacao)) {
            return false;
        }
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        if (!Objects.equals(this.aditvo, other.aditvo)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemLicitacaoPK{" + "credor=" + credor + ", licitacao=" + licitacao + ", item=" + item + ", aditvo=" + aditvo + ", lote=" + lote + '}';
    }
}
