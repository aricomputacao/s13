/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dataiterativa;

import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author gilmario
 */
public class SolicitacaoFinanceiraDataList extends ListDataModel<SolicitacaoFinanceira> implements SelectableDataModel<SolicitacaoFinanceira>, Serializable {

    @EJB
    private SolicitacaoFinanceiraController controller;

    public SolicitacaoFinanceiraDataList() {
    }

    public SolicitacaoFinanceiraDataList(List<SolicitacaoFinanceira> list) {
        super(list);
    }

    @Override
    public Object getRowKey(SolicitacaoFinanceira t) {
        try {
            return t.getId();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public SolicitacaoFinanceira getRowData(String id) {
        try {
            return controller.carregar(id);
        } catch (Exception e) {
            return null;
        }
    }

}
