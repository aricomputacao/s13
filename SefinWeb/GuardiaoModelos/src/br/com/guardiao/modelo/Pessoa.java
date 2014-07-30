/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto guardiao - Criado em 20/03/2013 - Classe que identifica
 * informações pessoais do usuario do sistema
 *
 * @author Gilmário
 */
@Embeddable
public class Pessoa implements Serializable {

    // Email da pessoa
    @Email
    @Column(name = "pes_email", length = 255)
    private String email;
    // Nome da pessoa
    @NotNull
    @Column(name = "pes_nome", length = 255, nullable = false)
    @Length(min = 3)
    private String nome;
    // cidade parte do endereço da pessoa
    @Column(name = "pes_telefone")
    private String telefone;

    public Pessoa() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {

        this.telefone = Util.removeCaracteresFromString(telefone, "(;); ;-", ";");
    }
}
