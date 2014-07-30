/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.auditoria.modelo.SolicitacaFinaceiraAudPk;
import br.com.siafi.auditoria.modelo.SolicitacaoFinanceiraAud;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class SolicitacaoFinanceiraAudDAO extends DAO<SolicitacaoFinanceiraAud, SolicitacaFinaceiraAudPk> implements Serializable {

    public SolicitacaoFinanceiraAudDAO() {
        super(SolicitacaoFinanceiraAud.class);
    }

    public List<SolicitacaoFinanceiraAud> listarAuditoria(String campo, String id) throws Exception {
        return getEm().createQuery("SELECT a FROM SolicitacaoFinanceiraAud a WHERE a.solicitacaFinaceiraAudPk.id=:id order by a.solicitacaFinaceiraAudPk.entidadeRevisional.dataRevisao ").setParameter("id", id).getResultList();
    }
}
