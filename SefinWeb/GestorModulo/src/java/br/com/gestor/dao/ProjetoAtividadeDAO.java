package br.com.gestor.dao;

import br.com.gestor.modelo.ProjetoAtividade;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Stateless(name = "GestorProjetoAtividadeDao")
public class ProjetoAtividadeDAO extends GestorDAO<ProjetoAtividade> implements Serializable {

    public ProjetoAtividadeDAO() {
        super(ProjetoAtividade.class);
    }
}
