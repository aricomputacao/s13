/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.CredorController;
import br.com.siafi.modelo.Credor;
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
public class CredorMB extends BeanGenerico<Credor> implements Serializable {

    @EJB
    private CredorController controler;
    private List<Credor> lista;

    public CredorMB() {
        super(Credor.class);
        lista = new ArrayList<>();
    }

    public void listar() {
        setCampoOrdem("pessoa.nome");

        try {
            lista = controler.listarTodos(getCampoOrdem(), getCampoBusca(), getValorBusca().toUpperCase());
            if (lista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
            lista = new ArrayList<>();
        }
    }

    public void listarPorNome() {
        try {
            lista = controler.listarOutros("pessoa.nome", getValorBusca().toUpperCase());
            if (lista.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public List<Credor> getLista() {
        return lista;
    }

    public void setLista(List<Credor> lista) {
        this.lista = lista;
    }
}
