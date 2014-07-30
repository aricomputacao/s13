/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.Pessoa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.ItemLicitacaoController;
import br.com.siafi.controller.LicitacaoController;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.ItemLicitacao;
import br.com.siafi.modelo.Licitacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Classe do Projeto Siafi - Criado em 02/07/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class ItemLicitacaoMB extends BeanGenerico<ItemLicitacao> implements Serializable {

    @EJB
    private ItemLicitacaoController ilcontroler;
    @EJB
    private LicitacaoController licitacaoControler;
    @EJB
    private UsuarioMB usuarioMb;
    private List<ItemLicitacao> lista;
    private Date dataInicial;
    private Date dataFinal;
    private Integer numero;
    private Credor credor;
    private List<Licitacao> licitacaoLista;
    private Licitacao licitacao;
    private UnidadeOrcamentaria unidadeOrcamentaria;

    public ItemLicitacaoMB() {
        super(ItemLicitacao.class);
        lista = new ArrayList<>();
        dataInicial = new Date();
        dataFinal = new Date();
        numero = 0;
        credor = new Credor();
        licitacaoLista = new ArrayList<>();
        unidadeOrcamentaria = new UnidadeOrcamentaria();
    }

    @PostConstruct
    private void inicializa() {
        unidadeOrcamentaria = usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias().get(0);
    }

    public void listar() {
        try {
            if (credor.getId() != null) {
                licitacaoLista = licitacaoControler.listarUnidadeOrcamentariaCredor(unidadeOrcamentaria, credor);
                if (licitacaoLista.isEmpty()) {
                    MenssagemUtil.addMessageInfo("Nenhum registro encontrado.");
                }
            } else {
                MenssagemUtil.addMessageInfo("Selecione um credor");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public void listarItens() {
        try {
            lista = ilcontroler.listarPorLicitacao(licitacao);
            if (lista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao listar itens", ex, this.getClass().getName());
        }
    }

    public List<ItemLicitacao> getLista() {
        return lista;
    }

    public void setLista(List<ItemLicitacao> lista) {
        this.lista = lista;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Credor getCredor() {
        if (credor.getPessoa() == null) {
            credor.setPessoa(new Pessoa());
        }
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
        lista.clear();
        listar();
    }

    public List<Licitacao> getLicitacaoLista() {
        return licitacaoLista;
    }

    public void setLicitacaoLista(List<Licitacao> licitacaoLista) {
        this.licitacaoLista = licitacaoLista;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<UnidadeOrcamentaria> getListaUnidadeOrcamentarias() {
        return usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias();
    }
}
