/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.Exercicio;
import br.com.sefin.enumerated.Vinculo;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Usuario;
import br.com.sefin.enumerated.ModalidadeEmpenho;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto SiafiModelos criada em 16/04/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "solicitacao_financeira", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Audited
public class SolicitacaoFinanceira implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "sol_id", nullable = false, length = 10)
    private String id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")
    private Exercicio exercicio;
    @Column(name = "sol_valor", nullable = false)
    @Min(value = 1, message = "A solicitação financeira não pode ter valor menor que R$ 1,00")
    private BigDecimal valor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usu_documento", nullable = false, referencedColumnName = "usu_documento")
    private Usuario usuario;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usu_autorizou", nullable = true, referencedColumnName = "usu_documento")
    private Usuario usuarioAutorizou;
    @Column(name = "sol_data_solicitacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;
    @Column(name = "sol_data_autorizacao")
    @Temporal(TemporalType.DATE)
    private Date dataAutorizacao;
    @Column(name = "sol_mes_competencia", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Mes mesCompetencia;
    @Column(name = "sol_ano_competencia", nullable = false)
    private Integer anoCompetencia;
    @Column(name = "sol_situacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private SituacaoSolicitacao situacaoSolicitacao;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "cot_id", nullable = false, referencedColumnName = "cot_id")
    private Cota cota;
    @Column(name = "sol_historico", nullable = false, length = 1024)
    @Length(max = 1024)
    private String historico;
    @Column(name = "sol_justificativa", nullable = true, length = 1024)
    @Length(max = 1024)
    private String justificativa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "dot_id", nullable = false, referencedColumnName = "dot_id"),
        @JoinColumn(name = "dot_id_reduzido", nullable = false, referencedColumnName = "dot_id_reduzido")})
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Dotacao dotacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "con_id", nullable = true, referencedColumnName = "con_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Contrato contrato;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ord_comp_id", referencedColumnName = "ord_comp_id")
    private OrdemCompra ordemCompra;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lic_id", referencedColumnName = "lic_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Licitacao licitacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sol_local", nullable = false, referencedColumnName = "are_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private AreaAdministrativa local;
    @Temporal(TemporalType.DATE)
    @Column(name = "sol_data_pagamento", nullable = true)
    private Date dataPagamento;
    @Column(name = "sol_valo_liquido", nullable = true)
    private BigDecimal valorLiquido;
    @Column(name = "sol_inss", nullable = true)
    private BigDecimal inss;
    @Column(name = "sol_iss", nullable = true)
    private BigDecimal iss;
    @Column(name = "sol_irrf", nullable = true)
    private BigDecimal irrf;
    @Column(name = "sol_nota_fiscal", nullable = true, length = 1024)
    private String notaFiscal;
    @Column(name = "sol_observacao", nullable = true, length = 1024)
    private String observacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "for_id", referencedColumnName = "for_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private FonteRecurso FonteRecurso;
    @Column(name = "sol_numero_empenho")
    private String empenho;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name = "cre_id", referencedColumnName = "cre_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Credor credor;
    @Enumerated(EnumType.STRING)
    @Column(name = "sol_vinculo")
    private Vinculo vinculo;
    @ManyToOne
    @JoinColumn(nullable = true, name = "cov_id", referencedColumnName = "cov_id")
    private Convenio convenio;
    @ManyToOne
    @NotAudited
    @JoinColumn(nullable = true, name = "adi_id", referencedColumnName = "adi_id")
    private Aditivo aditivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "sol_modalidade", length = 50)
    private ModalidadeEmpenho modalidadeEmpenho;

    public SolicitacaoFinanceira() {
    }

    public ModalidadeEmpenho getModalidadeEmpenho() {
        return modalidadeEmpenho;
    }

    public void setModalidadeEmpenho(ModalidadeEmpenho modalidadeEmpenho) {
        this.modalidadeEmpenho = modalidadeEmpenho;
    }

    public Vinculo getVinculo() {
        return vinculo;
    }

    public void setVinculo(Vinculo vinculo) {
        this.vinculo = vinculo;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;

    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public BigDecimal getInss() {
        return inss;
    }

    public void setInss(BigDecimal inss) {
        this.inss = inss;
    }

    public BigDecimal getIss() {
        return iss;
    }

    public void setIss(BigDecimal iss) {
        this.iss = iss;
    }

    public BigDecimal getIrrf() {
        return irrf;
    }

    public void setIrrf(BigDecimal irrf) {
        this.irrf = irrf;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getEmpenho() {
        return empenho;
    }

    public void setEmpenho(String empenho) {
        this.empenho = empenho;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public FonteRecurso getFonteRecurso() {
        return FonteRecurso;
    }

    public void setFonteRecurso(FonteRecurso FonteRecurso) {
        this.FonteRecurso = FonteRecurso;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public Dotacao getDotacao() {
        return dotacao;
    }

    public void setDotacao(Dotacao dotacao) {
        this.dotacao = dotacao;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Date getDataAutorizacao() {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(Date dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
    }

    public SituacaoSolicitacao getSituacaoSolicitacao() {
        return situacaoSolicitacao;
    }

    public void setSituacaoSolicitacao(SituacaoSolicitacao situacaoSolicitacao) {
        this.situacaoSolicitacao = situacaoSolicitacao;
    }

    public Cota getCota() {
        return cota;
    }

    public void setCota(Cota cota) {
        this.cota = cota;
    }

    public Mes getMesCompetencia() {
        return mesCompetencia;
    }

    public void setMesCompetencia(Mes mesCompetencia) {
        this.mesCompetencia = mesCompetencia;
    }

    public Integer getAnoCompetencia() {
        return anoCompetencia;
    }

    public void setAnoCompetencia(Integer anoCompetencia) {
        this.anoCompetencia = anoCompetencia;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico.toUpperCase();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SolicitacaoFinanceira(String id) {
        this.id = id;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Usuario getUsuarioAutorizou() {
        return usuarioAutorizou;
    }

    public void setUsuarioAutorizou(Usuario usuarioAutorizou) {
        this.usuarioAutorizou = usuarioAutorizou;
    }

    public OrdemCompra getOrdemCompra() {
        return ordemCompra;
    }

    public void setOrdemCompra(OrdemCompra ordemCompra) {
        this.ordemCompra = ordemCompra;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public AreaAdministrativa getLocal() {
        return local;
    }

    public void setLocal(AreaAdministrativa local) {
        this.local = local;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Aditivo getAditivo() {
        return aditivo;
    }

    public void setAditivo(Aditivo aditivo) {
        this.aditivo = aditivo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final SolicitacaoFinanceira other = (SolicitacaoFinanceira) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "SolicitacaoFinanceira{" + "id=" + id + ", exercicio=" + exercicio + ", valor=" + valor + ", usuario=" + usuario + ", usuarioAutorizou=" + usuarioAutorizou + ", dataSolicitacao=" + dataSolicitacao + ", dataAutorizacao=" + dataAutorizacao + ", mesCompetencia=" + mesCompetencia + ", anoCompetencia=" + anoCompetencia + ", situacaoSolicitacao=" + situacaoSolicitacao + ", cota=" + cota + ", historico=" + historico + ", justificativa=" + justificativa + ", dotacao=" + dotacao + ", contrato=" + contrato + ", ordemCompra=" + ordemCompra + ", licitacao=" + licitacao + ", local=" + local + ", dataPagamento=" + dataPagamento + ", valorLiquido=" + valorLiquido + ", inss=" + inss + ", iss=" + iss + ", irrf=" + irrf + ", notaFiscal=" + notaFiscal + ", observacao=" + observacao + ", FonteRecurso=" + FonteRecurso + '}';
    }
}
