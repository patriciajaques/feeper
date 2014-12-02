package br.unisinos.feeper.corretorjava.Test;

import br.unisinos.feeper.corretorjava.FileUtils.CopyFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class StaticTest {

    private final String appResourcesPath;
    private final String destPath;

    public StaticTest(String appResourcesPath, String destPath) {
        this.appResourcesPath = appResourcesPath;
        this.destPath = destPath;
    }

    public void ExecuteTest(String className) {
        try {
            //Compila a classe caso necessário
            String srcFile = this.destPath + "\\" + className + ".class";
            File classe = new File(srcFile);
            if (classe.exists() == false) {
                this.CompileClass(className);
            }

            //copia o avaliador
            CopyFile.copy(this.appResourcesPath + "\\FindBugs", this.destPath + "\\FindBugs");

            //executa
            String jarFile = this.destPath + "\\FindBugs\\findbugs.jar";
            String outFile = this.destPath + "\\findBugs_output.html";

            String command = "java -jar " + jarFile;
            command += " -textui -low -html -outputFile " + outFile;
            command += " " + srcFile;

            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(command);

            //lê o resultado do comando
            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String s = null;
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean CompileClass(String className) {

        try {
            String fileName = this.destPath + "\\" + className + ".java";
            File sourceFile = new File(fileName);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int result = compiler.run(null, null, null, sourceFile.getPath());

            return result == 0;

        } catch (Exception e) {
            return false;
        }
    }
}
