package br.com.gestor.dao;

import br.com.gestor.modelo.CategoriaDespesa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorCategoriaDespesaDao")
public class CategoriaDespesaDAO extends GestorDAO<CategoriaDespesa> implements Serializable {

    public CategoriaDespesaDAO() {
        super(CategoriaDespesa.class);
    }
}
