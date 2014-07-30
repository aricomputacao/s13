/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Orgao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Guardião - Criado em 13/05/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class AreaAdministrativaDAO extends DAO<AreaAdministrativa, Long> implements Serializable {

    public AreaAdministrativaDAO() {
        super(AreaAdministrativa.class);
    }

    public List<AreaAdministrativa> listarPorAreaAdm(Orgao orgao) throws Exception {
        return getEm().createQuery(" from AreaAdministrativa where orgao=:o ", AreaAdministrativa.class).setParameter("o", orgao).getResultList();
    }

    public List<AreaAdministrativa> listarPorTipo(List<TipoAreaAdm> listadeTipos) throws Exception {
        return getEm().createQuery("SELECT a FROM AreaAdministrativa a WHERE a.tipoAreaAdm IN (:listaTipo)").setParameter("listaTipo", listadeTipos).getResultList();
    }

    public List<AreaAdministrativa> listar(Orgao orgao) throws Exception {
        return getEm().createQuery("SELECT a FROM AreaAdministrativa a WHERE a.orgao =:orgao").setParameter("orgao", orgao).getResultList();
    }

    public AreaAdministrativa areaAdministrativaTipo(TipoAreaAdm adm) throws Exception {
        return (AreaAdministrativa) getEm().createQuery("SELECT a FROM AreaAdministrativa a WHERE a.tipoAreaAdm =:adm").setParameter("adm", adm).getSingleResult();

    }

    public AreaAdministrativa pegarAreaFinanceiraOrgao(Orgao o, TipoAreaAdm adm) {
        return (AreaAdministrativa) getEm().createQuery("SELECT a FROM AreaAdministrativa a WHERE a.tipoAreaAdm =:adm and a.orgao = :o").setParameter("o", o).setParameter("adm", adm).getSingleResult();

    }

    public List<AreaAdministrativa> listarFinanceiro() {
             return getEm().createQuery("SELECT a FROM AreaAdministrativa a WHERE a.areaFinanceira = :fi ORDER BY a.nome").setParameter("fi", true).getResultList();

    }
}
