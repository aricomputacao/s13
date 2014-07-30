/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * Classe do Projeto guardiao - Criado em 20/03/2013 - Gerenciar o acesso dos
 * usuário a determinada tarefa
 *
 * @author Gilmário
 */
@Entity
@Table(name = "permissao", schema = "guardiao", uniqueConstraints
        = @UniqueConstraint(columnNames = {"tar_id", "usu_documento"}))
@Cache(region = "permissaoCache", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class Permissao implements Serializable {

    // Chave primaria da Classe permissao
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_id", nullable = false)
    private Long id;
    // Tarefa a qual a permissão se refere
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tar_id", nullable = false, referencedColumnName = "tar_id")
    @NotAudited
    private Tarefa tarefa;
    // Usuario a qual a permissão se refere
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "usu_documento", referencedColumnName = "usu_documento", nullable = false)
    private Usuario usuario;
    // Permitir ao usuario acessar e consultar um determinado formulario/página(Tarefas)
    @Column(name = "per_consultar")
    private boolean consultar;
    // Permitir ao usuario editar valores em uma detarminada tarefa
    @Column(name = "per_editar")
    private boolean editar;
    // Permitir ao usuario excluir valores em uma detarminada tarefa
    @Column(name = "per_excluir")
    private boolean excluir;
    // Permitir ao usuario incluir valores em uma detarminada tarefa
    @Column(name = "per_incluir")
    private boolean incluir;

    public Permissao() {
    }

    public Permissao(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    public boolean isIncluir() {
        return incluir;
    }

    public void setIncluir(boolean incluir) {
        this.incluir = incluir;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Permissao other = (Permissao) obj;
        return this.id == other.id || (this.id != null && this.id.equals(other.id));
    }
}
