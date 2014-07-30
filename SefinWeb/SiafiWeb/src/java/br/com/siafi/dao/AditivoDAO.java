/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.sefin.enumerated.SituacaoSolicitacao;
import br.com.sefin.enumerated.StatusOrdemCompra;
import br.com.siafi.modelo.Aditivo;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Credor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

/**
 * Classe do Projeto SiafiWeb criada em 26/06/2013
 *
 * @author: ari
 */
@Stateless
public class AditivoDAO extends DAO<Aditivo, Long> implements Serializable {

    public AditivoDAO() {
        super(Aditivo.class);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Aditivo buscaUniqueAditivo(Integer contratoCod, String aditivoCod) throws Exception {
        try {
            TypedQuery<Aditivo> q;
            q = getEm().createQuery("SELECT a FROM Aditivo a WHERE a.contrato.id = :contratoCod AND a.codigo = :aditivoCod", Aditivo.class);
            q.setParameter("aditivoCod", aditivoCod);
            q.setParameter("contratoCod", contratoCod);
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public List<Aditivo> aditivosContrato(String contrato, String campoOrdem) throws Exception {
        TypedQuery<Aditivo> q;
        if ("".equals(contrato)) {
            q = getEm().createQuery("SELECT a FROM Aditivo a ORDER BY :campoOrdem", Aditivo.class);
        } else {
            q = getEm().createQuery("SELECT a FROM Aditivo a WHERE a.contrato.numero = :contrato ORDER BY :campoOrdem", Aditivo.class);
            q.setParameter("contrato", contrato);
        }
        q.setParameter("campoOrdem", campoOrdem);
        return q.getResultList();
    }

    public Aditivo ultimoAditivo(Contrato contrato) throws Exception {
        return (Aditivo) getEm().createQuery("SELECT a FROM Aditivo a WHERE a.contrato =:contrato ORDER BY a.dataFim ASC").setParameter("contrato", contrato).getResultList().get(0);
    }

    public BigDecimal valorContrato(Integer contrato) throws Exception {
        TypedQuery<BigDecimal> q;
        q = getEm().createQuery("SELECT SUM(ad.valor) FROM Aditivo ad WHERE AD.contrato.id = :contrato", BigDecimal.class);
        q.setParameter("contrato", contrato);
        return q.getSingleResult();

    }

    public List<Aditivo> contratoNumeroUnidadeOrcamentariaCredor(String numero, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor) throws Exception {
        TypedQuery<Aditivo> q;

        if (("".equals(numero) || numero == null) && credor.getId() == null) {
            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.dataFim >= :dataAtual and c.contrato.unidadeOrcamentaria.orgao  = :orgao and c.contrato NOT IN (SELECT ord.contrato FROM OrdemCompra ord WHERE ord.status <> :canc)", Aditivo.class);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", StatusOrdemCompra.Cancelada);

        } else if ((!"".equals(numero) && numero != null) && credor.getId() == null) {
            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.contrato.numero :numero and c.dataFim >= :dataAtual and ac.contrato.unidadeOrcamentaria.orgao  = :orgao and c.contrato NOT IN (SELECT ord.contrato FROM OrdemCompra ord WHERE ord.status <> :canc)", Aditivo.class);
            q.setParameter("numero", numero);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", StatusOrdemCompra.Cancelada);

        } else if (("".equals(numero) || numero == null) && credor.getId() != null) {
            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.dataFim >= :dataAtual and c.contrato.unidadeOrcamentaria.orgao  = :orgao AND c.contrato.credor = :credor and c.contrato NOT IN (SELECT ord.contrato FROM OrdemCompra ord WHERE ord.status <> :canc) ", Aditivo.class);
            q.setParameter("credor", credor);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", StatusOrdemCompra.Cancelada);

        } else {

            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.dataFim >= :dataAtual and c.contrato.unidadeOrcamentaria.orgao  = :orgao and c.contrato NOT IN (SELECT ord.contrato FROM OrdemCompra ord WHERE ord.status <> :canc)", Aditivo.class);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", StatusOrdemCompra.Cancelada);

        }

        return q.getResultList();
    }

    //Metodo para listar o contrato na ordem de comprea
    public List<Aditivo> contratoNumeroUnidadeOrcamentariaCredorOrdemCompra(String numero, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor) throws Exception {
        TypedQuery<Aditivo> q;

        if (("".equals(numero) || numero == null) && credor.getId() == null) {
            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.dataFim >= :dataAtual and c.contrato.unidadeOrcamentaria.orgao  = :orgao and c.contrato.licitacao NOT IN (SELECT so.licitacao FROM SolicitacaoFinanceira so WHERE so.contrato = c.contrato AND so.situacaoSolicitacao  <> :canc  )", Aditivo.class);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", SituacaoSolicitacao.Cancelado);

        } else if ((!"".equals(numero) && numero != null) && credor.getId() == null) {
            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.contrato.numero :numero and c.dataFim >= :dataAtual and c.contrato.unidadeOrcamentaria.orgao  = :orgao and c.contrato.licitacao NOT IN (SELECT so.licitacao FROM SolicitacaoFinanceira so WHERE so.contrato = c.contrato AND so.situacaoSolicitacao  <> :canc) ", Aditivo.class);
            q.setParameter("numero", numero);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", SituacaoSolicitacao.Cancelado);

        } else if (("".equals(numero) || numero == null) && credor.getId() != null) {
            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.dataFim >= :dataAtual and c.contrato.unidadeOrcamentaria.orgao  = :orgao AND c.contrato.credor = :credor and c.contrato.licitacao NOT IN (SELECT so.licitacao FROM SolicitacaoFinanceira so WHERE so.contrato = c.contrato AND so.situacaoSolicitacao  <> :canc) ", Aditivo.class);
            q.setParameter("credor", credor);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", SituacaoSolicitacao.Cancelado);

        } else {

            q = getEm().createQuery("SELECT c FROM Aditivo c  WHERE c.dataFim >= :dataAtual and c.contrato.unidadeOrcamentaria.orgao  = :orgao and c.contrato.licitacao NOT IN (SELECT so.licitacao FROM SolicitacaoFinanceira so WHERE so.contrato = c.contrato AND so.situacaoSolicitacao  <> :canc) ", Aditivo.class);
            q.setParameter("orgao", unidadeOrcamentaria.getOrgao());
            q.setParameter("dataAtual", new Date());
            q.setParameter("canc", SituacaoSolicitacao.Cancelado);

        }

        return q.getResultList();
    }
}
