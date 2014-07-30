/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto Siafi - Criado em 06/06/2013 -
 *
 * @author Gilmário
 */
@Entity
@Table(name = "rpprojeto_atividade", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RpProjetoAtividade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "pja_id", nullable = false, length = 20)
    private String id;
    @Column(name = "pja_abreviacao", nullable = false, length = 255)
    private String abreviacao;
    @Column(name = "pja_tipo")
    private String tipo;
    @Column(name = "pja_data_abertura")
    @Temporal(TemporalType.DATE)
    private Date dataAbertura;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "rp_codigo", referencedColumnName = "rp_codigo", nullable = false),
        @JoinColumn(name = "rp_exercicio", referencedColumnName = "rp_exercicio", nullable = false),
        @JoinColumn(name = "rp_undidade_id", referencedColumnName = "rp_undidade_id", nullable = false)})
    private RpUnidadeOrcamentaria unidadeOrcamentaria;
    @Length(max = 2,min = 2)
    @Column(name = "rp_funcao",nullable = false,length = 2)    
    private String funcao;
    @Length(max = 3,min = 3)
    @Column(name = "rp_sub_funcao",nullable = false,length = 3)
    private String subfuncao;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id", nullable = false)
    private Programa programa;
    @Length(max = 3, min = 3)
    @Column(name = "pja_acao", nullable = false, length = 3)
    private String acao;
    @Length(max = 255)
    @Column(name = "pja_nome", nullable = false, length = 255)
    private String nome;

    public RpProjetoAtividade() {
    }

    public RpProjetoAtividade(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public RpUnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(RpUnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSubfuncao() {
        return subfuncao;
    }

    public void setSubfuncao(String subfuncao) {
        this.subfuncao = subfuncao;
    }

 

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final RpProjetoAtividade other = (RpProjetoAtividade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjetoAtividade{" + "id=" + id + ", abreviacao=" + abreviacao + '}';
    }
}
