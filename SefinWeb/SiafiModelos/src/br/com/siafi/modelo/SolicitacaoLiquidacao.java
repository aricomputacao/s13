/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Usuario;
import br.com.sefin.enumerated.StatusSolicitacaoLiquidacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;

/**
 *
 * @author ari
 */
@Entity
@Table(schema = "siafi", name = "solicitacao_liquidacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class SolicitacaoLiquidacao implements Serializable {

    @Id
    @Column(name = "sol_liq_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sol_id", referencedColumnName = "sol_id", nullable = false)
    private SolicitacaoFinanceira solicitacaoFinanceira;
    @Column(name = "sol_liq_documento")
    private String documento;
    @Column(name = "sol_liq_valor", nullable = false)
    private BigDecimal valor;
    @Column(name = "sol_liq_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private StatusSolicitacaoLiquidacao statusSolicitacaoLiquidacao;
    @ManyToOne
    @JoinColumn(name = "are_id", referencedColumnName = "are_id", nullable = false)
    private AreaAdministrativa areaAdministrativa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usu_documento", referencedColumnName = "usu_documento")
    private Usuario usuario;
//    @Column(name = "sol_liq_status_data_liquidacao", nullable = true)
//    @Temporal(TemporalType.DATE)
//    private Date dataLiquidacao;
//    @Column(name = "sol_liq_data_pagamento", nullable = true)
//    @Temporal(TemporalType.DATE)
//    private Date dataPagamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public StatusSolicitacaoLiquidacao getStatusSolicitacaoLiquidacao() {
        return statusSolicitacaoLiquidacao;
    }

    public void setStatusSolicitacaoLiquidacao(StatusSolicitacaoLiquidacao statusSolicitacaoLiquidacao) {
        this.statusSolicitacaoLiquidacao = statusSolicitacaoLiquidacao;
    }

    public AreaAdministrativa getAreaAdministrativa() {
        return areaAdministrativa;
    }

    public void setAreaAdministrativa(AreaAdministrativa areaAdministrativa) {
        this.areaAdministrativa = areaAdministrativa;
    }

//    public Date getDataLiquidacao() {
//        return dataLiquidacao;
//    }
//
//    public void setDataLiquidacao(Date dataLiquidacao) {
//        this.dataLiquidacao = dataLiquidacao;
//    }
//
//    public Date getDataPagamento() {
//        return dataPagamento;
//    }
//
//    public void setDataPagamento(Date dataPagamento) {
//        this.dataPagamento = dataPagamento;
//    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final SolicitacaoLiquidacao other = (SolicitacaoLiquidacao) obj;
        return Objects.equals(this.id, other.id);
    }

}
