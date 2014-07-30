/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.TipoFluxo;
import br.com.siafi.controller.CentroCustoController;
import br.com.siafi.modelo.CentroCusto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.jfree.chart.block.CenterArrangement;

/**
 * Classe do Projeto Siafi - Criado em 22/04/2013 - Bean gerenciador do modelo
 * CentroCusto
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class DespesaMB extends BeanGenerico<CentroCusto> implements Serializable {

    @EJB
    private CentroCustoController controller;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private CentroCusto despesa;
    private List<CentroCusto> listaDespesa;

    /**
     * Construtor padrão
     */
    public DespesaMB() {
        super(CentroCusto.class);

        this.listaDespesa = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        this.despesa = (CentroCusto) beanNavegacao.getRegistroMapa("despesa", new CentroCusto());
        listaDespesa = new ArrayList<>();
    }

    /**
     * Método para salvar uma determinada despesa no banco
     */
    public void salvar() {
        try {
            controller.salvarouAtualizar(despesa);
            despesa = new CentroCusto();
            MenssagemUtil.addMessageInfo("Despesa cadastrada com sucesso");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar", ex, this.getClass().getName());
        }

    }

    public void excluir(CentroCusto d) {
        try {
            CentroCusto cc = new CentroCusto();
            cc = controller.gerenciar(d.getId());
            controller.excluir(cc);
            listaDespesa.remove(d);
//            despesa = new CentroCusto();
            MenssagemUtil.addMessageInfo("Despesa excluida com sucesso");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar", ex, this.getClass().getName());
        }

    }

    public void listar() {
        listaDespesa = listar(controller);
    }

    public void listarDespesa() {
        try {
            listaDespesa = controller.listarOrdenado();
            if (listaDespesa.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma despesa encontrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao consultar despesas", ex, this.getClass().getName());
        }
    }

    public void selecionar() {
        try {
            despesa = controller.carregar(despesa.getId());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar despesa", ex, this.getClass().getName());
        }
    }

    public TipoFluxo[] getTipoFluxo() {
        return TipoFluxo.values();
    }

    /**
     *
     * @return
     */
    public CentroCusto getDespesa() {
        return despesa;
    }

    /**
     *
     * @param despesa
     */
    public void setDespesa(CentroCusto despesa) {
        this.despesa = despesa;
    }

    /**
     *
     * @return
     */
    public List<CentroCusto> getListaDespesa() {
        return listaDespesa;
    }

    /**
     *
     * @param listaDespesa
     */
    public void setListaDespesa(List<CentroCusto> listaDespesa) {
        this.listaDespesa = listaDespesa;
    }

    public void novo() {
        despesa = new CentroCusto();
    }
}
