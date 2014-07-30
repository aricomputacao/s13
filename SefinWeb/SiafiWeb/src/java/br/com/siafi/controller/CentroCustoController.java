/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.guardiao.controler.Controller;
import br.com.sefin.enumerated.Mes;
import br.com.siafi.dao.CentroCustoDAO;
import br.com.siafi.dao.ElementoDespesaDAO;
import br.com.siafi.modelo.Categoria;
import br.com.siafi.modelo.CentroCusto;
import br.com.siafi.modelo.CentroCustoDto;
import br.com.siafi.modelo.ElementoDespesa;
import br.com.guardiao.modelo.Exercicio;
import br.com.sefin.enumerated.TipoFluxo;
import br.com.siafi.modelo.SolicitacaoFinanceira;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Classe do Projeto Siafi - Criado em 16/04/2013 - controller do modelo
 * CentroCusto
 *
 * @author Gilmário
 */
@Stateless
public class CentroCustoController extends Controller<CentroCusto, Integer> implements Serializable {

    @EJB
    private CentroCustoDAO dao;
    @EJB
    private br.com.gestor.dao.CentroCustoDAO gestorDao;
    @EJB
    private ElementoDespesaDAO elementoDespesaDao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    public void importar() throws Exception {
        List<br.com.gestor.modelo.CentroCusto> gestorCustos = gestorDao.listarImportacao();
        for (br.com.gestor.modelo.CentroCusto gestCentroCusto : gestorCustos) {
            CentroCusto siafiCusto = new CentroCusto();
            ElementoDespesa elemento = elementoDespesaDao.carregar(gestCentroCusto.getElemento());
            siafiCusto.setElementoDespesa(elemento);
            siafiCusto.setId(gestCentroCusto.getId());
            siafiCusto.setNome(gestCentroCusto.getNome());
            siafiCusto.setFluxo(TipoFluxo.Financeiro);
            dao.atualizar(siafiCusto);
        }
        gestorDao.marcarImportados("CEN");
    }

    @Override
    public void salvarouAtualizar(CentroCusto t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<CentroCusto> listarOrdenado() throws Exception {
        return dao.listarCentroCustoOrdenado();
    }

    public List<CentroCusto> listarDespesaPorUnidadeOrcCategoria(UnidadeOrcamentaria uo, Categoria c) throws Exception {
        return dao.listarDespesaPorUnidadeOrcCategoria(uo, c);
    }

    public List<CentroCustoDto> listarUnidadeOrcamentariaMesDespesa(Exercicio exercicio, UnidadeOrcamentaria unidadeOrcamentaria, Mes mes) throws Exception {
        return dao.listarUnidadeOrcamentariaMesDespesa(exercicio, unidadeOrcamentaria, mes);
    }

    public List<CentroCustoDto> listarCusteioDtoPorUnidadeOrc(List<SolicitacaoFinanceira> solicitacoes) throws Exception {
        List<CentroCustoDto> centroCustoDtos = new ArrayList<>();
        CentroCustoDto centroCustoDto = new CentroCustoDto();

        for (SolicitacaoFinanceira s : solicitacoes) {
            // verifica se a unidade orçamentaria mudou
            if (s.getCota().getDespesa().equals(centroCustoDto.getCentroCusto())) {
                centroCustoDto.setCentroCusto(s.getCota().getDespesa());
            } else {
                //caso tenha mudado adicona o dto nalista cria uma novo
                centroCustoDto = new CentroCustoDto();
                centroCustoDto.setOrcamentaria(s.getCota().getUnidadeOrcamentaria());
                centroCustoDto.setTotal(s.getValor());
                centroCustoDto.setCentroCusto(s.getCota().getDespesa());

                centroCustoDtos.add(centroCustoDto);
            }
        }

        return centroCustoDtos;
    }

    public List<CentroCustoDto> listarUnidadeOrcPorCusteioDto(List<SolicitacaoFinanceira> solicitacoes) throws Exception {
        List<CentroCustoDto> centroCustoDtos = new ArrayList<>();
        CentroCustoDto centroCustoDto = new CentroCustoDto();

        for (SolicitacaoFinanceira s : solicitacoes) {
            // verifica se a unidade orçamentaria mudou
            if (s.getCota().getUnidadeOrcamentaria().equals(centroCustoDto.getOrcamentaria())) {
                centroCustoDto.setCentroCusto(s.getCota().getDespesa());
            } else {
                //caso tenha mudado adicona o dto nalista cria uma novo
                centroCustoDto = new CentroCustoDto();
                centroCustoDto.setOrcamentaria(s.getCota().getUnidadeOrcamentaria());
                centroCustoDto.setTotal(s.getValor());
                centroCustoDto.setCentroCusto(s.getCota().getDespesa());

                centroCustoDtos.add(centroCustoDto);
            }
        }

        return centroCustoDtos;
    }

    public List<CentroCustoDto> listarCusteioDtoTabela(List<SolicitacaoFinanceira> solicitacoes) throws Exception {
        List<CentroCustoDto> centroCustoDtos = new ArrayList<>();
        CentroCustoDto centroCustoDto = new CentroCustoDto();

        for (SolicitacaoFinanceira s : solicitacoes) {
            // verifica se a unidade orçamentaria mudou
            if (s.getCota().getDespesa().equals(centroCustoDto.getCentroCusto())) {
                centroCustoDto.setCentroCusto(s.getCota().getDespesa());
                centroCustoDto.setTotal(centroCustoDto.getTotal().add(s.getValor()));
            } else {
                //caso tenha mudado adicona o dto nalista cria uma novo
                centroCustoDto = new CentroCustoDto();
                centroCustoDto.setOrcamentaria(s.getCota().getUnidadeOrcamentaria());
                centroCustoDto.setTotal(s.getValor());
                centroCustoDto.setCentroCusto(s.getCota().getDespesa());

                centroCustoDtos.add(centroCustoDto);
            }
        }

        return centroCustoDtos;
    }

    public List<CentroCustoDto> processarCentroCusto(List<CentroCustoDto> centroCustoDtosUnidadeOrcamentaria) throws Exception {
        CentroCustoDto cc = new CentroCustoDto();
        List<CentroCustoDto> custoDtos = new ArrayList<>();
        for (CentroCustoDto c : centroCustoDtosUnidadeOrcamentaria) {
            if (c.getOrcamentaria().equals(cc.getOrcamentaria())) {
                cc.setTotal(cc.getTotal().add(c.getTotal()));
            } else {
                cc = new CentroCustoDto();
                cc.setCentroCusto(c.getCentroCusto());
                cc.setOrcamentaria(c.getOrcamentaria());
                cc.setTotal(c.getTotal());
                custoDtos.add(cc);
            }
        }
        return custoDtos;
    }

}
