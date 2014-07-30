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
 * @Sistema GestorModulo
 * @Data 17/07/2013
 * @author gilmario
 */
@Embeddable
public class EmpenhoSolicitacaoItemPk implements Serializable {

    @Column(name = "SEM_NUMERO", nullable = false)
    private String numeroSolicitacao;
    @Column(name = "EMA_SEQUEN")
    private Integer sequencia;
    @Column(name = "ITE_CODIGO")
    private Integer itemCodigo;

    public String getNumeroSolicitacao() {
        return numeroSolicitacao;
    }

    public void setNumeroSolicitacao(String numeroSolicitacao) {
        this.numeroSolicitacao = numeroSolicitacao;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public Integer getItemCodigo() {
        return itemCodigo;
    }

    public void setItemCodigo(Integer itemCodigo) {
        this.itemCodigo = itemCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.numeroSolicitacao);
        hash = 53 * hash + Objects.hashCode(this.sequencia);
        hash = 53 * hash + Objects.hashCode(this.itemCodigo);
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
        final EmpenhoSolicitacaoItemPk other = (EmpenhoSolicitacaoItemPk) obj;
        if (!Objects.equals(this.numeroSolicitacao, other.numeroSolicitacao)) {
            return false;
        }
        if (!Objects.equals(this.sequencia, other.sequencia)) {
            return false;
        }
        if (!Objects.equals(this.itemCodigo, other.itemCodigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmpenhoSolicitacaoItemPk{" + "numeroSolicitacao=" + numeroSolicitacao + ", sequencia=" + sequencia + ", itemCodigo=" + itemCodigo + '}';
    }
}
