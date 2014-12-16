/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Creators;

import br.unisinos.feeper.corretorjava.FileUtils.CopyFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author gilvani
 */
public class JarCreator {

    private final String appResourcesPath;
    private final String destPath;

    public JarCreator(String appResourcesPath, String destPath) {
        this.appResourcesPath = appResourcesPath;
        this.destPath = destPath;
    }

    public void Prepare() throws IOException {

        CopyFile.copy(this.appResourcesPath + "\\JarComponents\\MANIFEST.MF", this.destPath + "\\MANIFEST.MF");
        CopyFile.copy(this.appResourcesPath + "\\JarComponents\\junit.jar", this.destPath + "\\junit.jar");
        CopyFile.copy(this.appResourcesPath + "\\JarComponents\\org.hamcrest.core.jar", this.destPath + "\\org.hamcrest.core.jar");
        CopyFile.copy(this.appResourcesPath + "\\JarComponents\\policy.txt", this.destPath + "\\policy.txt");
        CopyFile.copy(this.appResourcesPath + "\\JarComponents\\ExercicioCorrecao.java", this.destPath + "\\ExercicioCorrecao.java");
        CopyFile.copy(this.appResourcesPath + "\\JarComponents\\ExercicioCorrecaoErro.java", this.destPath + "\\ExercicioCorrecaoErro.java");
    }

    public void Compile() throws Exception {

        String command = "javac -classpath junit.jar;org.hamcrest.core.jar *.java";

        File dir = new File(this.destPath);
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command, new String[0], dir);

        BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        String error = "";
        String s = null;
        while ((s = stdError.readLine()) != null) {
            error += s + "\n";
        }
        if (error.equals("") == false) {
            throw new Exception(error);
        }
    }

    public void Pack() throws Exception {

        String command = "jar cfm result.jar MANIFEST.MF *.jar *.class";

        File dir = new File(this.destPath);
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command, new String[0], dir);

        BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        String error = "";
        String s = null;
        while ((s = stdError.readLine()) != null) {
            error += s + "\n";
        }
        if (error.equals("") == false) {
            throw new Exception(error);
        }
    }

    public void Run() throws Exception {

        String command = "java -Djava.security.manager -Djava.security.policy=policy.txt -jar";
        command += " result.jar";

        File dir = new File(this.destPath);
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command, new String[0], dir);

        //lê o resultado do comando
        BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        String error = "";
        String s = null;
        while ((s = stdError.readLine()) != null) {
            error += s + "\n";
        }
        if (error.equals("") == false) {
            throw new Exception(error);
        }
    }
}
