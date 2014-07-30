/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.OrgaoPk;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto guardiao criada em 22/03/2013 - DAO gerenciador do modelo
 * Orgao
 *
 * @author: ari
 */
@Stateless
public class OrgaoDAO extends DAO<Orgao, OrgaoPk> implements Serializable {

    public OrgaoDAO() {
        super(Orgao.class);
    }

    /*
     * Metodo para checar se Órgão existe
     *
     * @Retorno - boolean
     * @Parametros - (String id)
     */
    public boolean checarOrgao(OrgaoPk id) throws Exception {
        try {
            return carregar(id) == null;
        } catch (Exception e) {
            return false;
        }

    }

    /*
     * Metodo para checar se Unidade Orçamentaria contem na lista de Órgão
     *
     * @Retorno - boolean
     * @Parametros - (UnidadeOrcamentaria selecionado, Lista de UnidadeOrcamentaria do órgão)
     */
    public boolean checarUnidadeOrcamentaria(UnidadeOrcamentaria o, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        if (unidadeOrcamentarias != null) {
            for (UnidadeOrcamentaria uo : unidadeOrcamentarias) {
                if (uo.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existe(OrgaoPk id) throws Exception {
        try {
            return carregar(id) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Orgao orgaoAtivo(String id) throws Exception {
        TypedQuery<Orgao> qry;
        qry = getEm().createQuery("SELECT s FROM Orgao s where s.ativo = true AND s.id.id = :id", Orgao.class);
        qry.setParameter("id", id);
        return qry.getSingleResult();
    }

    public List<Orgao> listarNome(String nome) throws Exception {
        TypedQuery<Orgao> qry;
        qry = getEm().createQuery("SELECT o FROM Orgao o where o.nome like :nome", Orgao.class);
        qry.setParameter("nome", "%" + nome + "%");
        return qry.getResultList();
    }

    public Orgao buscarNome(String nome) throws Exception {
        TypedQuery<Orgao> qry;
        qry = getEm().createQuery("SELECT s FROM Orgao s where s.nome = :nome", Orgao.class);
        qry.setParameter("nome", nome);
        return qry.getSingleResult();
    }

    public List<Orgao> listarAtivos() throws Exception {
        return getEm().createQuery("SELECT s FROM Orgao s where s.ativo = true ORDER BY s.id").getResultList();
    }
}
