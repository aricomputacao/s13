/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.RpOrgao;
import br.com.siafi.modelo.RpOrgaoPk;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class RPOrgaoDAO extends DAO<RpOrgao, RpOrgaoPk> implements Serializable {

    public RPOrgaoDAO() {
        super(RpOrgao.class);
    }
}
