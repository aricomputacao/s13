/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.NaturezaDespesaController;
import br.com.siafi.modelo.NaturezaDespesa;
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
public class NaturezaDespesaMB extends BeanGenerico<NaturezaDespesa> implements Serializable {

    private NaturezaDespesa naturezaDespesa;
    private List<NaturezaDespesa> listaNaturezaDespesas;
    @EJB
    private NaturezaDespesaController naturezaDespesaControler;
    @Inject
    private BeanNavegacaoMB beanNavegacao;

    /**
     * Creates a new instance of NaturezaDespesaMb
     */
    public NaturezaDespesaMB() {
        super(NaturezaDespesa.class);
        this.listaNaturezaDespesas = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
//        this.naturezaDespesa = (NaturezaDespesa) beanNavegacao.getRegistroMapa("naturezaDespesa", new NaturezaDespesa());
    }

    public void listar() {
        try {
            listaNaturezaDespesas = naturezaDespesaControler.listarTodos("id", getCampoBusca(), getValorBusca());
            if (listaNaturezaDespesas.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhum registro encontrado");
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("", e, null);
        }
    }

    public NaturezaDespesa getNaturezaDespesa() {
        return naturezaDespesa;
    }

    public void setNaturezaDespesa(NaturezaDespesa naturezaDespesa) {
        this.naturezaDespesa = naturezaDespesa;
    }

    public List<NaturezaDespesa> getListaNaturezaDespesas() {
        return listaNaturezaDespesas;
    }

    public void setListaNaturezaDespesas(List<NaturezaDespesa> listaNaturezaDespesas) {
        this.listaNaturezaDespesas = listaNaturezaDespesas;
    }
}
