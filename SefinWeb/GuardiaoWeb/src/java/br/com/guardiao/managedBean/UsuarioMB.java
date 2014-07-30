/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.controler.CargoController;
import br.com.guardiao.controler.OrgaoController;
import br.com.guardiao.controler.UsuarioController;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Cargo;
import br.com.guardiao.modelo.Orgao;
import br.com.guardiao.modelo.Pessoa;
import br.com.guardiao.modelo.Usuario;
import br.com.guardiao.modelo.Util;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.modelo.CriptografiaSenha;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 * Classe do Projeto guardiao - Criado em 22/03/2013 - Bean gerenciador da
 * classe Usuário
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class UsuarioMB extends BeanGenerico<Usuario> implements Serializable {

    @Inject
    private UsuarioLogadoMB usuarioLogadoMb;
    @EJB
    private UsuarioController controler;
    @EJB
    private OrgaoController orgaoControler;
    @EJB
    private CargoController cargoController;
    @EJB
    private AreaAdministrativaController areaAdministrativaController;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private Usuario usuarioSelecionado;
    private Pessoa pessoa = new Pessoa();
    private List<Usuario> usuarios;
    private boolean docCpf;
    private List<AreaAdministrativa> listaAreasAdministrativas;
    private String senhaAtual;
    private String novaSenha;
    private String confirmeSenha;

    public UsuarioMB() {
        super(Usuario.class);

    }

    @PostConstruct
    private void geraListarUnidadesAdm() {
        usuarios = new ArrayList<>();
        listaAreasAdministrativas = new ArrayList<>();
        usuarioSelecionado = (Usuario) navegacaoGuardiao.getRegistroMapa("usuario", new Usuario());
        pessoa = new Pessoa();
        if (usuarioSelecionado.getPessoa() != null) {
            pessoa = usuarioSelecionado.getPessoa();
            docCpf = usuarioSelecionado.getDocumento().length() == 11;
        }
        if (usuarioSelecionado.getDocumento() != null && usuarioSelecionado.getOrgao() != null) {
            try {
                listaAreasAdministrativas = areaAdministrativaController.listarPorAreaAdm(usuarioSelecionado.getOrgao());
            } catch (Exception ex) {
                MenssagemUtil.addMessageErro(ex);
            }
        }
    }

    public void validateSenhaAtual(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Usuario u = usuarioLogadoMb.getUsuario();
        CriptografiaSenha cs = new CriptografiaSenha();
        senhaAtual = (String) value;
        if (!u.getSenha().equals(cs.criptografarSenha(senhaAtual))) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha atual incorreta!", ""));
        }
    }

    public void alterarSenha() {
        try {
            Usuario u = usuarioLogadoMb.getUsuario();
            controler.alterarSenha(u, senhaAtual, novaSenha, confirmeSenha);
            MenssagemUtil.addMessageInfo("Senha altera!");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao alterar senha", ex, this.getClass().getName());
        } finally {
            novaSenha = "";
            confirmeSenha = "";
            senhaAtual = "";
        }
    }

    public void listar() {
        usuarios = listar(controler);
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Orgao> getListaOrgao() {
        try {
            return orgaoControler.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
        return null;
    }

    public List<Cargo> getListaCargo() {
        try {
            return cargoController.listarTodos("nome");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
        return null;
    }

    public void listarAreasAdministrativa() {
        try {
            listaAreasAdministrativas = areaAdministrativaController.listarPorAreaAdm(usuarioSelecionado.getOrgao());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
    }

    public boolean isDocCpf() {
        return docCpf;
    }

    public void setDocCpf(boolean docCpf) {
        this.docCpf = docCpf;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void validate(FacesContext fc, UIComponent uic, Object value) {
        String doc = value.toString().replaceAll("[^0-9]", "");
        try {
            if (docCpf == true) {
                if ((Util.CPF(doc) == false) && (Util.validaCNPJ(doc) == false)) {
                    ((UIInput) uic).setValid(false);
                    MenssagemUtil.addMessageErro("Documento invalido");
                    return;
                }
            }

            if (controler.carregar(doc) != null) {
                ((UIInput) uic).setValid(false);
                MenssagemUtil.addMessageErro("Já existe um usuario cadastrado com esse documento.");

            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e);
        }

    }

    public void salvar() {
        try {
            // Tirar caracteres stranhos deixando apenas numeros
            usuarioSelecionado.setDocumento(usuarioSelecionado.getDocumento().replaceAll("[^0-9]", ""));
            usuarioSelecionado.setPessoa(pessoa);
//            usuarioSelecionado.setEndereco(endereco);
            controler.salvarouAtualizar(this.usuarioSelecionado);
            usuarioSelecionado = new Usuario();
            pessoa = new Pessoa();
            MenssagemUtil.addMessageInfo("Usuario salvo com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex);
        }
    }

    public List<AreaAdministrativa> getListaAreasAdministrativas() {
        return listaAreasAdministrativas;
    }

    public void setListaAreasAdministrativas(List<AreaAdministrativa> listaAreasAdministrativas) {
        this.listaAreasAdministrativas = listaAreasAdministrativas;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

}
