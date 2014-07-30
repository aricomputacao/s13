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
 * Arquivo do projeto GestorModulo criando em 05/08/2013
 * @author ari
 */
@Entity
@Table(name = "CENTROCUSTO",schema = "", catalog = "")
public class CentroCusto implements Serializable{
    @Id
    @Column(name = "CENCOD",nullable = false)
    private Integer id;
    @Column(name = "CENNOME",nullable = false,length = 1024)
    private String nome;
    @Column(name = "CENELEMENTO",length = 2)
    private String elemento;
    @Column(name = "CENEXPORTADO",nullable = false)
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

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
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
        final CentroCusto other = (CentroCusto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
