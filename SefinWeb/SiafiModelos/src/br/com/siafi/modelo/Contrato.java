/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.NotAudited;

/**
 * Classe do Projeto SiafiModelos criada em 25/06/2013
 *
 * @author: ari
 */
@Entity
@Table(name = "contrato", schema = "siafi", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uno_id", "org_id", "exe_ano", "con_numero", "orc_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contrato implements Serializable {

    @Id
    @Column(name = "con_id", nullable = false)
    private Integer id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "uno_id", nullable = false, referencedColumnName = "uno_id"),
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    @NotAudited
    private UnidadeOrcamentaria unidadeOrcamentaria;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false, name = "cre_id", referencedColumnName = "cre_id")
    private Credor credor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "lic_id", referencedColumnName = "lic_id")
    private Licitacao licitacao;
    @Column(name = "con_numero", nullable = false)
    private String numero;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "orc_id", referencedColumnName = "orc_id", nullable = false)
    private Orcamento orcamento;
    @OneToMany(mappedBy = "contrato", fetch = FetchType.EAGER)
    private List<Aditivo> aditivosList;

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<Aditivo> getAditivosList() {
        return aditivosList;
    }

    public void setAditivosList(List<Aditivo> aditivosList) {
        this.aditivosList = aditivosList;
    }
}
