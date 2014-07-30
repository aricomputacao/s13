/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.controller;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.controler.ExercicioController;
import br.com.sefin.enumerated.Mes;
import br.com.siafi.dao.CotaControleDAO;
import br.com.siafi.modelo.Cota;
import br.com.siafi.modelo.CotaControle;
import br.com.guardiao.modelo.Exercicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * @Sistema SiafiWeb
 * @Data 05/08/2013
 * @author gilmario
 */
@Stateless
public class CotaControleController extends Controller<CotaControle, Long> implements Serializable {

    @EJB
    private CotaControleDAO cotaControleDao;
    @EJB
    private ExercicioController exercicioControler;
    @EJB
    private SolicitacaoFinanceiraController solicitacaoFinanceiraControler;

    @Override
    public void salvarouAtualizar(CotaControle t) throws Exception {
        if (t.getId() == null) {
            cotaControleDao.salvar(t);
        } else {
            cotaControleDao.atualizar(t);
        }

    }

    public void novoValorCota(Cota cota,Mes competencia) throws Exception {
        Exercicio exercicio = exercicioControler.exercicioVigente();
        CotaControle cotaControle;
        try {
            cotaControle = cotaControleDao.localizaCotaControleUnique(competencia, exercicio, cota);
        } catch (Exception e) {
            cotaControle = new CotaControle();
        }
        // As competencias sempre devem valer para o mes anterior
        if (!competencia.equals(Mes.Janeiro)) {
            cotaControle.setCompetencia(Mes.values()[competencia.ordinal() - 1]);
        } else {
            cotaControle.setCompetencia(competencia);
        }
        cotaControle.setCota(cota);
        cotaControle.setDataCadastrada(new Date());
        cotaControle.setExercicio(exercicio);
        cotaControle.setValor(cota.getValor());
        salvarouAtualizar(cotaControle);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean podeAlterarCota(Cota cota) throws Exception {

        // Encotrar o mes vigente para a competencia
        Mes competencia = Mes.values()[Calendar.getInstance().get(Calendar.MONTH)];
        // Encontrar o exiecicio vigente
        Exercicio exercicio = exercicioControler.exercicioVigente();
        // verificar se existe alguma cotaControle nessas condições : cota , exercicio, competencia
        try {
            CotaControle cotaControle = cotaControleDao.localizaCotaControleUnique(competencia, exercicioControler.exercicioVigente(), cota);
        } catch (Exception e) {
            //cotaControle = null;
            return true;
        }

        // se o valor gasto for maior que o novo valor da cota não deve ser possivel aterar o valor da cota
        BigDecimal valorGasto = solicitacaoFinanceiraControler.saldoUtilizado(cota, competencia, exercicio.getAno());
        return valorGasto.compareTo(cota.getValor()) <= 0;
    }

    public CotaControle valorAtualCota(Cota cota, Mes competencia, Exercicio exercicio) throws Exception {
        return cotaControleDao.valorAtualCota(cota, competencia, exercicio);
    }

    @Override
    @PostConstruct
    protected void inicializaDAO() {
        setDAO(cotaControleDao);
    }
}
