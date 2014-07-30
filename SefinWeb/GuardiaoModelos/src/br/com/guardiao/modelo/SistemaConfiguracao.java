/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import br.com.guardiao.enumerated.TipoConfiguracao;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @Sistema GuardiaoModelos
 * @Data 07/08/2013
 * @author gilmario
 */
@Entity
@Table(name = "sistema_configuracao", schema = "guardiao", uniqueConstraints
        = @UniqueConstraint(columnNames = {"sis_id", "sic_nome"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class SistemaConfiguracao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sic_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "sis_id", referencedColumnName = "sis_id")
    @NotAudited
    private Sistema sistema;
    @Column(name = "sic_nome", unique = true, length = 20)
    private String nome;
    @Column(name = "sic_descricao", length = 1024)
    private String descricao;
    @Column(name = "sic_valor")
    private Serializable valor;
    @Column(name = "sic_tipo_configuracao")
    @Enumerated(EnumType.STRING)
    private TipoConfiguracao tipoConfiguracao;

    public SistemaConfiguracao() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
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

    public Serializable getValor() {
        return valor;
    }

    public void setValor(Serializable valor) {
        this.valor = valor;
    }

    public TipoConfiguracao getTipoConfiguracao() {
        return tipoConfiguracao;
    }

    public void setTipoConfiguracao(TipoConfiguracao tipoConfiguracao) {
        this.tipoConfiguracao = tipoConfiguracao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final SistemaConfiguracao other = (SistemaConfiguracao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SistemaConfiguracao{" + "nome=" + nome + '}';
    }
}
