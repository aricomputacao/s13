/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto Siafi - Criado em 06/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "natureza_despesa", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NaturezaDespesa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false, length = 8, name = "nad_id")
    private String id;
    @Column(nullable = false, length = 500, name = "nad_nome")
    private String nome;
    @Column(nullable = true, length = 2000, name = "nad_uso")
    private String uso;
    @Column(nullable = true, length = 255, name = "nad_localizacao")
    private String localizacao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cad_id", referencedColumnName = "cad_id", nullable = true)
    private CategoriaDespesa categoriaDespesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "gnd_id", referencedColumnName = "gnd_id", nullable = true)
    private GrupoNaturezaDespesa grupoNaturezaDespesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "mad_id", referencedColumnName = "mad_id", nullable = true)
    private ModalidadeAplicacaoDespesa modalidadeAplicacaoDespesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "eld_id", referencedColumnName = "eld_id", nullable = true)
    private ElementoDespesa elementoDespesa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sed_id", referencedColumnName = "sed_id", nullable = true)
    private SubElementoDespesa subElementoDespesa;

    public NaturezaDespesa() {
    }

    public NaturezaDespesa(String id) {
        this.id = id;
    }

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

    public CategoriaDespesa getCategoriaDespesa() {
        return categoriaDespesa;
    }

    public void setCategoriaDespesa(CategoriaDespesa categoriaDespesa) {
        this.categoriaDespesa = categoriaDespesa;
    }

    public GrupoNaturezaDespesa getGrupoNaturezaDespesa() {
        return grupoNaturezaDespesa;
    }

    public void setGrupoNaturezaDespesa(GrupoNaturezaDespesa grupoNaturezaDespesa) {
        this.grupoNaturezaDespesa = grupoNaturezaDespesa;
    }

    public ModalidadeAplicacaoDespesa getModalidadeAplicacaoDespesa() {
        return modalidadeAplicacaoDespesa;
    }

    public void setModalidadeAplicacaoDespesa(ModalidadeAplicacaoDespesa modalidadeAplicacaoDespesa) {
        this.modalidadeAplicacaoDespesa = modalidadeAplicacaoDespesa;
    }

    public ElementoDespesa getElementoDespesa() {
        return elementoDespesa;
    }

    public void setElementoDespesa(ElementoDespesa elementoDespesa) {
        this.elementoDespesa = elementoDespesa;
    }

    public SubElementoDespesa getSubElementoDespesa() {
        return subElementoDespesa;
    }

    public void setSubElementoDespesa(SubElementoDespesa subElementoDespesa) {
        this.subElementoDespesa = subElementoDespesa;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NaturezaDespesa)) {
            return false;
        }
        NaturezaDespesa other = (NaturezaDespesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.siafi.modelo.NaturezaDespesa[ id=" + id + " ]";
    }
}
