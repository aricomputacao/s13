/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.ModuloController;
import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.controler.TarefaController;
import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.Tarefa;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class RelatorioTarefaMB implements Serializable {

    @EJB
    private SistemaController sistemaControler;
    @EJB
    private TarefaController tarefaControler;
    @EJB
    private ModuloController moduloControler;
    private List<Sistema> sistemasList;
    private List<Tarefa> tarefasList;
    private Sistema sistema;
    private List<Modulo> modulosList;
    private Modulo modulo;

    public RelatorioTarefaMB() {
    }

    @PostConstruct
    private void init() {
        try {
            sistemasList = sistemaControler.listarTodos("nome");
            modulosList = new ArrayList<>();
            tarefasList = new ArrayList<>();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
        }
    }

    public void lista() {

        try {
            if (sistema != null) {
                if (modulo != null) {

                    tarefasList = tarefaControler.listarPorModulo(modulo);

                } else {
                    tarefasList = tarefaControler.listarPorSistema(sistema);
                }
            } else {
                MenssagemUtil.addMessageWarn("Selecione um sistema");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public void listaModulos() {
        try {
            if (sistema != null) {
                modulosList = moduloControler.listar(sistema);
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar módulos do sistema selecionado", ex, this.getClass().getName());
        }
    }

    public List<Sistema> getSistemasList() {
        return sistemasList;
    }

    public void setSistemasList(List<Sistema> sistemasList) {
        this.sistemasList = sistemasList;
    }

    public List<Tarefa> getTarefasList() {
        return tarefasList;
    }

    public void setTarefasList(List<Tarefa> tarefasList) {
        this.tarefasList = tarefasList;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    public List<Modulo> getModulosList() {
        return modulosList;
    }

    public void setModulosList(List<Modulo> modulosList) {
        this.modulosList = modulosList;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public void impressao() {
        try {
            Map<String, Object> map = new HashMap<>();
            byte[] rel = new AssistentedeRelatorio().relatorioemByte(tarefasList, map, "WEB-INF/relatorios/listagem_tarefas.jasper", "Listagem de Tarefas do sistema " + sistema.getNome());
            RelatorioSession.setBytesRelatorioInSession(rel);
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao gerar impressão", e, this.getClass().getName());
        }
    }
}
