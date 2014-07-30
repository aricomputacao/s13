/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.controler.ExercicioController;
import br.com.guardiao.modelo.Exercicio;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.inject.Inject;

/**
 * Classe do Projeto Siafi - Criado em 23/04/2013 - Bean gerenciado do modelo
 * Exercicio.
 *
 * @author Gilmário
 */
@ManagedBean
@ViewScoped
public class ExercicioMB extends BeanGenerico<Exercicio> implements Serializable, Validator {

    @EJB
    private ExercicioController controller;
    @Inject
    private BeanNavegacaoMB beanNavegacao;
    private Exercicio exercicio;
    private List<Exercicio> listaExercicio;

    public void novo() {
        exercicio = new Exercicio();
    }

    /**
     *
     */
    public ExercicioMB() {
        super(Exercicio.class);
        this.listaExercicio = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        this.exercicio = (Exercicio) beanNavegacao.getRegistroMapa("exercicio", new Exercicio());
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(exercicio);
            exercicio = new Exercicio();
            MenssagemUtil.addMessageInfo("Exercicio cadastrado com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar registro", ex, this.getClass().getName());
        }
    }

    public void selecionar() {
        try {
            exercicio = controller.carregar(exercicio.getAno());
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar registro", ex, this.getClass().getName());
        }
    }

    public void listar() {
        listaExercicio = listar(controller);
    }

    public void excluir() {
        try {
            controller.excluir(exercicio);
            exercicio = new Exercicio();
            MenssagemUtil.addMessageInfo("Registro excluido");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao excluir registro", ex, this.getClass().getName());
        }
    }

    public Exercicio exercicioPadrao() {
        try {
            return controller.exercicioVigente();
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao selecionar exercicio vigente", ex, this.getClass().getName());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public List<Exercicio> getListaExercicio() {
        return listaExercicio;
    }

    /**
     *
     * @param listExercicio
     */
    public void setListaExercicio(List<Exercicio> listExercicio) {
        this.listaExercicio = listExercicio;
    }

    /**
     *
     * @return
     */
    public Exercicio getExercicio() {
        return exercicio;
    }

    /**
     *
     * @param exercicio
     */
    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    /**
     *
     * @param context
     * @param component
     * @param value
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (!controller.validaAno((Integer) value)) {
            ((UIInput) component).setValid(false);
            MenssagemUtil.addMessageErro("Já existe um exercicio cadastrado com esse ano.");
        }
    }

}
