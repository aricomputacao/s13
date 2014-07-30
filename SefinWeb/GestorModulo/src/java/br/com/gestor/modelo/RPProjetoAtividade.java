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
@Table(name = "RPPROJETOATIVIDADE", catalog = "", schema = "")
public class RPProjetoAtividade implements Serializable {

    @EmbeddedId
    private RPProjetoAtividadePK id;
    @Column(name = "RPPTIPO")
    private Integer tipo;
    @Column(name = "RPPSUBCOD")
    private String subCodigo;
    @Column(name = "RPPNOME")
    private String nome;
    @Column(name = "RPPEXPORTADO")
    private boolean exportado;

    public RPProjetoAtividade() {
    }

    public RPProjetoAtividadePK getId() {
        return id;
    }

    public void setId(RPProjetoAtividadePK id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getSubCodigo() {
        return subCodigo;
    }

    public void setSubCodigo(String subCodigo) {
        this.subCodigo = subCodigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isExportado() {
        return exportado;
    }

    public void setExportado(boolean exportado) {
        this.exportado = exportado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final RPProjetoAtividade other = (RPProjetoAtividade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
