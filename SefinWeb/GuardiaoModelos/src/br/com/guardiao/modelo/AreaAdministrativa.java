/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import br.com.guardiao.enumerated.TipoAreaAdm;
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto Siafi - Criado em 10/05/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "area_administrativa", schema = "siafi",uniqueConstraints = @UniqueConstraint(columnNames = {"org_id","exe_ano","are_nome"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class AreaAdministrativa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "are_id", nullable = false)
    private Long id;
    @Column(length = 255, name = "are_nome", nullable = false)
    @Length(min = 5)
    private String nome;
    @Column(name = "are_acesso_unidade_orcamentaria")
    private boolean acessoUnidadeOrcamentaria;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    private Orgao orgao;
    @Enumerated(EnumType.STRING)
    @Column(name = "are_tipo", nullable = false)
    private TipoAreaAdm tipoAreaAdm;
    @Column(name = "are_financeiro",columnDefinition = "boolean default false")
    private boolean areaFinanceira;

    public boolean isAreaFinanceira() {
        return areaFinanceira;
    }

    public void setAreaFinanceira(boolean areaFinanceira) {
        this.areaFinanceira = areaFinanceira;
    }
    
    

    public AreaAdministrativa() {
    }

    public AreaAdministrativa(Long id) {
        this.id = id;
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

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public boolean isAcessoUnidadeOrcamentaria() {
        return acessoUnidadeOrcamentaria;
    }

    public void setAcessoUnidadeOrcamentaria(boolean acessoUnidadeOrcamentaria) {
        this.acessoUnidadeOrcamentaria = acessoUnidadeOrcamentaria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final AreaAdministrativa other = (AreaAdministrativa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public TipoAreaAdm getTipoAreaAdm() {
        return tipoAreaAdm;
    }

    public void setTipoAreaAdm(TipoAreaAdm tipoAreaAdm) {
        this.tipoAreaAdm = tipoAreaAdm;
    }
}
