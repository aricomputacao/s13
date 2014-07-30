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
 *
 * @author gilmario
 */
@Entity
@Table(name = "RPEMPENHO", catalog = "", schema = "")
public class RPEmpenho implements Serializable {

    @Id
    @Column(name = "RPECOD")
    private Integer id;
    @Column(name = "RPENUMERO")
    private String numero;
    @Column(name = "RPEORGAO")
    private String orgao;
    @Column(name = "RPEUNIDADE")
    private String unidade;
    @Column(name = "RPEFUNCAO")
    private String funcao;
    @Column(name = "RPESUBFUNCAO")
    private String subFuncao;
    @Column(name = "RPEPROGRAMA")
    private String programa;
    @Column(name = "RPEPA")
    private String projetoAtividade;
    @Column(name = "RPESUBPROJETO")
    private String subProjeto;
    @Column(name = "RPENATUREZA")
    private String natureza;
    @Column(name = "RPEPESSOA")
    private Integer pessoa;
    @Column(name = "RPEEXERCICIO")
    private Integer exercicio;
    @Column(name = "RPECENTROCUSTO")
    private Integer centrocusto;
    @Temporal(TemporalType.DATE)
    @Column(name = "RPEDATA")
    private Date dataEmpenho;
    @Column(name = "RPEMODALIDADE")
    private String modalidade;
    @Column(name = "RPEVALOR")
    private BigDecimal valor;
    @Column(name = "RPEHISTORICO")
    private String historico;
    @Column(name = "RPECONVENIO")
    private Integer convenio;
    @Column(name = "RPECONTRATO")
    private Integer contrato;
    @Column(name = "RPEOBRA")
    private Integer obra;
    @Column(name = "RPEEVENTO")
    private Integer evento;
    @Column(name = "RPESUBCONTA")
    private Integer subconta;
    @Column(name = "RPEORGAOATUAL")
    private String orgaoAtual;
    @Column(name = "RPEUNIDADEATUAL")
    private String unidadeAtual;
    @Column(name = "RPESTATUS")
    private String status;
    @Column(name = "RPESTATUSMSG")
    private String statusMsg;
    @Column(name = "RPEFUNDEB")
    private Integer fundeb;
    @Column(name = "RPETIPOOBRA")
    private String tipoObra;
    @Column(name = "CODENTIDADE")
    private Integer entidade;
    @Column(name = "RPEEXPORTADO")
    private Boolean exportado;

    public RPEmpenho() {
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
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

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSubFuncao() {
        return subFuncao;
    }

    public void setSubFuncao(String subFuncao) {
        this.subFuncao = subFuncao;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getProjetoAtividade() {
        return projetoAtividade;
    }

    public void setProjetoAtividade(String projetoAtividade) {
        this.projetoAtividade = projetoAtividade;
    }

    public String getSubProjeto() {
        return subProjeto;
    }

    public void setSubProjeto(String subProjeto) {
        this.subProjeto = subProjeto;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public Integer getPessoa() {
        return pessoa;
    }

    public void setPessoa(Integer pessoa) {
        this.pessoa = pessoa;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    public Integer getCentrocusto() {
        return centrocusto;
    }

    public void setCentrocusto(Integer centrocusto) {
        this.centrocusto = centrocusto;
    }

    public Date getDataEmpenho() {
        return dataEmpenho;
    }

    public void setDataEmpenho(Date dataEmpenho) {
        this.dataEmpenho = dataEmpenho;
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

    public Integer getConvenio() {
        return convenio;
    }

    public void setConvenio(Integer convenio) {
        this.convenio = convenio;
    }

    public Integer getContrato() {
        return contrato;
    }

    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }

    public Integer getObra() {
        return obra;
    }

    public void setObra(Integer obra) {
        this.obra = obra;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(Integer evento) {
        this.evento = evento;
    }

    public Integer getSubconta() {
        return subconta;
    }

    public void setSubconta(Integer subconta) {
        this.subconta = subconta;
    }

    public String getOrgaoAtual() {
        return orgaoAtual;
    }

    public void setOrgaoAtual(String orgaoAtual) {
        this.orgaoAtual = orgaoAtual;
    }

    public String getUnidadeAtual() {
        return unidadeAtual;
    }

    public void setUnidadeAtual(String unidadeAtual) {
        this.unidadeAtual = unidadeAtual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Integer getFundeb() {
        return fundeb;
    }

    public void setFundeb(Integer fundeb) {
        this.fundeb = fundeb;
    }

    public String getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(String tipoObra) {
        this.tipoObra = tipoObra;
    }

    public Integer getEntidade() {
        return entidade;
    }

    public void setEntidade(Integer entidade) {
        this.entidade = entidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final RPEmpenho other = (RPEmpenho) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
