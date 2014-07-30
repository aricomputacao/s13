/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.LicitacaoController;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Licitacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Classe do Projeto SIAFI - Criado em 28/06/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class LicitacaoMB implements Serializable {

    @EJB
    private LicitacaoController controler;
    @EJB
    private CredorController credorControler;
    @EJB
    private UsuarioMB usuarioMb;
    private List<Licitacao> licitacoes;
    private String numero;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Credor credor;

    public LicitacaoMB() {
        licitacoes = new ArrayList<>();
        numero = "";
        credor = new Credor();
    }

    public void listar() {
        try {
            if (unidadeOrcamentaria != null) {
                licitacoes = controler.listar(unidadeOrcamentaria, numero, credor);
            } else {
                licitacoes = controler.listar(usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), numero, credor);
            }
            if (licitacoes.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public List<Licitacao> getLicitacoes() {
        return licitacoes;
    }

    public void setLicitacoes(List<Licitacao> licitacoes) {
        this.licitacoes = licitacoes;
    }

    public Credor getCredor() {
        return credor;
    }

    public void setCredor(Credor credor) {
        this.credor = credor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }
}
