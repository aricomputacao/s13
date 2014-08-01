/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.dao;

import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.SistemaConfiguracao;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * @Sistema GuardiaoInfra
 * @Data 07/08/2013
 * @author gilmario
 */
@Stateless
public class SistemaConfiguracaoDAO extends DAO<SistemaConfiguracao, Integer> implements Serializable {

    public SistemaConfiguracaoDAO() {
        super(SistemaConfiguracao.class);
    }

    public List<SistemaConfiguracao> listar(Sistema sistema) throws Exception {
        return getEm().createQuery("SELECT a FROM SistemaConfiguracao a WHERE a.sistema =:sistema ").setParameter("sistema", sistema).getResultList();
    }

    // Retorna o valor de uma configuração ou um valor default
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Serializable pegarValorConfiguracaoDef(Serializable def, String nome, Sistema sistema) throws Exception {
        try {
            return getEm().createQuery("SELECT a.valor FROM SistemaConfiguracao a WHERE a.sistema =:sis AND a.nome=:nome", Serializable.class).setParameter("sis", sistema).setParameter("nome", nome).getSingleResult();
        } catch (Exception e) {
            return def;
        }
    }
}
