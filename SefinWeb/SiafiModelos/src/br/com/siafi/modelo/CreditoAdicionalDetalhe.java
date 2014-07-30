/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "credito_adicional_detalhe", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CreditoAdicionalDetalhe implements Serializable {

    @EmbeddedId
    private CreditoAdicionalDetalhePk id;
    @ManyToOne
    @JoinColumn(name = "cad_id", referencedColumnName = "cad_id", nullable = false)
    private CreditoAdicional creditoAdicional;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "dot_id", nullable = false, referencedColumnName = "dot_id"),
        @JoinColumn(name = "dot_id_reduzido", nullable = false, referencedColumnName = "dot_id_reduzido")})
    private Dotacao dotacao;
    @Column(name = "cad_tipo")
    private String tipo;
    @Column(name = "cad_sub_tipo")
    private String subTipo;
    @Column(name = "cad_fonte")
    private String fonte;
    @Column(name = "cad_valor")
    private BigDecimal valor;
    @Column(name = "cad_super_avit")
    private BigDecimal superAvit;
    @Column(name = "cad_excesso")
    private BigDecimal excesso;
    @Column(name = "cad_credito")
    private BigDecimal credito;
    @Column(name = "cad_anulacao")
    private BigDecimal anulacao;
    @Column(name = "cad_seq_dia")
    private Integer seqDia;
    @ManyToOne
    @JoinColumn(name = "for_id", referencedColumnName = "for_id")
    private FonteRecurso fonteRecurso;

    public CreditoAdicionalDetalhe() {
    }

    public CreditoAdicionalDetalhe(CreditoAdicionalDetalhePk id) {
        this.id = id;
    }

    public CreditoAdicionalDetalhePk getId() {
        return id;
    }

    public void setId(CreditoAdicionalDetalhePk id) {
        this.id = id;
    }

    public CreditoAdicional getCreditoAdicional() {
        return creditoAdicional;
    }

    public void setCreditoAdicional(CreditoAdicional creditoAdicional) {
        this.creditoAdicional = creditoAdicional;
    }

    public Dotacao getDotacao() {
        return dotacao;
    }

    public void setDotacao(Dotacao dotacao) {
        this.dotacao = dotacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSuperAvit() {
        return superAvit;
    }

    public void setSuperAvit(BigDecimal superAvit) {
        this.superAvit = superAvit;
    }

    public BigDecimal getExcesso() {
        return excesso;
    }

    public void setExcesso(BigDecimal excesso) {
        this.excesso = excesso;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public BigDecimal getAnulacao() {
        return anulacao;
    }

    public void setAnulacao(BigDecimal anulacao) {
        this.anulacao = anulacao;
    }

    public Integer getSeqDia() {
        return seqDia;
    }

    public void setSeqDia(Integer seqDia) {
        this.seqDia = seqDia;
    }

    public FonteRecurso getFonteRecurso() {
        return fonteRecurso;
    }

    public void setFonteRecurso(FonteRecurso fonteRecurso) {
        this.fonteRecurso = fonteRecurso;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final CreditoAdicionalDetalhe other = (CreditoAdicionalDetalhe) obj;
        return Objects.equals(this.id, other.id);
    }
}
