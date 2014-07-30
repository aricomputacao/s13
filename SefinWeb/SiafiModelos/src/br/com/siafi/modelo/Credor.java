/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.Pessoa;
import br.com.guardiao.modelo.Util;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto SiafiModelos criada em 25/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "credor", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Credor implements Serializable {

    @Id
    @Column(name = "cre_id", nullable = false)
    private Integer id;
    @Embedded
    private Pessoa pessoa;
    @Column(name = "cre_pis_pasep")
    private String pisPasep;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tip_cre_id", nullable = false, referencedColumnName = "tip_cre_id")
    private TipoCredor tipoCredor;
    @Column(name = "cre_documento")
    private String documento;
    // Apenas para merito de relatórios
    @Column(name = "cre_endereco")
    private String endereco;
    @Column(name = "cre_bairro")
    private String bairro;
    @Column(name = "cre_cidade")
    private String cidade;
    @Column(name = "cre_uf")
    private String uf;

    public Credor() {
    }

    public Credor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public TipoCredor getTipoCredor() {
        return tipoCredor;
    }

    public void setTipoCredor(TipoCredor tipoCredor) {
        this.tipoCredor = tipoCredor;
    }

    public String getDocumento() {
        return documento;
    }

    public String getDocumentoFormatado() {
        if (documento != null) {
            if (documento.length() == 11) {
                return Util.formataCPF(documento);
            } else if (documento.length() == 14) {
                return Util.formataCNPJ(documento);
            } else {
                return documento;
            }
        }
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Credor other = (Credor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Credor{" + "pessoa=" + pessoa.getNome() + '}';
    }
}
