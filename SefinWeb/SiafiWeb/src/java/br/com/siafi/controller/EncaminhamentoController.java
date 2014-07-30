/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.gestor.dao.EmpenhoSolicitacaoDAO;
import br.com.gestor.dao.EmpenhoSolicitacaoItemDAO;
import br.com.gestor.modelo.EmpenhoSolicitacao;
import br.com.gestor.modelo.EmpenhoSolicitacaoItem;
import br.com.guardiao.controler.AreaAdministrativaController;
import br.com.guardiao.controler.Controller;
import br.com.guardiao.controler.UsuarioController;
import br.com.guardiao.dao.AreaAdministrativaDAO;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.modelo.Usuario;
import br.com.guardiao.util.MenssagemUtil;
import br.com.sefin.enumerated.TipoFluxo;
import br.com.siafi.dao.EncaminhamentoDAO;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.EmpenhoDetalhe;
import br.com.siafi.modelo.Encaminhamento;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;

/**
 * @Sistema SiafiWeb
 * @Data 19/07/2013
 * @author gilmario
 */
@Stateless(name = "SiafiEncaminhamentoController")
public class EncaminhamentoController extends Controller<Encaminhamento, Long> implements Serializable {

    @EJB
    private EncaminhamentoDAO dao;
    @EJB
    private AreaAdministrativaDAO administrativaDao;
    @EJB
    private EmpenhoSolicitacaoDAO gestorEmpenhoSolicitacaoDao;
    @EJB
    private AreaAdministrativaController areaAdministrativaControler;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;
    @EJB
    private UsuarioController usuarioControler;
    @EJB
    private EmpenhoSolicitacaoItemDAO gestorEmpenhoItemDao;
    @EJB
    private EmpenhoDetalheController empenhoDetalheController;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(Encaminhamento t) throws Exception {
        if (t.getId() == null) {
            encaminharParaContabilidade(t);
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    /**
     * Metodo que checa se todas as liquidações estão pagas para marcar o
     * protocolo como pago e encaminhar para contabilidade
     *
     * @param sf
     * @param u
     */
    public void encaminharLiquidacaoesPagasContabilidae(SolicitacaoFinanceira sf, Usuario u) throws Exception {
        List<EmpenhoDetalhe> detalhes = new ArrayList<>();
        detalhes =  empenhoDetalheController.importarDetalhesPagamento(sf);
        
        BigDecimal valor = empenhoDetalheController.somarLiquidacoes(detalhes);
        if (empenhoDetalheController.checaLiquidacoesPagas(detalhes) && (sf.getValor().compareTo(valor) == 0)) {
            sf.setSituacaoSolicitacao(SituacaoSolicitacao.Pago);
            solicitacaoFinanceiraControler.atualiza(sf);
            
            //Encaminha para contabilidae
            Encaminhamento e = new Encaminhamento();
            e.setDataEncaminhamento(new Date());
            e.setDestino(areaAdministrativaControler.administrativaTipo(TipoAreaAdm.Contabilidade));
            e.setSolicitacaoFinanceira(sf);
            e.setUsuarioEncaminhou(u);
            e.setObservacao("");
            dao.salvar(e);
        }

    }

    //Metodo excluir do empenho_aut se contabilidade devolver
    public void excluirSolicitacaoGestor(Encaminhamento e) {
        if (e.getUsuarioEncaminhou().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && e.getDestino().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro) && e.getSolicitacaoFinanceira().getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
            EmpenhoSolicitacao empenhoSolicitacao = gestorEmpenhoSolicitacaoDao.buscarEmpenhoAut(e.getSolicitacaoFinanceira().getId());
            List<EmpenhoSolicitacaoItem> listEmpenhoItem = gestorEmpenhoItemDao.buscarPorSolicitacao(e.getSolicitacaoFinanceira().getId());
            if (empenhoSolicitacao != null) {
                for (EmpenhoSolicitacaoItem empenhoSolicitacaoItem : listEmpenhoItem) {
                    gestorEmpenhoItemDao.remover(empenhoSolicitacaoItem);
                }
                gestorEmpenhoSolicitacaoDao.removerEmpAut(empenhoSolicitacao);

            }

        }
    }

    //Metodo de envio automatico para contabilidade
    public void encaminharParaContabilidade(Encaminhamento e) throws PersistenceException, EJBException, Exception {
        if (e.getUsuarioEncaminhou().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro) && e.getDestino().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && e.getSolicitacaoFinanceira().getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
            e.setDataRecebimento(new Date());
            e.setObservacao("Encaminhamento gerado automaticamente!");
            e.getSolicitacaoFinanceira().setLocal(areaAdministrativaControler.administrativaTipo(TipoAreaAdm.Contabilidade));
            e.setUsuarioRecebeu(usuarioControler.carregar("0000005"));
            solicitacaoFinanceiraControler.exportaSolicitacao(e.getSolicitacaoFinanceira());
            solicitacaoFinanceiraControler.salvarouAtualizar(e.getSolicitacaoFinanceira());

        }
    }

//    public void receberSPU(Encaminhamento e) throws SQLException, PersistenceException, EJBException, Exception {
//        Protocolo protocolo = verificaCriaProtocoloSpu(e.getSolicitacaoFinanceira());
//        // Localiza o encaminhamento pendente de recebimentodeste protocolo para dar baixa
//        EncaminhamentoSpu encaminhamentoSPU = controllerSPU.ultimoEncaminhamento(protocolo);
//        encaminhamentoSPU.setDataRecebimento(e.getDataRecebimento());
//        encaminhamentoSPU.setSituacao(SituacaoEncaminhamento.Recebido);
//        encaminhamentoSPU.setUsuarioDestino(e.getUsuarioRecebeu());
//        controllerSPU.salvarouAtualizar(encaminhamentoSPU);
//        protocolo.setLocal(e.getUsuarioRecebeu());
//        protocoloController.salvarouAtualizar(protocolo);
//    }
//    public void concluirProtocoloSPU(Encaminhamento e) throws SQLException, PersistenceException, EJBException, Exception {
//        Protocolo protocolo = verificaCriaProtocoloSpu(e.getSolicitacaoFinanceira());
//        // Localiza o encaminhamento pendente de recebimentodeste protocolo para dar baixa
//        protocolo.setConclusao("Solicitação financeira consluida e arquivada.");
//        protocolo.setSituacao(SituacaoProtocolo.Concluido);
//        protocoloController.salvarouAtualizar(protocolo);
//    }
//    public void encaminharSPU(Encaminhamento e) throws SQLException, PersistenceException, EJBException, Exception {
//        Protocolo protocolo = verificaCriaProtocoloSpu(e.getSolicitacaoFinanceira());
//        // Criar um encaminhamento no SPU
//        br.com.spu.modelo.EncaminhamentoSpu encaminhamentoSPU = new br.com.spu.modelo.EncaminhamentoSpu();
//        encaminhamentoSPU.setAreaAdministrativa(e.getDestino());
//        encaminhamentoSPU.setDataEncaminhamento(e.getDataEncaminhamento());
//        encaminhamentoSPU.setDataRecebimento(e.getDataRecebimento());
//        encaminhamentoSPU.setDespacho(e.getObservacao() + "\r\nEstatus do Processo: " + e.getSolicitacaoFinanceira().getSituacaoSolicitacao().toString());
//        encaminhamentoSPU.setHoraEncaminhamento(e.getDataEncaminhamento());
//        encaminhamentoSPU.setProtocolo(protocolo);
//        encaminhamentoSPU.setSequencia(controllerSPU.getProximaSequencia(protocolo));
//        encaminhamentoSPU.setSituacao(SituacaoEncaminhamento.Encaminhado);
//        encaminhamentoSPU.setUsuarioDestino(null);
//        encaminhamentoSPU.setUsuarioOrigem(e.getUsuarioEncaminhou());
//        controllerSPU.salvarouAtualizar(encaminhamentoSPU);
//    }
//    private Protocolo verificaCriaProtocoloSpu(SolicitacaoFinanceira solicitacaoFinanceira) throws PersistenceException, EJBException, Exception {
//        // tipo documento 'PF'(Processo financeiro) tem que estar cadastrado previamente
//        TipoDocumento tp = tdcontroller.buscaUnique("mnemonico", "PF");
//        Sistema sis = sistemaControler.buscarMnemonico("SAF");
//        // Orgao, numero da solicitação, tipo de documento (PF), ano do documento, Sistema em questão (SIAFI);
//        // Localizar um protocolo de uma solicitação financeira se o mesmo não existir então este deverá ser criado
//        Protocolo protocolo;
//        try {
//            protocolo = protocoloController.localizar(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria().getOrgao(), solicitacaoFinanceira.getId(), tp, solicitacaoFinanceira.getAnoCompetencia(), sis);
//        } catch (Exception e) {
//            protocolo = new Protocolo();
//            protocolo.setAno(solicitacaoFinanceira.getAnoCompetencia());
//            protocolo.setData(solicitacaoFinanceira.getDataSolicitacao());
//            protocolo.setDataCriacao(new Date());
//            protocolo.setDescricao(solicitacaoFinanceira.getHistorico());
//            protocolo.setHora(new Date());
//            protocolo.setLocal(solicitacaoFinanceira.getUsuario());
//            protocolo.setNumeroDocumento(solicitacaoFinanceira.getId());
//            protocolo.setOrgao(solicitacaoFinanceira.getCota().getUnidadeOrcamentaria().getOrgao());
//            protocolo.setSistema(sis);
//            protocolo.setSituacao(SituacaoProtocolo.Tramitado);
//            protocolo.setTipo(tp);
//            protocolo.setUsuario(solicitacaoFinanceira.getUsuario());
//            protocoloController.salvarouAtualizar(protocolo);
//        }
//        return protocolo;
//    }
    public List<Encaminhamento> listar(SolicitacaoFinanceira solicitacaoFinanceira) throws SQLException, PersistenceException, EJBException, Exception {
        return dao.listar(solicitacaoFinanceira);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Encaminhamento verificaSolicitacaoAberta(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return dao.verificaSolicitacaoAberta(solicitacaoFinanceira);
    }

//    //Renderizar o botão do fluxo da contabilidade
//    public boolean fluxoContabilidade(SolicitacaoFinanceira sf){
//        return (sf.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade));
//
//    }
//    public void encaminharFluxoContabilidade(SolicitacaoFinanceira sf){
//        if (fluxoContabilidade(sf)) {
//
//        }
//    }
    public List<AreaAdministrativa> selecionarAreaEncaminhamento(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        List<TipoAreaAdm> listadeTipos = new ArrayList<>();

        if (solicitacaoFinanceira.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade)) {
            /**
             * Encaminhamentos da Contabilidade
             *
             */
            if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceira.getCota().getDespesa().getFluxo().equals(TipoFluxo.Financeiro)) {
                //Retorna para a origem pois não foi empenhado
                listadeTipos.add(TipoAreaAdm.Financeiro);
                List<AreaAdministrativa> areaEspecifica = new ArrayList<>();
                areaEspecifica.add(solicitacaoFinanceira.getUsuario().getAreaAdministrativa());
                return areaEspecifica;

            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceira.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria)) {
                listadeTipos.add(TipoAreaAdm.Tesouraria);
                return administrativaDao.listarPorTipo(listadeTipos);

            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceira.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade)) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);
            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) && solicitacaoFinanceira.getCota().getDespesa().getFluxo().equals(TipoFluxo.Contabilidade_Tesouraria)) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);
            }
        } else if (solicitacaoFinanceira.getUsuario().getAreaAdministrativa().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro)) {
            if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);
            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Empenhado)) {
                listadeTipos.add(TipoAreaAdm.Controladoria);
                return administrativaDao.listarPorTipo(listadeTipos);
            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Financeiro) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Liquidado)) {
                listadeTipos.add(TipoAreaAdm.Tesouraria);
                return administrativaDao.listarPorTipo(listadeTipos);
            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.DocumentaçãoLiberada)) {
                listadeTipos.add(TipoAreaAdm.Financeiro);
                List<AreaAdministrativa> areaEspecifica = new ArrayList<>();
                areaEspecifica.add(solicitacaoFinanceira.getUsuario().getAreaAdministrativa());
                return areaEspecifica;
            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Empenhado)) {
                listadeTipos.add(TipoAreaAdm.Financeiro);
                List<AreaAdministrativa> areaEspecifica = new ArrayList<>();
                areaEspecifica.add(solicitacaoFinanceira.getUsuario().getAreaAdministrativa());
                return areaEspecifica;
            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Contabilidade) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Aprovado)) {
                listadeTipos.add(TipoAreaAdm.Financeiro);
                List<AreaAdministrativa> areaEspecifica = new ArrayList<>();
                areaEspecifica.add(solicitacaoFinanceira.getUsuario().getAreaAdministrativa());
                return areaEspecifica;

            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Pago)) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);

                /**
                 * Encaminhamentos da Controladoria
                 *
                 */
            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Controladoria) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Empenhado)) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);

            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Controladoria) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.DocumentaçãoLiberada)) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);

                /**
                 * Encaminhamentos da Tesouraria
                 *
                 */
            } else if ((solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) || solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.TesourariaSaude)) && solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Pago)) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);

            } else if (solicitacaoFinanceira.getLocal().getTipoAreaAdm().equals(TipoAreaAdm.Tesouraria) && (solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.Liquidado) || solicitacaoFinanceira.getSituacaoSolicitacao().equals(SituacaoSolicitacao.AutorizadoPagamento))) {
                listadeTipos.add(TipoAreaAdm.Contabilidade);
                return administrativaDao.listarPorTipo(listadeTipos);

            }
        } else {

        }
        return null;//;administrativaDao.listarPorTipo(listadeTipos);

    }

    public List<Encaminhamento> listaReceber(AreaAdministrativa areaAdministrativa, List<UnidadeOrcamentaria> unidadesOrcamentarias) throws Exception {
        //return dao.listarReceber(areaAdministrativa);
        return dao.listarReceber(areaAdministrativa, unidadesOrcamentarias);
    }

    public List<Encaminhamento> listaReceber(AreaAdministrativa areaAdministrativa, List<UnidadeOrcamentaria> unidadesOrcamentarias, UnidadeOrcamentaria orcamentaria, SituacaoSolicitacao situacao) throws Exception {
        //return dao.listarReceber(areaAdministrativa);
        if (orcamentaria == null) {
            return dao.listarReceber(areaAdministrativa, unidadesOrcamentarias, situacao);
        } else {
            return dao.listarReceber(areaAdministrativa, unidadesOrcamentarias, orcamentaria, situacao);

        }
    }

    public Encaminhamento buscaUltimoEncaminhamento(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return dao.buscarUltimoEncaminhamento(solicitacaoFinanceira);
    }

    public Encaminhamento buscarUltimoEncaminhamentoAbertoTesouraria(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return dao.buscarUltimoEncaminhamentoAbertoTesouraria(solicitacaoFinanceira);
    }

    public Encaminhamento buscaUltimoEncaminhamentoTesouraria(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return dao.buscarUltimoEncaminhamentoTesouraria(solicitacaoFinanceira);
    }

    public List<Encaminhamento> listarPorLocal(AreaAdministrativa areaAdministrativa, UnidadeOrcamentaria unidadeOrcamentaria, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        if (situacaoSolicitacao == null) {
            if (unidadeOrcamentaria == null) {
                return dao.listarPorLocal(areaAdministrativa);
            } else {
                return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentaria, areaAdministrativa);
            }
        } else {
            if (unidadeOrcamentaria == null) {
                return dao.listarPorLocal(areaAdministrativa, situacaoSolicitacao);
            } else {
                return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentaria, areaAdministrativa, situacaoSolicitacao);
            }
        }
    }

    public List<Encaminhamento> listarPorLocal(AreaAdministrativa areaAdministrativa, List<UnidadeOrcamentaria> unidadeOrcamentarias, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        if (situacaoSolicitacao == null) {
            return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentarias, areaAdministrativa);

        } else {
            return dao.listarPorUnidadeOrcamentaria(unidadeOrcamentarias, areaAdministrativa, situacaoSolicitacao);
        }
    }

    public List<Encaminhamento> buscarPorSolicitacao(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return dao.buscarPorSoolicitacao(solicitacaoFinanceira);
    }

    public Long contarNRecebidos(AreaAdministrativa area, List<UnidadeOrcamentaria> unidades) throws Exception {
        return dao.contarNRecebidos(area, unidades);
    }

    public Long contarEncaminhados(List<UnidadeOrcamentaria> unidades, UnidadeOrcamentaria unidade, AreaAdministrativa area, SituacaoSolicitacao situacao) throws Exception {
        return dao.contarEncaminhamentosNRecebidos(unidades, unidade, area, situacao);
    }

    public List<Encaminhamento> listaEncaminhamentosDataDestino(AreaAdministrativa localizacao, Date dataInicio, Date dataFinal, AreaAdministrativa destino, Credor credor) throws Exception {
        return dao.listaEncaminhamentosDataDestino(localizacao, dataInicio, dataFinal, destino, credor);
    }

    public List<Encaminhamento> listaEncaminhamentosNRecebidos(List<UnidadeOrcamentaria> unidades, UnidadeOrcamentaria unidade, AreaAdministrativa area, SituacaoSolicitacao situacao) throws Exception {
        return dao.listaEncaminhamentosNRecebidos(unidades, unidade, area, situacao);
    }

    public void confereCorrigeNumeroEmpenho(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        String empenho = gestorEmpenhoSolicitacaoDao.buscarEmpenho(solicitacaoFinanceira.getId());
        if (!empenho.equals(solicitacaoFinanceira.getEmpenho())) {
            MenssagemUtil.addMessageInfoLogger("Efetuando correção de número do empenho da solicitação.\r\nSolicitação:" + solicitacaoFinanceira.getId() + "\r\nNumero Empenho Antigo:" + solicitacaoFinanceira.getEmpenho() + "\r\nNovo Numero Empenho:" + empenho);
            solicitacaoFinanceira.setEmpenho(empenho);
            solicitacaoFinanceiraControler.atualiza(solicitacaoFinanceira);
            MenssagemUtil.addMessageInfoLogger("Numero empenho corrigido.");

        }
    }

}
