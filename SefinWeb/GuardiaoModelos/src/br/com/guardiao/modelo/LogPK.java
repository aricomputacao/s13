/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe do Projeto guardiao - Criado em 20/03/2013 - Classe que representa a
 * chave primária composta da classe Log
 *
 * @author Gilmário
 */
@Embeddable
public class LogPK implements Serializable {

    // Data e hora do log
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar datalog;
    // Tarefa acessada no momento do log
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tar_id", referencedColumnName = "tar_id", nullable = false)
    private Tarefa tarefa;
    // Usuario do log
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usu_documento", referencedColumnName = "usu_documento", nullable = false)
    private Usuario usuario;

    public LogPK() {
    }

    public LogPK(Calendar datalog, Tarefa tarefa, Usuario usuario) {
        this.datalog = datalog;
        this.tarefa = tarefa;
        this.usuario = usuario;
    }

    public Calendar getDatalog() {
        return datalog;
    }

    public void setDatalog(Calendar datalog) {
        this.datalog = datalog;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.datalog != null ? this.datalog.hashCode() : 0);
        hash = 11 * hash + (this.tarefa != null ? this.tarefa.hashCode() : 0);
        hash = 11 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
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
        final LogPK other = (LogPK) obj;
        if (this.datalog != other.datalog && (this.datalog == null || !this.datalog.equals(other.datalog))) {
            return false;
        }
        if (this.tarefa != other.tarefa && (this.tarefa == null || !this.tarefa.equals(other.tarefa))) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }
}
