/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.siafi.modelo.SubElementoDespesa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 10/06/2013 - Dao do modelo Sub Elemento
 * de Despesa
 *
 * @author Gilmário
 */
@Stateless
public class SubElementoDespesaDAO extends DAO<SubElementoDespesa, String> implements Serializable {

    public SubElementoDespesaDAO() {
        super(SubElementoDespesa.class);
    }
}
