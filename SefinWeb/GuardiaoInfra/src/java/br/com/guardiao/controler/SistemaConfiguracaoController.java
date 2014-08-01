/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.SistemaConfiguracaoDAO;
import br.com.guardiao.dao.SistemaDAO;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.SistemaConfiguracao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @Sistema GuardiaoInfra
 * @Data 07/08/2013
 * @author gilmario
 */
@Stateless
public class SistemaConfiguracaoController extends Controller<SistemaConfiguracao, Integer> implements Serializable {

    @EJB
    private SistemaConfiguracaoDAO dao;
    @EJB
    private SistemaDAO sistemaDAO;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(SistemaConfiguracao t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<SistemaConfiguracao> listar(Sistema sistema) throws Exception {
        return dao.listar(sistema);
    }

    /**
     * Retorna o valor de uma configuração ou um valor default
     *
     * @param def
     * @param nome
     * @param mnemonico
     * @return
     * @throws Exception
     */
    public Serializable pegarValorConfiguracaoDef(Serializable def, String nome, String mnemonico) throws Exception {
        Sistema sistema = sistemaDAO.buscarMnemonico(mnemonico);
        return dao.pegarValorConfiguracaoDef(def, nome, sistema);
    }

}
