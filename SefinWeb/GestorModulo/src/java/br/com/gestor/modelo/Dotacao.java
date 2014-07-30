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
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "DOTACOES", catalog = "", schema = "")
public class Dotacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "DOTREDUZIDO", nullable = false)
    private Integer id;
    @Column(name = "DOTNUMEROPROJETO", nullable = false)
    private String projetoAtividade;
    @Column(name = "DOTELEMENTO", nullable = false)
    private String naturezaDespesa;
    @Column(name = "DOTVALOR")
    private BigDecimal dotValor;
    @Column(name = "DOTEXPORTADO")
    private Boolean exportado;

    public Boolean getExportado() {
        return exportado;
    }

    public Dotacao() {
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

    public String getProjetoAtividade() {
        return projetoAtividade;
    }

    public void setProjetoAtividade(String projetoAtividade) {
        this.projetoAtividade = projetoAtividade;
    }

    public String getNaturezaDespesa() {
        return naturezaDespesa;
    }

    public void setNaturezaDespesa(String naturezaDespesa) {
        this.naturezaDespesa = naturezaDespesa;
    }

    public BigDecimal getDotValor() {
        return dotValor;
    }

    public void setDotValor(BigDecimal dotValor) {
        this.dotValor = dotValor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Dotacao other = (Dotacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dotacao{" + "id=" + id + ", projetoAtividade=" + projetoAtividade + ", naturezaDespesa=" + naturezaDespesa + '}';
    }
}
