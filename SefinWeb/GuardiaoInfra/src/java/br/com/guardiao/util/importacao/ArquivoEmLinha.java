/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.util.importacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Classe do Projeto Guardiao - Criado em 07/06/2013 -
 *
 * @author Gilmário
 */
public class ArquivoEmLinha implements Serializable {

    public static List<String> lerArquivo(File arquivo) throws FileNotFoundException, IOException {
        if (arquivo.exists()) {
            List<String> linhas = new ArrayList<>();
            InputStreamReader isr;
            BufferedReader br;
            try (FileInputStream reader = new FileInputStream(arquivo)) {
                isr = new InputStreamReader(reader, "ISO-8859-1");
                br = new BufferedReader(isr);
                while (br.ready()) {
                    String l = br.readLine();
                    if (!"".equals(l)) {

                        linhas.add(l);
                    }
                }
            }
            isr.close();
            br.close();
            return linhas;
        } else {
            return new ArrayList<>();
        }
    }

    public static void copiarErroArquivo(File arquivo) {
        if (arquivo.exists()) {
            File pastaErro = new File(arquivo.getAbsolutePath().replace(arquivo.getName(), "") + "erro");
            if (!pastaErro.exists()) {
                pastaErro.mkdirs();
            }
            FileOutputStream fisDestino;
            try {
                try (FileInputStream fisOrigem = new FileInputStream(arquivo)) {
                    fisDestino = new FileOutputStream(new File(arquivo.getPath().replace(arquivo.getName(), "") + "erro\\" + arquivo.getName()));
                    FileChannel fcOrigem = fisOrigem.getChannel();
                    FileChannel fcDestino = fisDestino.getChannel();
                    fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
                }
                fisDestino.close();
                arquivo.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void copiarArquivoProcessado(File arquivo) throws FileNotFoundException, IOException {
        if (arquivo.exists()) {
            Date data = new Date();
            File pastaProcessados = new File(arquivo.getAbsolutePath().replace(arquivo.getName(), "") + "processados");
            if (!pastaProcessados.exists()) {
                pastaProcessados.mkdirs();
            }
            FileOutputStream fisDestino;
            try (FileInputStream fisOrigem = new FileInputStream(arquivo)) {
                fisDestino = new FileOutputStream(new File(arquivo.getPath().replace(arquivo.getName(), "") + "processados\\" + arquivo.getName() + String.format("%td-%tm-%tY_%tH_%tM_%tS ", data, data, data, data, data, data)));
                FileChannel fcOrigem = fisOrigem.getChannel();
                FileChannel fcDestino = fisDestino.getChannel();
                fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);
            }
            fisDestino.close();
            arquivo.delete();
        }
    }

    public static List<String> linhaEmArray(String linha, String separador, int tamanho) {
        String[] linhaPartes = linha.split(separador, tamanho);

        return Arrays.asList(linhaPartes);
    }

    public static List<String> listarArquivosContidos(File path) {
        List<String> nomesarquivos = new ArrayList<>();
        List<File> arquivos = Arrays.asList(path.listFiles());
        for (File f : arquivos) {
            if (!f.isDirectory()) {
                nomesarquivos.add(f.getName());
            }
        }
        return nomesarquivos;
    }
}
