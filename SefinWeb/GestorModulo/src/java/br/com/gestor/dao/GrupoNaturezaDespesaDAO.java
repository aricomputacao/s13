package br.com.gestor.dao;

import br.com.gestor.modelo.GrupoNaturezaDespesa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorGrupoNaturezaDespesaDao")
public class GrupoNaturezaDespesaDAO extends GestorDAO<GrupoNaturezaDespesa> implements Serializable {

    public GrupoNaturezaDespesaDAO() {
        super(GrupoNaturezaDespesa.class);
    }
}
