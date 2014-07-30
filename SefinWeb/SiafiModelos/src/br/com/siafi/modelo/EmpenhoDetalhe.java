/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto Siafi - Criado em 24/07/2013 -
 *
 * @author ari
 */
@Entity
@Table(name = "empenho_detalhe", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class EmpenhoDetalhe implements Serializable {

    @Id
    @Column(name = "emp_det_id", nullable = false)
    private Integer id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sol_id", referencedColumnName = "sol_id", nullable = false)
    private SolicitacaoFinanceira solicitacaoFinanceira;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "con_id", referencedColumnName = "con_id")
    private Conta conta;
    @Column(name = "emp_det_empenho", nullable = false)
    private String empenho;
    @Column(name = "emp_det_valor", nullable = false)
    private BigDecimal valor;
    @Column(name = "emp_det_data_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    @Column(name = "emp_det_data_autorizacao")
    @Temporal(TemporalType.DATE)
    private Date dataAutorizacao;
    @Column(name = "emp_det_documento_pagamento")
    private String documentoPagamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
    }

    public String getEmpenho() {
        return empenho;
    }

    public void setEmpenho(String empenho) {
        this.empenho = empenho;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataAutorizacao() {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(Date dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
    }

    public String getDocumentoPagamento() {
        return documentoPagamento;
    }

    public void setDocumentoPagamento(String documentoPagamento) {
        this.documentoPagamento = documentoPagamento;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final EmpenhoDetalhe other = (EmpenhoDetalhe) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmpenhoDetalhe{" + "id=" + id + ", solicitacaoFinanceira=" + solicitacaoFinanceira + ", empenho=" + empenho + ", valor=" + valor + ", dataPagamento=" + dataPagamento + ", dataAutorizacao=" + dataAutorizacao + ", documentoPagamento=" + documentoPagamento + '}';
    }
}
