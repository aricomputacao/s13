/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

/**
 * Classe do Projeto guardiao criada em 20/03/2013 - Classe que representa as
 * Unidades Administrativas cadastrados no sistema guardião
 *
 * @author Ari
 */
@Entity
@Table(name = "unidade_administrativa", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class UnidadeAdministrativa implements Serializable {

    /**
     * chave primaria da classe. referente ao mesmo id do registro de Unidade
     * Administrativa do Sistema de Contabilidade.
     */
    @Id
    @Column(name = "una_id", nullable = false, length = 2)
    private String id;
    //valor referente ao nome da Unidade Administrativa
    @Column(name = "una_nome", nullable = false, unique = true)
    private String nome;
    //chave estrangeira referente aos orgãos cadastrados para essa Unidade Administrativa
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Orgao> orgaos;

    public List<Orgao> getOrgaos() {
        return orgaos;
    }

    public void setOrgaos(List<Orgao> orgaos) {
        this.orgaos = orgaos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final UnidadeAdministrativa other = (UnidadeAdministrativa) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public UnidadeAdministrativa() {
    }
}
