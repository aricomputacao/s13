/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util.relatorio;

import java.io.Serializable;
import javax.el.ELResolver;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Classe do Projeto Guardião - Criado em 28/05/2013 -
 *
 * @author Gilmário
 */
@ManagedBean
@SessionScoped
public class RelatorioSession implements Serializable {

    public static final String CHAVE_RELATORIO = "byte_relatorio";
    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public void clear() {
        bytes = new byte[0];
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public static void setBytesRelatorio(byte[] bytes) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ELResolver resolver = context.getApplication().getELResolver();
            RelatorioSession rel = (RelatorioSession) resolver.getValue(context.getELContext(), null, "relatorioSession");
            if (rel != null) {
                rel.setBytes(bytes);
            } else {
                rel = new RelatorioSession();
                rel.setBytes(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setBytesRelatorioInSession(byte[] bytes) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(CHAVE_RELATORIO, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
