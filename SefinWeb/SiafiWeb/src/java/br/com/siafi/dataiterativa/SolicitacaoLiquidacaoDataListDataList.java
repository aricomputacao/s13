/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dataiterativa;

import br.com.siafi.controller.EncaminhamentoLiquidacaoController;
import br.com.siafi.controller.SolicitacaoLiquidacaoController;
import br.com.siafi.modelo.EncaminhamentoLiquidacao;
import br.com.siafi.modelo.SolicitacaoLiquidacao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author gilmario
 */
public class SolicitacaoLiquidacaoDataListDataList extends ListDataModel<SolicitacaoLiquidacao> implements SelectableDataModel<SolicitacaoLiquidacao>, Serializable {

    @EJB
    private SolicitacaoLiquidacaoController controler;

    public SolicitacaoLiquidacaoDataListDataList() {
    }

    public SolicitacaoLiquidacaoDataListDataList(List<SolicitacaoLiquidacao> list) {
        super(list);
    }

    @Override
    public Object getRowKey(SolicitacaoLiquidacao t) {
        return t.getId().toString();
    }

    @Override
    public SolicitacaoLiquidacao getRowData(String chave) {
        try {
            return controler.carregar(Long.parseLong(chave));
        } catch (Exception e) {
            return null;
        }

    }

}
