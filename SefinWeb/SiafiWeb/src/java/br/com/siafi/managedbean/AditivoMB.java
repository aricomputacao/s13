/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.siafi.managedbean;

import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import br.com.siafi.controller.AditivoController;
import br.com.siafi.modelo.Aditivo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Bean gerenciado do Projeto SiafiWeb criado em 02/07/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class AditivoMB extends BeanGenerico<Aditivo> implements Serializable {

    @EJB
    private AditivoController aditivoControler;
    private List<Aditivo> aditivos;
    private String contrato;

    public AditivoMB() {
        super(Aditivo.class);
        contrato = "";

    }

    public void listar() {
        try {
            aditivos = aditivoControler.aditivosContrato(contrato, getCampoOrdem());
            if (aditivos.isEmpty()) {
                MenssagemUtil.addMessageInfo("nenhum resultado encontrado");
            }
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("erro ao listar Aditivos", ex, this.getClass().getName());
        } finally {
            contrato = "";
        }
    }

    public List<Aditivo> getAditivos() {
        return aditivos;
    }

    public void setAditivos(List<Aditivo> aditivos) {
        this.aditivos = aditivos;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }
}
