/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Classe do Projeto guardiao criada em 20/03/2013 - Classe que representa os
 * sistemas cadastrados no sistema guardião
 *
 * @author Gilmário
 */
@Entity
@Table(name = "sistema", schema = "guardiao")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Sistema implements Serializable {

    // chave primaria da classe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sis_id", nullable = false)
    private Long id;
    // nome do sistema
    @Column(name = "sis_nome", length = 255, nullable = false, unique = true)
    private String nome;
    // breve descricao do sistema
    @Column(name = "sis_descricao", length = 10000, nullable = false)
    private String descricao;
    @Column(name = "sis_sigla", length = 20, unique = true, nullable = false)
    private String sigla;
    @Column(name = "sis_mnemonico", length = 3, unique = true, nullable = false)
    private String mnemonico;

    public Sistema() {
    }

    public Sistema(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getMnemonico() {
        return mnemonico;
    }

    public void setMnemonico(String mnemonico) {
        this.mnemonico = mnemonico;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Sistema other = (Sistema) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sistema{" + "id=" + id + ", nome=" + nome + ", descricao=" + descricao + '}';
    }
}
