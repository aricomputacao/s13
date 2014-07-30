/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gestor.dao;

import br.com.gestor.modelo.Aditivo;
import br.com.gestor.modelo.MovimentacaoBanco;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ari
 */
@Stateless(name = "MovimentacaoBancoGestorDAO")
public class MovimentacaoBancoDAO extends GestorDAO<MovimentacaoBanco> implements Serializable {

    public MovimentacaoBancoDAO() {
        super(MovimentacaoBanco.class);
    }
    
    public List<MovimentacaoBanco> listaImportar(){
        TypedQuery<MovimentacaoBanco> q;
        q = getEm().createQuery("SELECT m from MovimentacaoBanco m WHERE m.mbatransferencia = :p", MovimentacaoBanco.class);
        q.setParameter("p", "1");
        return q.getResultList();
    }
}
