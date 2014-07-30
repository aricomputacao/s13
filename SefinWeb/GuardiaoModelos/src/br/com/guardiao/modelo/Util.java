/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.guardiao.modelo;

import java.io.Serializable;

/**
 * Classe do Projeto guardiao criada em 26/03/2013 tem como responsabilidade
 * manter metodos utilitarios
 *
 *
 * @author: ari
 */
public class Util implements Serializable {

    /*
     * IFPA - Instituto Federal de Educação, Ciência e Tecnologia do Pará - Pólo
     * de Tucumã Tecnologia de Análise e Desenvolvimento de Sistemas TAC -
     * Trabalho Acadêmico de Conclusão
     */
    /**
     * @author Anderson Marques Neto Matrícula: 200879217 - Turma: C791UE (A) -
     * E-mail: [url="mailto:andersonneto@msn.com"]andersonneto@msn.com[/url]
     */
    public static boolean CPF(String strCpf) {
        if (strCpf.equals("")) {
            return false;
        }
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.    
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.    
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da divisão por 11.    
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.    
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.    
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.    
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Digito verificador do CPF que está sendo validado.    
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.    
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.    
        return nDigVerific.equals(nDigResult);
    }

    /**
     * Valida um número de CNPJ.
     *
     * @param numeroCNPJ
     *
     */
    public static boolean validaCNPJ(String numeroCNPJ) {
        if (numeroCNPJ == null) {
            return false;
        }
        boolean resultado = false;
        numeroCNPJ = numeroCNPJ.trim();
        int tamanho = numeroCNPJ.length();
        if (tamanho > 14 && tamanho < 19) {
            numeroCNPJ = retiraFormatacaoCNPJ(numeroCNPJ);
        }
        if (numeroCNPJ.length() > 14) {
            return false;
        }
        int posicaoTemp = 0;
        try {
            for (int i = 0, j = 5; i < 12; i++) {
                posicaoTemp += j-- * (numeroCNPJ.charAt(i) - '0');
                if (j < 2) {
                    j = 9;
                }
            }
            posicaoTemp = 11 - (posicaoTemp % 11);
            if (posicaoTemp > 9) {
                posicaoTemp = 0;
            }
            if (posicaoTemp == (numeroCNPJ.charAt(12) - '0')) {
                posicaoTemp = 0;
                for (int i = 0, j = 6; i < 13; i++) {
                    posicaoTemp += j-- * (numeroCNPJ.charAt(i) - '0');
                    if (j < 2) {
                        j = 9;
                    }
                }
                posicaoTemp = 11 - (posicaoTemp % 11);
                if (posicaoTemp > 9) {
                    posicaoTemp = 0;
                }
                if (posicaoTemp == (numeroCNPJ.charAt(13) - '0')) {
                    resultado = true;
                }
            }
            return resultado;
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Retira formatação de um número de CNPJ.
     *
     * @param numeroCNPJ
     *
     */
    public static String retiraFormatacaoCNPJ(String numeroCNPJ) {
        if (numeroCNPJ == null) {
            return null;
        }
        numeroCNPJ = numeroCNPJ.trim();
        numeroCNPJ = numeroCNPJ.replace(".", "");
        numeroCNPJ = numeroCNPJ.replace("/", "");
        numeroCNPJ = numeroCNPJ.replace("-", "");
        return numeroCNPJ;
    }

    /**
     * Formata um número de CNPJ.
     *
     * @param numeroCNPJ
     *
     */
    public static String formataCNPJ(String numeroCNPJ) {
        if (numeroCNPJ == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(numeroCNPJ);
        sb.insert(2, ".");
        sb.insert(6, ".");
        sb.insert(10, "/");
        sb.insert(15, "-");
        return sb.toString();
    }

    /**
     * Retira formatação um número de CPF.
     *
     * @param numeroCPF
     *
     */
    public static String retiraFormatacaoCPF(String numeroCPF) {
        if (numeroCPF == null) {
            return null;
        }
        numeroCPF = numeroCPF.trim();
        numeroCPF = numeroCPF.replace(".", "");
        numeroCPF = numeroCPF.replace("-", "");
        return numeroCPF;
    }

    /**
     * Formata um número de CPF.
     *
     * @param numeroCPF
     *
     */
    public static String formataCPF(String numeroCPF) {
        if (numeroCPF == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(numeroCPF);
        if (sb.length() > 3) {
            sb.insert(3, ".");
        }
        if (sb.length() > 7) {
            sb.insert(7, ".");
        }
        if (sb.length() > 11) {
            sb.insert(11, "-");
        }
        return sb.toString();
    }

    /**
     *
     * @brief Remove caracteres (charsRemove) da string de entrada.
     *
     * @param str - string de entrada
     * @param charsRemove - string aonde deve ser informado os caracteres a ser
     * removido da string de entrada. necessário definir um delimitador.
     * exemplo: charsRemove="x;y;z" --> delimitador eh ";"
     * @param delimiter - string usada para separar os caracteres no split
     * interno do método.
     *
     * @author ricardo spinoza
     * @date 13/06/2012
     *
     * @return string sem formatação  
     *
     */
    public static String removeCaracteresFromString(String str, String charsRemove, String delimiter) {

        if (charsRemove != null && charsRemove.length() > 0 && str != null) {

            String[] remover = charsRemove.split(delimiter);

            for (int i = 0; i < remover.length; i++) {
                //System.out.println("i: " + i + " ["+ remover[i]+"]");
                if (str.indexOf(remover[i]) != -1) {
                    str = str.replace(remover[i], "");
                }
            }
        }

        return str;
    }

    public static String formatarDocumento(String documento) {
        if (documento.length() == 14) {
            return formataCNPJ(documento);
        } else if (documento.length() == 11) {
            return formataCPF(documento);
        } else {
            return null;
        }
    }
}
