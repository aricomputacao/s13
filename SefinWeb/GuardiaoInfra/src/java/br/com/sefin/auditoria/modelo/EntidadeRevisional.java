/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.auditoria.modelo;

import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 * Classe do Projeto Guardião - Criado em 19/04/2013 - Modelo que armazena as
 * informações de auditorias das tabelas que forem auditadas.
 *
 * @author Gilmário
 */
@Entity
@RevisionEntity(RevisionalListener.class)
@Table(name = "entidade_revisional")
public class EntidadeRevisional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eti_id", nullable = false)
    @RevisionNumber
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ent_datarevisao", nullable = false)
    @RevisionTimestamp
    private Date dataRevisao;
    @ManyToOne
    @JoinColumn(referencedColumnName = "usu_documento", name = "usu_documento", nullable = false)
    private Usuario usuario;

    public EntidadeRevisional() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataRevisao() {
        return dataRevisao;
    }

    public void setDataRevisao(Date dataRevisao) {
        this.dataRevisao = dataRevisao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof EntidadeRevisional)) {
            return false;
        }
        EntidadeRevisional other = (EntidadeRevisional) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "EntidadeRevisional{" + "id=" + id + ", dataRevisao=" + dataRevisao + ", usuario=" + usuario + '}';
    }
}
