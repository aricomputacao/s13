/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "CREDITOADICIONALDETALHE", catalog = "", schema = "")
public class CreditoAdicionalDetalhe implements Serializable {

    @EmbeddedId
    private CreditoAdicionalDetalhePK id;
    @Column(name = "CADDOTACAO")
    private Integer dotacao;
    @Column(name = "CADTIPO")
    private String tipo;
    @Column(name = "CADSUBTIPO")
    private String subTipo;
    @Column(name = "CADFONTE")
    private String fonte;
    @Column(name = "CADVALOR")
    private BigDecimal valor;
    @Column(name = "CADSUPERAVIT")
    private BigDecimal superAvit;
    @Column(name = "CADEXCESSO")
    private BigDecimal excesso;
    @Column(name = "CADCREDITO")
    private BigDecimal credito;
    @Column(name = "CADANULACAO")
    private BigDecimal anulacao;
    @Column(name = "CADANULACAOCOD")
    private Integer anulacaoCod;
    @Column(name = "CADSEQDIA")
    private Integer seqdia;
    @Column(name = "CADFONTERECURSO")
    private String fonteRecurso;
    @Column(name = "CADEXPORTADO")
    private Boolean exportado;

    public CreditoAdicionalDetalhe() {
    }

    public CreditoAdicionalDetalhePK getId() {
        return id;
    }

    public void setId(CreditoAdicionalDetalhePK id) {
        this.id = id;
    }

    public Boolean isExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public Integer getDotacao() {
        return dotacao;
    }

    public void setDotacao(Integer dotacao) {
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

    public Integer getAnulacaoCod() {
        return anulacaoCod;
    }

    public void setAnulacaoCod(Integer anulacaoCod) {
        this.anulacaoCod = anulacaoCod;
    }

    public Integer getSeqdia() {
        return seqdia;
    }

    public void setSeqdia(Integer seqdia) {
        this.seqdia = seqdia;
    }

    public String getFonteRecurso() {
        return fonteRecurso;
    }

    public void setFonteRecurso(String fonteRecurso) {
        this.fonteRecurso = fonteRecurso;
    }

    public Boolean getExportado() {
        return exportado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
