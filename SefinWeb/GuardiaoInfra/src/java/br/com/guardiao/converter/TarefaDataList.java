/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.modelo.Tarefa;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * Classe do Projeto guardiao - Criado em 26/03/2013 - Modelo de tabela
 * selecionavel para o modelo Tarefa
 *
 * @author Gilmário
 */
public class TarefaDataList extends ListDataModel<Tarefa> implements SelectableDataModel<Tarefa> {

    public TarefaDataList() {
    }

    public TarefaDataList(List<Tarefa> list) {
        super(list);
    }

    @Override
    public Object getRowKey(Tarefa t) {
        return t.getId();
    }

    @Override
    public Tarefa getRowData(String nome) {
        List<Tarefa> list = (List<Tarefa>) getWrappedData();
        for (Tarefa s : list) {
            if (s.getId().equals(Long.parseLong(nome))) {
                return s;
            }
        }
        return null;
    }
}
