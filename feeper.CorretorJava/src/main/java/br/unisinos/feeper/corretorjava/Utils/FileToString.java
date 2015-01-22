/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author gilvani
 */
public class FileToString {

    public static String toString(String srcFile) throws IOException {

        File file = new File(srcFile);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return new String(data, "UTF-8");
    }

}
