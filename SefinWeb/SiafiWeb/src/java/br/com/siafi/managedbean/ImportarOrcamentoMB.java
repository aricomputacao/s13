/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.gestor.dao.OrdenadorUnidadeOrcamentariaDAO;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.AditivoController;
import br.com.siafi.controller.CategoriaDespesaController;
import br.com.siafi.controller.CentroCustoController;
import br.com.siafi.controller.ContaController;
import br.com.siafi.controller.ContratoController;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.DotacaoController;
import br.com.siafi.controller.ElementoDespesaController;
import br.com.guardiao.controler.ExercicioController;
import br.com.guardiao.controler.UnidadeOrcamentariaController;
import br.com.siafi.controller.FonteRecursoController;
import br.com.siafi.controller.FuncaoController;
import br.com.siafi.controller.GrupoItemController;
import br.com.siafi.controller.GrupoNaturezaDespesaController;
import br.com.siafi.controller.ItemController;
import br.com.siafi.controller.ItemLicitacaoController;
import br.com.siafi.controller.LicitacaoController;
import br.com.siafi.controller.LicitacaoDotacaoController;
import br.com.siafi.controller.ModalidadeAplicacaoDepesaController;
import br.com.siafi.controller.NaturezaDespesaController;
import br.com.siafi.controller.OrcamentoController;
import br.com.siafi.controller.ProgramaController;
import br.com.siafi.controller.ProjetoAtividadeController;
import br.com.siafi.controller.SubElementoDespesaController;
import br.com.siafi.controller.SubFuncaoController;
import br.com.siafi.controller.TipoFonteRecursoController;
import br.com.siafi.controller.UnidadeMedidaController;
import br.com.guardiao.modelo.Exercicio;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.controller.CreditoAdicionalController;
import br.com.siafi.controller.CreditoAdicionalDetalheController;
import br.com.siafi.controller.MovimentacaoBancoController;
import br.com.siafi.controller.RPOrgaoController;
import br.com.siafi.controller.RPUnidadeOrcamentariaController;
import br.com.siafi.controller.RpEmpenhoController;
import br.com.siafi.controller.RpLiquidacaoController;
import br.com.siafi.controller.RpProjetoAtividadeController;
import br.com.siafi.modelo.Orcamento;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @Sistema SiafiWeb
 * @Data 01/08/2013
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class ImportarOrcamentoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private LicitacaoDotacaoController licitacaoDotacaoControler;
    @EJB
    private UnidadeMedidaController unidadeMedidaControler;
    @EJB
    private FuncaoController funcaoControler;
    @EJB
    private SubFuncaoController subFuncaoControler;
    @EJB
    private ProgramaController programaControler;
    @EJB
    private ProjetoAtividadeController projetoAtividadeControler;
    @EJB
    private SubElementoDespesaController subElementoDespesaControler;
    @EJB
    private GrupoNaturezaDespesaController grupoNaturezaDespesaControler;
    @EJB
    private CategoriaDespesaController categoriaDespesaControler;
    @EJB
    private ModalidadeAplicacaoDepesaController modalidadeAplicacaoDepesaControler;
    @EJB
    private ElementoDespesaController elementoDespesaControler;
    @EJB
    private NaturezaDespesaController naturezaDespesaControler;
    @EJB
    private TipoFonteRecursoController tipoFonteRecursoControler;
    @EJB
    private FonteRecursoController fonteRecursoControler;
    @EJB
    private DotacaoController dotacaoControler;
    @EJB
    private GrupoItemController grupoItemControler;
    @EJB
    private ItemController itemControler;
    @EJB
    private CredorController credorControler;
    @EJB
    private LicitacaoController licitacaoControler;
    @EJB
    private ItemLicitacaoController itemLicitacaoControler;
    @EJB
    private ContratoController contratoControler;
    @EJB
    private AditivoController aditivoControler;
    @EJB
    private ContaController contaControler;
    @EJB
    private ExercicioController exercicioControler;
    @EJB
    private CentroCustoController centroCustoControler;
    @EJB
    private OrcamentoController orcamentoControler;
    @EJB
    private RPOrgaoController rPOrgaoController;
    @EJB
    private RPUnidadeOrcamentariaController rPUnidadeOrcamentariaController;
    @EJB
    private CreditoAdicionalController creditoAdicionalController;
    @EJB
    private CreditoAdicionalDetalheController creditoAdicionalDetalheController;
    @EJB
    private RpEmpenhoController rpEmpenhoController;
    @EJB
    private RpProjetoAtividadeController rpProjetoAtividadeController;
    @EJB
    private RpLiquidacaoController rpLiquidacaoController;
    @EJB
    private OrdenadorUnidadeOrcamentariaDAO ordenadorUnidadeOrcamentariaDao;
    @EJB
    private UnidadeOrcamentariaController unidadeOrcamentariaController;
    @EJB
    private MovimentacaoBancoController movimentacaoBancoController;
    private Exercicio exercicio;
    private Orcamento orcamento;

    public ImportarOrcamentoMB() {
    }

    @PostConstruct
    private void init() {
        try {
            exercicio = exercicioControler.exercicioVigente();
            orcamento = orcamentoControler.getOrcamentoVigente();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErroLogger(ex, this.getClass().getName());
        }
    }

    public void importarRpOrgao() {
        try {
            rPOrgaoController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarRpProjetoAtividade() {
        try {
            rpProjetoAtividadeController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());

        }
    }

    public void importaCreditoAdicional() {
        try {
            creditoAdicionalController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importaCreditoAdicionalDetalhe() {
        try {
            creditoAdicionalDetalheController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarRpUnidadesOrcamentarias() {
        try {
            rPUnidadeOrcamentariaController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarCentroCusto() {
        try {
            centroCustoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarConta() {
        try {
            contaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarDotacao() {
        try {
            dotacaoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarFonteRecurso() {
        try {
            fonteRecursoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarTipoFonteRecurso() {
        try {
            tipoFonteRecursoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarNaturezaDespesa() {
        try {
            naturezaDespesaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarElementoDespesa() {
        try {
            elementoDespesaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarModalidadeAplicacaoDepesa() {
        try {
            modalidadeAplicacaoDepesaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarCategoriaDespesa() {
        try {
            categoriaDespesaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarGrupoNaturezaDespesa() {
        try {
            grupoNaturezaDespesaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarSubElementoDespesa() {
        try {
            subElementoDespesaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarProjetoAtividade() {
        try {
            projetoAtividadeControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarPrograma() {
        try {
            programaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarSubFuncao() {
        try {
            subFuncaoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarFuncao() {
        try {
            funcaoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarUnidadeMedida() {
        try {
            unidadeMedidaControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarGrupoItem() {
        try {
            grupoItemControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarItem() {
        try {
            itemControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarCredor() {
        try {
            credorControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarLicitacao() {
        try {
            licitacaoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarItemLicitacao() {
        try {
            itemLicitacaoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarLicitacaoDotacao() {
        try {
            licitacaoDotacaoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarContrato() {
        try {
            contratoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarAditivo() {
        try {
            aditivoControler.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarRpEmpenho() {
        try {
            rpEmpenhoController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());

        }
    }

    public void importarRpLiquidacao() {
        try {
            rpLiquidacaoController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public void importarMovimentacaoBanco() {
        try {
            movimentacaoBancoController.importar();
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    /**
     * Importar o ordenador das unidades orçamentarias
     */
    public void importarOrdenadorUnidadeOrcamentaria() {
        try {
            List<UnidadeOrcamentaria> unidadeOrcamentarias = unidadeOrcamentariaController.listarAtivos();
            for (UnidadeOrcamentaria un : unidadeOrcamentarias) {
                un.setOrdenador(ordenadorUnidadeOrcamentariaDao.getOrdenador(un.getUnidadeOrcamentariaPK().getIdOrgao(), un.getUnidadeOrcamentariaPK().getId()));
                unidadeOrcamentariaController.salvarouAtualizar(un);
            }
            MenssagemUtil.addMessageInfo("Registros importados sem erro.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao importar", ex, this.getClass().getName());
        }
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

}
