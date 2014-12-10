package br.unisinos.feeper.corretorjava.Test;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCorrecao;
import br.unisinos.feeper.corretorjava.FileUtils.CopyFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class StaticTest {

    private final String appResourcesPath;
    private final String destPath;

    public StaticTest(String appResourcesPath, String destPath) {
        this.appResourcesPath = appResourcesPath;
        this.destPath = destPath;
    }

    public void ExecuteTest(String className, ExercicioCorrecao correcao) throws Exception {

        String srcFile = this.destPath + "\\" + className + ".class";
        File classe = new File(srcFile);
        //não compilou esta classe, então já aparece um erro de compilacao;
        if (classe.exists() == false) {
            return;
        }

        //copia o avaliador
        CopyFile.copy(this.appResourcesPath + "\\FindBugs", this.destPath + "\\FindBugs");

        //executa
        String jarFile = this.destPath + "\\FindBugs\\findbugs.jar";
        String outFile = this.destPath + "\\findBugs_output.xml";

        String command = "java -jar " + jarFile;
        command += " -textui -low -xml -outputFile " + outFile;
        command += " " + srcFile;

        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command);

        //lê o resultado do comando
        BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        String error = "";
        String s = null;
        while ((s = stdError.readLine()) != null) {
            error += s + "\n";
        }
        if (error.equals("") == false) {
            //throw new Exception(error);
        }
    }
}
