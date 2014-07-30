package br.com.siafi.auditoria.modelo;

import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Usuario;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Dotacao;
import br.com.guardiao.modelo.Exercicio;
import br.com.siafi.modelo.FonteRecurso;
import br.com.siafi.modelo.Licitacao;
import br.com.siafi.modelo.OrdemCompra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

/**
 * Classe do Projeto SiafiModelos criada em 16/04/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "solicitacao_financeira_aud", schema = "siafi")
public class SolicitacaoFinanceiraAud implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private SolicitacaFinaceiraAudPk solicitacaFinaceiraAudPk;
    @Column(name = "revtype")
    private Integer revtype;
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cot_id", nullable = false, referencedColumnName = "cot_id")
    private Cota cota;
    @Column(name = "sol_historico", nullable = false, length = 1024)
    private String historico;
    @Column(name = "sol_justificativa", nullable = true, length = 1024)
    private String justificativa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "dot_id", nullable = false, referencedColumnName = "dot_id"),
        @JoinColumn(name = "dot_id_reduzido", nullable = false, referencedColumnName = "dot_id_reduzido")})
    private Dotacao dotacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "con_id", nullable = true, referencedColumnName = "con_id")
    private Contrato contrato;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ord_comp_id", referencedColumnName = "ord_comp_id")
    private OrdemCompra ordemCompra;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lic_id", referencedColumnName = "lic_id")
    private Licitacao licitacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sol_local", nullable = false, referencedColumnName = "are_id")
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
    @Column(name = "sol_nota_fiscal", nullable = true)
    private String notaFiscal;
    @Column(name = "sol_observacao", nullable = true, length = 1024)
    private String observacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "for_id", referencedColumnName = "for_id")
    private FonteRecurso FonteRecurso;
    @Column(name = "sol_numero_empenho")
    private String empenho;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name = "cre_id", referencedColumnName = "cre_id")
    private Credor credor;

    public SolicitacaoFinanceiraAud() {
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Dotacao getDotacao() {
        return dotacao;
    }

    public void setDotacao(Dotacao dotacao) {
        this.dotacao = dotacao;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
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

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
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

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public FonteRecurso getFonteRecurso() {
        return FonteRecurso;
    }

    public void setFonteRecurso(FonteRecurso FonteRecurso) {
        this.FonteRecurso = FonteRecurso;
    }

    public String getEmpenho() {
        return empenho;
    }

    public void setEmpenho(String empenho) {
        this.empenho = empenho;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Integer getRevtype() {
        return revtype;
    }

    public void setRevtype(Integer revtype) {
        this.revtype = revtype;
    }

    public SolicitacaFinaceiraAudPk getSolicitacaFinaceiraAudPk() {
        return solicitacaFinaceiraAudPk;
    }

    public void setSolicitacaFinaceiraAudPk(SolicitacaFinaceiraAudPk solicitacaFinaceiraAudPk) {
        this.solicitacaFinaceiraAudPk = solicitacaFinaceiraAudPk;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioAutorizou() {
        return usuarioAutorizou;
    }

    public void setUsuarioAutorizou(Usuario usuarioAutorizou) {
        this.usuarioAutorizou = usuarioAutorizou;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.solicitacaFinaceiraAudPk);
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
        final SolicitacaoFinanceiraAud other = (SolicitacaoFinanceiraAud) obj;
        if (!Objects.equals(this.solicitacaFinaceiraAudPk, other.solicitacaFinaceiraAudPk)) {
            return false;
        }
        return true;
    }
}
