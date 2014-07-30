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
 * Classe do Projeto Siafi - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "GRUPO_ITENS_DESP", catalog = "", schema = "")
public class GrupoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "GIDCOD")
    private Integer id;
    @Column(name = "GIDNOME")
    private String nome;
    @Column(name = "GIDEXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public GrupoItem() {
    }

    public GrupoItem(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final GrupoItem other = (GrupoItem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GrupoItem{" + "id=" + id + ", nome=" + nome + ", exportado=" + exportado + '}';
    }
}
