/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "convenio", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Audited
public class Convenio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cov_id", nullable = false)
    private Integer id;
    @NotNull(message = "Informe o objetivo")
    @Length(max = 1024, message = "Tamanho maximo do objetivo excedido")
    @Column(name = "cov_objetivo", nullable = false, length = 1024)
    private String objetivo;
    @NotNull(message = "Informe o Credor")
    @JoinColumn(name = "cre_id", referencedColumnName = "cre_id", nullable = false)
    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Credor credor;
    @NotNull(message = "Informe a unidade orçamentaria")
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "uno_id", nullable = false, referencedColumnName = "uno_id"),
        @JoinColumn(name = "org_id", nullable = false, referencedColumnName = "org_id"),
        @JoinColumn(name = "exe_ano", nullable = false, referencedColumnName = "exe_ano")})
    private UnidadeOrcamentaria unidadeOrcamentaria;
    @NotNull(message = "Informe o credor")
    @Temporal(TemporalType.DATE)
    @Column(name = "cov_data_inicio", nullable = false)
    private Date dataInicio;
    @NotNull(message = "Informe o número do convênio")
    @Length(max = 20, message = "O Tamanho do número do convênio não pode ser maior que 20")
    @Column(name = "cov_numero", nullable = false, length = 20)
    private String numero;
    @OneToMany(mappedBy = "convenio")
    private List<AditivoConvenio> aditivoConveniosList;

    public Convenio() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<AditivoConvenio> getAditivoConveniosList() {
        return aditivoConveniosList;
    }

    public void setAditivoConveniosList(List<AditivoConvenio> aditivoConveniosList) {
        this.aditivoConveniosList = aditivoConveniosList;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Convenio other = (Convenio) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
