/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "PROJETOSATIVIDADES", catalog = "", schema = "")
public class ProjetoAtividade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PRJNUMERO", nullable = false)
    private String id;
    @Column(name = "PRJORGAO", nullable = false)
    private String orgao;
    @Column(name = "PRJUNIDADE", nullable = false)
    private String unidadeOrcamentaria;
    @Column(name = "PRJFUNCAO", nullable = false)
    private String funcao;
    @Column(name = "PRJSUBFUNCAO", nullable = false)
    private String subfuncao;
    @Column(name = "PRJPROGRAMA", nullable = false)
    private String programa;
    @Column(name = "PRJCODIGO", nullable = false)
    private String acao;
    @Column(name = "PRJNOME", nullable = false)
    private String nome;
    @Column(name = "PRJABREV", nullable = false)
    private String abreviacao;
    @Column(name = "PRJORGAOUNIDADE", nullable = false)
    private String orgaoUnidade;
    @Column(name = "PRJDATAABERTURA", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAbertura;
    @Column(name = "PRJTIPO", nullable = false)
    private Integer tipo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(String unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSubfuncao() {
        return subfuncao;
    }

    public void setSubfuncao(String subfuncao) {
        this.subfuncao = subfuncao;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public String getOrgaoUnidade() {
        return orgaoUnidade;
    }

    public void setOrgaoUnidade(String orgaoUnidade) {
        this.orgaoUnidade = orgaoUnidade;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final ProjetoAtividade other = (ProjetoAtividade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjetoAtividade{" + "id=" + id + ", orgao=" + orgao + ", unidadeOrcamentaria=" + unidadeOrcamentaria + ", funcao=" + funcao + ", subfuncao=" + subfuncao + ", programa=" + programa + ", acao=" + acao + ", nome=" + nome + ", abreviacao=" + abreviacao + ", orgaoUnidade=" + orgaoUnidade + ", dataAbertura=" + dataAbertura + ", tipo=" + tipo + '}';
    }
}
