/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Arquivo do projeto SiafiModelos criando em 16/10/2013
 *
 * @author ari
 */
@Entity
@Table(name = "rpempenho", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RpEmpenho implements Serializable {

    @Id
    @Column(name = "rem_id")
    private Integer id;
    @Column(name = "rem_numero")
    private String numero;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "rp_codigo", referencedColumnName = "rp_codigo", nullable = false),
        @JoinColumn(name = "rp_exercicio", referencedColumnName = "rp_exercicio", nullable = false),
        @JoinColumn(name = "rp_undidade_id", referencedColumnName = "rp_undidade_id", nullable = false)})
    private RpUnidadeOrcamentaria rpunidadeOrcamentaria;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pja_id", referencedColumnName = "pja_id")
    private RpProjetoAtividade rpProjetoAtividade;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cet_id", referencedColumnName = "cet_id")
    private CentroCusto centroCusto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cre_id", referencedColumnName = "cre_id")
    private Credor credor;
    @Column(name = "rem_data")
    @Temporal(TemporalType.DATE)
    private Date dataEmpenho;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "nad_id", referencedColumnName = "nad_id")
    private NaturezaDespesa natureza;
    @Column(name = "rem_modalidade")
    private String modalidade;
    @Column(name = "rem_valor")
    private BigDecimal valor;
    @Column(name = "rem_historico",length = 1024)
    private String historico;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cov_id", referencedColumnName = "cov_id")
    private Convenio convenio;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "con_id", referencedColumnName = "con_id")
    private Contrato contrato;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "uno_id", nullable = false, referencedColumnName = "uno_id"),
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    private UnidadeOrcamentaria unidadeOrcamentariaAtual;
    @Column(name = "rem_status")
    private String status;
    @Column(name = "rem_cod_entidade")
    private Integer codEntidade;

    public RpEmpenho() {
    }

    public RpEmpenho(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public RpUnidadeOrcamentaria getRpunidadeOrcamentaria() {
        return rpunidadeOrcamentaria;
    }

    public void setRpunidadeOrcamentaria(RpUnidadeOrcamentaria rpunidadeOrcamentaria) {
        this.rpunidadeOrcamentaria = rpunidadeOrcamentaria;
    }

    public RpProjetoAtividade getRpProjetoAtividade() {
        return rpProjetoAtividade;
    }

    public void setRpProjetoAtividade(RpProjetoAtividade rpProjetoAtividade) {
        this.rpProjetoAtividade = rpProjetoAtividade;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Date getDataEmpenho() {
        return dataEmpenho;
    }

    public void setDataEmpenho(Date dataEmpenho) {
        this.dataEmpenho = dataEmpenho;
    }

    public NaturezaDespesa getNatureza() {
        return natureza;
    }

    public void setNatureza(NaturezaDespesa natureza) {
        this.natureza = natureza;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentariaAtual() {
        return unidadeOrcamentariaAtual;
    }

    public void setUnidadeOrcamentariaAtual(UnidadeOrcamentaria unidadeOrcamentariaAtual) {
        this.unidadeOrcamentariaAtual = unidadeOrcamentariaAtual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCodEntidade() {
        return codEntidade;
    }

    public void setCodEntidade(Integer codEntidade) {
        this.codEntidade = codEntidade;
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
        final RpEmpenho other = (RpEmpenho) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
