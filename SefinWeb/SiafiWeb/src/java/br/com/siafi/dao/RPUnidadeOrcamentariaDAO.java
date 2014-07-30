/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.RpUnidadeOrcamentaria;
import br.com.siafi.modelo.RpUnidadeOrcamentariaPk;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class RPUnidadeOrcamentariaDAO extends DAO<RpUnidadeOrcamentaria, RpUnidadeOrcamentariaPk> implements Serializable {

    public RPUnidadeOrcamentariaDAO() {
        super(RpUnidadeOrcamentaria.class);
    }
}
