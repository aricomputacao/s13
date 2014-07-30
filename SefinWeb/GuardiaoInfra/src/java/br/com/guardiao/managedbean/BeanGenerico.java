/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedbean;

import br.com.guardiao.controler.Controller;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 * Classe do Projeto Siafi - Criado em 23/04/2013 - Bean generico com funções
 * especializadas para os outros beans.
 *
 * @param <T>
 * @author Gilmário
 */
public abstract class BeanGenerico<T> implements Serializable {

    /**
     * Nome do campo que está sendo utilizado para fazer a busca
     */
    private String campoBusca;
    /**
     * Nome do campo que está sendo utilizado para ordenar a busca
     */
    private String campoOrdem;
    /**
     * Valor utilizado na busca por string
     */
    private String valorBusca;
    /**
     * lista de campos da classe central do bean
     */
    private List<String> campos;
    /**
     * lista de campos do tipo String da classe do determinado bean
     */
    private List<String> camposString;

    public BeanGenerico(Class classe) {
        pegarListadeCampos(classe);
        campoBusca = "";
        campoOrdem = "";
        valorBusca = "";
    }

    /**
     *
     * @param key
     * @return
     */
    protected T getValueSession(String key) {
        return (T) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    /**
     *
     * @param key
     * @return
     */
    protected Object removeValueSession(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }

    /**
     *
     * @param key
     * @param value
     */
    protected void setValueSession(String key, T value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    protected List<T> listar(Controller controller) {
        try {
            List<T> lista;
            if (getCampoBusca() != null) {
                if (!getCampoBusca().equals("")) {
                    lista = controller.listarTodos(getCampoOrdem(), getCampoBusca(), getValorBusca());
                    if (lista.isEmpty()) {
                        MenssagemUtil.addMessageInfo("Nenhum registro encontrado.");
                    }
                } else {
                    MenssagemUtil.addMessageInfo("não ha campo para pesquisar");
                    lista = new ArrayList<>();
                }
            } else {
                MenssagemUtil.addMessageInfo("não ha campo para pesquisar");
                lista = new ArrayList<>();
            }
            return lista;
        } catch (Exception e) {
            MenssagemUtil.addMessageErro("Erro ao fazer a consulta", e, this.getClass().getName());
            return new ArrayList<>();
        }
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public String getCampoOrdem() {
        return campoOrdem;
    }

    public void setCampoOrdem(String campoOrdem) {
        this.campoOrdem = campoOrdem;
    }

    public String getValorBusca() {
        return valorBusca;
    }

    public void setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;

    }

    public String getMsg(String messageId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String msg = "";
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("mensagens", locale);
        try {
            msg = bundle.getString(messageId);
        } catch (Exception e) {
        }
        return msg;
    }

    public String getMsgLabel(String messageId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String msg = "";
        Locale locale = facesContext.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("label", locale);
        try {
            msg = bundle.getString(messageId);
        } catch (Exception e) {
        }
        return msg;
    }

    private void pegarListadeCampos(Class entidade) {
        campos = new ArrayList<>();
        camposString = new ArrayList<>();
        for (Field f : entidade.getDeclaredFields()) {
            if (f.getType().equals(Long.class) || f.getType().equals(Date.class) || f.getType().equals(Integer.class) || f.getType().equals(String.class)) {
                campos.add(f.getName());
            }

            if (f.getType().equals(String.class) && !"senha".equals(f.getName())) {
                camposString.add(f.getName());
            }
        }
    }

    public List<String> getCampos() {
        return campos;
    }

    public void setCampos(List<String> campos) {
        this.campos = campos;
    }

    public List<String> getCamposString() {
        return camposString;
    }

    public void setCamposString(List<String> camposString) {
        this.camposString = camposString;
    }

}
