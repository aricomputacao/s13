/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dataiterativa;

import br.com.siafi.controller.EncaminhamentoLiquidacaoController;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author gilmario
 */
public class EncaminhamentoLiquidacaoDataList extends ListDataModel<EncaminhamentoLiquidacao> implements SelectableDataModel<EncaminhamentoLiquidacao>, Serializable {

    @EJB
    private EncaminhamentoLiquidacaoController controler;

    public EncaminhamentoLiquidacaoDataList() {
    }

    public EncaminhamentoLiquidacaoDataList(List<EncaminhamentoLiquidacao> list) {
        super(list);
    }

    @Override
    public Object getRowKey(EncaminhamentoLiquidacao t) {
        return t.getId().toString();
    }

    @Override
    public EncaminhamentoLiquidacao getRowData(String chave) {
        try {
            return controler.carregar(Long.parseLong(chave));
        } catch (Exception e) {
            return null;
        }

    }

}
