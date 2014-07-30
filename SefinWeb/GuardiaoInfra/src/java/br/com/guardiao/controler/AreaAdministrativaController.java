/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.controler;

import br.com.guardiao.dao.AreaAdministrativaDAO;
import br.com.guardiao.enumerated.TipoAreaAdm;
import br.com.guardiao.modelo.AreaAdministrativa;
import br.com.guardiao.modelo.Orgao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author gilmario
 */
@Stateless
public class AreaAdministrativaController extends Controller<AreaAdministrativa, Long> implements Serializable {

    @EJB
    private AreaAdministrativaDAO dao;

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(dao);
    }

    @Override
    public void salvarouAtualizar(AreaAdministrativa t) throws Exception {
        if (t.getId() == null) {
            dao.salvar(t);
        } else {
            dao.atualizar(t);
        }
    }

    public List<AreaAdministrativa> listarPorAreaAdm(Orgao orgao) throws Exception {
        return dao.listarPorAreaAdm(orgao);
    }

    public AreaAdministrativa administrativaTipo(TipoAreaAdm tipoAreaAdm) throws Exception {
        return dao.areaAdministrativaTipo(tipoAreaAdm);
    }
    
    public AreaAdministrativa pegarAreaFinanceiraOrgao(Orgao o,TipoAreaAdm adm){
        return dao.pegarAreaFinanceiraOrgao(o,adm);
    }

    public List<AreaAdministrativa> listarTodosFinanceiro(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    public List<AreaAdministrativa> listarAreasFinanceiro() {
        return dao.listarFinanceiro(); 
    }

}
