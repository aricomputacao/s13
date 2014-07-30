/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.dao.UnidadeOrcamentariaDAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.AditivoDAO;
import br.com.siafi.dao.ContratoDAO;
import br.com.siafi.dao.CredorDAO;
import br.com.siafi.dao.LicitacaoDAO;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Orcamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto SiafiWeb criada em 28/06/2013
 *
 * @author: ari
 */
@Stateless
public class ContratoController extends Controller<Contrato, Integer> implements Serializable {

    @EJB
    private ContratoDAO contratoDao;
    @EJB
    private br.com.gestor.dao.ContratoDAO contratoGestorDao;
    @EJB
    private CredorDAO credorDao;
    @EJB
    private LicitacaoDAO licitacaoDao;
    @EJB
    private UnidadeOrcamentariaDAO unidadeOrcamentariaDao;
    @EJB
    private OrcamentoController orcamentoControler;
    @EJB
    private AditivoDAO aditivoDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(contratoDao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.Contrato> contratosGestor = contratoGestorDao.listarImportacao();
        Orcamento orcamento = orcamentoControler.getOrcamentoVigente();

        for (br.com.gestor.modelo.Contrato contrato : contratosGestor) {
            //System.out.println(contrato);
            UnidadeOrcamentaria unidadeOrcamentaria = unidadeOrcamentariaDao.unidadeAtiva(contrato.getOrgao(), contrato.getUnidadeAdministrativa());
            Credor credor = credorDao.carregar(contrato.getCredor().getId());
            Contrato contratoSiafi;
            //try {
            //contratoSiafi = contratoDao.contratoUnidadeOrcamentariaCredorNumero(unidadeOrcamentaria, orcamento, contrato.getNumero());
            contratoSiafi = contratoDao.existeContrato(contrato.getId());

//            } catch (Exception e) {
//                contratoSiafi = null;
//            }
            if (contratoSiafi == null) {
                contratoSiafi = new Contrato();
                contratoSiafi.setId(contrato.getId());
                if (contrato.getLicitacao() != null) {
                    contratoSiafi.setLicitacao(licitacaoDao.carregar(contrato.getLicitacao().getId()));
                }
                contratoSiafi.setNumero(contrato.getNumero());
                contratoSiafi.setUnidadeOrcamentaria(unidadeOrcamentaria);
                contratoSiafi.setCredor(credor);
                contratoSiafi.setOrcamento(orcamento);
                contratoSiafi.setNumero(contrato.getNumero());
                contratoDao.salvar(contratoSiafi);
            } else {
                if (contrato.getLicitacao() != null) {
                    contratoSiafi.setLicitacao(licitacaoDao.carregar(contrato.getLicitacao().getId()));
                }
                contratoSiafi.setNumero(contrato.getNumero());
                contratoSiafi.setUnidadeOrcamentaria(unidadeOrcamentaria);
                contratoSiafi.setCredor(credor);
                contratoSiafi.setOrcamento(orcamento);
                contratoSiafi.setNumero(contrato.getNumero());
                contratoSiafi.setOrcamento(orcamento);
                contratoDao.atualizar(contratoSiafi);
            }
            //contratoDao.atualizar(contratoSiafi);
            // limpa o cache a cada 1000 importações
//            if (contrato.getId() % 1000 == 0) {
////                contratoDao.comitaTransacao();
////                contratoDao.limpaEntityManager();
//                System.out.println("Cache limpo");
//            }

        }
        contratoGestorDao.marcarImportados("CTR");
    }

    public List<Contrato> contratoPorUnidadeOrçamentariaCredor(String numero, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor) throws Exception {
        List<Contrato> contratos = contratoDao.contratoNumeroUnidadeOrcamentariaCredor(numero, unidadeOrcamentaria, credor);
        return contratos;
    }

    public List<Contrato> listarPorUnidadeOrcamentariaNumeroCredor(String numero, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor) throws Exception {
        return contratoDao.listarPorUnidadeOrcamentariaCredor(unidadeOrcamentaria, credor, numero);
    }

    public BigDecimal valorContrato(Integer contrato) throws Exception {
        return aditivoDao.valorContrato(contrato);
    }

    @Override
    public void salvarouAtualizar(Contrato t) throws Exception {
        //
    }

    public List<Contrato> contratoCredor(Credor credor) throws Exception {
        return contratoDao.contratoCredor(credor);
    }

}
