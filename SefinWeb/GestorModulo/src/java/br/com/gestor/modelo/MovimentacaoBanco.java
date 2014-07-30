/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ari
 */
@Entity
@Table(name = "MOV_BANCO")
public class MovimentacaoBanco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "MBACOD")
    private Integer mbacod;

    @Column(name = "MBADATADEBCRED")
    @Temporal(TemporalType.DATE)
    private Date mbadatadebcred;
    
    @NotNull
    @Column(name = "MBADATALANCA")
    @Temporal(TemporalType.DATE)
    private Date mbadatalanca;
    
    @NotNull
    @Column(name = "MBAVALOR")
    private BigDecimal mbavalor;
    
    @NotNull
    @Column(name = "MBATIPDOCUMENTO")
    private char mbatipdocumento;
    
    @Size(max = 20)
    @Column(name = "MBANUMDOCUMENTO")
    private String mbanumdocumento;
    @Size(max = 60)
    @Column(name = "MBAHISTORICO")
    private String mbahistorico;
    @Size(max = 1)
    @Column(name = "MBATRANSFERENCIA")
    private String mbatransferencia;
    @Column(name = "MBACONCILIADO")
    private Integer mbaconciliado;
    
    @Column(name = "MBACONTADEBITO")
    private Integer contaDebito;
    
    
    @Column(name = "MBACONTACREDITO")
    private Integer contaCredito;
    
    public MovimentacaoBanco() {
    }

    public MovimentacaoBanco(Integer mbacod) {
        this.mbacod = mbacod;
    }

 

    public Integer getMbacod() {
        return mbacod;
    }

    public void setMbacod(Integer mbacod) {
        this.mbacod = mbacod;
    }

    public Date getMbadatadebcred() {
        return mbadatadebcred;
    }

    public void setMbadatadebcred(Date mbadatadebcred) {
        this.mbadatadebcred = mbadatadebcred;
    }

    public Date getMbadatalanca() {
        return mbadatalanca;
    }

    public void setMbadatalanca(Date mbadatalanca) {
        this.mbadatalanca = mbadatalanca;
    }

    public BigDecimal getMbavalor() {
        return mbavalor;
    }

    public void setMbavalor(BigDecimal mbavalor) {
        this.mbavalor = mbavalor;
    }

    public char getMbatipdocumento() {
        return mbatipdocumento;
    }

    public void setMbatipdocumento(char mbatipdocumento) {
        this.mbatipdocumento = mbatipdocumento;
    }

    public String getMbanumdocumento() {
        return mbanumdocumento;
    }

    public void setMbanumdocumento(String mbanumdocumento) {
        this.mbanumdocumento = mbanumdocumento;
    }

  

    public String getMbahistorico() {
        return mbahistorico;
    }

    public void setMbahistorico(String mbahistorico) {
        this.mbahistorico = mbahistorico;
    }

    public String getMbatransferencia() {
        return mbatransferencia;
    }

    public void setMbatransferencia(String mbatransferencia) {
        this.mbatransferencia = mbatransferencia;
    }

    public Integer getMbaconciliado() {
        return mbaconciliado;
    }

    public void setMbaconciliado(Integer mbaconciliado) {
        this.mbaconciliado = mbaconciliado;
    }

    public Integer getContaDebito() {
        return contaDebito;
    }

    public void setContaDebito(Integer contaDebito) {
        this.contaDebito = contaDebito;
    }

    public Integer getContaCredito() {
        return contaCredito;
    }

    public void setContaCredito(Integer contaCredito) {
        this.contaCredito = contaCredito;
    }

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mbacod != null ? mbacod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimentacaoBanco)) {
            return false;
        }
        MovimentacaoBanco other = (MovimentacaoBanco) object;
        if ((this.mbacod == null && other.mbacod != null) || (this.mbacod != null && !this.mbacod.equals(other.mbacod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gestor.modelo.MovimentacaoBanco[ mbacod=" + mbacod + " ]";
    }
    
}
