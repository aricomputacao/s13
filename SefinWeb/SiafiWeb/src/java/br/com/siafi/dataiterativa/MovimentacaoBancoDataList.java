/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.siafi.dataiterativa;

import br.com.siafi.controller.MovimentacaoBancoController;
import br.com.siafi.modelo.MovimentacaoBanco;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author ari
 */
public class MovimentacaoBancoDataList extends ListDataModel<MovimentacaoBanco> implements SelectableDataModel<MovimentacaoBanco>, Serializable  {

    @EJB
    private MovimentacaoBancoController controller;

    public MovimentacaoBancoDataList() {
    }

    public MovimentacaoBancoDataList(List<MovimentacaoBanco> list) {
        super(list);
    }

     
    
    
   @Override
    public Object getRowKey(MovimentacaoBanco t) {
        try {
            return t.getId().toString();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public MovimentacaoBanco getRowData(String id) {
        try {
            return controller.carregar(Long.parseLong(id));
        } catch (Exception e) {
            return null;
        }
    }
    
}
