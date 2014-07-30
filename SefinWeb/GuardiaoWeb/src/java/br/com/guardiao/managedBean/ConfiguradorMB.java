/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.ModuloController;
import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.controler.TarefaController;
import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.Tarefa;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Criar as COnfigurações inicias como mnodulos e tarefas
 *
 * @author gilmario
 */
//@Singleton
//@Startup
public class ConfiguradorMB implements Serializable {

    @EJB
    private SistemaController sistemaControler;
    @EJB
    private ModuloController moduloControler;
    @EJB
    private TarefaController tarefaControler;

    public ConfiguradorMB() {
    }

    @PostConstruct
    private void init() {
        try {
            criaTarefas();
        } catch (Exception ex) {
            Logger.getLogger(ConfiguradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Sistema criaSistema() throws Exception {
        try {
            return sistemaControler.buscarMnemonico("GRD");
        } catch (Exception e) {
            Sistema s = new Sistema();
            s.setDescricao("Guardião Web");
            s.setNome("Guardião");
            s.setMnemonico("GRD");
            s.setSigla("GRD");
            sistemaControler.salvarouAtualizar(s);
            return s;
        }
    }

    private Modulo criaModulos(String mnemonico) throws Exception {
        Sistema s = criaSistema();
        ResourceBundle bundle = ResourceBundle.getBundle("modulo");
        Modulo m;
        try {
            m = moduloControler.existeModulo(s, mnemonico);
        } catch (Exception e) {
            m = new Modulo();
            m.setMnemonico(mnemonico);
            m.setNome(bundle.getString("modulo." + mnemonico));
            m.setSistema(s);
            moduloControler.salvarouAtualizar(m);
        }
        return m;
    }

    private void criaTarefas() throws Exception {
        for (int i = 1; i <= 5; i++) {
            Modulo modulo = criaModulos("0" + i);
            ResourceBundle bundle = ResourceBundle.getBundle("tarefas" + "0" + i);
            Enumeration<String> lista = bundle.getKeys();
            while (lista.hasMoreElements()) {
                String descricao = lista.nextElement();
                String mnemonico = bundle.getString(descricao);
                Tarefa tarefa;
                try {
                    tarefaControler.existeTarefa(mnemonico);
                } catch (Exception e) {
                    tarefa = new Tarefa();
                    String[] parte = descricao.split("\\.");
                    tarefa.setDescricao(parte[2] + " " + parte[1]);
                    tarefa.setNome(parte[2] + " " + parte[1]);
                    tarefa.setMnemonico(mnemonico);
                    tarefa.setModulo(modulo);
                    tarefaControler.salvarouAtualizar(tarefa);
                }
            }
        }
    }
}
