/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.AditivoConvenio;
import br.com.siafi.modelo.Convenio;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author gilmario
 */
@Stateless
public class AditivoConvenioDAO extends DAO<AditivoConvenio, Integer> implements Serializable {

    public AditivoConvenioDAO() {
        super(AditivoConvenio.class);
    }

    public boolean validaAditivo(AditivoConvenio a) throws Exception {
        Query q = getEm().createQuery("SELECT a FROM SolicitacaoFinanceira a WHERE a.convenio =:convenio AND a.dataSolicitacao >= :dataAditivo");
        q.setParameter("convenio", a.getConvenio());
        q.setParameter("dataAditivo", a.getDataCadastro());
        return q.getResultList().isEmpty();
    }

    public List<AditivoConvenio> listar(Convenio convenio) throws Exception {
        return getEm().createQuery("SELECT a FROM AditivoConvenio a WHERE a.convenio = :con ").setParameter("con", convenio).getResultList();
    }
}
