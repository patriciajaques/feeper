package br.unisinos.feeper.corretorjava.StaticTests;

import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucao;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucaoErro;
import br.unisinos.feeper.corretorjava.Utils.FileUtils;
import br.unisinos.feeper.corretorjava.Utils.EErrorType;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class StaticTestRunner {

    private final String appResourcesPath;
    private final String destPath;

    public StaticTestRunner(String appResourcesPath, String destPath) {
        this.appResourcesPath = appResourcesPath;
        this.destPath = destPath;
    }

    public void RunTest(String className, ExercicioSolucao solucao) throws Exception {

        String srcFile = this.destPath + File.separator + className + ".class";

        //copia o avaliador
        FileUtils.copy(this.appResourcesPath + File.separator + "FindBugs", this.destPath + File.separator + "FindBugs");

        //executa os testes
        String jarFile = this.destPath + File.separator + "FindBugs" + File.separator + "findbugs.jar";
        String outFile = this.destPath + File.separator + "findBugs_" + className + "_output.xml";

        String command = "java -jar " + jarFile;
        command += " -textui -low -effort:max -xml:withMessages -xdocs -outputFile " + outFile;
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

        //carrega os resultados
        File file = new File(outFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(BugCollection.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BugCollection correcaoEstatica = (BugCollection) jaxbUnmarshaller.unmarshal(file);

        for (ClassResult result : correcaoEstatica.file) {

            Filter_Translator filterTranslate = new Filter_Translator();
            List<BugInstance> bugs = filterTranslate.FilterAndTranslate(result);

            for (BugInstance bug : bugs) {
                ExercicioSolucaoErro erro = new ExercicioSolucaoErro(solucao.getId(), -1, "", EErrorType.ESTATICO, bug.message);
                erro.setLinhaErro(bug.line);
                erro.setStaticErrorType(bug.type);
                solucao.getErros().add(erro);
            }
        }
    }
}
