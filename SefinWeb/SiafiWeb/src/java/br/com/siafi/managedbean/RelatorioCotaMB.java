/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.util.MenssagemUtil;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.siafi.controller.CategoriaController;
import br.com.siafi.controller.CotaController;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.Cota;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @Sistema SiafiWeb
 * @Data 06/08/2013
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class RelatorioCotaMB implements Serializable {

    @EJB
    private CotaController cotaControler;
    @EJB
    private CategoriaController categoriaControler;
    @EJB
    private UsuarioMB usuarioMb;
    private Cota cota;
    private List<Cota> cotasList;
    private UnidadeOrcamentaria unidadeOrcamentaria;
    private Categoria categoria;
    private List<Categoria> categoriasList;

    public RelatorioCotaMB() {
        categoriasList = new ArrayList<>();
        cotasList = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            categoriasList = categoriaControler.listarCategoriasNormais();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro(ex, this.getClass().getName());
        }
    }

    public void listar() {
        try {
            if (unidadeOrcamentaria != null) {
                cotasList = cotaControler.listarUnidadeOrcamentariaCategoria(unidadeOrcamentaria, categoria);
            } else {
                cotasList = cotaControler.listarUnidadeOrcamentariaCategoria(usuarioMb.getUsuarioSelecionado().getUnidadeOrcamentarias(), categoria);
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao executar consulta", ex, this.getClass().getName());
        }
    }

    public Cota getCota() {
        return cota;
    }

    public void setCota(Cota cota) {
        this.cota = cota;
    }

    public List<Cota> getCotasList() {
        return cotasList;
    }

    public void setCotasList(List<Cota> cotasList) {
        this.cotasList = cotasList;
    }

    public UnidadeOrcamentaria getUnidadeOrcamentaria() {
        return unidadeOrcamentaria;
    }

    public void setUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) {
        this.unidadeOrcamentaria = unidadeOrcamentaria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Categoria> getCategoriasList() {
        return categoriasList;
    }

    public void setCategoriasList(List<Categoria> categoriasList) {
        this.categoriasList = categoriasList;
    }

    public void impressao() {
        try {
            if (cotasList.isEmpty()) {
                MenssagemUtil.addMessageInfo("Não hé nenhum registro para impressão");
            } else {
                String titulo = "Relatório de Solicitações Financeiras - Sintético";
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(cotasList, m, "WEB-INF/relatorios/relatorio_cota_sintetico.jasper", titulo);
                RelatorioSession.setBytesRelatorioInSession(rel);
            }
        } catch (Exception e) {
            MenssagemUtil.addMessageErro(e);
        }
    }
}
