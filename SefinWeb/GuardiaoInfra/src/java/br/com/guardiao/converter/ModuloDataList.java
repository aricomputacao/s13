/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.modelo.Modulo;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * Classe do Projeto guardiao - Criado em 26/03/2013 - Modelo de tabela
 * selecionavel para o modelo Modulo
 *
 * @author Gilmário
 */
public class ModuloDataList extends ListDataModel<Modulo> implements SelectableDataModel<Modulo> {

    public ModuloDataList() {
    }

    public ModuloDataList(List<Modulo> list) {
        super(list);
    }

    @Override
    public Object getRowKey(Modulo t) {

        return t.getId();
    }

    @Override
    public Modulo getRowData(String nome) {
        List<Modulo> list = (List<Modulo>) getWrappedData();
        for (Modulo s : list) {
            if (s.getId() == Long.parseLong(nome)) {
                return s;
            } else {
                return null;
            }
        }
        return null;
    }
}
