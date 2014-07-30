/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Arquivo do projeto SiafiModelos criando em 08/08/2013
 *
 * @author ari
 */
public class CentroCustoDto implements Serializable {

    private UnidadeOrcamentaria orcamentaria;
    private CentroCusto centroCusto;
    //Somatorio do valor das solicitações financeiras por cota
    private BigDecimal total;

    public CentroCustoDto() {
    }

   
    
    public CentroCustoDto(UnidadeOrcamentaria orcamentaria, CentroCusto centroCusto, BigDecimal total) {
        this.orcamentaria = orcamentaria;
        this.centroCusto = centroCusto;
        this.total = total;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public UnidadeOrcamentaria getOrcamentaria() {
        return orcamentaria;
    }

    public void setOrcamentaria(UnidadeOrcamentaria orcamentaria) {
        this.orcamentaria = orcamentaria;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.centroCusto);
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
        final CentroCustoDto other = (CentroCustoDto) obj;
        if (!Objects.equals(this.centroCusto, other.centroCusto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CentroCustoDto{" + "orcamentaria=" + orcamentaria + ", centroCusto=" + centroCusto + ", total=" + total + '}';
    }
}
