/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.ModuloController;
import br.com.guardiao.controler.PermissaoController;
import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.controler.TarefaController;
import br.com.guardiao.controler.UsuarioController;
import br.com.guardiao.converter.SistemaDataList;
import br.com.guardiao.modelo.Modulo;
import br.com.guardiao.modelo.Permissao;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.modelo.Tarefa;
import br.com.guardiao.modelo.Usuario;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Classe do Projeto guardiao - Criado em 26/03/2013 - Bean Gerenciador dos
 * acessos. Responsável por mostrar e incluir novos acessos.
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class PermissaoMB implements Serializable {

    @EJB
    private PermissaoController controller;
    @EJB
    private SistemaController sistemaControler;
    @EJB
    private ModuloController moduloControler;
    @EJB
    private TarefaController tarefaControler;
    @EJB
    private UsuarioController usuarioControler;
    // Permissão selecionada
    private Permissao permissaoSel;
    // Lista de Sistemas que o usuario possues acesso
    private List<Sistema> sistemas;
    // Lista de modulos do sistema selecionado.
    private List<Modulo> modulos;
    // Lista de Tarefas do determinado modulo selecionada
    private List<Tarefa> tarefas;
    // Modelo de lista de sistemas selecionavel
    private SistemaDataList sistemaDataList;
    // Sistema selecionado
    private Sistema sistema;
    // Tarefa selecionada
    private Tarefa tarefa;
    // Usuario do selecionado
    private Usuario usuario;
    // Usuario utilizado na clonagem de acessos
    private Usuario usuClone;
    // Permissões do usuario da clonagem de acessos
    private List<Permissao> usoClonePermissoes;
    // Modulo selecionado
    private Modulo modulo;
    // Lista de permissões do usuario
    private List<Permissao> permissoes;
    // Definir a tab selecionada no momento
    private int index;
    private boolean sel;

    /**
     * Construtor com inicialização das listas
     */
    public PermissaoMB() {
        this.tarefas = new ArrayList<>();
        this.sistemas = new ArrayList<>();
        this.modulos = new ArrayList<>();
        this.permissoes = new ArrayList<>();
        this.usuario = new Usuario();
        this.modulo = new Modulo();
        this.permissaoSel = new Permissao();
        this.sistema = new Sistema();
        this.tarefa = new Tarefa();
        this.usuClone = new Usuario();
    }

    /**
     * Como o escopo é de sessão então se deve esvaziar variáveis comuns.
     */
    private void limpar() {
        permissaoSel = new Permissao();
        sistemas = new ArrayList<>();
        modulos.clear();
        tarefas.clear();
        sistema = new Sistema();
        tarefa = new Tarefa();
        modulo = new Modulo();
        permissoes.clear();
        usuClone = new Usuario();
        usoClonePermissoes = new ArrayList<>();
    }

    public boolean sel() {
        return sel;
    }

    public void sel(boolean s) {
        sel = s;
    }

    /**
     * Salvar as permissões selecionadas
     */
    public void salvaPermissao() {
        try {
            controller.salvarouAtualizar(permissaoSel);
            MenssagemUtil.addMessageInfo("Permissão atualizada.");
            permissoes.add(permissaoSel);
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao salvar permissão", e, this.getClass().getName());
        }
    }

    public void excluirPermissao() {
        try {
            permissaoSel = controller.gerenciar(permissaoSel.getId());
            controller.excluir(permissaoSel);
            usuario.getPermissoes().remove(permissaoSel);
            permissoes.remove(permissaoSel);
            permissaoSel = new Permissao();
            MenssagemUtil.addMessageInfo("Permissão excluída com sucesso.");
            //permissoes.clear();
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao excluir permissão", e, this.getClass().getName());
        }
    }

    /**
     *
     */
    public void cancelarClonagem() {
        usuClone = new Usuario();
        usoClonePermissoes = new ArrayList<>();
    }

    /**
     *
     */
    public void clonarPermissoes() {
        if (usuClone.getDocumento() != null) {
            if (usuClone.getDocumento().equals(usuario.getDocumento())) {
                MenssagemUtil.addMessageErro("Selecione um usuario diferente");
                return;
            }
            // Deletar as permissões antigas do usuario
            try {
                usuario.getPermissoes().clear();
                controller.deletarPermissoes(usuario);
            } catch (Exception e) {
                MenssagemUtil.addMessageErro("Erro ao erxcluir as permissões antigas.", e, this.getClass().getName());
            }

            // Iterar e incluir as novas permissões do usuario
            for (Permissao cp : usoClonePermissoes) {
                Permissao p = new Permissao();
                p.setConsultar(cp.isConsultar());
                p.setEditar(cp.isEditar());
                p.setExcluir(cp.isExcluir());
                p.setIncluir(cp.isIncluir());
                p.setTarefa(cp.getTarefa());
                p.setUsuario(usuario);
                try {
                    controller.salvarouAtualizar(p);
                } catch (Exception e) {
                    MenssagemUtil.addMessageErro("Erro ao salvar a permissão " + cp.toString(), e, this.getClass().getName());
                }
            }
            MenssagemUtil.addMessageInfo("Permissões clonadas.");
            // re-inicializar as variaveis
            selecionaUsuario(usuario);

        }
    }

    /**
     * Listar os sistemas cadastrados no construtor
     */
    public void atualizaSistemaDataList() {
        try {
            sistemaDataList = new SistemaDataList(sistemaControler.listarTodos("nome"));

        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao atualizar lista de Sistemas", ex, this.getClass().getName());
        }
    }

    /**
     * Caso seja selecionado algum sistema na tabela de sistemas ele será
     * incluido na lista de sistemas do usuário
     */
    public void incluiAcessoSistemaUsuario() {
        try {
            if (usuario.getSistemas() == null) {
                usuario.setSistemas(new ArrayList<Sistema>());
            }
            usuario.getSistemas().clear();
            usuario.getSistemas().addAll(sistemas);
            // salvar os sistemas adicionados
            usuarioControler.salvarouAtualizar(usuario);
            MenssagemUtil.addMessageInfo("Acesso aos sistemas selecionado foi configurado.");
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao selecionar a lista de sistemas", e, this.getClass().getName());
        }
    }

    /**
     * Atualizar a lista de sistemas disponiveis para o usuário
     */
    public void atualizaSistemas() {
        try {
            if (usuario.getSistemas() == null) {
                usuario.setSistemas(new ArrayList<Sistema>());
            }
            sistemas = usuario.getSistemas();
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao atualizar lista de Sistemas", e, this.getClass().getName());
        }
    }

    /**
     *
     */
    public void atualizaModulos() {
        try {
            if (sistema.getId() != null) {

                modulos = moduloControler.listar(sistema);

            } else {
                modulos.clear();
                MenssagemUtil.addMessageInfo("Selecione um sistema");
            }
            limpaPermissao();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao atualizar lista de modulos", ex, this.getClass().getName());
        }
    }

    private void limpaPermissao() {
        permissaoSel = new Permissao();
    }

    // Mostrar a lista de tarfas de um determinado módulo
    /**
     *
     */
    public void atualizaTarefas() {
        limpaPermissao();
        try {
            if (modulo != null) {
                if (modulo.getId() != null) {
                    modulo = moduloControler.carregar(modulo.getId());
                    tarefas = tarefaControler.listarPorModulo(modulo);
                } else {
                    MenssagemUtil.addMessageErro("Não há nenhum módulo selecionado");
                }
            } else {
                MenssagemUtil.addMessageErro("Não há nenhum módulo selecionado");
            }

        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao atualizar lista de Tarefas", e, this.getClass().getName());
            tarefas = new ArrayList<>();
        }
    }

    /**
     *
     */
    public void selecionaPermissaoTarefa() {
        if (tarefa != null) {
            permissaoSel = new Permissao();
            for (Permissao p : permissoes) {
                if (p.getTarefa().equals(tarefa)) {
                    permissaoSel = p;
                }
            }
            if (permissaoSel.getId() == null) {
                permissaoSel = new Permissao();
                permissaoSel.setTarefa(tarefa);
                permissaoSel.setUsuario(usuario);
            }
        }
    }

    /**
     *
     * @param u
     */
    public void selecionaUsoClone(Usuario u) {
        try {
            usuClone = u;
            usoClonePermissoes = usuClone.getPermissoes();
            if (usoClonePermissoes.isEmpty()) {
                MenssagemUtil.addMessageInfo("Esse Usuario não possue permissões.");
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao buscar permissões do usuario a ser clonado", e, this.getClass().getName());
            usoClonePermissoes = new ArrayList<>();
        }
    }

    public void selecionaUsuario(Usuario u) {
        try {
            this.usuario = usuarioControler.carregar(u.getDocumento());
            index = 0;
            limpar();
            atualizaSistemaDataList();
            atualizaSistemas();
            atualizaPermissoes();
        } catch (Exception ex) {
            this.usuario = null;
            MenssagemUtil.addMessageErro("Erro ao selecionar Usuario", ex, this.getClass().getName());
        }
    }

    public void ativarUsuario() {
        try {
            usuario.setAcesso(true);
            usuarioControler.salvarouAtualizar(usuario);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao ativar o usuario", ex, this.getClass().getName());
        }
    }

    public void desativarUsuario() {
        try {
            usuario.setAcesso(false);
            usuarioControler.salvarouAtualizar(usuario);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao ativar o usuario", ex, this.getClass().getName());
        }
    }

    /**
     *
     */
    public void atualizaPermissoes() {
        try {
            permissoes = usuario.getPermissoes();
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Ocorreu um erro ao listar as permissões.", e, this.getClass().getName());
        }
    }

    /**
     * Getter e Setter
     *
     * @return
     */
    public Usuario getUsuario() {
        return usuario;
    }
    // set usuario com metodos de inicialização de variaveis.

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
    public Permissao getPermissaoSel() {
        return permissaoSel;
    }

    /**
     *
     * @param permissaoSel
     */
    public void setPermissaoSel(Permissao permissaoSel) {
        this.permissaoSel = permissaoSel;
    }

    /**
     *
     * @return
     */
    public List<Modulo> getModulos() {
        return modulos;
    }

    /**
     *
     * @param modulos
     */
    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    /**
     *
     * @return
     */
    public List<Sistema> getSistemas() {
        return sistemas;
    }

    /**
     *
     * @param sistemas
     */
    public void setSistemas(List<Sistema> sistemas) {
        this.sistemas = sistemas;
    }

    /**
     *
     * @return
     */
    public Sistema getSistema() {
        return sistema;
    }

    /**
     *
     * @param sistema
     */
    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    /**
     *
     * @return
     */
    public Modulo getModulo() {
        return modulo;
    }

    /**
     *
     * @param modulo
     */
    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    /**
     *
     * @return
     */
    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    /**
     *
     * @param tarefas
     */
    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    /**
     *
     * @return
     */
    public SistemaDataList getSistemaDataList() {
        return sistemaDataList;
    }

    /**
     *
     * @param sistemaDataList
     */
    public void setSistemaDataList(SistemaDataList sistemaDataList) {
        this.sistemaDataList = sistemaDataList;
    }

    /**
     *
     * @return
     */
    public Tarefa getTarefa() {
        return tarefa;
    }

    /**
     *
     * @param tarefa
     */
    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    /**
     *
     * @return
     */
    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    /**
     *
     * @param permissoes
     */
    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    /**
     *
     * @return
     */
    public Usuario getUsuClone() {
        return usuClone;
    }

    /**
     *
     * @param usuClone
     */
    public void setUsuClone(Usuario usuClone) {
        this.usuClone = usuClone;
    }

    /**
     *
     * @return
     */
    public List<Permissao> getUsoClonePermissoes() {
        return usoClonePermissoes;
    }

    /**
     *
     * @param usoClonePermissoes
     */
    public void setUsoClonePermissoes(List<Permissao> usoClonePermissoes) {
        this.usoClonePermissoes = usoClonePermissoes;
    }

    public void marcarTodos() {
        permissaoSel.setIncluir(true);
        permissaoSel.setEditar(true);
        permissaoSel.setExcluir(true);
        permissaoSel.setConsultar(true);
    }

    public void desMarcarTodos() {
        permissaoSel.setIncluir(false);
        permissaoSel.setEditar(false);
        permissaoSel.setExcluir(false);
        permissaoSel.setConsultar(false);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean existePermissao() {
        try {
            return permissaoSel.getTarefa() != null && permissaoSel.getTarefa().getId() != null;
        } catch (Exception e) {
            return false;
        }
    }

}
