/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.NotAudited;

/**
 *
 * @author ari
 */
@Entity
@Table(name = "licitacao_unidade_orcamentaria", schema = "siafi")
@IdClass(value = LicitacaoUnidadeOrcamentariaPk.class)
public class LicitacaoUnidadeOrcamentaria implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "lic_id", referencedColumnName = "lic_id")
    private Licitacao licitacao;
    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "uno_id", nullable = false, referencedColumnName = "uno_id", updatable = false, insertable = false),
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id", updatable = false, insertable = false),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano", updatable = false, insertable = false)})
    @NotAudited
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
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.licitacao);
        hash = 89 * hash + Objects.hashCode(this.unidadeOrcamentaria);
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
        final LicitacaoUnidadeOrcamentaria other = (LicitacaoUnidadeOrcamentaria) obj;
        if (!Objects.equals(this.licitacao, other.licitacao)) {
            return false;
        }
        return Objects.equals(this.unidadeOrcamentaria, other.unidadeOrcamentaria);
    }

}
