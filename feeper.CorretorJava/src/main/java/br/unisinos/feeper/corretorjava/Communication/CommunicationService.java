package br.unisinos.feeper.corretorjava.Communication;

import br.unisinos.feeper.corretorjava.FileUtils.CopyFile;
import br.unisinos.feeper.corretorjava.Test.DinamicTest;
import br.unisinos.feeper.corretorjava.Test.StaticTest;
import feeper.Data.entity.ExercicioSolucao;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author gilvani
 */
@Path("/")
public class CommunicationService {

    @POST
    @Path("/efetuaCorrecao")
    @Consumes(MediaType.APPLICATION_XML)
    public Response efetuaCorrecao(InputStream incomingData) {

        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }

            String xmlData = builder.toString();

            JAXBContext jaxbContext = JAXBContext.newInstance(ExercicioSolucao.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(xmlData);
            ExercicioSolucao solucao = (ExercicioSolucao) jaxbUnmarshaller.unmarshal(reader);

            String alunoID = solucao.idAluno.toString();
            String exercicioID = solucao.idExercicio.toString();

            String partialPath = getClass().getClassLoader().getResource("FindBugs").toURI().getPath();
            String appResourcesPath = new File(partialPath).getParentFile().getPath();
            String tmpPath = System.getProperty("user.home") + "\\Feeper\\Tmp\\" + alunoID + "\\" + exercicioID;

            //Le o xml
            //Move a Solucao do aluno para a pasta tmp
            String dummyPath = appResourcesPath + "\\DummyData";
            CopyFile.copy(dummyPath + "\\Aluno.txt", tmpPath + "\\Aluno.java");

            //Cria os Testes na pasta
            CopyFile.copy(dummyPath + "\\parameterizedTest.txt", tmpPath + "\\parameterizedTest.java");
            CopyFile.copy(dummyPath + "\\Main.txt", tmpPath + "\\Main.java");

            //Executa os testes Dinamicos
            DinamicTest teste1 = new DinamicTest(appResourcesPath, tmpPath);
            teste1.ExecuteTest();

            //Executa os testes Estaticos
            StaticTest teste2 = new StaticTest(appResourcesPath, tmpPath);
            teste2.ExecuteTest("Aluno");
            //trata os resultados

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(200).entity("OK").build();
    }
}
