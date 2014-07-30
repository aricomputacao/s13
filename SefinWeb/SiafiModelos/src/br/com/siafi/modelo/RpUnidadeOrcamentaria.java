/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeAdministrativa;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Arquivo do projeto SiafiModelos criando em 16/10/2013
 *
 * @author ari
 */
@Entity
@Table(name = "rp_unidade_orcamentaria", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RpUnidadeOrcamentaria implements Serializable {

    @EmbeddedId
    private RpUnidadeOrcamentariaPk orcamentariaPk;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "org_id", referencedColumnName = "org_id", nullable = false),
        @JoinColumn(name = "exe_ano", referencedColumnName = "exe_ano", nullable = false)})
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Orgao orgaoAtual;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "rp_codigo", referencedColumnName = "rp_codigo", insertable = false, updatable = false),
        @JoinColumn(name = "rp_exercicio", referencedColumnName = "rp_exercicio", insertable = false, updatable = false)})
    private RpOrgao rpOrgao;
    @NotNull
    @Column(name = "rp_nome", nullable = false)
    private String nome;

    public RpUnidadeOrcamentaria() {
    }

    public RpUnidadeOrcamentaria(RpUnidadeOrcamentariaPk orcamentariaPk) {
        this.orcamentariaPk = orcamentariaPk;
    }

    public RpUnidadeOrcamentariaPk getOrcamentariaPk() {
        return orcamentariaPk;
    }

    public void setOrcamentariaPk(RpUnidadeOrcamentariaPk orcamentariaPk) {
        this.orcamentariaPk = orcamentariaPk;
    }

    public Orgao getOrgaoAtual() {
        return orgaoAtual;
    }

    public void setOrgaoAtual(Orgao orgaoAtual) {
        this.orgaoAtual = orgaoAtual;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RpOrgao getRpOrgao() {
        return rpOrgao;
    }

    public void setRpOrgao(RpOrgao rpOrgao) {
        this.rpOrgao = rpOrgao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.orcamentariaPk);
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
        final RpUnidadeOrcamentaria other = (RpUnidadeOrcamentaria) obj;
        if (!Objects.equals(this.orcamentariaPk, other.orcamentariaPk)) {
            return false;
        }
        return true;
    }
}
