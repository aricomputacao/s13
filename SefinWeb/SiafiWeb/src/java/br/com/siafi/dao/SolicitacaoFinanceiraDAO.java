/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.Mes;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.siafi.modelo.CategoriaDespesa;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.CentroCustoDto;
import br.com.siafi.modelo.CentroCustoUnidadeOrcamentariaDto;
import br.com.siafi.modelo.Convenio;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Dotacao;
import br.com.guardiao.modelo.Exercicio;
import br.com.sefin.enumerated.StatusOrdemCompra;
import br.com.siafi.modelo.FonteRecurso;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import br.com.sefin.enumerated.Vinculo;
import br.com.sefin.modelo.dto.DTOInformacao;
import br.com.siafi.modelo.Aditivo;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.OrdemCompra;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto Siafi - Criado em 18/04/2013 - Dao do modelo solicitação
 * financeira
 *
 * @author Gilmário
 */
@Stateless
public class SolicitacaoFinanceiraDAO extends DAO<SolicitacaoFinanceira, String> implements Serializable {

    /**
     * Construtor padrão
     */
    public SolicitacaoFinanceiraDAO() {
        super(SolicitacaoFinanceira.class);
    }

    public BigDecimal saldoUtilizado(Cota cota, Mes mes, Integer ano) throws Exception {
        List<SituacaoSolicitacao> sit = new ArrayList<>();
        sit.add(SituacaoSolicitacao.Negado);
        sit.add(SituacaoSolicitacao.Cancelado);
        String Query = " select sum(s.valor) as total from SolicitacaoFinanceira s where s.cota=:c and s.mesCompetencia =:mes and anoCompetencia =:ano and s.situacaoSolicitacao not in(:situacao)";
        TypedQuery<BigDecimal> q = getEm().createQuery(Query, BigDecimal.class);
        q.setParameter("mes", mes);
        q.setParameter("situacao", sit);
        q.setParameter("ano", ano);
        q.setParameter("c", cota);
        return q.getSingleResult();
    }

    public String gerarCodigo() throws Exception {
        Calendar calendar = Calendar.getInstance();

        String dia = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String mes = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String ano = Integer.toString(calendar.get(Calendar.YEAR)).substring(2, 4);
        String cod;
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        if (mes.length() == 1) {
            mes = "0" + mes;
        }
        TypedQuery<Long> q = getEm().createQuery("select count(s.id)+1 as maximo from SolicitacaoFinanceira s where  (s.dataSolicitacao = CURRENT_DATE)", Long.class);
        Long x = q.getSingleResult();
        cod = x.toString();
        while (cod.length() < 4) {
            cod = "0" + cod;
        }
        return dia + mes + ano + cod;
    }

    public List<SolicitacaoFinanceira> listarSolicitacoesSituacao(String id, UnidadeOrcamentaria uo, SituacaoSolicitacao situacao) throws Exception {
        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.cota.unidadeOrcamentaria =:uo and s.situacaoSolicitacao =:situacao and s.id like :id order by s.dataSolicitacao", SolicitacaoFinanceira.class);
        q.setParameter("uo", uo);
        q.setParameter("situacao", situacao);
        q.setParameter("id", "%" + id + "%");
        return q.getResultList();

    }

    public List<SolicitacaoFinanceira> listarSolicitacoesSituacao(String id, List<UnidadeOrcamentaria> uos, SituacaoSolicitacao situacao) throws Exception {
        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where   s.situacaoSolicitacao =:situacao and s.cota.unidadeOrcamentaria IN (:uos) and s.id like :id order by s.dataSolicitacao", SolicitacaoFinanceira.class);
        q.setParameter("uos", uos);
        q.setParameter("situacao", situacao);
        q.setParameter("id", "%" + id + "%");
        return q.getResultList();

    }

    public List<SolicitacaoFinanceira> listarSolicitacoesCusteio(Exercicio e, CategoriaDespesa cd, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        List<SituacaoSolicitacao> situacaoCusteio = new ArrayList<>();
        situacaoCusteio.add(SituacaoSolicitacao.Pago);
        situacaoCusteio.add(SituacaoSolicitacao.Empenhado);
        situacaoCusteio.add(SituacaoSolicitacao.Liquidado);
        situacaoCusteio.add(SituacaoSolicitacao.Concluido);
        situacaoCusteio.add(SituacaoSolicitacao.AutorizadoPagamento);
        situacaoCusteio.add(SituacaoSolicitacao.DocumentaçãoLiberada);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.dotacao.naturezaDespesa.categoriaDespesa = :cat and s.exercicio = :exercicio and s.situacaoSolicitacao IN (:situacao) AND s.situacaoSolicitacao.cota.unidadeOrcamentaria IN (:unidades) order by s.cota.unidadeOrcamentaria, s.mesCompetencia,s.credor", SolicitacaoFinanceira.class);
        q.setParameter("situacao", situacaoCusteio);
        q.setParameter("exercicio", e);
        q.setParameter("unidades", unidadeOrcamentarias);
        q.setParameter("cat", cd);
        return q.getResultList();
    }

    //Consulta em cima do contrato
    public BigDecimal solicitacaoPorContrato(Integer contrato) throws Exception {
        TypedQuery<BigDecimal> q;

        q = getEm().createQuery("SELECT SUM(sol.valor) FROM SolicitacaoFinanceira sol WHERE sol.contrato.id  = :contrato AND (sol.situacaoSolicitacao <>  :situacaoCancelada AND sol.situacaoSolicitacao <>  :situacaoNegado)", BigDecimal.class);
        q.setParameter("contrato", contrato);
        q.setParameter("situacaoCancelada", SituacaoSolicitacao.Cancelado);
        q.setParameter("situacaoNegado", SituacaoSolicitacao.Negado);

        return q.getSingleResult();

    }

    //consulta em cima do contrato
    public BigDecimal solicitacaoPorAContratordemCompra(Integer contrato) throws Exception {
        TypedQuery<BigDecimal> q;

        q = getEm().createQuery("SELECT SUM(ord.valorTotal) FROM OrdemCompra ord WHERE ord.contrato.id = :contrato AND (ord.status <>  :situacaoCancelada)", BigDecimal.class);
        q.setParameter("contrato", contrato);

        q.setParameter("situacaoCancelada", StatusOrdemCompra.Cancelada);

        return q.getSingleResult();

    }

