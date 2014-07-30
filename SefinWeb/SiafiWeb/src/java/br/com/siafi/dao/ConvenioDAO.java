/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Convenio;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class ConvenioDAO extends DAO<Convenio, Integer> implements Serializable {

    public ConvenioDAO() {
        super(Convenio.class);
    }
    // Convenios validos são aqueles que possuem aditivos com data superior a data da solicitação

    public List<Convenio> listarConveniosValidos(UnidadeOrcamentaria uo, Date dataSolicitacao, Credor credor) throws Exception {
        return getEm().createQuery(" SELECT c FROM Convenio c WHERE c.unidadeOrcamentaria =:uo AND c.credor =:credor ").setParameter("credor", credor).setParameter("uo", uo).getResultList();
    }
}
