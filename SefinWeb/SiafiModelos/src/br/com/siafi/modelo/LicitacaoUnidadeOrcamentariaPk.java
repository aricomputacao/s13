/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author ari
 */
public class LicitacaoUnidadeOrcamentariaPk implements Serializable {

    private Licitacao licitacao;
    private UnidadeOrcamentaria unidadeOrcamentaria;

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.licitacao);
        hash = 97 * hash + Objects.hashCode(this.unidadeOrcamentaria);
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
        final LicitacaoUnidadeOrcamentariaPk other = (LicitacaoUnidadeOrcamentariaPk) obj;
        if (!Objects.equals(this.licitacao, other.licitacao)) {
            return false;
        }
        return Objects.equals(this.unidadeOrcamentaria, other.unidadeOrcamentaria);
    }

}
