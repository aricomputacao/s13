/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import java.util.Objects;
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
 * Classe do Projeto guardiao Criada em 20/03/2013 - Representa uma tela de um
 * módulo de um sistema
 *
 * @author Gilmário
 *
 */
@Entity
@Table(name = "tarefa", schema = "guardiao", uniqueConstraints
        = @UniqueConstraint(columnNames = {"mod_id", "tar_nome"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tarefa implements Serializable {

    //  Nome da tarefa e chave primaria da classe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tar_id", nullable = false)
    private Long id;
    //Nome da Tarefa
    @Column(name = "tar_nome", nullable = false)
    private String nome;
    @Column(name = "tar_mnemonico", nullable = false, length = 255, unique = true)
    private String mnemonico;
    // Chave estrangeira da tabela modulo importada para a Tarefa.
    @ManyToOne
    @Cascade(CascadeType.MERGE)
    @JoinColumn(name = "mod_id", nullable = false, referencedColumnName = "mod_id")
    private Modulo modulo;
    // Descrição da tarefa
    @Column(name = "tar_descricao", nullable = false)
    private String descricao;
    //  Módulo ao qual á tarefa pertence

    public Tarefa() {
    }

    public Tarefa(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public String getMnemonico() {
        return mnemonico;
    }

    public void setMnemonico(String mnemonico) {
        this.mnemonico = mnemonico;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Tarefa other = (Tarefa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tarefa{" + "id=" + id + ", nome=" + nome + ", modulo=" + modulo + ", descricao=" + descricao + '}';
    }
}
