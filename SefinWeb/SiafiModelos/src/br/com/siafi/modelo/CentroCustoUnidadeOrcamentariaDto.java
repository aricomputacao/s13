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
 * @author ari
 */
public class CentroCustoUnidadeOrcamentariaDto implements Serializable{

    private UnidadeOrcamentaria unidadeOrcamentaria;
    private BigDecimal total;

    public CentroCustoUnidadeOrcamentariaDto(UnidadeOrcamentaria unidadeOrcamentaria, BigDecimal total) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
        this.total = total;
    }
    
    
    

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.unidadeOrcamentaria);
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
        final CentroCustoUnidadeOrcamentariaDto other = (CentroCustoUnidadeOrcamentariaDto) obj;
        if (!Objects.equals(this.unidadeOrcamentaria, other.unidadeOrcamentaria)) {
            return false;
        }
        return true;
    }
    
    
}
