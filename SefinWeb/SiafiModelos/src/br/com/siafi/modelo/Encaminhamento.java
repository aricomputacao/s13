/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @Sistema SiafiModelos
 * @Data 19/07/2013
 * @author gilmario
 */
@Entity
@Table(name = "encaminhamento", schema = "siafi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Encaminhamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enc_id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sol_id", referencedColumnName = "sol_id", nullable = false)
    private SolicitacaoFinanceira solicitacaoFinanceira;
    @Column(name = "enc_data_encaminhamento", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEncaminhamento;
    @Column(name = "enc_data_recebimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRecebimento;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usu_encaminhou_documento", referencedColumnName = "usu_documento", nullable = false)
    private Usuario usuarioEncaminhou;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usu_recebeu_documento", referencedColumnName = "usu_documento")
    private Usuario usuarioRecebeu;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "are_id", referencedColumnName = "are_id", nullable = false)
    private AreaAdministrativa destino;
    @Column(name = "enc_observacao", nullable = false, length = 2000)
    private String observacao;

    public Encaminhamento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SolicitacaoFinanceira getSolicitacaoFinanceira() {
        return solicitacaoFinanceira;
    }

    public void setSolicitacaoFinanceira(SolicitacaoFinanceira solicitacaoFinanceira) {
        this.solicitacaoFinanceira = solicitacaoFinanceira;
    }

    public Date getDataEncaminhamento() {
        return dataEncaminhamento;
    }

    public void setDataEncaminhamento(Date dataEncaminhamento) {
        this.dataEncaminhamento = dataEncaminhamento;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Usuario getUsuarioEncaminhou() {
        return usuarioEncaminhou;
    }

    public void setUsuarioEncaminhou(Usuario usuarioEncaminhou) {
        this.usuarioEncaminhou = usuarioEncaminhou;
    }

    public Usuario getUsuarioRecebeu() {
        return usuarioRecebeu;
    }

    public void setUsuarioRecebeu(Usuario usuarioRecebeu) {
        this.usuarioRecebeu = usuarioRecebeu;
    }

    public AreaAdministrativa getDestino() {
        return destino;
    }

    public void setDestino(AreaAdministrativa destino) {
        this.destino = destino;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Encaminhamento other = (Encaminhamento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Encaminhamento{" + "id=" + id + ", solicitacaoFinanceira=" + solicitacaoFinanceira + ", dataEncaminhamento=" + dataEncaminhamento + ", dataRecebimento=" + dataRecebimento + ", usuarioEncaminhou=" + usuarioEncaminhou + ", usuarioRecebeu=" + usuarioRecebeu + ", destino=" + destino + ", observacao=" + observacao + '}';
    }
}
