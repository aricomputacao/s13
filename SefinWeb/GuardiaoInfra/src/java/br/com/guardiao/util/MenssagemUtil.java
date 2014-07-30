/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util;

import br.com.guardiao.util.log.LoggerControlle;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Classe do Projeto ******* - Criado em 17/05/2013 -
 *
 * @author Gilmário
 */
public class MenssagemUtil {

    public static void addMessageInfo(String menssagem) {
        addMenssagem(null, FacesMessage.SEVERITY_INFO, "Informação", menssagem);
    }

    public static void addMessageInfoLogger(String menssagem) {
        new LoggerControlle().salvaLogFileInfo(Logger.getLogger(MenssagemUtil.class.getName()), menssagem);
    }

    public static void addMessageWarn(String menssagem) {
        addMenssagem(null, FacesMessage.SEVERITY_WARN, "Atenção", menssagem);
    }

    public static void addMessageFatal(String menssagem) {
        addMenssagem(null, FacesMessage.SEVERITY_FATAL, "Fatal", menssagem);
    }

    public static void addMessageErro(Exception menssagem, String nomeClasse) {
        addMenssagem(null, FacesMessage.SEVERITY_ERROR, "Erro", menssagem.toString());
        new LoggerControlle().salvaLogFile(Logger.getLogger(nomeClasse), menssagem);
    }

    public static void addMessageErro(String menssagem, Exception erro, String nomeClasse) {
        addMenssagem(null, FacesMessage.SEVERITY_ERROR, "Erro", menssagem);
        new LoggerControlle().salvaLogFile(Logger.getLogger(nomeClasse), erro);
    }

    public static void addMessageErroLogger(Exception erro, String nomeClasse) {
        new LoggerControlle().salvaLogFile(Logger.getLogger(nomeClasse), erro);
    }

    public static void addMessageErro(Exception erro) {
        addMenssagem(null, FacesMessage.SEVERITY_ERROR, "Erro", erro.getMessage());
    }

    public static void addMessageErro(String menssagem) {
        addMenssagem(null, FacesMessage.SEVERITY_ERROR, "Erro", menssagem);
    }

    public static void addMenssagem(String clientId, FacesMessage.Severity severity, String sumario, String detalhe) {
        FacesContext c = FacesContext.getCurrentInstance();
        FacesMessage m = new FacesMessage(severity, sumario, detalhe);
        c.addMessage(clientId, m);
    }
}
