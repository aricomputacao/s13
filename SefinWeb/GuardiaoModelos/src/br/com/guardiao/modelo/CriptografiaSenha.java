/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.guardiao.modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ari
 */
public class CriptografiaSenha {

    public CriptografiaSenha() {
    }
    
    public  String criptografarSenha(String senha){
         MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(senha.getBytes());
            BigInteger hash = new BigInteger(1, digest.digest());
            senha = hash.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ex.getMessage()).log(Level.SEVERE, null, ex);
        }
        return senha;
    }
    
}
