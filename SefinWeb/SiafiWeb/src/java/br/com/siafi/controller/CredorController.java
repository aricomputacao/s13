/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.Pessoa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.CredorDAO;
import br.com.siafi.dao.TipoCredorDAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Licitacao;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 27/06/2013
 *
 * @author: ari
 */
@Stateless
public class CredorController extends Controller<Credor, Integer> {

    @EJB
    private CredorDAO credorDao;
    @EJB
    private br.com.gestor.dao.CredorDAO credorGestorDao;
    @EJB
    private TipoCredorDAO tipoCredorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(credorDao);
    }

    @Override
    public void salvarouAtualizar(Credor t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Credor> listarOutros(String campo, String valor) throws Exception {
        return credorDao.listarOutros(campo, valor);
    }

    public List<Credor> listarCredoresUnidadeOrcamentaria(UnidadeOrcamentaria un, String nome) throws Exception {
        return credorDao.listarPorUnidadeOrcamentaria(un, nome);
    }

    public List<Credor> listarTodos(Licitacao licitacao) throws Exception {
        return credorDao.listarTodos(licitacao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Credor> credoresGestor = credorGestorDao.listarImportacao();
        System.out.println("Importando " + credoresGestor.size() + " Credores.");

        for (br.com.gestor.modelo.Credor credor : credoresGestor) {
            System.out.println(credor.toString());
            Credor credorSiafi = new Credor();
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(credor.getNome());
            pessoa.setTelefone(credor.getTelefone());
            pessoa.setEmail(credor.getEmail());
            credorSiafi.setEndereco(credor.getEndereco());
            credorSiafi.setBairro(credor.getBairro());
            if (credor.getCidade() != null) {
                credorSiafi.setCidade(credor.getCidade().getNome());
            }
            if (credor.getUf() != null) {
                credorSiafi.setUf(credor.getUf());
            }
            credorSiafi.setTipoCredor(tipoCredorDao.carregar(credor.getTipo()));
            if (credor.getCnpj() != null && !credor.getCnpj().equals("")) {
                credorSiafi.setDocumento(credor.getCnpj());
            } else if (credor.getCpf() != null && !credor.getCpf().equals("")) {
                credorSiafi.setDocumento(credor.getCpf());
                credorSiafi.setPisPasep(credor.getPasep());
            }
            credorSiafi.setId(credor.getId());
            credorSiafi.setPessoa(pessoa);
            credorDao.atualizar(credorSiafi);
        }
        credorGestorDao.marcarImportados("PES");
    }

}
