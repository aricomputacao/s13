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
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Embeddable
public class DotacaoLicitacaoPK implements Serializable {

    @Column(name = "LIDLICITACAO")
    private Integer licitacao;
    @Column(name = "LIDDOTACAO")
    private Integer dotacao;

    public Integer getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Integer licitacao) {
        this.licitacao = licitacao;
    }

    public Integer getDotacao() {
        return dotacao;
    }

    public void setDotacao(Integer dotacao) {
        this.dotacao = dotacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.licitacao);
        hash = 43 * hash + Objects.hashCode(this.dotacao);
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
        final DotacaoLicitacaoPK other = (DotacaoLicitacaoPK) obj;
        if (!Objects.equals(this.licitacao, other.licitacao)) {
            return false;
        }
        if (!Objects.equals(this.dotacao, other.dotacao)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DotacaoLicitacaoPK{" + "licitacao=" + licitacao + ", dotacao=" + dotacao + '}';
    }
}
