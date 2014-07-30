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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Length;

/**
 * Classe do Projeto guardiao - Criado em 20/03/2013 - Classe que identifica os
 * acessos de um usuario ao sistema
 *
 * @author Gilmário
 */
@Entity
@Table(name = "usuario", schema = "guardiao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class Usuario implements Serializable {

    /**
     * Representa um documento único que identifica o usuário no sistema
     *
     */
    @Id
    @Column(length = 25, nullable = false, name = "usu_documento")
    @Length(min = 1, max = 25)
    private String documento;
    // Representa o orgão a qual a pessoa pertence
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumns({
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    private Orgao orgao;
    // Senha da pessoa utilizado no login do sistema
    @NotNull
    @Length(min = 6, max = 40)
    @Column(name = "usu_senha", length = 1024, nullable = false)
    private String senha;
    private Pessoa pessoa;
    //Lista de sistemas cadastrados para o usuário
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @NotAudited
    private List<Sistema> sistemas;
    // indicar se a usuario está ativa ou não no sistema
    @Column(name = "pes_acesso")
    private boolean acesso;
    // Lista de permissões do usuario
    @Cache(region = "permissaoCache", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Permissao> permissoes;
    // Representa o cargo  da pessoa
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "car_id", nullable = false, referencedColumnName = "car_id")
    private Cargo cargo;
    // Lista de Unidades Orçamentarias  do usuario
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @OrderBy(value = "unidadeOrcamentariaPK.idOrgao, unidadeOrcamentariaPK.id")
    private List<UnidadeOrcamentaria> unidadeOrcamentarias;
    //Área administrativa do usuario
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "are_id", nullable = true, referencedColumnName = "are_id")
    private AreaAdministrativa areaAdministrativa;
    @Embedded
    private Endereco endereco;

    public Usuario() {
    }

    public Usuario(String documento) {
        this.documento = documento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Orgao getOrgao() {
        return orgao;
    }

    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        CriptografiaSenha cs = new CriptografiaSenha();
        this.senha = cs.criptografarSenha(senha);

    }

    public boolean isAcesso() {
        return acesso;
    }

    public void setAcesso(boolean acesso) {
        this.acesso = acesso;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Sistema> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<Sistema> sistemas) {
        this.sistemas = sistemas;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<UnidadeOrcamentaria> getUnidadeOrcamentarias() {
        return unidadeOrcamentarias;
    }

    public void setUnidadeOrcamentarias(List<UnidadeOrcamentaria> unidadeOrcamentarias) {
        this.unidadeOrcamentarias = unidadeOrcamentarias;
    }

    public AreaAdministrativa getAreaAdministrativa() {
        return areaAdministrativa;
    }

    public void setAreaAdministrativa(AreaAdministrativa areaAdministrativa) {
        this.areaAdministrativa = areaAdministrativa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.documento);
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
        final Usuario other = (Usuario) obj;
        return Objects.equals(this.documento, other.getDocumento());
    }

    @Override
    public String toString() {
        return "Usuario{ documento " + documento + '}';
    }
}
