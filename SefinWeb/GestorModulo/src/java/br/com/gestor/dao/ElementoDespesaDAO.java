package br.com.gestor.dao;

import br.com.gestor.modelo.ElementoDespesa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorElementoDespesaDao")
public class ElementoDespesaDAO extends GestorDAO<ElementoDespesa> implements Serializable {

    public ElementoDespesaDAO() {
        super(ElementoDespesa.class);
    }
}
