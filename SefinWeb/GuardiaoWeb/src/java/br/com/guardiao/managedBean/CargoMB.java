package br.com.guardiao.managedBean;

import br.com.guardiao.controler.CargoController;
import br.com.guardiao.modelo.Cargo;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Bean gerenciado do Projeto guardiao criado em 02/04/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class CargoMB extends BeanGenerico<Cargo> implements Serializable {

    @EJB
    private CargoController controller;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private Cargo cargoSelecionado;
    private List<Cargo> cargos;

    /**
     *
     */
    public CargoMB() {
        super(Cargo.class);
        cargos = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        cargoSelecionado = (Cargo) navegacaoGuardiao.getRegistroMapa("cargo", new Cargo());
    }

    public void listar() {
        cargos = listar(controller);
    }

    /**
     *
     * @return
     */
    public Cargo getCargoSelecionado() {
        return cargoSelecionado;
    }

    /**
     *
     * @param cargoSelecionado
     */
    public void setCargoSelecionado(Cargo cargoSelecionado) {
        this.cargoSelecionado = cargoSelecionado;
    }

    /**
     *
     * @return
     */
    public List<Cargo> getCargos() {
        return cargos;
    }

    /**
     *
     * @param cargos
     */
    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public void salvar() {
        try {
            controller.salvarouAtualizar(cargoSelecionado);
            cargoSelecionado = new Cargo();
            MenssagemUtil.addMessageInfo("Cargo cadastrado com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar", ex, this.getClass().getName());
        }
    }

}
