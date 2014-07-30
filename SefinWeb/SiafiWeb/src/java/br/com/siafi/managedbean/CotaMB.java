/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.Usuario;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.Mes;
import br.com.siafi.controller.CategoriaController;
import br.com.siafi.controller.CotaControleController;
import br.com.siafi.controller.CotaController;
import br.com.siafi.controller.CentroCustoController;
import br.com.siafi.controller.SolicitacaoFinanceiraController;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.CentroCusto;
import br.com.sefin.modelo.dto.CotaAnaliticoDto;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JRException;

/**
 * Classe do Projeto Siafi - Criado em 07/05/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class CotaMB extends BeanGenerico<Cota> implements Serializable {

    @EJB
    private CotaController controle;
    @EJB
    private CategoriaController categoriaControler;
    @EJB
    private CentroCustoController despesaControler;
    @EJB
    private UnidadeOrcamentariaController unidadeOrcamentariaControler;
    @EJB
    private UsuarioMB usuarioMb;
    @EJB
    private CotaControleController cotaControleControler;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    private Cota cota;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private List<Cota> listaCotas;
    private List<UnidadeOrcamentaria> listaUnidadeOrcamentarias;
    private List<CentroCusto> listaDespesas;
    private List<Categoria> listaCategorias;
    private List<CotaAnaliticoDto> cotaAnaliticoDtos;
    private Usuario usuario;
    private boolean renderizarFormCadastro;
    private boolean editarCota;
    private Mes competencia;

    public CotaMB() {
        super(Cota.class);
        listaCotas = new ArrayList<>();
        cota = new Cota();
    }

    @PostConstruct
    public void popularListas() {
        usuario = usuarioMb.getUsuarioSelecionado();
        try {
            listaUnidadeOrcamentarias = unidadeOrcamentariaControler.listarAtivos();
            listaCategorias = categoriaControler.listarTodos("nome");
            listaDespesas = despesaControler.listarOrdenado();
            renderizarFormCadastro = false;
            cotaAnaliticoDtos = new ArrayList<>();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao iniciar bean", ex, this.getClass().getName());
        }
    }

    public void novaCota() {
        cota = new Cota();
        renderizarFormCadastro = true;
        editarCota = false;
        listaCotas.clear();

    }

    public void cancelar() {
        cota = new Cota();
        renderizarFormCadastro = false;
    }

    public void salvar() {
        try {
            if (cotaControleControler.podeAlterarCota(cota)) {
                cota.setUnidadeOrcamentaria(unidadeOrcamentaria);
                controle.salvarouAtualizar(cota);
                cotaControleControler.novoValorCota(cota, competencia);
                addCota(cota);
                cota = new Cota();
                MenssagemUtil.addMessageInfo("Registro Salvo com sucesso");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar cota", ex, this.getClass().getName());
        }
    }

    public List<Mes> getListaMeses() {
        return Arrays.asList(Mes.values());
    }

    public void editar(Cota c) {
        cota = c;
        renderizarFormCadastro = true;
        editarCota = true;
    }

    private void addCota(Cota cota) {
        if (!listaCotas.contains(cota)) {
            listaCotas.add(cota);
        }
    }

    public void excluir() {
        try {
            controle.excluir(cota);
            listaCotas.remove(cota);
            MenssagemUtil.addMessageInfo("Cota excluida com sucesso");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao excluir cota", ex, this.getClass().getName());
        }
    }

    public void listar() {
        try {
            listaCotas = controle.listarTodos("despesa.nome");
            if (listaCotas.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nenhuma cota encontrada");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public void listarCotas() {
        try {
            listaCotas = controle.listarUnidadeOrcCategoria(unidadeOrcamentaria, cota.getCategoria());
            if (listaCotas.isEmpty()) {
                MenssagemUtil.addMessageInfo("Nemhuma cota encontrada");
            }
        } catch (Exception e) {
            listaCotas = new ArrayList<>();
            MenssagemUtil.addMessageErro("Erro ao executar consulta", e, this.getClass().getName());
        }

    }

    public void selecionar() {
        try {
            controle.carregar(cota.getId());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar registro", ex, this.getClass().getName());
        }
    }

    public void consultaAnalitica() {
        try {
            if (unidadeOrcamentaria != null) {
                List<Cota> cotas = controle.listarPorUnidadeOrcamentaria(unidadeOrcamentaria);
                List<SolicitacaoFinanceira> financeiras = solicitacaoFinanceiraControler.listarPorCota(competencia, unidadeOrcamentaria);
                cotaAnaliticoDtos = controle.CotaAnaliticoDto(financeiras, cotas, competencia);
            } else {
                cotaAnaliticoDtos = new ArrayList<>();
                MenssagemUtil.addMessageInfo("Selecione uma Unidade Orçamentaria");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao executar consulta", ex, this.getClass().getName());
        }

    }

    public void impressao() {
        if (!listaCotas.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(listaCotas, m, "WEB-INF/relatorios/relatorio_cota_sintetico.jasper", "Relatorio de Custeio por Cota");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        } else {
            MenssagemUtil.addMessageInfo("Não há dados na consulta");
        }
    }

    public void impressaoCotaAnalitica() {
        if (!cotaAnaliticoDtos.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(cotaAnaliticoDtos, m, "WEB-INF/relatorios/relatorio_cota_analitico.jasper", "Relatorio de Analíticos de  Cotas");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar relatório", ex, this.getClass().getName());
            }
        } else {
            MenssagemUtil.addMessageInfo("Não há dados na consulta");
        }
    }

    public void novo() {
        cota = new Cota();
    }

    

    public Cota getCota() {
        return cota;
    }

    public void setCota(Cota cota) {
        this.cota = cota;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public List<Cota> getListaCotas() {
        return listaCotas;
    }

    public void setListaCotas(List<Cota> listaCotas) {
        this.listaCotas = listaCotas;
    }

    public List<CentroCusto> getListaDespesas() {
        return listaDespesas;
    }

    public void setListaDespesas(List<CentroCusto> listaDespesas) {
        this.listaDespesas = listaDespesas;
    }

    public List<Categoria> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<UnidadeOrcamentaria> getListaUnidadeOrcamentarias() {
        return listaUnidadeOrcamentarias;
    }

    public void setListaUnidadeOrcamentarias(List<UnidadeOrcamentaria> listaUnidadeOrcamentarias) {
        this.listaUnidadeOrcamentarias = listaUnidadeOrcamentarias;
    }

    public boolean isRenderizarFormCadastro() {
        return renderizarFormCadastro;
    }

    public void setRenderizarFormCadastro(boolean renderizarFormCadastro) {
        this.renderizarFormCadastro = renderizarFormCadastro;
    }

    public boolean isEditarCota() {
        return editarCota;
    }

    public void setEditarCota(boolean editarCota) {
        this.editarCota = editarCota;
    }

    public Mes getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Mes competencia) {
        this.competencia = competencia;
    }

    public List<CotaAnaliticoDto> getCotaAnaliticoDtos() {
        return cotaAnaliticoDtos;
    }

    public void setCotaAnaliticoDtos(List<CotaAnaliticoDto> cotaAnaliticoDtos) {
        this.cotaAnaliticoDtos = cotaAnaliticoDtos;
    }
}
