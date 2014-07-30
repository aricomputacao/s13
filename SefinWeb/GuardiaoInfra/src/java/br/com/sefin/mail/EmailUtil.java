/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sefin.mail;

import br.com.guardiao.util.MailJava;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Classe do Projeto ******* - Criado em 11/06/2013 -
 *
 * @author Gilmário
 */
public class EmailUtil {

    public static void enviarEmail(String smtpHost, String porta, String emailAnvio, String nome, String senha, String destinatario, String nomeDest, String assunto, String mensagem) throws UnsupportedEncodingException, MessagingException {
        MailJava mj = new MailJava();
        mj.setSmtpHostMail(smtpHost);
        mj.setSmtpPortMail(porta);
        mj.setSmtpAuth("true");
        mj.setSmtpStarttls("true");
        mj.setUserMail(emailAnvio);
        mj.setFromNameMail(nome);
        mj.setPassMail(senha);
        mj.setCharsetMail("ISO-8859-1");
        mj.setSubjectMail(assunto);
        mj.setTypeTextMail(MailJava.TYPE_TEXT_PLAIN);
        Map<String, String> map = new HashMap<>();
        mj.setBodyMail(mensagem);
        map.put(destinatario, nomeDest);
        mj.setToMailsUsers(map);
        EmailUtil.enviar(mj);
    }

    public static void enviar(final MailJava mail) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    enviarEmail(mail);
                } catch (UnsupportedEncodingException | MessagingException ex) {
                    Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    private static void enviarEmail(final MailJava mail) throws UnsupportedEncodingException, MessagingException {
        Properties props = new Properties();

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mail.getSmtpHostMail());
        props.setProperty("mail.smtp.auth", mail.getSmtpAuth());
        props.setProperty("mail.smtp.ssl.enable", "false");
        props.setProperty("mail.smtp.ssl.trust", mail.getSmtpHostMail());
        props.setProperty("mail.smtp.starttls.enable", mail.getSmtpStarttls());
        props.setProperty("mail.smtp.port", mail.getSmtpPortMail());
        props.setProperty("mail.mime.charset", mail.getCharsetMail());
        //classe anonima que realiza a autenticação
        //do usuario no servidor de email.
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        mail.getUserMail(), mail.getPassMail());
            }
        };
        // Cria a sessao passando as propriedades e a autenticação
//        Session session = Session.getDefaultInstance(props, auth);
        Session session = Session.getInstance(props, auth);
        // Gera um log no console referente ao processo de envio
        session.setDebug(true);

        //cria a mensagem setando o remetente e seus destinatários
        Message msg = new MimeMessage(session);
        //aqui seta o remetente
        msg.setFrom(new InternetAddress(
                mail.getUserMail(), mail.getFromNameMail()));
        msg.setHeader("Content-Type", "text/plain; charset=ISO-8859-1");
        boolean first = true;
        for (Map.Entry<String, String> map : mail.getToMailsUsers().entrySet()) {
            if (first) {
                //setamos o 1° destinatario
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(map.getKey(), map.getValue()));
                first = false;
            } else {
                //setamos os demais destinatarios
                msg.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(map.getKey(), map.getValue()));
            }
        }

        // Adiciona um Assunto a Mensagem
        msg.setSubject(mail.getSubjectMail());

        // Cria o objeto que recebe o texto do corpo do email
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(mail.getBodyMail(), mail.getTypeTextMail() + "; charset=ISO-8859-1");

        // Monta a mensagem SMTP  inserindo o conteudo, texto e anexos
        Multipart mps = new MimeMultipart();
        mps.addBodyPart(textPart);

        //adiciona a mensagem o conteúdo texto e anexo
        msg.setContent(mps);

        // Envia a Mensagem
        Transport.send(msg);
    }

    public static void emailDeErro(String mens) throws UnsupportedEncodingException, MessagingException {
        MailJava mj = new MailJava();
        //configuracoes de envio
        mj.setSmtpHostMail("smtp.gmail.com");
        mj.setSmtpPortMail("587");
        mj.setSmtpAuth("true");
        mj.setSmtpStarttls("true");
        mj.setUserMail("suporte@sefin.caucaia.ce.gov.br");
        mj.setFromNameMail("Secretaria de Finanças Orçamento e Planejamento");
        mj.setPassMail("mmc10bpm*");
        mj.setCharsetMail("ISO-8859-1");
        mj.setSubjectMail("Comunicado do SIAFI");

        mj.setTypeTextMail(MailJava.TYPE_TEXT_PLAIN);

        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<>();
        mj.setBodyMail(mens);
        map.put("gilmariosoftware@gmail.com", "Gilmario");
        map.put("aricomputacao@gmail.com", "Ari");

        mj.setToMailsUsers(map);
        EmailUtil.enviarEmail(mj);

    }
//    public static void main(String[] args) {
//        try {
//            EmailUtil.emailDeErro("Erro ");
//        } catch (UnsupportedEncodingException | MessagingException ex) {
//            Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
