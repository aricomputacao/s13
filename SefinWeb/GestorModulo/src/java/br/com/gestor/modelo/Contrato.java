/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Classe do Projeto SIAFI - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "CONTRATOS", catalog = "", schema = "")
public class Contrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CTRCOD")
    private Integer id;
    @Column(name = "CTRNUMERO")
    private String numero;
    @Column(name = "CTRUNIDADE")
    private String unidadeAdministrativa;
    @Column(name = "CTRORGAO")
    private String orgao;
    @JoinColumn(name = "CTRLICITACAO", referencedColumnName = "LICCOD")
    @ManyToOne
    private Licitacao licitacao;
    @JoinColumn(name = "CTRPESSOA", referencedColumnName = "PESCOD")
    @ManyToOne
    private Credor credor;
    @Column(name = "CTREXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public Contrato() {
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

    public String getUnidadeAdministrativa() {
        return unidadeAdministrativa;
    }

    public void setUnidadeAdministrativa(String unidadeAdministrativa) {
        this.unidadeAdministrativa = unidadeAdministrativa;
    }

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Contrato other = (Contrato) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contrato{" + "id=" + id + ", numero=" + numero + ", unidadeAdministrativa=" + unidadeAdministrativa + ", orgao=" + orgao + ", licitacao=" + licitacao + ", credor=" + credor + ", exportado=" + exportado + '}';
    }
}
