/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedbean;

import br.com.guardiao.controler.SistemaConfiguracaoController;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 * @Sistema GuardiaoWeb
 * @Data 08/08/2013
 * @author gilmario
 */
@Named
@Singleton
public class ConfigMB implements Serializable {

    @EJB
    private SistemaConfiguracaoController configuracaoControler;
    private String tema;
    private String temaSiafi;
    private Integer tempoSessao;

    public ConfigMB() {
    }

    @PostConstruct
    private void init() {
        try {
            tema = (String) configuracaoControler.pegarValorConfiguracaoDef("blitzer", "TEMA", new Sistema(1L));
            temaSiafi = (String) configuracaoControler.pegarValorConfiguracaoDef("black-tie", "TEMA", new Sistema(2L));
            tempoSessao = (Integer) configuracaoControler.pegarValorConfiguracaoDef(30, "TEMPO_SESSAO", new Sistema(1L));
        } catch (Exception ex) {
            MenssagemUtil.addMessageErroLogger(ex, this.getClass().getName());
        }
    }

    public void atualiza() {
        init();
        MenssagemUtil.addMessageInfo("Configurrações recarragadas.");
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Integer getTempoSessao() {
        return tempoSessao;
    }

    public void setTempoSessao(Integer tempoSessao) {
        this.tempoSessao = tempoSessao;
    }
}
