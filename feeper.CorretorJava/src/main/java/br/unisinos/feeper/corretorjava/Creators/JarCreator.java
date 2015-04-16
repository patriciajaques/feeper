/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Creators;

import br.unisinos.feeper.corretorjava.Utils.FileUtils;
import br.unisinos.feeper.corretorjava.Utils.FeeperCompiler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

        FileUtils.copy(this.appResourcesPath + File.separator + "JarComponents" + File.separator + "MANIFEST.MF", this.destPath + File.separator + "MANIFEST.MF");
        FileUtils.copy(this.appResourcesPath + File.separator + "JarComponents" + File.separator + "junit.jar", this.destPath + File.separator + "junit.jar");
        FileUtils.copy(this.appResourcesPath + File.separator + "JarComponents" + File.separator + "org.hamcrest.core.jar", this.destPath + File.separator + "org.hamcrest.core.jar");
        FileUtils.copy(this.appResourcesPath + File.separator + "JarComponents" + File.separator + "policy.txt", this.destPath + File.separator + "policy.txt");
        FileUtils.copy(this.appResourcesPath + File.separator + "JarComponents" + File.separator + "ExercicioSolucao.java", this.destPath + File.separator + "ExercicioSolucao.java");
        FileUtils.copy(this.appResourcesPath + File.separator + "JarComponents" + File.separator + "ExercicioSolucaoErro.java", this.destPath + File.separator + "ExercicioSolucaoErro.java");
    }

    public void CompileMain() throws Exception {

        FeeperCompiler compiler = new FeeperCompiler();
        compiler.CompileClass(this.destPath + File.separator + "ExercicioSolucao.java");
        compiler.CompileClass(this.destPath + File.separator + "ExercicioSolucaoErro.java");

        ArrayList<String> classPathFileNames = new ArrayList<>();
        classPathFileNames.add(this.destPath + File.separator + "junit.jar");
        classPathFileNames.add(this.destPath + File.separator + "org.hamcrest.core.jar");

        compiler.CompileTest(this.destPath + File.separator + "Main.java", classPathFileNames);
    }

    public void Pack() throws Exception {

        File dir = new File(this.destPath);

        String command = "jar cfm result.jar MANIFEST.MF";

        for (String arquivo : dir.list()) {

            if (arquivo.toLowerCase().endsWith(".jar") == false && arquivo.toLowerCase().endsWith(".class") == false) {
                continue;
            }

            command += " " + arquivo;
        }

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

        String error = "";

        File dir = new File(this.destPath);
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command, new String[0], dir);

        Worker worker = new Worker(pr);
        worker.start();
        try {
            worker.join(120000);
            if (worker.exitValue != null) {
                //lê o resultado do comando
                BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
                String s = null;
                while ((s = stdError.readLine()) != null) {
                    error += s + "\n";
                }
            } else {
                error = "A correção do exercício demorou mais que o esperado! Certifique-se que sua solução nao possui um loop infinito!";
            }
        } finally {
            pr.destroy();
        }

        if (error.equals("") == false) {
            throw new Exception(error);
        }
    }

    private static class Worker extends Thread {

        private final Process process;
        private Integer exitValue;

        private Worker(Process process) {
            this.process = process;
        }

        public void run() {
            try {
                exitValue = process.waitFor();
            } catch (InterruptedException ignore) {
                return;
            }
        }
    }
}
