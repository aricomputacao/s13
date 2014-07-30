/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.ContaController;
import br.com.siafi.modelo.Conta;
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
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class ContaMB extends BeanGenerico<Conta> implements Serializable {

    @EJB
    private ContaController controler;
    @EJB
    private UnidadeOrcamentariaController unidadeOrcamentariaController;
    private Conta conta;
    private List<Conta> listaContas;
    private Integer numero;
    private List<UnidadeOrcamentaria> unidadeOrcamentarias;
    private UnidadeOrcamentaria unidadeOrcamentaria;

    public ContaMB() {
        super(Conta.class);
        conta = new Conta();
        listaContas = new ArrayList<>();
        numero = 0;
        unidadeOrcamentaria = new UnidadeOrcamentaria();

    }

    @PostConstruct
    private void listaUnidadeOrcametaria() {
        try {
            unidadeOrcamentarias = unidadeOrcamentariaController.listarAtivos();
            listaContas = controler.listarTodos("numero");
        } catch (Exception ex) {
            Logger.getLogger(ContaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listarContas() {
        try {
            listaContas = controler.listarNumero(numero, unidadeOrcamentaria);
            if (listaContas.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum resultado encontrado");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        } finally {
            numero = 0;
            unidadeOrcamentaria = new UnidadeOrcamentaria();
        }
    }

    public void imprimir() {
        if (!listaContas.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaContas, m, "WEB-INF/relatorios/relatorio_contas.jasper", "Contas");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public List<Conta> getListaContas() {
        return listaContas;
    }

    public void setListaContas(List<Conta> listaContas) {
        this.listaContas = listaContas;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<UnidadeOrcamentaria> getUnidadeOrcamentarias() {
        return unidadeOrcamentarias;
    }

    public void setUnidadeOrcamentarias(List<UnidadeOrcamentaria> unidadeOrcamentarias) {
        this.unidadeOrcamentarias = unidadeOrcamentarias;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }
}
