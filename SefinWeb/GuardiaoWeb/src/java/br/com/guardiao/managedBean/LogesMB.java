/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.managedBean;

import br.com.guardiao.util.log.ArquivoControle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author gilmario
 */
@ManagedBean
@ViewScoped
public class LogesMB implements Serializable {

    private final ArquivoControle controle;
    private String menssagemLog;
    private List<File> logs;

    public LogesMB() {
        controle = new ArquivoControle();
    }

    public void excluirExcecao(File e) {
        controle.excluirAqruivo(e);
        listar();
    }

    public List<File> getLogs() {
        return logs;
    }

    public void setLogs(List<File> logs) {
        this.logs = logs;
    }

    public void mostraEcecao(File e) {
        menssagemLog = controle.arquivoEmObjeto(e);
    }

    public void listar() {
        logs = controle.listarAquivos();
    }

    public String getMenssagemLog() {
        return menssagemLog;
    }

    public void setMenssagemLog(String menssagemLog) {
        this.menssagemLog = menssagemLog;
    }

    public StreamedContent baixar(File f) {
        try {
            return new DefaultStreamedContent(new FileInputStream(f), "plain/text", f.getName());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LogesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void limpar() {
        menssagemLog = "";
    }

}
