/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.converter;

import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.UnidadeOrcamentariaPK;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 * Classe do Projeto guardiao - Criado em 03/04/2013 - Modelo de tabela
 * selecionável para UnidadeOrcamentaria
 *
 * @author Gilmário
 */
public class UnidadeOrcamentariaDataList extends ListDataModel<UnidadeOrcamentaria> implements SelectableDataModel<UnidadeOrcamentaria>, Serializable {

    @EJB
    private UnidadeOrcamentariaController controller;

    public UnidadeOrcamentariaDataList() {
        super();
    }

    public UnidadeOrcamentariaDataList(List<UnidadeOrcamentaria> lista) {
        super(lista);
    }

    @Override
    public Object getRowKey(UnidadeOrcamentaria t) {
        return t.getUnidadeOrcamentariaPK().getId() + "|" + t.getUnidadeOrcamentariaPK().getIdOrgao() + "|" + t.getUnidadeOrcamentariaPK().getExercicio_ano();
    }

    @Override
    public UnidadeOrcamentaria getRowData(String id) {
        try {
            return controller.carregar(new UnidadeOrcamentariaPK(id.split("\\|")[0], id.split("\\|")[1], Integer.parseInt(id.split("\\|").toString())));
        } catch (Exception e) {
            return null;
        }
    }
}
