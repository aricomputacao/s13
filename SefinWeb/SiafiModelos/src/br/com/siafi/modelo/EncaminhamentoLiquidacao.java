/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 *
 * @author gilmario
 */
@Entity
@Table(schema = "siafi", name = "encaminhamento_liquidacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EncaminhamentoLiquidacao implements Serializable {

    @Id
    @Column(name = "enc_liq_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "enc_liq_data_encaminhamento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataEncaminhamento;
    @Column(name = "enc_liq_data_recebimento")
    @Temporal(TemporalType.DATE)
    private Date dataRecebimento;
    @Column(name = "enc_liq_observacao", nullable = false, length = 2000)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "sol_liq_id", referencedColumnName = "sol_liq_id", nullable = false)
    private SolicitacaoLiquidacao solicitacaoLiquidacao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usu_encaminhou_documento", referencedColumnName = "usu_documento", nullable = false)
    private Usuario usuarioEncaminhou;
    @ManyToOne
    @JoinColumn(name = "are_id_destino", referencedColumnName = "are_id", nullable = false)
    private AreaAdministrativa destino;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usu_recebeu_documento", referencedColumnName = "usu_documento")
    private Usuario usuarioRecebeu;

    public EncaminhamentoLiquidacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public SolicitacaoLiquidacao getSolicitacaoLiquidacao() {
        return solicitacaoLiquidacao;
    }

    public void setSolicitacaoLiquidacao(SolicitacaoLiquidacao solicitacaoLiquidacao) {
        this.solicitacaoLiquidacao = solicitacaoLiquidacao;
    }

    public Usuario getUsuarioEncaminhou() {
        return usuarioEncaminhou;
    }

    public void setUsuarioEncaminhou(Usuario usuarioEncaminhou) {
        this.usuarioEncaminhou = usuarioEncaminhou;
    }

    public AreaAdministrativa getDestino() {
        return destino;
    }

    public void setDestino(AreaAdministrativa destino) {
        this.destino = destino;
    }

    public Usuario getUsuarioRecebeu() {
        return usuarioRecebeu;
    }

    public void setUsuarioRecebeu(Usuario usuarioRecebeu) {
        this.usuarioRecebeu = usuarioRecebeu;
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
        final EncaminhamentoLiquidacao other = (EncaminhamentoLiquidacao) obj;
        return Objects.equals(this.id, other.id);
    }

}