    //consulta em cima do aditivo
    public BigDecimal solicitacaoPorAditivoOrdemCompra(Aditivo a) throws Exception {
        TypedQuery<BigDecimal> q;

        q = getEm().createQuery("SELECT SUM(ord.valorTotal) FROM OrdemCompra ord WHERE ord.dataEmissao BETWEEN :dtIni AND :dtFim and ord.contrato.id = :contrato AND (ord.status <>  :situacaoCancelada)", BigDecimal.class);
        q.setParameter("contrato", a.getContrato().getId());
        q.setParameter("dtIni", a.getDataInicio());
        q.setParameter("dtFim", a.getDataFim());
        q.setParameter("situacaoCancelada", StatusOrdemCompra.Cancelada);

        return q.getSingleResult();

    }

    public BigDecimal solicitacaoPorAditivoOrdemCompra(Aditivo a, OrdemCompra o) throws Exception {
        TypedQuery<BigDecimal> q;
        q = getEm().createQuery("SELECT SUM(ord.valorTotal) FROM OrdemCompra ord WHERE ord.dataEmissao BETWEEN :dtIni AND :dtFim AND ord.id<>:id AND ord.contrato.id = :contrato AND (ord.status <>  :situacaoCancelada)", BigDecimal.class);
        q.setParameter("contrato", a.getContrato().getId());
        q.setParameter("dtIni", a.getDataInicio());
        q.setParameter("dtFim", a.getDataFim());
        q.setParameter("id", o.getId());
        q.setParameter("situacaoCancelada", StatusOrdemCompra.Cancelada);

        return q.getSingleResult();

    }

    //consulta em cima do aditivo
    public BigDecimal solicitacaoPorAditivo(Aditivo a) throws Exception {
        TypedQuery<BigDecimal> q;

        q = getEm().createQuery("SELECT SUM(sol.valor) FROM SolicitacaoFinanceira sol WHERE  sol.dataSolicitacao BETWEEN :dtIni AND :dtFim and sol.contrato.id = :contrato AND (sol.situacaoSolicitacao <>  :situacaoCancelada AND sol.situacaoSolicitacao <>  :situacaoNegado)", BigDecimal.class);
        q.setParameter("contrato", a.getContrato().getId());
        q.setParameter("dtIni", a.getDataInicio());
        q.setParameter("dtFim", a.getDataFim());
        q.setParameter("situacaoCancelada", SituacaoSolicitacao.Cancelado);
        q.setParameter("situacaoNegado", SituacaoSolicitacao.Negado);

        return q.getSingleResult();

    }

    /**
     * Lista solicitações financeiras filtrada por alguns paramentros e a
     * unidade orçamentaria é obrigatorio.
     *
     * @param dtIn
     * @param dtFi
     * @param numero
     * @param sits
     *
     * @param unidadeOrcamentaria
     * @param credor
     * @param empenho
     * @param ano
     * @param competencia
     * @return
     * @throws SQLException
     * @throws PersistenceException
     * @throws EJBException
     * @throws Exception
     */
    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentaria(Date dtIn, Date dtFi, String numero, List<SituacaoSolicitacao> sits, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor, String empenho, Integer ano, Mes competencia) throws Exception {
        String sql = "SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:unidade AND s.id like :numero ";
        if (dtIn != null && dtFi != null) {
            sql += " AND s.dataSolicitacao BETWEEN :dtIn AND :dtFi ";
        }
        if (!sits.isEmpty()) {
            for (int i = 0; i <= sits.size(); i++) {
                if (i == 0) {
                    sql += " and (s.situacaoSolicitacao = '" + (sits.get(i)) + "'";
                } else if ((i > 0) && ((i) < sits.size())) {
                    sql += " or s.situacaoSolicitacao = '" + (sits.get(i)) + "'";
                } else {
                    sql += ")";
                }

            }
        }
        if (credor != null) {
            sql += " AND s.credor = :credor ";
        }
        if (competencia != null && ano != null) {
            sql += " AND s.mesCompetencia = :mes AND s.anoCompetencia =:ano";
        }
        if (!"".equals(empenho) && empenho != null) {
            sql += " AND s.empenho like :empenho ";
        }
        Query q = getEm().createQuery(sql.concat(" order by s.cota.unidadeOrcamentaria"));
        q.setParameter("unidade", unidadeOrcamentaria);
        q.setParameter("numero", numero + "%");

        if (!"".equals(empenho) && empenho != null) {
            q.setParameter("empenho", empenho + "%");
        }
        if (dtIn != null && dtFi != null) {
            q.setParameter("dtIn", dtIn);
            q.setParameter("dtFi", dtFi);
        }
        if (credor != null) {
            q.setParameter("credor", credor);
        }
        if (competencia != null && ano != null) {
            q.setParameter("mes", competencia);
            q.setParameter("ano", ano);
        }

        return q.getResultList();
    }

