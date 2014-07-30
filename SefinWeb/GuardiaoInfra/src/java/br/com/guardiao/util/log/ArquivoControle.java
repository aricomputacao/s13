/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilmario
 */
public class ArquivoControle implements Serializable {

    static {
        criaPath();
    }
    public static final String PATH = "LOG";

    private static void criaPath() {
        File f = new File(PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public List<File> listarAquivos() {
        return Arrays.asList(new File(PATH).listFiles());
    }

    public boolean excluirAqruivo(File f) {
        return f.delete();
    }

    public String arquivoEmObjeto(File f) {
        String menssagem = "";
        try {
            FileReader reader = new FileReader(f);
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                menssagem += br.readLine() + "\r\n";
            }
        } catch (IOException ex) {
            Logger.getLogger(ArquivoControle.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return menssagem;
    }

}
