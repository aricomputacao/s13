/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.controler.UsuarioController;
import br.com.guardiao.converter.UnidadeOrcamentariaDataList;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.Usuario;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Classe do Projeto guardiao - Criado em 03/04/2013 - Bean responsavél por
 * selecionar as unidade orçamentarias do usuario.
 *
 *
 * @author Gilmário
 *
 */
@ManagedBean
@ViewScoped
public class UsuarioUnidadeOrcamentariaMB implements Serializable {

    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    // Usuario selecionado
    private Usuario usuario;
    // Dao do usuario selecionado
    @EJB
    private UsuarioController usuarioControler;
    // Dao da unidade orçamentária
    @EJB
    private UnidadeOrcamentariaController unidadeOrcamentariaController;
    // Lista das unidades orçamentarias do orgão ao qual o usuario faz parte.
    private List<UnidadeOrcamentaria> unidadesOrcamentariasOrgao;
    // Array que representa as unidades orçamentarias selecionadas na tabela.
    private UnidadeOrcamentariaDataList unidadeOrcamentariDataList;

    /**
     *
     */
    public UsuarioUnidadeOrcamentariaMB() {

    }

    public void init() {
        unidadesOrcamentariasOrgao = new ArrayList<>();
        unidadeOrcamentariDataList = new UnidadeOrcamentariaDataList();
    }

    @PostConstruct
    private void usuarioSelecionado() {
        usuario = (Usuario) navegacaoGuardiao.getRegistroMapa("usuario_und_orcamentaria", new Usuario());
        if (usuario.getDocumento() != null) {
            definirVariaveis();
        }
    }

    private void definirVariaveis() {
        try {
            if (usuario.getDocumento() != null) {
                if (usuario.getOrgao() != null) {
                    if (usuario.getAreaAdministrativa() != null && usuario.getAreaAdministrativa().isAcessoUnidadeOrcamentaria()) {
                        unidadeOrcamentariDataList = new UnidadeOrcamentariaDataList(unidadeOrcamentariaController.listarAtivos());
                    } else {
                        unidadeOrcamentariDataList = new UnidadeOrcamentariaDataList(unidadeOrcamentariaController.listar(usuario.getOrgao()));
                    }
                }
                if (usuario.getUnidadeOrcamentarias() == null) {
                    usuario.setUnidadeOrcamentarias(new ArrayList<UnidadeOrcamentaria>());
                }
            }

        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao selecionar o usuario", e, this.getClass().getName());
        }
    }

    // Incluir ou remover unidades orçamentarias
    /**
     *
     */
    public void atualizaUnidadesOrcamentarias() {
        try {
            usuarioControler.salvarouAtualizar(usuario);
            MenssagemUtil.addMessageInfo("Unidades Orçamentarias definidas com sucesso.");
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e.getMessage(), e, this.getClass().getName());
        }
    }

    public void selecionaUsuario(Usuario u) {
        try {
            this.usuario = usuarioControler.carregar(u.getDocumento());
            definirVariaveis();
        } catch (Exception ex) {
            this.usuario = null;
            MenssagemUtil.addMessageErro("Erro ao selecionar Usuario", ex, this.getClass().getName());
        }
    }

    public void selecionaUsuario() {
        definirVariaveis();
    }

    /**
     * Getters e Setters
     *
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     *
     * @return
     */
    public List<UnidadeOrcamentaria> getUnidadesOrcamentariasOrgao() {
        return unidadesOrcamentariasOrgao;
    }

    /**
     *
     * @param unidadesOrcamentariasOrgao
     */
    public void setUnidadesOrcamentariasOrgao(List<UnidadeOrcamentaria> unidadesOrcamentariasOrgao) {
        this.unidadesOrcamentariasOrgao = unidadesOrcamentariasOrgao;
    }

    /**
     *
     * @return
     */
    public UnidadeOrcamentariaDataList getUnidadeOrcamentariDataList() {
        return unidadeOrcamentariDataList;
    }

    /**
     *
     * @param unidadeOrcamentariDataList
     */
    public void setUnidadeOrcamentariDataList(UnidadeOrcamentariaDataList unidadeOrcamentariDataList) {
        this.unidadeOrcamentariDataList = unidadeOrcamentariDataList;
    }
}
