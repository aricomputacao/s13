/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Arquivo do projeto GestorModulo criando em 29/07/2013
 *
 * @author ari
 */
@Entity
@Table(name = "CONTA", schema = "", catalog = "")
public class Conta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CONCOD", nullable = false)
    private Integer id;
    
    @Column(name = "CONNOME",nullable = false)
    private String nome;
    @Column(name = "CONNUMERO",nullable = false)
    private Integer numero;
    @Column(name = "CONNOMECLATURA")
    private String nomeclatura;
    
    @Column(name = "CONORGAO",length = 2,nullable = false)
    private String orgao;
    @Column(name = "CONUNIDADEORCAMENTARIA",length = 2,nullable = false)
    private String unidadeAdministrativa;
    
    @Column(name = "CONFONTERECURSO",length = 6)
    private String fonteRecurso;
    
    @Column(name = "CONSALDOINICIAL")
    private BigDecimal saldoInicial;
    
    @Column(name = "CONEXPORTADO")
    private Boolean exportado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNomeclatura() {
        return nomeclatura;
    }

    public void setNomeclatura(String nomeclatura) {
        this.nomeclatura = nomeclatura;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getUnidadeAdministrativa() {
        return unidadeAdministrativa;
    }

    public void setUnidadeAdministrativa(String unidadeAdministrativa) {
        this.unidadeAdministrativa = unidadeAdministrativa;
    }

 

    public String getFonteRecurso() {
        return fonteRecurso;
    }

    public void setFonteRecurso(String fonteRecurso) {
        this.fonteRecurso = fonteRecurso;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Conta other = (Conta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
