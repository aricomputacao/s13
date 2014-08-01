/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.AditivoDAO;
import br.com.siafi.dao.ContratoDAO;
import br.com.siafi.modelo.Aditivo;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 * Classe do Projeto SiafiWeb criada em 01/07/2013
 *
 * @author: ari
 */
@Stateless
public class AditivoController extends Controller<Aditivo, Long> implements Serializable {

    @EJB
    private AditivoDAO aditivoDao;
    @EJB
    private br.com.gestor.dao.AditivoDAO aditivoGestorDao;
    @EJB
    private ContratoDAO contratoDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(aditivoDao);
    }

    public void importar() throws SQLException, PersistenceException, EJBException, Exception {

        List<br.com.gestor.modelo.Aditivo> aditivosGestor = aditivoGestorDao.listarImportacao();
        for (br.com.gestor.modelo.Aditivo aditivo : aditivosGestor) {
            System.out.println(aditivo.toString());
            Aditivo aditivoSiafi;
            aditivoSiafi = aditivoDao.buscaUniqueAditivo(aditivo.getAditivoPK().getIdContrato(), aditivo.getAditivoPK().getId());
            if (aditivoSiafi == null) {
                aditivoSiafi = new Aditivo();
                aditivoSiafi.setCodigo(aditivo.getAditivoPK().getId());
                aditivoSiafi.setContrato(contratoDao.carregar(aditivo.getAditivoPK().getIdContrato()));
                aditivoSiafi.setDataInicio(aditivo.getInicio());
                aditivoSiafi.setDataFim(aditivo.getFim());
                aditivoSiafi.setDataPublicacao(aditivo.getPublicacao());
                aditivoSiafi.setObjetivo(aditivo.getObjetivo());
                aditivoSiafi.setValor(aditivo.getValor());
                aditivoDao.salvar(aditivoSiafi);
            } else {
                aditivoSiafi.setCodigo(aditivo.getAditivoPK().getId());
                aditivoSiafi.setContrato(contratoDao.carregar(aditivo.getAditivoPK().getIdContrato()));
                aditivoSiafi.setDataInicio(aditivo.getInicio());
                aditivoSiafi.setDataFim(aditivo.getFim());
                aditivoSiafi.setDataPublicacao(aditivo.getPublicacao());
                aditivoSiafi.setObjetivo(aditivo.getObjetivo());
                aditivoSiafi.setValor(aditivo.getValor());
                aditivoDao.atualizar(aditivoSiafi);
            }

        }
        aditivoGestorDao.marcarImportados("ADC");
    }

    @Override
    public void salvarouAtualizar(Aditivo t) throws Exception {

    }

    public List<Aditivo> aditivosContrato(String numContrato, String campoOrdem) throws SQLException, PersistenceException, EJBException, Exception {
        return aditivoDao.aditivosContrato(numContrato, campoOrdem);
    }

    public List<Aditivo> listarAditivosValidos(String numero, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor) throws Exception {
        if (unidadeOrcamentaria != null && credor != null) {
            return aditivoDao.contratoNumeroUnidadeOrcamentariaCredor(numero, unidadeOrcamentaria, credor);
        } else {
            return new ArrayList<>();
        }

    }

    public Aditivo ultimoAditivo(Contrato contrato) throws Exception {
        return aditivoDao.ultimoAditivo(contrato);
    }

    public List<Aditivo> listarAditivosValidosOrdemCompra(String numero, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor) throws Exception {
        return aditivoDao.contratoNumeroUnidadeOrcamentariaCredorOrdemCompra(numero, unidadeOrcamentaria, credor);
    }

}
