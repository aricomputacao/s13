/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.dao;

import br.com.guardiao.dao.DAO;
import br.com.guardiao.modelo.UnidadeOrcamentaria;
import br.com.siafi.modelo.Contrato;
import br.com.siafi.modelo.Credor;
import br.com.siafi.modelo.Licitacao;
import br.com.siafi.modelo.Orcamento;
import java.io.Serializable;
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
public class ContratoDAO extends DAO<Contrato, Integer> implements Serializable {

    public ContratoDAO() {
        super(Contrato.class);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Contrato existeContrato(Integer id) {
        try {
            return carregar(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Contrato> contratoCredor(Credor credor) throws Exception {
        TypedQuery<Contrato> q;

        q = getEm().createQuery("SELECT c FROM Contrato c WHERE c.credor = :credor ORDER BY c.id", Contrato.class);
        q.setParameter("credor", credor);

        return q.getResultList();

    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Contrato contratoUnidadeOrcamentariaCredorNumero(UnidadeOrcamentaria unidadeOrcamentaria, Orcamento orcamento, String numero) {
        try {
            TypedQuery<Contrato> q = getEm().createQuery("SELECT c FROM Contrato c WHERE c.unidadeOrcamentaria = :unidadeOrcamentaria AND c.numero = :numero AND c.orcamento = :orcamento ", Contrato.class);
            q.setParameter("unidadeOrcamentaria", unidadeOrcamentaria);
            q.setParameter("numero", numero);
            q.setParameter("orcamento", orcamento);
            return q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public List<Contrato> contratoNumeroUnidadeOrcamentariaCredor(String numero, UnidadeOrcamentaria unidadeOrcamentaria, Credor credor) throws Exception {
        TypedQuery<Contrato> q;
        if (("".equals(numero) || numero == null) && credor.getId() == null) {
            q = getEm().createQuery("SELECT c FROM Contrato c  WHERE c.unidadeOrcamentaria = :unidadeOrcamentaria AND  c.licitacao NOT IN (SELECT ord.licitacao FROM OrdemCompra ord) AND c.numero in (SELECT ad.contrato.numero FROM Aditivo ad WHERE ad.dataFim >= :dataAtual)  ", Contrato.class);
            q.setParameter("unidadeOrcamentaria", unidadeOrcamentaria);
            q.setParameter("dataAtual", new Date());

        } else if ((!"".equals(numero) && numero != null) && credor.getId() == null) {
            q = getEm().createQuery("SELECT c FROM Contrato c WHERE c.numero = :numero AND c.unidadeOrcamentaria = :unidadeOrcamentaria AND c.licitacao NOT IN (SELECT ord.licitacao FROM OrdemCompra ord) AND c.numero in (SELECT ad.contrato.numero FROM Aditivo ad WHERE ad.dataFim >= :dataAtual)", Contrato.class);
            q.setParameter("numero", numero);
            q.setParameter("unidadeOrcamentaria", unidadeOrcamentaria);
            q.setParameter("dataAtual", new Date());

        } else if (("".equals(numero) || numero == null) && credor.getId() != null) {
            q = getEm().createQuery("SELECT c FROM Contrato c WHERE c.credor = :credor AND c.unidadeOrcamentaria = :unidadeOrcamentaria AND c.licitacao NOT IN (SELECT ord.licitacao FROM OrdemCompra ord) AND c.numero in (SELECT ad.contrato.numero FROM Aditivo ad WHERE ad.dataFim >= :dataAtual)", Contrato.class);
            q.setParameter("credor", credor);
            q.setParameter("unidadeOrcamentaria", unidadeOrcamentaria);
            q.setParameter("dataAtual", new Date());

        } else {
            q = getEm().createQuery("SELECT c FROM Contrato c WHERE  c.unidadeOrcamentaria = :unidadeOrcamentaria AND c.licitacao NOT IN (SELECT ord.licitacao FROM OrdemCompra ord) AND c.numero in (SELECT ad.contrato.numero FROM Aditivo ad WHERE ad.dataFim >= :dataAtual)", Contrato.class);

            q.setParameter("unidadeOrcamentaria", unidadeOrcamentaria);
            q.setParameter("dataAtual", new Date());

        }

        return q.getResultList();
    }

    public List<Contrato> contratoLicitacao(Licitacao licitacao) throws Exception {
        TypedQuery<Contrato> q;

        q = getEm().createQuery("SELECT c FROM Contrato c WHERE c.licitacao = :licitacao ", Contrato.class);
        q.setParameter("licitacao", licitacao);

        return q.getResultList();

    }

    public List<Contrato> listarPorUnidadeOrcamentariaCredor(UnidadeOrcamentaria un, Credor credor, String numero) throws Exception {
        TypedQuery<Contrato> q;
        if (numero != null && credor.getId() != null) {
            q = getEm().createQuery("SELECT c FROM Contrato c  WHERE c.unidadeOrcamentaria = :unidadeOrcamentaria AND c.numero like :numero AND c.credor = :credor", Contrato.class);
            q.setParameter("unidadeOrcamentaria", un);
            q.setParameter("numero", numero + "%");
            q.setParameter("credor", credor);
        } else if ((!"".equals(numero) && numero != null) && credor.getId() == null) {
            q = getEm().createQuery("SELECT c FROM Contrato c WHERE c.numero like :numero AND c.unidadeOrcamentaria = :unidadeOrcamentaria", Contrato.class);
            q.setParameter("numero", numero + "%");
            q.setParameter("unidadeOrcamentaria", un);
        } else if (("".equals(numero) || numero == null) && credor.getId() != null) {
            q = getEm().createQuery("SELECT c FROM Contrato c WHERE c.credor = :credor AND c.unidadeOrcamentaria = :unidadeOrcamentaria", Contrato.class);
            q.setParameter("credor", credor);
            q.setParameter("unidadeOrcamentaria", un);
        } else {
            q = getEm().createQuery("SELECT c FROM Contrato c WHERE  c.unidadeOrcamentaria = :unidadeOrcamentaria", Contrato.class);
            q.setParameter("unidadeOrcamentaria", un);
        }

        return q.getResultList();
    }
}
