/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.siafi.dao.CredorDAO;
import br.com.siafi.dao.ItemDAO;
import br.com.siafi.dao.ItemLicitacaoDAO;
import br.com.siafi.dao.LicitacaoDAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Item;
import br.com.siafi.modelo.ItemLicitacao;
import br.com.siafi.modelo.Licitacao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 01/07/2013 -
 *
 * @author Gilmário
 */
@Stateless
public class ItemLicitacaoController extends Controller<ItemLicitacao, Long> implements Serializable {

    @EJB
    private ItemLicitacaoDAO dao;
    @EJB
    private CredorDAO credorDao;
    @EJB
    private ItemDAO itemDao;
    @EJB
    private LicitacaoDAO licitacaoDao;
    @EJB
    private br.com.gestor.dao.ItemLicitacaoDAO itemGestorDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    //Metodo utilizadao para setar liiLote e liiAditivo quando for nulo
    public void editarLicitacaoItem() {
        itemGestorDao.atualizarItemLicitacaAditivoNulo();
        itemGestorDao.atualizarItemLicitacaLoteNulo();
    }

    public void importar() throws Exception {
        editarLicitacaoItem();
        List<br.com.gestor.modelo.ItemLicitacao> itensLicitacaoGestor = itemGestorDao.listarImportacao();
        for (br.com.gestor.modelo.ItemLicitacao itemLicitacaoGestor : itensLicitacaoGestor) {
            if (itemLicitacaoGestor.getId().getLote() != 999) {
                System.out.println(itemLicitacaoGestor.toString());
                Credor credor = credorDao.carregar(itemLicitacaoGestor.getId().getCredor());
                Item item = itemDao.carregar(itemLicitacaoGestor.getId().getItem());
                Licitacao licitacao = licitacaoDao.carregar(itemLicitacaoGestor.getId().getLicitacao());
                String aditivo = itemLicitacaoGestor.getId().getAditvo();
                String lote = itemLicitacaoGestor.getId().getLote().toString();
                ItemLicitacao itemLicitacao;
                try {
                    itemLicitacao = dao.buscarPorLicitacaoCredorItemAditivoLote(licitacao, credor, item, aditivo, lote);
                } catch (Exception ex) {
                    itemLicitacao = null;
                }

                if (itemLicitacao == null) {
                    itemLicitacao = new ItemLicitacao();
                    itemLicitacao.setAditivo(aditivo);
                    itemLicitacao.setCredor(credor);
                    itemLicitacao.setItem(item);
                    itemLicitacao.setLicitacao(licitacao);
                    itemLicitacao.setLote(lote);
                    itemLicitacao.setCodigo(itemLicitacaoGestor.getCodigo());
                    itemLicitacao.setQuantidade(itemLicitacaoGestor.getQuantidade());
                    itemLicitacao.setValor(itemLicitacaoGestor.getValor());
                    itemLicitacao.setValorTotal(itemLicitacaoGestor.getTotal());
                    dao.atualizar(itemLicitacao);
                }
            }
        }

        itemGestorDao.marcarImportados("LII");
    }

    public ItemLicitacao buscaPorLicitacaoItemCredorAditivo(Item item, Licitacao licitacao, Credor credor, String aditivo) throws Exception {
        return dao.buscarPorLicitacaoCredorItemAditivo(licitacao, credor, item, aditivo);
    }

    public List<Licitacao> listarporCredor(Credor credor) throws Exception {
        return dao.listarPorCredor(credor);
    }

    public List<ItemLicitacao> listarPorLicitacao(Licitacao licitacao) throws Exception {
        return dao.listaPorLicitacao(licitacao);
    }

    public List<Credor> listarCredoresItens(String nome) throws Exception {
        return dao.listarCredores(nome);
    }

}
