/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.TipoLicitacao;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 28/06/2013
 *
 * @author: ari
 */
@Stateless
public class TipoLicitacaoDAO extends DAO<TipoLicitacao, Integer> implements Serializable {

    public TipoLicitacaoDAO() {
        super(TipoLicitacao.class);
    }

}
