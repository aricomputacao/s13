/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.mail;

import br.com.guardiao.controler.SistemaConfiguracaoController;
import br.com.guardiao.modelo.Sistema;
import br.com.guardiao.util.MenssagemUtil;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.mail.MessagingException;

/**
 *
 * @author gilmario
 */
public abstract class ServidorAvisos implements Serializable {

    private String servidor;
    private String porta;
    private String senha;
    private String emailAviso;
    private Sistema sistema;
    private final String mnemonicoSis;

    @EJB
    private SistemaConfiguracaoController controler;

    public ServidorAvisos(String mnemonico) {
        this.mnemonicoSis = mnemonico;
    }

    @PostConstruct
    private void iniciar() {
        try {
            servidor = (String) controler.pegarValorConfiguracaoDef("", "SERVER_MAIL_HOST", mnemonicoSis);
            porta = (String) controler.pegarValorConfiguracaoDef("", "SERVER_MAIL_PORTA", mnemonicoSis);
            senha = (String) controler.pegarValorConfiguracaoDef("", "SERVER_MAIL_SENHA", mnemonicoSis);
            emailAviso = (String) controler.pegarValorConfiguracaoDef("", "SERVER_MAIL_EMAIL", mnemonicoSis);
        } catch (Exception ex) {
            MenssagemUtil.addMessageErroLogger(ex, this.getClass().getName());
        }

    }

    public void emitirAviso(String emailDest, String nomeDest, String assunto, String menssagem) throws Exception {
        iniciar();
        if ("".equals(servidor) || "".equals(porta) || "".equals(senha) || "".equals(emailAviso)) {
            throw new Exception("As configurações estão incorretas, Informe ao administrador");
        }
        try {
            EmailUtil.enviarEmail(servidor, porta, emailAviso, sistema.getNome(), senha, emailDest, nomeDest, assunto, menssagem);
        } catch (UnsupportedEncodingException | MessagingException e) {
            MenssagemUtil.addMessageErro(e, this.getClass().getName());
        }
    }
}
