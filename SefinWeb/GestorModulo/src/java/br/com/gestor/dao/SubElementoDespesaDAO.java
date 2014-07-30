package br.com.gestor.dao;

import br.com.gestor.modelo.SubElementoDespesa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorSubElementoDespesaDao")
public class SubElementoDespesaDAO extends GestorDAO<SubElementoDespesa> implements Serializable {

    public SubElementoDespesaDAO() {
        super(SubElementoDespesa.class);
    }
}
