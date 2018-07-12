package br.unisinos.feeper.feeper.CorretorJava.StaticTests;

import br.unisinos.feeper.feeper.CorretorJava.Entities.ExercicioSolucao;
import br.unisinos.feeper.feeper.CorretorJava.Entities.ExercicioSolucaoErro;
import br.unisinos.feeper.feeper.CorretorJava.Utils.FileUtils;
import br.unisinos.feeper.feeper.CorretorJava.Utils.EErrorType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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

    	String OS = System.getProperty("os.name");
    	String OSDesc = OS.indexOf("indows") > 0 ? "Windows" : OS.indexOf("nux") > 0 ? "Linux" : "";
    	String PathADD = OSDesc.equals("Windows") ? "\"" : ""; 
    	
    	String srcFile = PathADD + this.destPath + File.separator + className + ".class" + PathADD;

        //copia o avaliador
        FileUtils.copy(this.appResourcesPath + File.separator + "FindBugs", this.destPath + File.separator + "FindBugs");

        //executa os testes
        String jarFile = PathADD + this.destPath + File.separator + "FindBugs" + File.separator + "findbugs.jar" + PathADD;
        String outFile = PathADD + this.destPath + File.separator + "findBugs_" + className + "_output.xml" + PathADD;

        String command = "java -jar " + jarFile;
        command += " -textui -low -effort:min -xml:withMessages -xdocs -outputFile " + outFile;
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
        File file = new File(outFile.replace("\"", ""));
        JAXBContext jaxbContext = JAXBContext.newInstance(BugCollection.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        BugCollection correcaoEstatica = (BugCollection) jaxbUnmarshaller.unmarshal(file);

        if (correcaoEstatica.file != null) {

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
}
