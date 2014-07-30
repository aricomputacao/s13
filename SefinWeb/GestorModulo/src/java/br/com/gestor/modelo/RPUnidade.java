/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "RPUNIDADE", catalog = "", schema = "")
public class RPUnidade implements Serializable {

    @EmbeddedId
    private RPUnidadePK rpUnidadePK;
    @Column(name = "RPUORGAOATUAL")
    private String orgaoAtual;
    @Column(name = "RPUUNIDADEATUAL")
    private String unidadeAtual;
    @Column(name = "RPUUNIFICADO")
    private String unificado;
    @Column(name = "RPUNOME")
    private String nome;
    
    @Column(name = "RPUEXPORTADO")
    private Boolean exportado;

    public RPUnidade() {
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    
    public RPUnidadePK getRpUnidadePK() {
        return rpUnidadePK;
    }

    public void setRpUnidadePK(RPUnidadePK rpUnidadePK) {
        this.rpUnidadePK = rpUnidadePK;
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

    public String getUnificado() {
        return unificado;
    }

    public void setUnificado(String unificado) {
        this.unificado = unificado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.rpUnidadePK);
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
        final RPUnidade other = (RPUnidade) obj;
        if (!Objects.equals(this.rpUnidadePK, other.rpUnidadePK)) {
            return false;
        }
        return true;
    }
}
