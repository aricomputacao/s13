package br.com.gestor.dao;

import br.com.gestor.modelo.NaturezaDespesa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorNaturezaDespesaDao")
public class NaturezaDespesaDAO extends GestorDAO<NaturezaDespesa> implements Serializable {

    public NaturezaDespesaDAO() {
        super(NaturezaDespesa.class);
    }
}
