/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.modelo.dto;

import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.ItemLicitacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Gilmario
 */
public class CredorItemDTO implements Serializable {

    private Credor credor;
    private List<ItemLicitacao> itens;

    public CredorItemDTO() {
        itens = new ArrayList<>();
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public List<ItemLicitacao> getItens() {
        return itens;
    }

    public void setItens(List<ItemLicitacao> itens) {
        this.itens = itens;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.credor);
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
        final CredorItemDTO other = (CredorItemDTO) obj;
        return Objects.equals(this.credor, other.credor);
    }
}
