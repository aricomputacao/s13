/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gestor.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author gilmario
 */
@Entity
@Table(name = "RPEMPENHOANULACAO", catalog = "", schema = "")
public class RPEmpenhoAnulacao implements Serializable {

    @EmbeddedId
    private RPEmpenhoAnulacaoPK id;
    @Column(name = "RPAVALOR")
    private BigDecimal valor;
    @Column(name = "RPAHISTORICO")
    private String historico;
    @Column(name = "CODENTIDADE")
    private Integer codEntidade;

    public RPEmpenhoAnulacao() {
    }

    public RPEmpenhoAnulacaoPK getId() {
        return id;
    }

    public void setId(RPEmpenhoAnulacaoPK id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Integer getCodEntidade() {
        return codEntidade;
    }

    public void setCodEntidade(Integer codEntidade) {
        this.codEntidade = codEntidade;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final RPEmpenhoAnulacao other = (RPEmpenhoAnulacao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
