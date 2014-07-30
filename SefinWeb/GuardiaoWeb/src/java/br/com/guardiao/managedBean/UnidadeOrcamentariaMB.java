/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.OrgaoController;
import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.UnidadeOrcamentariaPK;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 * Classe do Projeto guardiao - Criado em 25/03/2013 - Bean Gerenciador das
 * Unidades Orçamentarias
 *
 * @author Ari
 */
@ManagedBean
@ViewScoped
public class UnidadeOrcamentariaMB extends BeanGenerico<UnidadeOrcamentaria> implements Serializable {

    @EJB
    private UnidadeOrcamentariaController controller;
    @EJB
    private OrgaoController orgaoControler;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private UnidadeOrcamentaria unidadeOrcamentariaSelecionada;
    private List<UnidadeOrcamentaria> listaUnidadeOrcamentarias;
    private UnidadeOrcamentariaPK orcamentariaPK;

    /**
     * Creates a new instance of UnidadeOrcamentariaMb
     */
    public UnidadeOrcamentariaMB() {
        super(UnidadeOrcamentaria.class);

    }

    @PostConstruct
    private void init() {
        unidadeOrcamentariaSelecionada = (UnidadeOrcamentaria) navegacaoGuardiao.getRegistroMapa("unidadeOrc", new UnidadeOrcamentaria());
        orcamentariaPK = new UnidadeOrcamentariaPK();
        if (unidadeOrcamentariaSelecionada.getUnidadeOrcamentariaPK() != null) {
            orcamentariaPK = unidadeOrcamentariaSelecionada.getUnidadeOrcamentariaPK();
        }
    }

    public void listar() {
        listaUnidadeOrcamentarias = listar(controller);
    }

    /**
     *
     * @return
     */
    public UnidadeOrcamentaria getUnidadeOrcamentariaSelecionada() {
        return unidadeOrcamentariaSelecionada;
    }

    /**
     *
     * @param unidadeOrcamentariaSelecionada
     */
    public void setUnidadeOrcamentariaSelecionada(UnidadeOrcamentaria unidadeOrcamentariaSelecionada) {
        this.unidadeOrcamentariaSelecionada = unidadeOrcamentariaSelecionada;
    }

    /**
     *
     * @return
     */
    public UnidadeOrcamentariaPK getOrcamentariaPK() {
        return orcamentariaPK;
    }

    /**
     *
     * @param orcamentariaPK
     */
    public void setOrcamentariaPK(UnidadeOrcamentariaPK orcamentariaPK) {
        this.orcamentariaPK = orcamentariaPK;
    }

    public List<UnidadeOrcamentaria> getListaUnidadeOrcamentarias() {
        return listaUnidadeOrcamentarias;
    }

    public void setListaUnidadeOrcamentarias(List<UnidadeOrcamentaria> listaUnidadeOrcamentarias) {
        this.listaUnidadeOrcamentarias = listaUnidadeOrcamentarias;
    }

    public List<Orgao> getListaOrgao() {
        try {
            return orgaoControler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
        return new ArrayList<>();
    }

    // Vai validar correntamente por que o orgão não pode ser nulo e nem o id
    public void validaId(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            if (unidadeOrcamentariaSelecionada.getOrgao() != null) {
                UnidadeOrcamentariaPK pk = new UnidadeOrcamentariaPK();
                pk.setId((String) value);
                pk.setIdOrgao(unidadeOrcamentariaSelecionada.getOrgao().getId().getId());
                if (controller.carregar(pk) != null) {
                    ((UIInput) component).setValid(false);
                    MenssagemUtil.addMessageErro("id já cadastrado ");
                }
            }
        } catch (Exception ex) {
            // Significa que não encontrou
        }
    }

    public void salvar() {
        try {
            orcamentariaPK.setIdOrgao(unidadeOrcamentariaSelecionada.getOrgao().getId().getId());
            orcamentariaPK.setExercicio_ano(unidadeOrcamentariaSelecionada.getOrgao().getExercicio().getAno());
            unidadeOrcamentariaSelecionada.setUnidadeOrcamentariaPK(orcamentariaPK);
            controller.salvarouAtualizar(unidadeOrcamentariaSelecionada);
            unidadeOrcamentariaSelecionada = new UnidadeOrcamentaria();
            orcamentariaPK = new UnidadeOrcamentariaPK();
            MenssagemUtil.addMessageInfo("Unidade Orçamentária cadastrada com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar Unidade Orçamentária", ex, this.getClass().getName());
        }
    }

}
