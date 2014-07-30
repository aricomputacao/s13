/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dataiterativa;

import br.com.siafi.controller.EmpenhoDetalheController;
import br.com.siafi.modelo.EmpenhoDetalhe;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author ari
 */
public class EmpenhoDetalheDataList extends ListDataModel<EmpenhoDetalhe> implements SelectableDataModel<EmpenhoDetalhe>, Serializable {

    @EJB
    private EmpenhoDetalheController controller;

    public EmpenhoDetalheDataList() {
    }

    public EmpenhoDetalheDataList(List<EmpenhoDetalhe> list) {
        super(list);
    }

    @Override
    public Object getRowKey(EmpenhoDetalhe t) {
        try {
            return t.getId();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EmpenhoDetalhe getRowData(String id) {
        try {
            return controller.carregar(id);
        } catch (Exception e) {
            return null;
        }
    }

}
