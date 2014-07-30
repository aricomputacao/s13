/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.Usuario;
import br.com.sefin.enumerated.StatusOrdemCompra;
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 *
 * @author ari
 */
@Entity
@Table(name = "ordem_compra", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class OrdemCompra implements Serializable {

    @Id
    @Column(name = "ord_comp_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cre_id", referencedColumnName = "cre_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Credor credor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lic_id", referencedColumnName = "lic_id", nullable = false)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Licitacao licitacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "uno_id", nullable = false, referencedColumnName = "uno_id"),
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    private UnidadeOrcamentaria unidadeOrcamentaria;
    @Column(nullable = false, name = "ord_comp_status")
    @Enumerated(EnumType.STRING)
    private StatusOrdemCompra status;
    @Column(nullable = false, name = "ord_comp_valor_total")
    private BigDecimal valorTotal;
    @Column(name = "ord_comp_data_emissao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;
    // Usuario emissor da ordem de compra
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "usu_documento_emissor", referencedColumnName = "usu_documento", nullable = false)
    private Usuario emissor;
    // Usuario responsavel pelo cancelamento da ordem de compra
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "usu_documento_cancelou", referencedColumnName = "usu_documento", nullable = true)
    private Usuario cancelou;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "con_id", nullable = true, referencedColumnName = "con_id")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Contrato contrato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusOrdemCompra getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemCompra status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Usuario getEmissor() {
        return emissor;
    }

    public void setEmissor(Usuario emissor) {
        this.emissor = emissor;
    }

    public Usuario getCancelou() {
        return cancelou;
    }

    public void setCancelou(Usuario cancelou) {
        this.cancelou = cancelou;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final OrdemCompra other = (OrdemCompra) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
