/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.dao.RPEmpenhoDAO;
import br.com.guardiao.controler.Controller;
import br.com.guardiao.dao.UnidadeOrcamentariaDAO;
import br.com.siafi.dao.CentroCustoDAO;
import br.com.siafi.dao.CredorDAO;
import br.com.siafi.dao.NaturezaDespesaDAO;
import br.com.siafi.dao.RPUnidadeOrcamentariaDAO;
import br.com.siafi.dao.RpEmpenhoDAO;
import br.com.siafi.dao.RpProjetoAtividadeDAO;
import br.com.siafi.modelo.RpEmpenho;
import br.com.siafi.modelo.RpUnidadeOrcamentariaPk;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class RpEmpenhoController extends Controller<RpEmpenho, Integer> implements Serializable {

    @EJB
    private RpEmpenhoDAO empenhoDao;
    @EJB
    private RPEmpenhoDAO gestorDao;
    @EJB
    private UnidadeOrcamentariaDAO unidadeOrcamentariaDao;
    @EJB
    private RPUnidadeOrcamentariaDAO rPUnidadeOrcamentariaDao;
    @EJB
    private RpProjetoAtividadeDAO rPProjetoAtividadeDao;
    @EJB
    private CentroCustoDAO centroCustoDao;
    @EJB
    private CredorDAO credorDao;
    @EJB
    private NaturezaDespesaDAO naturezaDespesaDao;

    @Override
    protected void inicializaDAO() {
        setDAO(empenhoDao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.RPEmpenho> lista = gestorDao.listarImportacao();
        System.out.println(lista.size());
        for (br.com.gestor.modelo.RPEmpenho j : lista) {
            System.out.println(j.getId());
            RpUnidadeOrcamentariaPk rpUnidadeOrcamentariaPk = new RpUnidadeOrcamentariaPk();
            RpEmpenho i = new RpEmpenho();
            i.setCentroCusto(centroCustoDao.carregar(j.getCentrocusto()));
            i.setCodEntidade(j.getEntidade());
            i.setCredor(credorDao.carregar(j.getPessoa()));
            i.setDataEmpenho(j.getDataEmpenho());
            i.setHistorico(j.getHistorico());
            i.setId(j.getId());
            i.setModalidade(j.getModalidade());
            i.setNatureza(naturezaDespesaDao.carregar(j.getNatureza()));
            i.setNumero(j.getNumero());
            i.setRpProjetoAtividade(rPProjetoAtividadeDao.carregar((j.getFuncao() + j.getSubFuncao() + j.getPrograma() + j.getProjetoAtividade())));
            rpUnidadeOrcamentariaPk.setExercicio(j.getExercicio());
            rpUnidadeOrcamentariaPk.setId(j.getUnidade());
            rpUnidadeOrcamentariaPk.setOrgao(j.getOrgao());
            i.setRpunidadeOrcamentaria(rPUnidadeOrcamentariaDao.carregar(rpUnidadeOrcamentariaPk));
            i.setStatus(j.getStatus());
            i.setUnidadeOrcamentariaAtual(unidadeOrcamentariaDao.unidadeAtiva(j.getOrgaoAtual(), j.getUnidadeAtual()));
            i.setValor(j.getValor());
            salvarouAtualizar(i);
        }
        gestorDao.marcarImportados("RPE");
        System.out.println("Rpempenho Concluído");
    }

}
