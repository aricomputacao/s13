/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.SistemaConfiguracaoController;
import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.enumerated.TipoConfiguracao;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.SistemaConfiguracao;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @Sistema GuardiaoWeb
 * @Data 07/08/2013
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class ConfiguracaoMB implements Serializable {

    @EJB
    private SistemaController sistemaControler;
    @EJB
    private SistemaConfiguracaoController sistemaConfiguracaoControler;
    private List<Sistema> sistemasLista;
    private Sistema sistema;
    private SistemaConfiguracao sistemaConfiguracao;
    private List<SistemaConfiguracao> sistemaConfiguracaoLista;

    public ConfiguracaoMB() {
        sistemaConfiguracaoLista = new ArrayList<>();
        sistemasLista = new ArrayList<>();
        sistemaConfiguracao = new SistemaConfiguracao();
    }

    @PostConstruct
    private void init() {
        try {
            sistemasLista = sistemaControler.listarTodos("nome");
            if (sistemasLista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma sistema cadastrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErroLogger(ex, this.getClass().getName());
        }
    }

    public void selecionaSistema() {
        try {
            sistemaConfiguracaoLista = sistemaConfiguracaoControler.listar(sistema);
            if (sistemaConfiguracaoLista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma configuração cadastrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao selecionar sistema", ex, this.getClass().getName());
        }
    }

    public void salvar() {
        try {
            if (sistemaConfiguracao.getTipoConfiguracao().equals(TipoConfiguracao.Integer)) {
                sistemaConfiguracao.setValor(new Integer((String) sistemaConfiguracao.getValor()));
            } else if (sistemaConfiguracao.getTipoConfiguracao().equals(TipoConfiguracao.Date)) {
                sistemaConfiguracao.setValor((Date) sistemaConfiguracao.getValor());
            } else if (sistemaConfiguracao.getTipoConfiguracao().equals(TipoConfiguracao.Boolean)) {
                sistemaConfiguracao.setValor((Boolean) sistemaConfiguracao.getValor());
            }
            sistemaConfiguracaoControler.salvarouAtualizar(sistemaConfiguracao);
            MenssagemUtil.addMessageInfo("Configuração Salvar com sucesso");
            sistemaConfiguracao = new SistemaConfiguracao();
            selecionaSistema();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar", ex, this.getClass().getName());
        }
    }

    public void excluir() {
        try {
            sistemaConfiguracao = sistemaConfiguracaoControler.gerenciar(sistemaConfiguracao.getId());
            sistemaConfiguracaoControler.excluir(sistemaConfiguracao);
            sistemaConfiguracao = new SistemaConfiguracao();
            selecionaSistema();
            MenssagemUtil.addMessageInfo("Configuração Excluida");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao Excluir configuração", ex, this.getClass().getName());
        }
    }

    public List<TipoConfiguracao> getTiposConfiguracao() {
        return Arrays.asList(TipoConfiguracao.values());
    }

    public List<Sistema> getSistemasLista() {
        return sistemasLista;
    }

    public void setSistemasLista(List<Sistema> sistemasLista) {
        this.sistemasLista = sistemasLista;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public SistemaConfiguracao getSistemaConfiguracao() {
        return sistemaConfiguracao;
    }

    public void setSistemaConfiguracao(SistemaConfiguracao sistemaConfiguracao) {
        this.sistemaConfiguracao = sistemaConfiguracao;
    }

    public List<SistemaConfiguracao> getSistemaConfiguracaoLista() {
        return sistemaConfiguracaoLista;
    }

    public void setSistemaConfiguracaoLista(List<SistemaConfiguracao> sistemaConfiguracaoLista) {
        this.sistemaConfiguracaoLista = sistemaConfiguracaoLista;
    }
}
