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
 * Classe do Projeto Siafi - Criado em 25/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "ITENS_DESP", catalog = "", schema = "")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ITDCOD")
    private Long id;
    @JoinColumn(name = "ITDGRUPO", referencedColumnName = "GIDCOD")
    @ManyToOne
    private GrupoItem grupoItem;
    @JoinColumn(name = "IDTUNIDADEMEDIDA", referencedColumnName = "UNMCOD")
    @ManyToOne
    private UnidadeMedida unidadeMedida;
    @Column(name = "IDTNOME")
    private String nome;
    @Column(name = "IDTEXPECIFICACAO", length = 2000)
    private String especificacao;
    @Column(name = "ITDEXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GrupoItem getGrupoItem() {
        return grupoItem;
    }

    public void setGrupoItem(GrupoItem grupoItem) {
        this.grupoItem = grupoItem;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecificacao() {
        return especificacao;
    }

    public void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
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
        final Item other = (Item) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", grupoItem=" + grupoItem + ", unidadeMedida=" + unidadeMedida + ", nome=" + nome + ", especificacao=" + especificacao + ", exportado=" + exportado + '}';
    }
}
