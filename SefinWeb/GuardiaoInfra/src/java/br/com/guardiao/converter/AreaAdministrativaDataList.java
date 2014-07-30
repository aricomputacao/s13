/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.modelo.AreaAdministrativa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * Classe do Projeto ******* - Criado em 17/05/2013 -
 *
 * @author Gilmário
 */
public class AreaAdministrativaDataList extends ListDataModel<AreaAdministrativa> implements SelectableDataModel<AreaAdministrativa>, Serializable {

    @EJB
    private AreaAdministrativaController controller;

    public AreaAdministrativaDataList() {
    }

    public AreaAdministrativaDataList(List<AreaAdministrativa> lista) {
        super(lista);
    }

    @Override
    public Object getRowKey(AreaAdministrativa t) {
        return t.getId();
    }

    @Override
    public AreaAdministrativa getRowData(String id) {
        try {
            return controller.carregar(Long.parseLong(id));
        } catch (Exception e) {
            return null;
        }
    }
}
