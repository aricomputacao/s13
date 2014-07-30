/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.CreditoAdicional;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class CreditoAdicionalDAO extends DAO<CreditoAdicional, Integer> implements Serializable {

    public CreditoAdicionalDAO() {
        super(CreditoAdicional.class);
    }
}
