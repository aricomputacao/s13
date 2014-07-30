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
import javax.persistence.Table;

/**
 * Classe do Projeto SIAFI - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "UNIDADE_MEDIDA", catalog = "", schema = "")
public class UnidadeMedida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "UNMCOD")
    private Integer id;
    @Column(name = "UNMNOME")
    private String nome;
    @Column(name = "UNMABREVIACAO")
    private String abreviacao;
    @Column(name = "UNMEXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public UnidadeMedida() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
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
        final UnidadeMedida other = (UnidadeMedida) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UnidadeMedida{" + "id=" + id + ", nome=" + nome + ", abreviacao=" + abreviacao + ", exportado=" + exportado + '}';
    }
}
