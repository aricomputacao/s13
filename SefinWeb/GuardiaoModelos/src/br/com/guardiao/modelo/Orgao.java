/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

/**
 * Classe do Projeto guardiao criada em 20/03/2013 - Classe que representa
 * conjuntos de Orgãos cadastrados nos sistemas
 *
 * @author: ari
 */
@Entity
@Table(name = "orgao", schema = "siafi", uniqueConstraints =
        @UniqueConstraint(columnNames = {"org_id", "exe_ano"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class Orgao implements Serializable {

    /**
     * chave primaria da classe. referente ao mesmo id do registro de Orgão do
     * Sistema de Contabilidade.
     */
    @EmbeddedId
    private OrgaoPk id;
    //Campo referente a chave estrangeira Unidade Administrativa
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "una_id", nullable = false, referencedColumnName = "una_id")
    private UnidadeAdministrativa unidadeAdministrativa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano", insertable = false, updatable = false)
    private Exercicio exercicio;
    //Campo referente  ao nome do orgão
    @Column(name = "org_nome", length = 256, nullable = false, unique = true)
    private String nome;
    //Campo referente  a abreviação do orgão
    @Column(name = "org_abreviacao", length = 128, nullable = false)
    private String abreviacao;
    //Campo referente  ao cnpj do orgão
    @Column(name = "org_cnpj", length = 14, nullable = false)
    private String cnpj;
    //Campo referente  ao endereço do orgão
    @Column(name = "org_endereco", length = 1024)
    private String endereco;
    //Campo referente  ao telefone do orgão
    @Column(name = "org_fone", length = 10)
    private String fone;
    //Campo referente  ao email do orgão
    @Email
    @Column(name = "org_email", length = 80)
    private String email;
    //Campo para desecrever o objetivo do orgão
    @Column(name = "org_objetivo", length = 1024)
    private String objetivo;
    //Campo para verificar se o orgão ainda está ativo
    @Column(name = "org_ativo", nullable = false)
    private boolean ativo;
    //Lista de orgãos cadastrados para o usuário
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<UnidadeOrcamentaria> unidadesOrcamentarias;

    public List<UnidadeOrcamentaria> getUnidadesOrcamentarias() {
        return unidadesOrcamentarias;
    }

    public void setUnidadesOrcamentarias(List<UnidadeOrcamentaria> unidadesOrcamentarias) {
        this.unidadesOrcamentarias = unidadesOrcamentarias;
    }

    public Orgao() {
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public OrgaoPk getId() {
        return id;
    }

    public void setId(OrgaoPk id) {
        this.id = id;
    }

    public UnidadeAdministrativa getUnidadeAdministrativa() {
        return unidadeAdministrativa;
    }

    public String getNome() {
        return nome;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getFone() {
        return fone;
    }

    public String getEmail() {
        return email;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setUnidadeAdministrativa(UnidadeAdministrativa unidadeAdministrativa) {
        this.unidadeAdministrativa = unidadeAdministrativa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = Util.retiraFormatacaoCNPJ(cnpj);
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setFone(String fone) {
        this.fone = Util.removeCaracteresFromString(fone, "(;); ;-", ";");

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Orgao other = (Orgao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Orgao{" + "id=" + id + ", nome=" + nome + ", nome=" + nome + '}';
    }
}
