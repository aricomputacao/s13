package br.com.guardiao.util.log;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author gilmario
 */
public class LoggerControlle implements Serializable {

    public void salvaLogFile(Logger log, Exception e) {
        try {
            FileHandler h = new FileHandler(ArquivoControle.PATH + "//" + log.getName() + " " + new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", new Locale("pt", "BR")).format(new Date()) + ".log");
            h.setFormatter(new SimpleFormatter());
            log.addHandler(h);
            log.log(Level.SEVERE, e.getMessage(), e);
            log.setUseParentHandlers(true);
        } catch (IOException ee) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ee);
        }
    }

    public void salvaLogFileInfo(Logger log, String mensagem) {
        try {
            FileHandler h = new FileHandler(ArquivoControle.PATH + "/" + "Informação" + new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", new Locale("pt", "BR")).format(new Date()) + ".log");
            h.setFormatter(new SimpleFormatter());
            log.addHandler(h);
            log.log(Level.INFO, mensagem);
            log.setUseParentHandlers(true);
        } catch (IOException ee) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ee);
        }
    }
}
