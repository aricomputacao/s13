/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dataiterativa;

import br.com.siafi.controller.EncaminhamentoController;
import br.com.siafi.modelo.Encaminhamento;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author gilmario
 */
public class EncaminhamentoDataList extends ListDataModel<Encaminhamento> implements SelectableDataModel<Encaminhamento>, Serializable {

    @EJB
    private EncaminhamentoController controler;

    public EncaminhamentoDataList() {
    }

    public EncaminhamentoDataList(List<Encaminhamento> list) {
        super(list);
    }

    @Override
    public Object getRowKey(Encaminhamento t) {
        return t.getId().toString();
    }

    @Override
    public Encaminhamento getRowData(String chave) {
        try {
            return controler.carregar(Long.parseLong(chave));
        } catch (Exception e) {
            return null;
        }

    }

}
