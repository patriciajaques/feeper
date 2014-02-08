/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 * fabioalves
 */
public class Util {
    
    public static String criptoMD5(String value)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                    sb.append(Integer.toHexString((int) (b & 0xff)));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            return value;
        }
    }
    
    private static final String MAIUSCULAS = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
    private static final String MINUSCULAS = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
    private static final String NUMEROS = "0,1,2,3,4,5,6,7,8,9";
    private static final String SIMBOLOS = "!,@,#,?";
    
    public static String gerarSenha(int tamanhoSenha)
    {
        String senhaGerada = "";
        boolean valido = false;
        String[] permitidos = String.format("%s,%s,%s,%s", MAIUSCULAS, MINUSCULAS, NUMEROS, SIMBOLOS).split(",");

        do
        {
            Random rd = new Random();

            String temp = "";
            senhaGerada = "";

            for (int i = 0; i < tamanhoSenha; i++)
            {
                temp = permitidos[rd.nextInt(permitidos.length)];
                senhaGerada += temp;
            }

            valido = validarFormatoSenha(senhaGerada);
        } while (!valido);

        return senhaGerada;
    }

    public static boolean validarFormatoSenha(String senha)
    {
        String[] listaMaiusculas = MAIUSCULAS.split(",");
        String[] listaMinusculas = MINUSCULAS.split(",");
        String[] listaNumeros = NUMEROS.split(",");
        String[] listaSimbolos = SIMBOLOS.split(",");

        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temSimbolo = false;
        
        for (int i = 0; i < listaMaiusculas.length; i++)
            if (senha.contains(listaMaiusculas[i]))
            {
                temMaiuscula = true;
                break;
            }
        for (int i = 0; i < listaMinusculas.length; i++)
            if (senha.contains(listaMinusculas[i]))
            {
                temMinuscula = true;
                break;
            }
        for (int i = 0; i < listaNumeros.length; i++)
            if (senha.contains(listaNumeros[i]))
            {
                temNumero = true;
                break;
            }
        for (int i = 0; i < listaSimbolos.length; i++)
            if (senha.contains(listaSimbolos[i]))
            {
                temSimbolo = true;
                break;
            }
        
        return (temMaiuscula && temMinuscula && temNumero && temSimbolo);
    }
    
}
