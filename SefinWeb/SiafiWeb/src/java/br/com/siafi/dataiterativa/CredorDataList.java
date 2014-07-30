/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.siafi.dataiterativa;

import br.com.siafi.controller.CredorController;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author ari
 */
public class CredorDataList extends ListDataModel<Credor> implements SelectableDataModel<Credor>, Serializable {

    @EJB
    private CredorController controller;

    public CredorDataList() {
    }

    

    public CredorDataList(List<Credor> list) {
        super(list);
    }

    @Override
    public Object getRowKey(Credor t) {
          try {
            return t.getId();
        } catch (Exception e) {
            return null;
        }
    }

  
    @Override
    public Credor getRowData(String id) {
        try {
            return controller.carregar(Integer.getInteger(id));
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
  
    
}