    /**
     * Lista solicitações financeiras filtrada por alguns paramentros e a
     * unidade orçamentaria não é obrigatorio.
     *
     * @param dtIn
     * @param dtFi
     * @param numero
     * @param sits
     * @param credor
     * @param unidadeOrcamentarias
     * @param empenho
     * @param ano
     * @param competencia
     * @return
     * @throws SQLException
     * @throws PersistenceException
     * @throws EJBException
     * @throws Exception
     */
    public List<SolicitacaoFinanceira> listarFiltros(Date dtIn, Date dtFi, String numero, List<SituacaoSolicitacao> sits, Credor credor, List<UnidadeOrcamentaria> unidadeOrcamentarias, String empenho, Integer ano, Mes competencia) throws Exception {
        String sql = "SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:unidades) AND s.id like :numero ";
        if (dtIn != null && dtFi != null) {
            sql += " AND s.dataSolicitacao BETWEEN :dtIn AND :dtFi ";
        }
        if (!sits.isEmpty()) {
            for (int i = 0; i <= sits.size(); i++) {
                if (i == 0) {
                    sql += " and (s.situacaoSolicitacao = '" + (sits.get(i)) + "'";
                } else if ((i > 0) && ((i) < sits.size())) {
                    sql += " or s.situacaoSolicitacao = '" + (sits.get(i)) + "'";
                } else {
                    sql += ")";
                }

            }
        }
        if (credor != null) {
            sql += " AND s.credor = :credor ";
        }
        if (!"".equals(empenho) && empenho != null) {
            sql += " AND s.empenho like :empenho ";
        }
        if (competencia != null && ano != null) {
            sql += " AND s.mesCompetencia = :mes AND s.anoCompetencia =:ano";
        }
        Query q = getEm().createQuery(sql.concat(" order by s.cota.unidadeOrcamentaria"));
        q.setParameter("unidades", unidadeOrcamentarias);
        q.setParameter("numero", numero + "%");
        if (!"".equals(empenho) && empenho != null) {
            q.setParameter("empenho", empenho + "%");
        }

        if (dtIn != null && dtFi != null) {
            q.setParameter("dtIn", dtIn);
            q.setParameter("dtFi", dtFi);
        }
        if (credor != null) {
            q.setParameter("credor", credor);
        }
        if (competencia != null && ano != null) {
            q.setParameter("mes", competencia);
            q.setParameter("ano", ano);
        }
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorLocal(AreaAdministrativa areaAdministrativa) throws Exception {
        SituacaoSolicitacao[] s = new SituacaoSolicitacao[]{SituacaoSolicitacao.Cancelado, SituacaoSolicitacao.Pendente, SituacaoSolicitacao.Negado, SituacaoSolicitacao.Concluido};
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.local =:area AND s.situacaoSolicitacao NOT IN(:statusInvalidos) ORDER BY s.dataSolicitacao ").setParameter("area", areaAdministrativa).setParameter("statusInvalidos", Arrays.asList(s)).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorLocal(AreaAdministrativa areaAdministrativa, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.local =:area AND s.situacaoSolicitacao =:situacao ORDER BY s.dataSolicitacao ").setParameter("area", areaAdministrativa).setParameter("situacao", situacaoSolicitacao).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria, AreaAdministrativa areaAdministrativa) throws Exception {
        Query q;
        List<SolicitacaoFinanceira> solicitacoesEncaminhadas = this.solicitacoesEncaminhadas(null, areaAdministrativa, null, unidadeOrcamentaria);
        if (!solicitacoesEncaminhadas.isEmpty()) {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria = :uo AND s.situacaoSolicitacao NOT IN(:statusInvalidos) AND s.local =:area AND s NOT IN(:encaminhadas) ORDER BY s.dataSolicitacao");
            q.setParameter("encaminhadas", solicitacoesEncaminhadas);
        } else {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria = :uo AND s.situacaoSolicitacao NOT IN(:statusInvalidos) AND s.local =:area ORDER BY s.dataSolicitacao");
        }
        q.setParameter("uo", unidadeOrcamentaria);
        q.setParameter("area", areaAdministrativa);
        SituacaoSolicitacao[] s = new SituacaoSolicitacao[]{SituacaoSolicitacao.Cancelado, SituacaoSolicitacao.Pendente, SituacaoSolicitacao.Negado, SituacaoSolicitacao.Concluido};
        q.setParameter("statusInvalidos", Arrays.asList(s));
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> solicitacoesEncaminhadas(List<UnidadeOrcamentaria> unidadesOrcamentarias, AreaAdministrativa areaAdministrativa, SituacaoSolicitacao situacaoSolicitacao, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        String sql = "SELECT DISTINCT e.solicitacaoFinanceira FROM Encaminhamento e WHERE e.solicitacaoFinanceira.local =:area AND e.usuarioRecebeu IS NULL AND e.dataRecebimento IS NULL AND e.solicitacaoFinanceira.situacaoSolicitacao NOT IN(:statusInvalidos) ";
        if (situacaoSolicitacao != null) {
            sql += " AND e.solicitacaoFinanceira.situacaoSolicitacao =:situacao ";
        }
        if (unidadesOrcamentarias != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN (:uos) ";
        }
        if (unidadeOrcamentaria != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria =:uo ";
        }

        Query q = getEm().createQuery(sql);
        SituacaoSolicitacao[] s = new SituacaoSolicitacao[]{SituacaoSolicitacao.Cancelado, SituacaoSolicitacao.Pendente, SituacaoSolicitacao.Negado, SituacaoSolicitacao.Concluido};
        q.setParameter("statusInvalidos", Arrays.asList(s));
        q.setParameter("area", areaAdministrativa);
        if (situacaoSolicitacao != null) {
            q.setParameter("situacao", situacaoSolicitacao);
        }
        if (unidadeOrcamentaria != null) {
            q.setParameter("uo", unidadeOrcamentaria);
        }
        if (unidadesOrcamentarias != null) {
            q.setParameter("uos", unidadesOrcamentarias);
        }
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorUnidadeOrcamentaria(List<UnidadeOrcamentaria> unidadesOrcamentarias, AreaAdministrativa areaAdministrativa) throws Exception {
        Query q;
        List<SolicitacaoFinanceira> solicitacoesEncaminhadas = this.solicitacoesEncaminhadas(unidadesOrcamentarias, areaAdministrativa, null, null);
        if (!solicitacoesEncaminhadas.isEmpty()) {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uo) AND s.situacaoSolicitacao NOT IN(:statusInvalidos) AND s.local =:area AND s NOT IN(:encaminhadas) ORDER BY s.dataSolicitacao");
            q.setParameter("encaminhadas", solicitacoesEncaminhadas);
        } else {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uo) AND s.situacaoSolicitacao NOT IN(:statusInvalidos) AND s.local =:area ORDER BY s.dataSolicitacao");
        }
        q.setParameter("uo", unidadesOrcamentarias);
        q.setParameter("area", areaAdministrativa);
        SituacaoSolicitacao[] s = new SituacaoSolicitacao[]{SituacaoSolicitacao.Cancelado, SituacaoSolicitacao.Pendente, SituacaoSolicitacao.Negado, SituacaoSolicitacao.Concluido};
        q.setParameter("statusInvalidos", Arrays.asList(s));
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria, AreaAdministrativa areaAdministrativa, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        Query q;
        List<SolicitacaoFinanceira> solicitacoesEncaminhadas = this.solicitacoesEncaminhadas(null, areaAdministrativa, situacaoSolicitacao, unidadeOrcamentaria);
        if (!solicitacoesEncaminhadas.isEmpty()) {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria = :uo AND s.situacaoSolicitacao =:situacao AND s.local =:area AND s NOT IN(:encaminhadas) ORDER BY s.dataSolicitacao");
            q.setParameter("encaminhadas", solicitacoesEncaminhadas);
        } else {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria = :uo AND s.situacaoSolicitacao =:situacao AND s.local =:area ORDER BY s.dataSolicitacao");
        }
        q.setParameter("uo", unidadeOrcamentaria);
        q.setParameter("area", areaAdministrativa);
        q.setParameter("situacao", situacaoSolicitacao);
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorUnidadeOrcamentaria(List<UnidadeOrcamentaria> unidadesOrcamentarias, AreaAdministrativa areaAdministrativa, SituacaoSolicitacao situacaoSolicitacao) throws Exception {
        Query q;
        List<SolicitacaoFinanceira> solicitacoesEncaminhadas = this.solicitacoesEncaminhadas(unidadesOrcamentarias, areaAdministrativa, situacaoSolicitacao, null);
        if (!solicitacoesEncaminhadas.isEmpty()) {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uo) AND s.situacaoSolicitacao =:situacao AND s.local =:area AND s NOT IN(:encaminhadas) ORDER BY s.dataSolicitacao");
            q.setParameter("encaminhadas", solicitacoesEncaminhadas);
        } else {
            q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uo) AND s.situacaoSolicitacao =:situacao AND s.local =:area ORDER BY s.dataSolicitacao");
        }
        q.setParameter("uo", unidadesOrcamentarias);
        q.setParameter("area", areaAdministrativa);
        q.setParameter("situacao", situacaoSolicitacao);
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorUnidadeOrcamentaria(UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria = :uo AND s.situacaoSolicitacao NOT IN(:statusInvalidos) ORDER BY s.dataSolicitacao");
        q.setParameter("uo", unidadeOrcamentaria);
        SituacaoSolicitacao[] s = new SituacaoSolicitacao[]{SituacaoSolicitacao.Cancelado, SituacaoSolicitacao.Pendente, SituacaoSolicitacao.Negado, SituacaoSolicitacao.Concluido};
        q.setParameter("statusInvalidos", Arrays.asList(s));
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPendentesPagamento(List<UnidadeOrcamentaria> unidadesOrcamentarias, TipoAreaAdm area) {
        List<TipoAreaAdm> areas = new ArrayList<>();
        areas.add(TipoAreaAdm.Tesouraria);
        areas.add(TipoAreaAdm.TesourariaSaude);
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:unidades) AND s.situacaoSolicitacao =:situacao AND s.local.tipoAreaAdm IN (:areas) AND s.local.tipoAreaAdm =:area").setParameter("unidades", unidadesOrcamentarias).setParameter("situacao", SituacaoSolicitacao.Liquidado).setParameter("areas", areas).setParameter("area", area).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(FonteRecurso fonteRecurso, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso =:fonte AND s.cota.unidadeOrcamentaria IN (:unidades)").setParameter("fonte", fonteRecurso).setParameter("unidades", unidadeOrcamentarias).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso IS NOT NULL AND s.cota.unidadeOrcamentaria IN (:unidades)").setParameter("unidades", unidadeOrcamentarias).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(FonteRecurso fonteRecurso, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso =:fonte AND s.cota.unidadeOrcamentaria =:unidade").setParameter("fonte", fonteRecurso).setParameter("unidade", unidadeOrcamentaria).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso IS NOT NULL  AND s.cota.unidadeOrcamentaria =:unidade").setParameter("unidade", unidadeOrcamentaria).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(FonteRecurso fonteRecurso, Date dataInicio, Date dataFinal, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso =:fonte AND s.cota.unidadeOrcamentaria IN(:unidades) AND s.dataSolicitacao BETWEEN :dataIni AND :dataFim ").setParameter("dataIni", dataInicio).setParameter("dataFim", dataFinal).setParameter("fonte", fonteRecurso).setParameter("unidades", unidadeOrcamentarias).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(Date dataInicio, Date dataFinal, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso IS NOT NULL AND s.cota.unidadeOrcamentaria IN(:unidades) AND s.dataSolicitacao BETWEEN :dataIni AND :dataFim ").setParameter("dataIni", dataInicio).setParameter("dataFim", dataFinal).setParameter("unidades", unidadeOrcamentarias).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(FonteRecurso fonteRecurso, Date dataInicio, Date dataFinal, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso =:fonte AND s.cota.unidadeOrcamentaria =:unidade AND s.dataSolicitacao BETWEEN :dataIni AND :dataFim ").setParameter("dataIni", dataInicio).setParameter("dataFim", dataFinal).setParameter("fonte", fonteRecurso).setParameter("unidade", unidadeOrcamentaria).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorFonteRecurso(Date dataInicio, Date dataFinal, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.FonteRecurso IS NOT NULL AND s.cota.unidadeOrcamentaria =:unidade AND s.dataSolicitacao BETWEEN :dataIni AND :dataFim ").setParameter("dataIni", dataInicio).setParameter("dataFim", dataFinal).setParameter("unidade", unidadeOrcamentaria).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorCota(Mes mes, Cota cota) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.mesCompetencia = :mes AND s.cota = :cota ORDER BY s.cota.categoria,s.cota ").setParameter("mes", mes).setParameter("cota", cota).getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorCentroCusto(CentroCusto cc) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE  s.cota.despesa = :cc ORDER BY s.cota.categoria,s.cota ").setParameter("cc", cc).getResultList();
    }

    public List<SolicitacaoFinanceira> listarCompetencia(Mes mes, UnidadeOrcamentaria uo) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.mesCompetencia = :mes AND s.cota.unidadeOrcamentaria = :uo AND s.cota.categoria.nome != 'EXTRA' ORDER BY s.cota.categoria, s.cota.despesa.nome").setParameter("uo", uo).setParameter("mes", mes).getResultList();
    }

    public List<CentroCustoDto> listarCentroCusto(Date dtIni, Date dtFim) throws Exception {
        List<SituacaoSolicitacao> situacaos = new ArrayList<>();
        situacaos.add(SituacaoSolicitacao.Negado);
        situacaos.add(SituacaoSolicitacao.Cancelado);
        Query q;
        q = getEm().createQuery("SELECT NEW br.com.siafi.modelo.CentroCustoDto (s.cota.unidadeOrcamentaria,s.cota.despesa,SUM(s.valor)) FROM SolicitacaoFinanceira s "
                + "WHERE s.dataSolicitacao BETWEEN :dtIni AND :dtFim and s.situacaoSolicitacao NOT IN (:situacaos) group by s.cota.despesa,s.cota.unidadeOrcamentaria "
                + "order by s.cota.despesa,s.cota.unidadeOrcamentaria");
        q.setParameter("dtIni", dtIni);
        q.setParameter("dtFim", dtFim);
        q.setParameter("situacaos", situacaos);

        return q.getResultList();

    }

    public List<CentroCustoDto> listarCentroCustoDto(UnidadeOrcamentaria orcamentaria) throws Exception {
        List<SituacaoSolicitacao> situacaos = new ArrayList<>();
        situacaos.add(SituacaoSolicitacao.Negado);
        situacaos.add(SituacaoSolicitacao.Cancelado);
        Query q;
        q = getEm().createQuery("SELECT NEW br.com.siafi.modelo.CentroCustoDto (s.cota.unidadeOrcamentaria,s.cota.despesa,SUM(s.valor)) FROM SolicitacaoFinanceira s "
                + "WHERE s.cota.unidadeOrcamentaria = :orcamentaria and s.situacaoSolicitacao NOT IN (:situacaos) group by s.cota.despesa,s.cota.unidadeOrcamentaria  order by s.cota.unidadeOrcamentaria");
        q.setParameter("orcamentaria", orcamentaria);
        q.setParameter("situacaos", situacaos);
        return q.getResultList();

    }

    public List<CentroCustoUnidadeOrcamentariaDto> listarUnidadeOrcamentariaCentroCusto(CentroCusto centroCusto, List<UnidadeOrcamentaria> uos) throws Exception {

        Query q;
        q = getEm().createQuery("SELECT NEW br.com.siafi.modelo.CentroCustoUnidadeOrcamentariaDto (s.cota.unidadeOrcamentaria,SUM(s.valor)) FROM SolicitacaoFinanceira s WHERE s.cota.despesa = :centroCusto and s.cota.unidadeOrcamentaria IN (:uos) "
                + "GROUP BY s.cota.despesa, s.cota.unidadeOrcamentaria ORDER BY s.cota.despesa, s.cota.unidadeOrcamentaria ");
        q.setParameter("centroCusto", centroCusto);
        q.setParameter("uos", uos);

        return q.getResultList();

    }

    public List<SolicitacaoFinanceira> listaSolicitacaoData(Date dtIni, Date dtFim) throws Exception {
        List<SituacaoSolicitacao> situacaoSolicitacaos = new ArrayList<>();
        situacaoSolicitacaos.add(SituacaoSolicitacao.Cancelado);
        situacaoSolicitacaos.add(SituacaoSolicitacao.Negado);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.dataSolicitacao BETWEEN :dtIni and :dtFim AND s.situacaoSolicitacao NOT IN (:situacaoSolicitacaos) order by s.cota.unidadeOrcamentaria,s.mesCompetencia,s.credor", SolicitacaoFinanceira.class);
        q.setParameter("dtIni", dtIni);
        q.setParameter("dtFim", dtFim);
        q.setParameter("situacaoSolicitacaos", situacaoSolicitacaos);

        return q.getResultList();

    }
    //Ordenada por despesa

    public List<SolicitacaoFinanceira> listaSolicitacaoDataDespesa(Date dtIni, Date dtFim, List<UnidadeOrcamentaria> uos) throws Exception {
        List<SituacaoSolicitacao> situacaoSolicitacaos = new ArrayList<>();
        situacaoSolicitacaos.add(SituacaoSolicitacao.Cancelado);
        situacaoSolicitacaos.add(SituacaoSolicitacao.Negado);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.dataSolicitacao BETWEEN :dtIni and :dtFim AND s.situacaoSolicitacao NOT IN (:situacaoSolicitacaos) AND s.cota.unidadeOrcamentaria IN (:uos)  order by s.cota.despesa,s.cota.unidadeOrcamentaria", SolicitacaoFinanceira.class);
        q.setParameter("dtIni", dtIni);
        q.setParameter("dtFim", dtFim);
        q.setParameter("situacaoSolicitacaos", situacaoSolicitacaos);
        q.setParameter("uos", uos);

        return q.getResultList();

    }

    public List<SolicitacaoFinanceira> listaSolicitacaoDataDespesa(Date dtIni, Date dtFim, UnidadeOrcamentaria uo) throws Exception {
        List<SituacaoSolicitacao> situacaoSolicitacaos = new ArrayList<>();
        situacaoSolicitacaos.add(SituacaoSolicitacao.Cancelado);
        situacaoSolicitacaos.add(SituacaoSolicitacao.Negado);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.dataSolicitacao BETWEEN :dtIni and :dtFim AND s.situacaoSolicitacao NOT IN (:situacaoSolicitacaos) AND s.cota.unidadeOrcamentaria = :uo  order by s.cota.despesa,s.cota.unidadeOrcamentaria", SolicitacaoFinanceira.class);
        q.setParameter("dtIni", dtIni);
        q.setParameter("dtFim", dtFim);
        q.setParameter("situacaoSolicitacaos", situacaoSolicitacaos);
        q.setParameter("uo", uo);

        return q.getResultList();

    }

    public List<SolicitacaoFinanceira> listarSolicitacoesCusteio(Exercicio exercicio, UnidadeOrcamentaria unidadeOrcamentaria) throws Exception {
        List<SituacaoSolicitacao> situacaoCusteio = new ArrayList<>();
        situacaoCusteio.add(SituacaoSolicitacao.Pago);
        situacaoCusteio.add(SituacaoSolicitacao.Aprovado);
        situacaoCusteio.add(SituacaoSolicitacao.Empenhado);
        situacaoCusteio.add(SituacaoSolicitacao.Liquidado);
        situacaoCusteio.add(SituacaoSolicitacao.Concluido);
        situacaoCusteio.add(SituacaoSolicitacao.AutorizadoPagamento);
        situacaoCusteio.add(SituacaoSolicitacao.DocumentaçãoLiberada);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.exercicio = :exercicio and s.situacaoSolicitacao IN (:situacao) AND s.situacaoSolicitacao.cota.unidadeOrcamentaria =:unidade order by s.cota.unidadeOrcamentaria, s.mesCompetencia, s.cota.despesa ", SolicitacaoFinanceira.class);
        q.setParameter("situacao", situacaoCusteio);
        q.setParameter("exercicio", exercicio);
        q.setParameter("unidade", unidadeOrcamentaria);
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPorCentroCustoUnidadeOrcamentaria(CentroCusto centroCusto, UnidadeOrcamentaria orcamentaria) throws Exception {
        List<SituacaoSolicitacao> situacaoSolicitacaos = new ArrayList<>();
        situacaoSolicitacaos.add(SituacaoSolicitacao.Cancelado);
        situacaoSolicitacaos.add(SituacaoSolicitacao.Negado);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.cota.unidadeOrcamentaria = :orcamentaria and s.cota.despesa = :centroCusto AND s.situacaoSolicitacao NOT IN (:situacaoSolicitacaos) order by s.cota.despesa, s.cota.unidadeOrcamentaria", SolicitacaoFinanceira.class);
        q.setParameter("centroCusto", centroCusto);
        q.setParameter("orcamentaria", orcamentaria);
        q.setParameter("situacaoSolicitacaos", situacaoSolicitacaos);

        return q.getResultList();
    }

    //Ordenada por Unidade Orcamentaria
    public List<SolicitacaoFinanceira> listaSolicitacaoUnidadeOrcamentaria(Date dtIni, Date dtFim, List<UnidadeOrcamentaria> uos) throws Exception {
        List<SituacaoSolicitacao> situacaoSolicitacaos = new ArrayList<>();
        situacaoSolicitacaos.add(SituacaoSolicitacao.Cancelado);
        situacaoSolicitacaos.add(SituacaoSolicitacao.Negado);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.dataSolicitacao BETWEEN :dtIni and :dtFim AND s.situacaoSolicitacao NOT IN (:situacaoSolicitacaos) AND s.cota.unidadeOrcamentaria IN (:uos)  order by s.cota.unidadeOrcamentaria,s.cota.despesa", SolicitacaoFinanceira.class);
        q.setParameter("dtIni", dtIni);
        q.setParameter("dtFim", dtFim);
        q.setParameter("situacaoSolicitacaos", situacaoSolicitacaos);
        q.setParameter("uos", uos);

        return q.getResultList();

    }

    public List<SolicitacaoFinanceira> lista(Date dtIni, Date dtFim, List<UnidadeOrcamentaria> uos) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.dataSolicitacao BETWEEN :dtIni and :dtFim AND s.cota.unidadeOrcamentaria IN (:uos)", SolicitacaoFinanceira.class);
        q.setParameter("dtIni", dtIni);
        q.setParameter("dtFim", dtFim);
        q.setParameter("uos", uos);
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> lista(Date dtIni, Date dtFim, UnidadeOrcamentaria uo) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where s.dataSolicitacao BETWEEN :dtIni and :dtFim AND s.cota.unidadeOrcamentaria =:uo ", SolicitacaoFinanceira.class);
        q.setParameter("dtIni", dtIni);
        q.setParameter("dtFim", dtFim);
        q.setParameter("uo", uo);
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPendentesPagamento(List<UnidadeOrcamentaria> unidades) throws Exception {
        List<TipoAreaAdm> areas = new ArrayList<>();
        areas.add(TipoAreaAdm.Tesouraria);
        areas.add(TipoAreaAdm.TesourariaSaude);
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:unidades) AND s.situacaoSolicitacao =:situacao AND s.local.tipoAreaAdm IN (:areas)").setParameter("unidades", unidades).setParameter("situacao", SituacaoSolicitacao.Liquidado).setParameter("areas", areas).getResultList();
    }

    public BigDecimal somaLiquidacaoPagas(SolicitacaoFinanceira financeira) throws Exception {
        TypedQuery<BigDecimal> q;
        q = getEm().createQuery("SELECT SUM(e.valor) FROM EmpenhoDetalhe e where e.solicitacaoFinanceira = :fin", BigDecimal.class);
        q.setParameter("fin", financeira);
        return q.getSingleResult();

    }

    // Checar se ja tem solicitações com compra direta maior que o valor permitido, se for maior que zero é verdadeiro
    public boolean checarCompraDireta(Credor credor, UnidadeOrcamentaria uo, Exercicio exe) throws Exception {
        TypedQuery<BigDecimal> q;
        List<SituacaoSolicitacao> situacao = new ArrayList<>();

        situacao.add(SituacaoSolicitacao.Negado);
        situacao.add(SituacaoSolicitacao.Cancelado);
        BigDecimal bd = BigDecimal.valueOf(8000);
        q = getEm().createQuery("SELECT SUM(s.valor) FROM SolicitacaoFinanceira s where s.credor = :cre AND s.cota.unidadeOrcamentaria = :uo AND s.exercicio = :exe AND s.vinculo = :vin AND s.situacaoSolicitacao NOT IN (:sit)", BigDecimal.class);
        q.setParameter("cre", credor);
        q.setParameter("uo", uo);
        q.setParameter("exe", exe);
        q.setParameter("sit", situacao);
        q.setParameter("vin", Vinculo.Compra_Direta);
        try {
            int t = q.getSingleResult().compareTo(bd);
            return t >= 0;
        } catch (Exception e) {
            return false;
        }

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public BigDecimal saldoAnterior(SolicitacaoFinanceira solicitacaoFinanceira) throws Exception {
        return getEm().createQuery("SELECT s.valor FROM SolicitacaoFinanceira s WHERE s.id =:id AND s.cota =:cota ", BigDecimal.class).setParameter("cota", solicitacaoFinanceira.getCota()).setParameter("id", solicitacaoFinanceira.getId()).getSingleResult();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.convenio IS NOT NULL").setParameter("un", un).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria in(:uns) AND s.convenio IS NOT NULL").setParameter("uns", uns).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un, Convenio convenio) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("un", un).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns, Convenio convenio) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uns) AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("uns", uns).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un, Credor credor) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.credor =:credor AND s.convenio IS NOT NULL").setParameter("credor", credor).setParameter("un", un).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns, Credor credor) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uns) AND s.credor =:credor AND s.convenio IS NOT NULL").setParameter("credor", credor).setParameter("uns", uns).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un, Convenio convenio, Credor credor) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.credor =:credor AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("credor", credor).setParameter("un", un).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns, Convenio convenio, Credor credor) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uns) AND s.credor =:credor AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("credor", credor).setParameter("uns", uns).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.convenio IS NOT NULL").setParameter("un", un).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uns) AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.convenio IS NOT NULL").setParameter("uns", uns).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un, Convenio convenio, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("un", un).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns, Convenio convenio, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uns) AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("uns", uns).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un, Credor credor, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.credor =:credor AND s.convenio IS NOT NULL").setParameter("credor", credor).setParameter("un", un).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns, Credor credor, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uns) AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.credor =:credor AND s.convenio IS NOT NULL").setParameter("credor", credor).setParameter("uns", uns).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(UnidadeOrcamentaria un, Convenio convenio, Credor credor, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria =:un AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.credor =:credor AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("credor", credor).setParameter("un", un).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarConvenio(List<UnidadeOrcamentaria> uns, Convenio convenio, Credor credor, Date dataInicio, Date dataFinal) throws Exception {
        return getEm().createQuery(" SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN (:uns) AND s.dataSolicitacao BETWEEN :data1 AND :data2 AND s.credor =:credor AND s.convenio =:convenio").setParameter("convenio", convenio).setParameter("credor", credor).setParameter("uns", uns).setParameter("data1", dataInicio).setParameter("data2", dataFinal).getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentariaTesouraria(UnidadeOrcamentaria uo) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where (s.cota.unidadeOrcamentaria =:uo) AND (s.situacaoSolicitacao = :sitLiq OR s.situacaoSolicitacao = :sitPag OR "
                + "s.situacaoSolicitacao = :sitDoc)  AND (s.local.tipoAreaAdm = :loc)", SolicitacaoFinanceira.class);
        q.setParameter("sitLiq", SituacaoSolicitacao.Liquidado);
        q.setParameter("sitPag", SituacaoSolicitacao.Pago);
        q.setParameter("sitDoc", SituacaoSolicitacao.DocumentaçãoLiberada);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("uo", uo);
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentariaTesouraria(UnidadeOrcamentaria uo, Credor c) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where (s.cota.unidadeOrcamentaria =:uo) AND (s.situacaoSolicitacao = :sitLiq OR s.situacaoSolicitacao = :sitPag OR "
                + "s.situacaoSolicitacao = :sitDoc)  AND (s.local.tipoAreaAdm = :loc and s.credor = :c)", SolicitacaoFinanceira.class);
        q.setParameter("sitLiq", SituacaoSolicitacao.Liquidado);
        q.setParameter("sitPag", SituacaoSolicitacao.Pago);
        q.setParameter("sitDoc", SituacaoSolicitacao.DocumentaçãoLiberada);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("uo", uo);
        q.setParameter("c", c);

        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentariaTesouraria(List<UnidadeOrcamentaria> uos, UnidadeOrcamentaria uo, Credor c, SituacaoSolicitacao situacao, Date dataInicio, Date dataFinal) throws Exception {
        String sql = "SELECT s FROM SolicitacaoFinanceira s where s.local.tipoAreaAdm = :loc ";
        if (uos != null && !uos.isEmpty()) {
            sql += " AND s.cota.unidadeOrcamentaria IN(:uos) ";
        }
        if (uo != null) {
            sql += " AND s.cota.unidadeOrcamentaria =:uo ";
        }
        if (c != null) {
            sql += " AND s.credor = :credor ";
        }
        if (dataInicio != null && dataFinal != null) {
            sql += " AND s.dataSolicitacao BETWEEN :data1 AND :data2 ";
        }
        if (situacao != null) {
            sql += " AND s.situacaoSolicitacao = :situacao ";
        } else {
            sql += " AND s.situacaoSolicitacao IN(:sits) ";
        }

        Query q = getEm().createQuery(sql, SolicitacaoFinanceira.class);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        if (uos != null && !uos.isEmpty()) {
            q.setParameter("uos", uos);
        }
        if (uo != null) {
            q.setParameter("uo", uo);
        }
        if (c != null) {
            q.setParameter("credor", c);
        }
        if (dataInicio != null && dataFinal != null) {
            q.setParameter("data1", dataInicio);
            q.setParameter("data2", dataFinal);
        }
        if (situacao != null) {
            q.setParameter("situacao", situacao);
        } else {
            List<SituacaoSolicitacao> sits = new ArrayList<>();
            sits.add(SituacaoSolicitacao.Liquidado);
            sits.add(SituacaoSolicitacao.DocumentaçãoLiberada);
            sits.add(SituacaoSolicitacao.Pago);
            q.setParameter("sits", sits);
        }
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentariaTesourariaPagamentos(List<UnidadeOrcamentaria> uos, UnidadeOrcamentaria uo, Credor c, SituacaoSolicitacao situacao, Date dataInicio, Date dataFinal, Date dataPagoI, Date dataPagoF) throws Exception {
        String sql = "SELECT e.solicitacaoFinanceira FROM EmpenhoDetalhe e where 1=1 ";
        if (uos != null && !uos.isEmpty()) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria IN(:uos) ";
        }
        if (uo != null) {
            sql += " AND e.solicitacaoFinanceira.cota.unidadeOrcamentaria =:uo ";
        }
        if (c != null) {
            sql += " AND s.credor = :credor ";
        }
        if (dataInicio != null && dataFinal != null) {
            sql += " AND e.solicitacaoFinanceira.dataSolicitacao BETWEEN :data1 AND :data2 ";
        }
        if (dataPagoI != null && dataPagoF != null) {
            sql += " AND e.dataPagamento BETWEEN :datap1 AND :datap2 ";
        }
        if (situacao != null) {
            sql += " AND e.solicitacaoFinanceira.situacaoSolicitacao = :situacao ";
        } else {
            sql += " AND e.solicitacaoFinanceira.situacaoSolicitacao IN(:sits) ";
        }

        Query q = getEm().createQuery(sql, SolicitacaoFinanceira.class);
//        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        if (uos != null && !uos.isEmpty()) {
            q.setParameter("uos", uos);
        }
        if (uo != null) {
            q.setParameter("uo", uo);
        }
        if (c != null) {
            q.setParameter("credor", c);
        }
        if (dataInicio != null && dataFinal != null) {
            q.setParameter("data1", dataInicio);
            q.setParameter("data2", dataFinal);
        }
        if (dataPagoI != null && dataPagoF != null) {
            q.setParameter("datap1", dataPagoI);
            q.setParameter("datap2", dataPagoF);
        }
        if (situacao != null) {
            q.setParameter("situacao", situacao);
        } else {
            List<SituacaoSolicitacao> sits = new ArrayList<>();
            sits.add(SituacaoSolicitacao.Liquidado);
            sits.add(SituacaoSolicitacao.DocumentaçãoLiberada);
            sits.add(SituacaoSolicitacao.Pago);
            q.setParameter("sits", sits);
        }
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentariaTesouraria(List<UnidadeOrcamentaria> uos, Credor c) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where (s.cota.unidadeOrcamentaria IN (:uos)) AND (s.situacaoSolicitacao = :sitLiq OR s.situacaoSolicitacao = :sitPag OR "
                + "s.situacaoSolicitacao = :sitDoc)  AND (s.local.tipoAreaAdm = :loc and s.credor = :c)", SolicitacaoFinanceira.class);
        q.setParameter("sitLiq", SituacaoSolicitacao.Liquidado);
        q.setParameter("sitPag", SituacaoSolicitacao.Pago);
        q.setParameter("sitDoc", SituacaoSolicitacao.DocumentaçãoLiberada);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("uos", uos);
        q.setParameter("c", c);

        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentariaTesouraria(List<UnidadeOrcamentaria> uos) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where (s.cota.unidadeOrcamentaria IN (:uos)) AND (s.situacaoSolicitacao = :sitLiq OR s.situacaoSolicitacao = :sitPag OR "
                + "s.situacaoSolicitacao = :sitDoc)  AND (s.local.tipoAreaAdm = :loc) ", SolicitacaoFinanceira.class);
        q.setParameter("sitLiq", SituacaoSolicitacao.Liquidado);
        q.setParameter("sitPag", SituacaoSolicitacao.Pago);
        q.setParameter("sitDoc", SituacaoSolicitacao.DocumentaçãoLiberada);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("uos", uos);

        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosUnidadeOrcamentariaTesouraria(Credor c) throws Exception {
        Query q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s where (s.situacaoSolicitacao = :sitLiq OR s.situacaoSolicitacao = :sitPag OR "
                + "s.situacaoSolicitacao = :sitDoc)  AND (s.local.tipoAreaAdm = :loc and s.credor = :c)", SolicitacaoFinanceira.class);
        q.setParameter("sitLiq", SituacaoSolicitacao.Liquidado);
        q.setParameter("sitPag", SituacaoSolicitacao.Pago);
        q.setParameter("sitDoc", SituacaoSolicitacao.DocumentaçãoLiberada);
        q.setParameter("loc", TipoAreaAdm.Tesouraria);
        q.setParameter("c", c);

        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarFiltrosTesouraria(Date dataInicial, Date dataFinal, String numero, Credor credor, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<DTOInformacao> listarInformacao(AreaAdministrativa areaAdministrativa, List<UnidadeOrcamentaria> unidadeOrcamentarias) throws Exception {
        TypedQuery<DTOInformacao> q = getEm().createQuery("SELECT new br.com.sefin.modelo.dto.DTOInformacao(s.situacaoSolicitacao, COUNT(s)) FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria IN(:uo) AND s.situacaoSolicitacao IN(:situacoes) AND s.local =:area GROUP BY s.situacaoSolicitacao ORDER BY s.situacaoSolicitacao ", DTOInformacao.class);
        q.setParameter("uo", unidadeOrcamentarias);
        q.setParameter("area", areaAdministrativa);
        q.setParameter("situacoes", Arrays.asList(new SituacaoSolicitacao[]{SituacaoSolicitacao.Aprovado, SituacaoSolicitacao.Pendente, SituacaoSolicitacao.AutorizadoPagamento, SituacaoSolicitacao.DocumentaçãoLiberada, SituacaoSolicitacao.Empenhado, SituacaoSolicitacao.Liquidado, SituacaoSolicitacao.Pago}));
        return q.getResultList();
    }

    public List<SolicitacaoFinanceira> listarPendentesPagamento(UnidadeOrcamentaria unidade) {
        List<TipoAreaAdm> areas = new ArrayList<>();
        areas.add(TipoAreaAdm.Tesouraria);
        areas.add(TipoAreaAdm.TesourariaSaude);
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.cota.unidadeOrcamentaria = :unidade AND s.situacaoSolicitacao =:situacao AND s.local.tipoAreaAdm IN (:areas)")
                .setParameter("unidade", unidade).setParameter("situacao", SituacaoSolicitacao.Liquidado).setParameter("areas", areas).getResultList();
    }

    public List<SolicitacaoFinanceira> listar(Dotacao dotacao) throws Exception {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.dotacao =:d ORDER BY s.dataSolicitacao ").setParameter("d", dotacao).getResultList();
    }

    public List<SolicitacaoFinanceira> listarContratoOrdemCompra(Contrato c) {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.ordemCompra IS NOT NULL AND s.ordemCompra.contrato =:c ").setParameter("c", c).getResultList();
    }

    public List<SolicitacaoFinanceira> listarContratoSemOrdemCompra(Contrato c) {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.ordemCompra IS NULL AND s.contrato =:c ").setParameter("c", c).getResultList();
    }

    public List<SolicitacaoFinanceira> listar(Contrato c) {
        return getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.contrato =:c ").setParameter("c", c).getResultList();
    }

    public List<SolicitacaoFinanceira> listar(Credor credor, int ano, Mes competencia) {
        List<SituacaoSolicitacao> l = new ArrayList<>();
        l.add(SituacaoSolicitacao.Cancelado);
        l.add(SituacaoSolicitacao.Negado);

        TypedQuery<SolicitacaoFinanceira> q;
        q = getEm().createQuery("SELECT s FROM SolicitacaoFinanceira s WHERE s.credor =:cre AND s.anoCompetencia = :ex AND s.mesCompetencia = :com AND s.situacaoSolicitacao NOT IN (:l)  ", SolicitacaoFinanceira.class);
        q.setParameter("cre", credor);
        q.setParameter("ex", ano);
        q.setParameter("com", competencia);
        q.setParameter("l", l);
        if (q.getResultList().isEmpty()) {
            return new ArrayList<>();
        } else {
            return q.getResultList();
        }
    }
}
