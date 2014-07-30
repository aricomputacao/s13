/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.dao.DotacaoDAO;
import br.com.siafi.dao.LicitacaoDAO;
import br.com.siafi.dao.LicitacaoDotacaoDAO;
import br.com.siafi.dao.NaturezaDespesaDAO;
import br.com.siafi.dao.ProjetoAtividadeDAO;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.Dotacao;
import br.com.siafi.modelo.Licitacao;
import br.com.siafi.modelo.LicitacaoDotacao;
import br.com.siafi.modelo.LicitacaoUnidadeOrcamentaria;
import br.com.siafi.modelo.Orcamento;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ari
 */
@Stateless
public class LicitacaoDotacaoController extends Controller<LicitacaoDotacao, Integer> implements Serializable {

    @EJB
    private LicitacaoDAO licitacaoSiafiDao;
    @EJB
    private LicitacaoDotacaoDAO licitacaoDotacaoSiafiDao;
    @EJB
    private DotacaoDAO dotacaoSiafiDao;
    @EJB
    private br.com.gestor.dao.LicitacaoDotacaoDAO licitacaoDotacaoGestorDao;
    @EJB
    private br.com.gestor.dao.DotacaoDAO dotacaoGestorDao;
    @EJB
    private ProjetoAtividadeDAO projetoAtividadeDao;
    @EJB
    private NaturezaDespesaDAO naturezaDespesaDao;
    @EJB
    private OrcamentoController orcamentoController;
    @EJB
    private LicitacaoUnidadeOrcamentariaController licitacaoUnidadeOrcamentariaControler;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(licitacaoDotacaoSiafiDao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.LicitacaoDotacao> lista = licitacaoDotacaoGestorDao.listarImportacao();
        Orcamento orcamento = orcamentoController.getOrcamentoVigente();
        for (br.com.gestor.modelo.LicitacaoDotacao l : lista) {
            System.out.println(l.toString());
            LicitacaoDotacao licitacaoDotacaoSiafi = new LicitacaoDotacao();
            br.com.gestor.modelo.Dotacao dotacaoGestor = dotacaoGestorDao.carregarDotacao(l.getDotacaoLicitacaoPK().getDotacao());
            Dotacao dotacao = dotacaoSiafiDao.dotacaoProjetoAtividadeNatureza(projetoAtividadeDao.carregar(dotacaoGestor.getProjetoAtividade()), naturezaDespesaDao.carregar(dotacaoGestor.getNaturezaDespesa()), orcamento);
            Licitacao licitacao = licitacaoSiafiDao.carregar(l.getDotacaoLicitacaoPK().getLicitacao());
            if (!licitacaoDotacaoSiafiDao.existeLicitacaoDotacao(licitacao, dotacao)) {
                licitacaoDotacaoSiafi.setLicitacao(licitacao);
                licitacaoDotacaoSiafi.setDotacao(dotacao);
                licitacaoDotacaoSiafiDao.atualizar(licitacaoDotacaoSiafi);
                LicitacaoUnidadeOrcamentaria licitacaoUnidadeOrcamentaria = new LicitacaoUnidadeOrcamentaria();
                licitacaoUnidadeOrcamentaria.setLicitacao(licitacao);
                licitacaoUnidadeOrcamentaria.setUnidadeOrcamentaria(dotacao.getProjetoAtividade().getUnidadeOrcamentaria());
                licitacaoUnidadeOrcamentariaControler.salvarouAtualizar(licitacaoUnidadeOrcamentaria);

            }
        }
        licitacaoDotacaoGestorDao.marcarImportados("LID");
    }

    public List<ProjetoAtividade> listarLicitacaoDotacao(Orcamento o, Licitacao l, CentroCusto cc, UnidadeOrcamentaria uo) throws Exception {
        if (l == null) {
            return licitacaoDotacaoSiafiDao.listarLicitacaoDotacao(o, cc, uo);
        } else {
            return licitacaoDotacaoSiafiDao.listarLicitacaoDotacao(o, l, cc, uo);
        }

    }

}
