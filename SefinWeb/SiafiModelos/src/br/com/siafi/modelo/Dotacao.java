/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.NotAudited;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "dotacao", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dotacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private DotacalPk dotacalPk;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "nad_id", referencedColumnName = "nad_id", nullable = false)
    private NaturezaDespesa naturezaDespesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pja_id", referencedColumnName = "pja_id", nullable = false)
    private ProjetoAtividade projetoAtividade;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "orc_id", referencedColumnName = "orc_id", nullable = false)
    @NotAudited
    private Orcamento orcamento;
    @Column(name = "dot_valor")
    private BigDecimal valor;

    public Dotacao() {
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public NaturezaDespesa getNaturezaDespesa() {
        return naturezaDespesa;
    }

    public void setNaturezaDespesa(NaturezaDespesa naturezaDespesa) {
        this.naturezaDespesa = naturezaDespesa;
    }

    public ProjetoAtividade getProjetoAtividade() {
        return projetoAtividade;
    }

    public void setProjetoAtividade(ProjetoAtividade projetoAtividade) {
        this.projetoAtividade = projetoAtividade;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public DotacalPk getDotacalPk() {
        return dotacalPk;
    }

    public void setDotacalPk(DotacalPk dotacalPk) {
        this.dotacalPk = dotacalPk;
    }

    @Override
    public String toString() {
        return "Dotacao{" + "naturezaDespesa=" + naturezaDespesa + ", projetoAtividade=" + projetoAtividade + '}';
    }
}
