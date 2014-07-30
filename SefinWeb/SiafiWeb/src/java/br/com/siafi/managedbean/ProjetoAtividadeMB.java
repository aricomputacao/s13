/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.ProjetoAtividadeController;
import br.com.siafi.modelo.ProjetoAtividade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Bean gerenciado do Projeto SiafiWeb criado em 18/06/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ProjetoAtividadeMB extends BeanGenerico<ProjetoAtividade> implements Serializable {

    private ProjetoAtividade projetoAtividadeSelecionado;
    private List<ProjetoAtividade> listaProjetoAtividades;
    @EJB
    private ProjetoAtividadeController controller;
    private String tipoAcao;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    @Inject
    private UsuarioMB usuarioMB;

    public ProjetoAtividadeMB() {
        super(ProjetoAtividade.class);
        this.listaProjetoAtividades = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
//        this.projetoAtividadeSelecionado = (ProjetoAtividade) beanNavegacao.getRegistroMapa("projetoAtividade", new ProjetoAtividade());
    }

    public void listar() {
        listaProjetoAtividades = controller.listar(tipoAcao, getValorBusca(), unidadeOrcamentaria, usuarioMB.getUsuarioSelecionado().getUnidadeOrcamentarias());
        if (listaProjetoAtividades.isEmpty()) {
            MenssagemUtil.addMessageInfo("Nenhum resultado encontrado");
        }
    }

    public ProjetoAtividade getProjetoAtividadeSelecionado() {
        return projetoAtividadeSelecionado;
    }

    public void setProjetoAtividadeSelecionado(ProjetoAtividade projetoAtividadeSelecionado) {
        this.projetoAtividadeSelecionado = projetoAtividadeSelecionado;
    }

    public List<ProjetoAtividade> getListaProjetoAtividades() {
        return listaProjetoAtividades;
    }

    public void setListaProjetoAtividades(List<ProjetoAtividade> listaProjetoAtividades) {
        this.listaProjetoAtividades = listaProjetoAtividades;
    }

    public String getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

}
