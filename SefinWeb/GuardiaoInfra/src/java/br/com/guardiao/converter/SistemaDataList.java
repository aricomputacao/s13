/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.modelo.Sistema;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * Classe do Projeto guardiao - Criado em 26/03/2013 - Modelo de tabela
 * selecionavel para o modelo Sistema
 *
 * @author Gilmário
 */
public class SistemaDataList extends ListDataModel<Sistema> implements SelectableDataModel<Sistema>, Serializable {

    @EJB
    private SistemaController controler;

    public SistemaDataList() {
    }

    public SistemaDataList(List<Sistema> list) {
        super(list);
    }

    @Override
    public Object getRowKey(Sistema t) {
        return t.getId();
    }

    @Override
    public Sistema getRowData(String id) {
        try {
            return controler.carregar(Long.parseLong(id));
        } catch (Exception e) {
            return null;
        }

    }
}
