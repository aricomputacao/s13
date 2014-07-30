/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.controler.OrgaoController;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Classe do Projeto Guardião - Criado em 13/05/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class AreaAdministrativaMB extends BeanGenerico<AreaAdministrativa> implements Serializable {

    @EJB
    private AreaAdministrativaController controller;
    @EJB
    private OrgaoController orgaoControler;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private AreaAdministrativa areaAdministrativaSele;
    private List<AreaAdministrativa> listaAreasAdministrativas;

    public AreaAdministrativaMB() {
        super(AreaAdministrativa.class);
    }

    @PostConstruct
    public void init() {
        areaAdministrativaSele = (AreaAdministrativa) navegacaoGuardiao.getRegistroMapa("areaAdm", new AreaAdministrativa());
        listaAreasAdministrativas = new ArrayList<>();
    }

    public List<Orgao> getListaOrgao() {
        try {
            return orgaoControler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
        return new ArrayList<>();
    }

    public void listar() {
        listaAreasAdministrativas = listar(controller);
    }

    public AreaAdministrativa getAreaAdministrativaSele() {
        return areaAdministrativaSele;
    }

    public void setAreaAdministrativaSele(AreaAdministrativa areaAdministrativaSele) {
        this.areaAdministrativaSele = areaAdministrativaSele;
    }

    public List<AreaAdministrativa> getListaAreasAdministrativas() {
        return listaAreasAdministrativas;
    }

    public void setListaAreasAdministrativas(List<AreaAdministrativa> listaAreasAdministrativas) {
        this.listaAreasAdministrativas = listaAreasAdministrativas;
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(areaAdministrativaSele);
            areaAdministrativaSele = new AreaAdministrativa();
            MenssagemUtil.addMessageInfo("Área Administrativa cadastrada com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar", ex, this.getClass().getName());
        }
    }

    public List<TipoAreaAdm> getTipoAreaAdmList() {
        return Arrays.asList(TipoAreaAdm.values());
    }
}
