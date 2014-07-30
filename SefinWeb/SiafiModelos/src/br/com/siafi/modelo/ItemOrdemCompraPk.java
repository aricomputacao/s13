/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe do Projeto Siafi - Criado em 10/07/2013 -
 *
 * @author Gilmário
 */
@Embeddable
public class ItemOrdemCompraPk implements Serializable {

    @Column(name = "ite_ord_comp_id", nullable = false)
    private Integer ordem;
    @Column(name = "ord_comp_id_id", nullable = false)
    private Long idOrdemCompra;

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public ItemOrdemCompraPk() {
    }

    public Long getIdOrdemCompra() {
        return idOrdemCompra;
    }

    public void setIdOrdemCompra(Long idOrdemCompra) {
        this.idOrdemCompra = idOrdemCompra;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.ordem);
        hash = 89 * hash + Objects.hashCode(this.idOrdemCompra);
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
        final ItemOrdemCompraPk other = (ItemOrdemCompraPk) obj;
        if (!Objects.equals(this.ordem, other.ordem)) {
            return false;
        }
        if (!Objects.equals(this.idOrdemCompra, other.idOrdemCompra)) {
            return false;
        }
        return true;
    }
}
