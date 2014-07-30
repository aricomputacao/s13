/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.controler.SistemaController;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.util.relatorio.AssistentedeRelatorio;
import br.com.guardiao.util.relatorio.RelatorioSession;
import br.com.guardiao.managedbean.BeanGenerico;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JRException;

/**
 * Bean gerenciado do Projeto guardião criado em 20/03/2013
 *
 * @author ari
 */
@ManagedBean
@ViewScoped
public class SistemaMB extends BeanGenerico<Sistema> implements Serializable {

    @EJB
    private SistemaController controler;
    @Inject
    private NavegacaoGuardiaoMB navegacaoGuardiao;
    private Sistema sistemaSelecionado;
    private List<Sistema> sistemas;

    /**
     * Construtor Padrão
     */
    public SistemaMB() {
        super(Sistema.class);
    }

    @PostConstruct
    private void init() {
        sistemaSelecionado = (Sistema) navegacaoGuardiao.getRegistroMapa("sistema", new Sistema());
        sistemas = new ArrayList<>();
    }

    public void listar() {
        sistemas = listar(controler);
    }

    public Sistema getSistemaSelecionado() {
        return sistemaSelecionado;
    }

    public void setSistemaSelecionado(Sistema sistemaSelecionado) {
        this.sistemaSelecionado = sistemaSelecionado;
    }

    public List<Sistema> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<Sistema> sistemas) {
        this.sistemas = sistemas;
    }

    public void impressao() {
        if (!sistemas.isEmpty()) {
            try {
                Map<String, Object> m = new HashMap<>();
                byte[] rel = new AssistentedeRelatorio().relatorioemByte(sistemas, m, "WEB-INF/relatorios/listagemSistemas.jasper", "");
                RelatorioSession.setBytesRelatorioInSession(rel);
            } catch (JRException ex) {
                MenssagemUtil.addMessageErro("Erro ao gerar impressão", ex, this.getClass().getName());
            }
        }
    }

    public void salvar() {
        try {
            controler.salvarouAtualizar(this.sistemaSelecionado);
            sistemaSelecionado = new Sistema();
            MenssagemUtil.addMessageInfo("Sistema cadastrado com sucesso.");
        } catch (Exception ex) {
            MenssagemUtil.addMessageErro("Erro ao salvar Sistema", ex, this.getClass().getName());

        }
    }

}
