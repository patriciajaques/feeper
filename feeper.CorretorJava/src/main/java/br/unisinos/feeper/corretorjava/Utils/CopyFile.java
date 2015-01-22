package br.unisinos.feeper.corretorjava.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFile {

    public static void copy(String srcFile, String destFile) throws IOException {

        File src = new File(srcFile);
        File dest = new File(destFile);
        copy(src, dest);
    }

    private static void copy(File src, File dest) throws IOException {

        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdirs();
            }

            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                copy(srcFile, destFile);
            }
        } else {
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }
}
