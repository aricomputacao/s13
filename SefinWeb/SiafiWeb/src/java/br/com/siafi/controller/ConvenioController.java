/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.AditivoConvenioDAO;
import br.com.siafi.dao.ConvenioDAO;
import br.com.siafi.modelo.AditivoConvenio;
import br.com.siafi.modelo.Convenio;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class ConvenioController extends Controller<Convenio, Integer> implements Serializable {

    @EJB
    private ConvenioDAO convenioDao;
    @EJB
    private AditivoConvenioDAO aditivoConvenioDao;

    @Override
    public void salvarouAtualizar(Convenio t) throws Exception {
        if (t.getId() == null) {
            convenioDao.salvar(t);
        } else {
            convenioDao.atualizar(t);
        }
    }

    public void salvarouAtualizar(AditivoConvenio a) throws Exception {
        if (a.getId() == null) {
            aditivoConvenioDao.salvar(a);
        } else {
            aditivoConvenioDao.atualizar(a);
        }
    }

    public boolean validaAditivo(AditivoConvenio a) throws Exception {
        // Verificar se existe uma solicitação financeira com data igual ou maior doque a data de inicio do aditivo
        return aditivoConvenioDao.validaAditivo(a);
    }

    public List<AditivoConvenio> getAditivos(Convenio convenio) throws Exception {
        return aditivoConvenioDao.listar(convenio);
    }

    public List<Convenio> listarConveniosAtivos(UnidadeOrcamentaria unidadeOrcamentaria, Date dataSolicitacao, Credor credor) throws Exception {
        return convenioDao.listarConveniosValidos(unidadeOrcamentaria, dataSolicitacao, credor);
    }

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(convenioDao);
    }
}
