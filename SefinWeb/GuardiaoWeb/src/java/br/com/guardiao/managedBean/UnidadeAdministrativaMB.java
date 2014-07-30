/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.UnidadeAdministrativaController;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeAdministrativa;
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
 * Bean gerenciado do Projeto guardiao criado em 22/03/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class UnidadeAdministrativaMB extends BeanGenerico<UnidadeAdministrativa> implements Serializable {

    @EJB
    private UnidadeAdministrativaController controller;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private UnidadeAdministrativa unidadeAdministrativaSelecionada;
    private List<UnidadeAdministrativa> listaUnidadeAdministrativas;
    private Orgao orgao;

    /**
     * Creates a new instance of UnidadeAdministrativaMb
     */
    public UnidadeAdministrativaMB() {
        super(UnidadeAdministrativa.class);

    }

    @PostConstruct
    private void init() {
        orgao = new Orgao();
        unidadeAdministrativaSelecionada = (UnidadeAdministrativa) navegacaoGuardiao.getRegistroMapa("unidadeAdm", new UnidadeAdministrativa());
        listaUnidadeAdministrativas = new ArrayList<>();
    }

    public void listar() {
        listaUnidadeAdministrativas = listar(controller);
    }

    //Metodo para add órgão na lista  de órgãos em unidade administrativa
    /**
     *
     */
    public void addOrgao() {
        unidadeAdministrativaSelecionada.getOrgaos().add(orgao);
    }

    //MEtodo para remover órgão da lista de órgãos
    /**
     *
     */
    public void delOrg() {
        unidadeAdministrativaSelecionada.getOrgaos().remove(orgao);

    }

    /**
     *
     * @return
     */
    public UnidadeAdministrativa getUnidadeAdministrativaSelecionada() {
        return unidadeAdministrativaSelecionada;
    }

    /**
     *
     * @param unidadeAdministrativaSelecionada
     */
    public void setUnidadeAdministrativaSelecionada(UnidadeAdministrativa unidadeAdministrativaSelecionada) {
        this.unidadeAdministrativaSelecionada = unidadeAdministrativaSelecionada;
    }

    /**
     *
     * @return
     */
    public Orgao getOrgao() {
        return orgao;
    }

    /**
     *
     * @param orgao
     */
    public void setOrgao(Orgao orgao) {
        this.orgao = orgao;
    }

    public void validaId(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            if (controller.carregar((String) value) != null) {
                ((UIInput) component).setValid(false);
                MenssagemUtil.addMessageErro("Já existe um exercicio cadastrado com esse id.");
            }
        } catch (Exception ex) {
            // Significa que não encontrou
        }

    }

    public List<UnidadeAdministrativa> getListaUnidadeAdministrativas() {
        return listaUnidadeAdministrativas;
    }

    public void setListaUnidadeAdministrativas(List<UnidadeAdministrativa> listaUnidadeAdministrativas) {
        this.listaUnidadeAdministrativas = listaUnidadeAdministrativas;
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(unidadeAdministrativaSelecionada);
            MenssagemUtil.addMessageInfo("UnidadeAdministrativa cadastrada com sucesso.");
            unidadeAdministrativaSelecionada = new UnidadeAdministrativa();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
    }

}
