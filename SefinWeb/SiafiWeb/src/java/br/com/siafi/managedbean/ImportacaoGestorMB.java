package br.com.siafi.managedbean;

import br.com.guardiao.controler.SistemaConfiguracaoController;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.AditivoController;
import br.com.siafi.controller.CentroCustoController;
import br.com.siafi.controller.ContaController;
import br.com.siafi.controller.ContratoController;
import br.com.siafi.controller.CreditoAdicionalController;
import br.com.siafi.controller.CreditoAdicionalDetalheController;
import br.com.siafi.controller.CredorController;
import br.com.siafi.controller.DotacaoController;
import br.com.siafi.controller.GrupoItemController;
import br.com.siafi.controller.ItemController;
import br.com.siafi.controller.ItemLicitacaoController;
import br.com.siafi.controller.LicitacaoController;
import br.com.siafi.controller.LicitacaoDotacaoController;
import br.com.siafi.controller.UnidadeMedidaController;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Classe do Projeto Siafi - Criado em 05/07/2013 -
 *
 * @author Gilmário
 */
@Singleton
@Startup
public class ImportacaoGestorMB implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private LicitacaoDotacaoController licitacaoDotacaoControler;
    @EJB
    private UnidadeMedidaController unidadeMedidaControler;
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
    private boolean importando;
    @EJB
    private CentroCustoController centroCustoControler;
    @EJB
    private DotacaoController dotacaoControler;
    @EJB
    private SistemaConfiguracaoController sistemaConfiguracaoControler;
    @EJB
    private CreditoAdicionalController creditoAdicionalController;
    @EJB
    private CreditoAdicionalDetalheController creditoAdicionalDetalheController;

    @Schedule(hour = "*", minute = "0,15,30,45", second = "30", persistent = false, info = "Teste de Importação")
    private void importacaoTimer() {
        try {
            Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Iniciando Importação", "Iniciando Importação");
            if ((Boolean) sistemaConfiguracaoControler.pegarValorConfiguracaoDef(Boolean.FALSE, "IMPORT", new Sistema(2L))) {
                if (importando) {
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Ainda Importando", "Ainda Importando");
                } else {
                    importando = true;
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Começando a Importar", "Começando a Importar");
                    unidadeMedidaControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Unidade de Medida Importada", "Unidade de Medida Importada");
                    grupoItemControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Grupo Item Importada", "Grupo Item Importada");
                    itemControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Item Importado", "Item Importado");
                    credorControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Credor Importado", "Credor Importado");
                    licitacaoControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Licitação Importada", "Licitação Importada");
                    itemLicitacaoControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Item Licitação Importado", "Item Licitação Importado");
                    contratoControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Contrato Importado", "Contrato Importado");
                    aditivoControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Aditivo Importado", "Aditivo Importado");
                    dotacaoControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Dotação Importada", "Dotação Importada");
                    //licitacaoDotacaoControler.importar();
                    //Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Licitação Dotação Importada", "Licitação Dotação Importada");
                    contaControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Conta Importada", "Conta Importada");
                    centroCustoControler.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Centro de Custo Importado", "Centro de Custo Importado");
                    creditoAdicionalController.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Credito Adicional Importado", "Credito Adicoinal Importado");
                    creditoAdicionalDetalheController.importar();
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Credito Adicional Detalhe Importado", "Credito Adicional Detalhe Importado");
                    Logger.getLogger(ImportacaoGestorMB.class.getName()).log(Level.INFO, "Importação Concluida", "Importação Concluida");
                    if ((Boolean) sistemaConfiguracaoControler.pegarValorConfiguracaoDef(Boolean.FALSE, "IMPORT_TESTE", new Sistema(2L))) {
                        MenssagemUtil.addMessageInfoLogger("A Importação foi concluida as " + new SimpleDateFormat("HH:mm:ss dd/MM/YYYY", new Locale("pt", "BR")).format(new Date()));
                    }
                    importando = false;
                }

            }

        } catch (Exception e) {
            importando = false;
            MenssagemUtil.addMessageErroLogger(e, this.getClass().getName());

        }
    }
}
