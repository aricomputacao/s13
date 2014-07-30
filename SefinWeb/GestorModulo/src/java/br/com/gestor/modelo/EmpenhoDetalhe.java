/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @Sistema GestorModulo
 * @Data 24/07/2013
 * @author gilmario
 */
@Entity
@Table(name = "FEEMPDET", catalog = "", schema = "")
public class EmpenhoDetalhe implements Serializable {

    @Id
    @Column(name = "EMD_SEQ")
    private Integer id;
    @Column(name = "EMA_NUMEMP")
    private String numeroEmpenho;
    @Column(name = "EMD_NUMITE")
    private String sequencia;
    @Column(name = "EMD_VAUTPA")
    private BigDecimal valor;
    @Column(name = "EMD_DTPAGO")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    @Column(name = "EMD_DTAUT")
    @Temporal(TemporalType.DATE)
    private Date dataAutorizacao;
    @Column(name = "EMD_PAGDOC")
    private String documentoPagamento;
    @Column(name = "EMA_ORGAO")
    private String unidadeOrcamentaria;
    @Column(name = "EMD_CONTAC")
    private String conta;
    @Column(name = "EMD_CODAGE")
    private String codigoAgencia;
    @Column(name = "EMD_CODBAN")
    private String codigoBanco;

    public EmpenhoDetalhe() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroEmpenho() {
        return numeroEmpenho;
    }

    public void setNumeroEmpenho(String numeroEmpenho) {
        this.numeroEmpenho = numeroEmpenho;
    }

    public String getSequencia() {
        return sequencia;
    }

    public void setSequencia(String sequencia) {
        this.sequencia = sequencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDataAutorizacao() {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(Date dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDocumentoPagamento() {
        return documentoPagamento;
    }

    public void setDocumentoPagamento(String documentoPagamento) {
        this.documentoPagamento = documentoPagamento;
    }

    public String getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(String unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    public void setCodigoAgencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        return "EmpenhoDetalhe{" + "id=" + id + ", numeroEmpenho=" + numeroEmpenho + ", sequencia=" + sequencia + ", valor=" + valor + ", dataAutorizacao=" + dataAutorizacao + ", dataPagamento=" + dataPagamento + ", documentoPagamento=" + documentoPagamento + '}';
    }
}
