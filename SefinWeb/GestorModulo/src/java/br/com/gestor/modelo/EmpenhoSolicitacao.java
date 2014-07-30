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
 * Classe do projeto Siafi - Representa a tabela a ser importada para o sistema
 * gestor.
 *
 * @author gilmario
 */
@Entity
@Table(name = "FEEMPAUT", catalog = "", schema = "")
public class EmpenhoSolicitacao implements Serializable {

    @Id
    @Column(name = "SEM_NUMERO", nullable = false)
    private String numeroSolicitacao;
    @Column(name = "EMA_SEQUEN")
    private Integer sequencia;
    @Column(name = "EMA_ORGAO", length = 4)
    private String codUnidadeOrcamentaria;
    @Column(name = "EMA_NUMEMP", length = 8)
    private String numeroEmpenho;
    @Column(name = "EMA_CODPA", length = 4)
    private String codProjetoAtividadeAcao;
    @Column(name = "EMA_CODELE", length = 8)
    private String numeroelementoDespesa;
    @Column(name = "EMA_CREDOR", length = 7)
    private String codCredor;
    @Column(name = "EMA_CODCLA", length = 4)
    private String codDotacao;
    @Column(name = "EMA_DATEMP")
    @Temporal(TemporalType.DATE)
    private Date dataEmpenho;
    @Column(name = "EMA_VALEMP")
    private BigDecimal valorEmpenho;
    @Column(name = "EMA_DESCRI", length = 1024)
    private String descricao;
    @Column(name = "EMA_NOMECR", length = 60)
    private String nomeCredor;
    @Column(name = "EMA_STATUS", length = 1)
    private String status;
    @Column(name = "EMA_USUAR", length = 15)
    private String nomeUsuario;
    @Column(name = "EMA_DTPROG")
    @Temporal(TemporalType.DATE)
    private Date dataProgramacaoPagamento;
    @Column(name = "EMA_NRLIC", length = 20)
    private String idLicitacao;
    @Column(name = "EXE_COD")
    private Integer codExercicio;
    @Column(name = "EMA_CENCOD")
    private Integer codCentroCusto;
    @Column(name = "EMA_FONTE")
    private String codTipoFonte;
    @Column(name = "EMA_MODALIDADE")
    private String modalidade;
     @Column(name = "EMA_CONTRATO")
    private String contrato;

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

     
     
    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    
    
    public String getCodTipoFonte() {
        return codTipoFonte;
    }

    public void setCodTipoFonte(String codTipoFonte) {
        this.codTipoFonte = codTipoFonte;
    }

    public Integer getCodCentroCusto() {
        return codCentroCusto;
    }

    public void setCodCentroCusto(Integer codCentroCusto) {
        this.codCentroCusto = codCentroCusto;
    }

    public String getCodDotacao() {
        return codDotacao;
    }

    public void setCodDotacao(String codDotacao) {
        this.codDotacao = codDotacao;
    }

    public String getNumeroSolicitacao() {
        return numeroSolicitacao;
    }

    public void setNumeroSolicitacao(String numeroSolicitacao) {
        this.numeroSolicitacao = numeroSolicitacao;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public String getCodUnidadeOrcamentaria() {
        return codUnidadeOrcamentaria;
    }

    public void setCodUnidadeOrcamentaria(String codUnidadeOrcamentaria) {
        this.codUnidadeOrcamentaria = codUnidadeOrcamentaria;
    }

    public String getNumeroEmpenho() {
        return numeroEmpenho;
    }

    public void setNumeroEmpenho(String numeroEmpenho) {
        this.numeroEmpenho = numeroEmpenho;
    }

    public String getCodProjetoAtividadeAcao() {
        return codProjetoAtividadeAcao;
    }

    public void setCodProjetoAtividadeAcao(String codProjetoAtividadeAcao) {
        this.codProjetoAtividadeAcao = codProjetoAtividadeAcao;
    }

    public String getNumeroelementoDespesa() {
        return numeroelementoDespesa;
    }

    public void setNumeroelementoDespesa(String numeroelementoDespesa) {
        this.numeroelementoDespesa = numeroelementoDespesa;
    }

    public String getCodCredor() {
        return codCredor;
    }

    public void setCodCredor(String codCredor) {
        this.codCredor = codCredor;
    }

    public Date getDataEmpenho() {
        return dataEmpenho;
    }

    public void setDataEmpenho(Date dataEmpenho) {
        this.dataEmpenho = dataEmpenho;
    }

    public BigDecimal getValorEmpenho() {
        return valorEmpenho;
    }

    public void setValorEmpenho(BigDecimal valorEmpenho) {
        this.valorEmpenho = valorEmpenho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeCredor() {
        return nomeCredor;
    }

    public void setNomeCredor(String nomeCredor) {
        this.nomeCredor = nomeCredor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Date getDataProgramacaoPagamento() {
        return dataProgramacaoPagamento;
    }

    public void setDataProgramacaoPagamento(Date dataProgramacaoPagamento) {
        this.dataProgramacaoPagamento = dataProgramacaoPagamento;
    }

    public String getIdLicitacao() {
        return idLicitacao;
    }

    public void setIdLicitacao(String idLicitacao) {
        this.idLicitacao = idLicitacao;
    }

    public Integer getCodExercicio() {
        return codExercicio;
    }

    public void setCodExercicio(Integer codExercicio) {
        this.codExercicio = codExercicio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.numeroSolicitacao);
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
        final EmpenhoSolicitacao other = (EmpenhoSolicitacao) obj;
        return Objects.equals(this.numeroSolicitacao, other.numeroSolicitacao);
    }

    @Override
    public String toString() {
        return "EmpenhoSolicitacao{" + "numeroSolicitacao=" + numeroSolicitacao + ", sequencia=" + sequencia + ", codUnidadeOrcamentaria=" + codUnidadeOrcamentaria + ", numeroEmpenho=" + numeroEmpenho + ", codProjetoAtividadeAcao=" + codProjetoAtividadeAcao + ", numeroelementoDespesa=" + numeroelementoDespesa + ", codCredor=" + codCredor + ", dataEmpenho=" + dataEmpenho + ", valorEmpenho=" + valorEmpenho + ", descricao=" + descricao + ", nomeCredor=" + nomeCredor + ", status=" + status + ", nomeUsuario=" + nomeUsuario + ", dataAlgumaCOisa=" + dataProgramacaoPagamento + ", idLicitacao=" + idLicitacao + ", codExercicio=" + codExercicio + '}';
    }
}
