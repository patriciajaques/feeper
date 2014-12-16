package br.unisinos.feeper.corretorjava.StaticTests;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCorrecao;
import br.unisinos.feeper.corretorjava.Entities.ExercicioCorrecaoErro;
import br.unisinos.feeper.corretorjava.FileUtils.CopyFile;
import br.unisinos.feeper.corretorjava.FileUtils.FileToString;
import java.io.File;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class StaticTestRunner {

    private final String appResourcesPath;
    private final String destPath;

    public StaticTestRunner(String appResourcesPath, String destPath) {
        this.appResourcesPath = appResourcesPath;
        this.destPath = destPath;
    }

    public void RunTest(String className, ExercicioCorrecao correcao) throws Exception {

        String srcFile = this.destPath + "\\" + className + ".class";
        File classe = new File(srcFile);
        //não compilou esta classe, então já aparece um erro de compilacao;
        if (classe.exists() == false) {
            return;
        }

        //copia o avaliador
        CopyFile.copy(this.appResourcesPath + "\\FindBugs", this.destPath + "\\FindBugs");

        //executa os testes
        String jarFile = this.destPath + "\\FindBugs\\findbugs.jar";
        String outFile = this.destPath + "\\findBugs_" + className + "_output.xml";

        String command = "java -jar " + jarFile;
        command += " -textui -low -xml:withMessages -xdocs -outputFile " + outFile;
        command += " " + srcFile;

        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(command);

        //carrega os resultados
        File file = new File(outFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(BugCollection.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BugCollection correcaoEstatica = (BugCollection) jaxbUnmarshaller.unmarshal(file);

        for (ClassResult result : correcaoEstatica.file) {
            for (BugInstance bug : result.BugInstance) {
                ExercicioCorrecaoErro erro = new ExercicioCorrecaoErro(-1, "static", bug.message);
                correcao.erros.add(erro);
            }
        }
    }
}
