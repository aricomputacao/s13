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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Classe do Projeto guardiao Data 20/03/2013 - Classe que representa os módulos
 * dos sistemas cadastrados no guardião módulos é um conjunto de tarefas com um
 * objetivo especifico
 *
 * @author usuario
 *
 */
@Entity
@Table(name = "modulo", schema = "guardiao", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"mod_nome", "sis_id"}),
    @UniqueConstraint(columnNames = {"mod_mnemonico", "sis_id", "mod_pai_id"})})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Modulo implements Serializable {

    // Chave primaria da classe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mod_id", nullable = false)
    private Long id;
    //  Nome do módulo
    @Column(name = "mod_nome", nullable = false)
    private String nome;
    //
    @Column(name = "mod_mnemonico", nullable = false, length = 2)
    private String mnemonico;
    @Column(name = "mod_observacao", length = 1024)
    private String observacao;
    // Sistema a qual o módulo pertence
    @ManyToOne
    @Cascade(CascadeType.MERGE)
    @JoinColumn(name = "sis_id", nullable = false, referencedColumnName = "sis_id")
    private Sistema sistema;
    // Caso essa campo exista ou seja != de nulo então o modulo possue um submódulo
    @ManyToOne
    @Cascade(CascadeType.MERGE)
    @JoinColumn(name = "mod_pai_id", nullable = true, referencedColumnName = "mod_id")
    private Modulo moduloPai;

    public Modulo() {
    }

    public Modulo(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Modulo getModuloPai() {
        return moduloPai;
    }

    public void setModuloPai(Modulo moduloPai) {
        this.moduloPai = moduloPai;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMnemonico() {
        return mnemonico;
    }

    public void setMnemonico(String mnemonico) {
        this.mnemonico = mnemonico;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public String getMnemonicoPai() {
        if (moduloPai != null) {
            if (moduloPai.getMnemonico() != null) {
                return moduloPai.getMnemonicoPai() + mnemonico;
            } else {
                return mnemonico;
            }
        } else {
            return mnemonico;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Modulo other = (Modulo) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
