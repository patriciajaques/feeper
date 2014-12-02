package br.unisinos.feeper.corretorjava.Communication;

import br.unisinos.feeper.corretorjava.FileUtils.CopyFile;
import br.unisinos.feeper.corretorjava.Test.DinamicTest;
import br.unisinos.feeper.corretorjava.Test.StaticTest;
import java.io.File;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author gilvani
 */
@Path("/")
public class CommunicationService {

    @GET
    @Path("/efetuaCorrecao")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response efetuaCorrecao(InputStream incomingData) {

        try {
            //StringBuilder builder = new StringBuilder();
            //BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            //String line = null;
            //while ((line = in.readLine()) != null) {
            //    builder.append(line);
            //}

            String alunoID = "123";
            String exercicioID = "123";

            String partialPath = getClass().getClassLoader().getResource("FindBugs").toURI().getPath();
            String appResourcesPath = new File(partialPath).getParentFile().getPath();
            String tmpPath = System.getProperty("user.home") + "\\Feeper\\Tmp\\" + alunoID + "\\" + exercicioID;

            //Lê o xml
            //Move a Solução do aluno para a pasta tmp
            String dummyPath = appResourcesPath + "\\DummyData";
            CopyFile.copy(dummyPath + "\\Aluno.txt", tmpPath + "\\Aluno.java");

            //Cria os Testes na pasta
            CopyFile.copy(dummyPath + "\\parameterizedTest.txt", tmpPath + "\\parameterizedTest.java");
            CopyFile.copy(dummyPath + "\\Main.txt", tmpPath + "\\Main.java");

            //Executa os testes Dinamicos
            DinamicTest teste1 = new DinamicTest(appResourcesPath, tmpPath);
            teste1.ExecuteTest();

            //Executa os testes Estáticos
            StaticTest teste2 = new StaticTest(appResourcesPath, tmpPath);
            teste2.ExecuteTest("Aluno");
            //trata os resultados

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(200).entity("OK").build();
    }
}
