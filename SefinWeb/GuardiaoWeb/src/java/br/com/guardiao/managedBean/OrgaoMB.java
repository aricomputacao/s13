/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.ExercicioController;
import br.com.guardiao.controler.OrgaoController;
import br.com.guardiao.controler.UnidadeAdministrativaController;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.UnidadeAdministrativa;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.Exercicio;
import br.com.guardiao.modelo.OrgaoPk;
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
public class OrgaoMB extends BeanGenerico<Orgao> implements Serializable {

    @EJB
    private OrgaoController orgaoControler;
    @EJB
    private UnidadeAdministrativaController unidadeAdministrativaController;
    @EJB
    private ExercicioController exercicioControler;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private Orgao orgaoSelecionado;
    private List<Orgao> listaOrgaos;

    /**
     * Creates a nova instancia de OrgaoMb
     */
    public OrgaoMB() {
        super(Orgao.class);
    }

    @PostConstruct
    private void init() {
        Orgao o = new Orgao();
        o.setId(new OrgaoPk());
        orgaoSelecionado = (Orgao) navegacaoGuardiao.getRegistroMapa("orgao", o);
        listaOrgaos = new ArrayList<>();
    }

    public void listar() {
        listaOrgaos = listar(orgaoControler);
    }

    public List<Orgao> getListaOrgaos() {
        return listaOrgaos;
    }

    public void setListaOrgaos(List<Orgao> listaOrgaos) {
        this.listaOrgaos = listaOrgaos;
    }

    public List<Exercicio> getListExercicios() {
        try {
            return exercicioControler.listarTodos("ano");
        } catch (Exception e) {
            MenssagemUtil.addMessageErroLogger(e, this.getClass().getName());
            return null;
        }

    }

    /**
     *
     * @return
     */
    public Orgao getOrgaoSelecionado() {
        return orgaoSelecionado;
    }

    /**
     *
     * @param orgaoSelecionado
     */
    public void setOrgaoSelecionado(Orgao orgaoSelecionado) {
        this.orgaoSelecionado = orgaoSelecionado;
    }

    public List<UnidadeAdministrativa> getListUnidadesAdm() {
        try {
            return unidadeAdministrativaController.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao listar", ex, this.getClass().getName());
        }
        return new ArrayList<>();
    }

    public void validaId(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            if (orgaoControler.carregar((String) value) != null) {
                ((UIInput) component).setValid(false);
                MenssagemUtil.addMessageErro("Já existe um orgão cadastrado com esse id.");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao validar", ex, this.getClass().getName());
        }

    }

    public void salvar() {
        try {
            orgaoSelecionado.getId().setExercicio(orgaoSelecionado.getExercicio().getAno());
            orgaoControler.salvarouAtualizar(orgaoSelecionado);
            orgaoSelecionado = new Orgao();
            MenssagemUtil.addMessageInfo("Orgão salvo com sucesso");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar", ex, this.getClass().getName());
        }
    }

}
