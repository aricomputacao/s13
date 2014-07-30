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
 * Classe do Projeto Siafi - Criado em 24/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "PESSOA", catalog = "", schema = "")
public class Credor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PESCOD", nullable = false)
    private Integer id;
    @Column(name = "PESNOME", nullable = false, length = 50)
    private String nome;
    @Column(name = "PESCPF", length = 11)
    private String cpf;
    @Column(name = "PESEMAIL", length = 100)
    private String email;
    @Column(name = "PESCNPJ", length = 14)
    private String cnpj;
    @Column(name = "PESTELEFONE", length = 15)
    private String telefone;
    @Column(name = "PESPASEP")
    private String pasep;
    @Column(name = "PESTIPO", nullable = false)
    private Integer tipo;
    @Column(name = "PESEXPORTADO")
    private Boolean exportado;
    @Column(name = "PESENDERECO")
    private String endereco;
    @Column(name = "PESBAIRRO")
    private String bairro;
    @Column(name = "PESUF")
    private String uf;
    @JoinColumn(name = "PESCIDADE", referencedColumnName = "CIDADECOD")
    @ManyToOne
    private Cidade cidade;

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPasep() {
        return pasep;
    }

    public void setPasep(String pasep) {
        this.pasep = pasep;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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
        return "Credor{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", cnpj=" + cnpj + ", telefone=" + telefone + ", pasep=" + pasep + ", tipo=" + tipo + ", exportado=" + exportado + '}';
    }
}
