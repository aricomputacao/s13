/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Classe do Projeto GuardiaoModelos criada em 26/06/2013
 *
 * @author: ari
 */
@Embeddable
public class Endereco implements Serializable {

    // Rua, av. ou outro parte do endereço da pessoa
    @Column(name = "end_logradouro")
    private String logradouro;
    // numero do endereço da pessoa
    @Column(name = "end_numero")
    private String numero;
    // bairro parte do endereço da pessoa
    @Column(name = "end_bairro")
    private String bairro;
    // complemento parte do endereço da pessoa
    @Column(name = "end_complemento", length = 1024)
    private String complemento;
//    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "mun_id", referencedColumnName = "mun_id")
//    private Municipio cidade;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

}
