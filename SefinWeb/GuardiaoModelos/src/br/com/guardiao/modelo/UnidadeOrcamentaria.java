/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto guardiao criada em 20/03/2013 - Classe que representa as
 * Unidades Orçamentarias
 *
 * @author: ari
 */
@Entity
@Table(name = "unidade_orcamentaria", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class UnidadeOrcamentaria implements Serializable {

    /**
     * chave primaria composta da classe. referente ao mesmo id do registro de
     * Orgão do Sistema de Contabilidade.
     */
    @EmbeddedId
    private UnidadeOrcamentariaPK unidadeOrcamentariaPK;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id", updatable = false, insertable = false),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano", updatable = false, insertable = false)})
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Orgao orgao;
    //Campo referente  ao nome da Unidade Orçamentaria
    @Column(name = "uno_nome", length = 256, nullable = false, unique = true)
    private String nome;
    //Campo referente  a abreviação d Unidade ORçamentaria
    @Column(name = "uno_abreviacao", length = 128, nullable = false)
    private String abreviacao;
    //Campo referente  a abreviação d Unidade ORçamentaria
    @Column(name = "uno_objetivo", length = 128, nullable = false)
    private String objetivo;
    @Column(name = "uno_pendente_autorizacao", nullable = false)
    private boolean PendenteAutorizacao;
    @Column(name = "uno_ativo", nullable = false)
    private boolean ativo;
    @Length(max = 100)
    @Column(name = "uno_ordenador", length = 100)
    private String ordenador;

    public UnidadeOrcamentaria() {
    }

    public UnidadeOrcamentaria(UnidadeOrcamentariaPK unidadeOrcamentariaPK) {
        this.unidadeOrcamentariaPK = unidadeOrcamentariaPK;
    }

    public String getNome() {
        return nome;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public UnidadeOrcamentariaPK getUnidadeOrcamentariaPK() {
        return unidadeOrcamentariaPK;
    }

    public void setUnidadeOrcamentariaPK(UnidadeOrcamentariaPK unidadeOrcamentariaPK) {
        this.unidadeOrcamentariaPK = unidadeOrcamentariaPK;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.unidadeOrcamentariaPK);
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
        final UnidadeOrcamentaria other = (UnidadeOrcamentaria) obj;
        return Objects.equals(this.unidadeOrcamentariaPK, other.unidadeOrcamentariaPK);
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public boolean isPendenteAutorizacao() {
        return PendenteAutorizacao;
    }

    public void setPendenteAutorizacao(boolean PendenteAutorizacao) {
        this.PendenteAutorizacao = PendenteAutorizacao;
    }

    public String getOrdenador() {
        return ordenador;
    }

    public void setOrdenador(String ordenador) {
        this.ordenador = ordenador;
    }

    @Override
    public String toString() {
        if (unidadeOrcamentariaPK != null) {
            return unidadeOrcamentariaPK.toString() + " - " + abreviacao;
        } else {
            return abreviacao;
        }

    }

    public String definicao() {
        if (unidadeOrcamentariaPK != null) {
            return unidadeOrcamentariaPK.toString() + " - " + nome.toUpperCase();
        } else {
            return nome.toUpperCase();
        }

    }

}
