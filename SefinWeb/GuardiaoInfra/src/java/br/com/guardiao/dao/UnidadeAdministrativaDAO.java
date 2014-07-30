/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeAdministrativa;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Classe do Projeto guardiao criada em 22/03/2013
 *
 * @author: ari
 */
@Stateless
public class UnidadeAdministrativaDAO extends DAO<UnidadeAdministrativa, String> implements Serializable {

    public UnidadeAdministrativaDAO() {
        super(UnidadeAdministrativa.class);
    }

    /*
     * Metodo para checar se  já esta cadastrado uma unidade administrativa
     *
     * @Retorno - boolean
     * @Parametros - (String - código da unidade)
     */
    public boolean checarUnidade(String id) throws Exception {
        try {
            if (carregar(id) == null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // verificar se o objeto existe no banco de dados
    public boolean existe(String id) throws Exception {
        try {
            return carregar(id) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Metodo para checar se orgão já esta cadastrado na unidade administrativa
     *
     * @Retorno - boolean
     * @Parametros - (Órgão selecionado, Lista de orgãos da unidade)
     */
    public boolean checarOrgao(Orgao o, List<Orgao> orgaos) throws Exception {
        if (orgaos != null) {
            for (Orgao orgao : orgaos) {
                if (o.equals(orgao)) {
                    return true;
                }
            }
        }
        return false;
    }
}
