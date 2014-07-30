package br.com.gestor.dao;

import br.com.gestor.modelo.ModalidadeAplicacaoDespesa;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorModalidadeAplicacaoDespesaDao")
public class ModalidadeAplicacaoDespesaDAO extends GestorDAO<ModalidadeAplicacaoDespesa> implements Serializable {

    public ModalidadeAplicacaoDespesaDAO() {
        super(ModalidadeAplicacaoDespesa.class);
    }
}
