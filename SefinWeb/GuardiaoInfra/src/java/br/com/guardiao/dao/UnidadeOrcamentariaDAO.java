/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.UnidadeOrcamentariaPK;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto guardiao criada em 22/03/2013
 *
 * @author: ari
 */
@Stateless
public class UnidadeOrcamentariaDAO extends DAO<UnidadeOrcamentaria, UnidadeOrcamentariaPK> implements Serializable {

    public UnidadeOrcamentariaDAO() {
        super(UnidadeOrcamentaria.class);
    }

    public boolean checar(String id) throws Exception {
        try {
            TypedQuery<UnidadeOrcamentaria> qry;
            qry = getEm().createQuery("SELECT ud FROM UnidadeOrcamentaria ud where ud.id = :id ", UnidadeOrcamentaria.class);
            qry.setParameter("id", id);
            return qry.getSingleResult() != null;
        } catch (Exception e) {
            return false;
        }

    }

    /*
     * Metodo para checar se Unidade Orçamentaria existe
     *
     * @Retorno - boolean
     * @Parametros - (UnidadeOrcamentariaPK id)
     */
    public boolean checar(UnidadeOrcamentariaPK pK) throws Exception {
        try {
            TypedQuery<UnidadeOrcamentaria> qry;
            qry = getEm().createQuery("SELECT ud FROM UnidadeOrcamentaria ud where ud.unidadeOrcamentariaPK = :id ", UnidadeOrcamentaria.class);
            qry.setParameter("id", pK);
            return qry.getSingleResult() != null;
        } catch (Exception e) {
            return false;
        }
    }

    // Vrifica se o registro já existe no banco de dados
    public boolean existe(UnidadeOrcamentariaPK pK) throws Exception {
        try {
            TypedQuery<UnidadeOrcamentaria> qry;
            qry = getEm().createQuery(
                    "SELECT ud FROM UnidadeOrcamentaria ud where ud.unidadeOrcamentariaPK = :id ", UnidadeOrcamentaria.class);
            qry.setParameter("id", pK);
            return qry.getSingleResult() != null;
        } catch (Exception e) {
            return false;
        }

    }

    /*
     * Metodo para buscar por id composto a Unidade Orçamentaria
     *
     * @Retorno - Unidade Orçamentaria
     * @Parametros - (String idUnidade,String idOrgao)
     */
    public UnidadeOrcamentaria buscarId(String cod) throws Exception {
        String idUnd;
        String idOrg;
        String anoExe;
        idUnd = cod.substring(0, 2);
        idOrg = cod.substring(2, 4);
        anoExe = cod.substring(4, 8);
        try {
            TypedQuery<UnidadeOrcamentaria> qry;
            qry = getEm().createQuery("SELECT ud FROM UnidadeOrcamentaria ud where ud.unidadeOrcamentariaPK.id = :idUnd "
                    + "AND ud.unidadeOrcamentariaPK.idOrgao = :idOrg AND ud.unidadeOrcamentariaPK.exercicio_ano =:ano", UnidadeOrcamentaria.class);
            qry.setParameter("idUnd", idUnd);
            qry.setParameter("idOrg", idOrg);
            qry.setParameter("ano", Integer.parseInt(anoExe));
            return qry.getSingleResult();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Método para trazer todas as unidades orçamentarias de um determinado orgão
    public List<UnidadeOrcamentaria> listar(Orgao orgao) throws Exception {
        return getEm().createQuery("select a from UnidadeOrcamentaria a where a.orgao = :orgao AND a.ativo = true ").setParameter("orgao", orgao).getResultList();
    }

    /**
     * Para listar somente as unidades orçamentárias ativas
     *
     * @return
     * @throws java.lang.Exception
     */
    public List<UnidadeOrcamentaria> listarAtivos() throws Exception {
        return getEm().createQuery("SELECT a from UnidadeOrcamentaria a where a.ativo = true ORDER BY a.unidadeOrcamentariaPK.idOrgao, a.unidadeOrcamentariaPK.id ").getResultList();
    }

    public UnidadeOrcamentaria unidadeAtiva(String idOrgao, String idUnidade) throws Exception {
        Query q = getEm().createQuery("SELECT u FROM UnidadeOrcamentaria u WHERE u.unidadeOrcamentariaPK.idOrgao =:idOrgao AND u.unidadeOrcamentariaPK.id =:idUnidade AND u.ativo =:ativo ");
        q.setParameter("idOrgao", idOrgao);
        q.setParameter("idUnidade", idUnidade);
        q.setParameter("ativo", true);
        return (UnidadeOrcamentaria) q.getSingleResult();
    }

}
