package br.unisinos.feeper.corretorjava.Communication;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTeste;
import br.unisinos.feeper.corretorjava.Entities.ExercicioCorrecao;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucao;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucaoClasse;
import br.unisinos.feeper.corretorjava.Creators.DinamicTestCreator;
import br.unisinos.feeper.corretorjava.Creators.JarCreator;
import br.unisinos.feeper.corretorjava.Creators.MainCreator;
import br.unisinos.feeper.corretorjava.FileUtils.FileToString;
import br.unisinos.feeper.corretorjava.StaticTests.StaticTestRunner;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

            //Le o xml
            String xmlData = builder.toString();
            StringReader reader = new StringReader(xmlData);
            JAXBContext jaxbContext = JAXBContext.newInstance(ExercicioSolucao.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ExercicioSolucao solucao = (ExercicioSolucao) jaxbUnmarshaller.unmarshal(reader);
            String alunoID = solucao.idAluno.toString();
            String exercicioID = solucao.idExercicio.toString();
            String solucaoID = solucao.id.toString();

            String partialPath = getClass().getClassLoader().getResource("FindBugs").toURI().getPath();
            String appResourcesPath = new File(partialPath).getParentFile().getPath();
            String tmpPath = System.getProperty("user.home") + "\\Feeper\\Tmp\\" + alunoID + "\\" + exercicioID + "\\" + solucaoID;

            //inicializa a Pasta
            File dir = new File(tmpPath);
            dir.delete();
            dir.mkdirs();

            //Escreve a Solucao do aluno na pasta tmp
            for (ExercicioSolucaoClasse classe : solucao.classes) {

                PrintWriter writer = new PrintWriter(tmpPath + "\\" + classe.NomeClasse + ".java");
                writer.write(classe.codigo);
                writer.close();
            }

            //Cria os Testes na pasta tmp
            DinamicTestCreator testCreator = new DinamicTestCreator();
            for (ExercicioCasoTeste teste : solucao.testes) {

                String testString = testCreator.CreateTest(teste);

                PrintWriter writer = new PrintWriter(tmpPath + "\\test_" + teste.id + ".java");
                writer.write(testString);
                writer.close();
            }

            //Cria a main do jar na pasta tmp
            MainCreator creator = new MainCreator();
            String mainString = creator.CreateMain(solucao);
            PrintWriter writer = new PrintWriter(tmpPath + "\\Main.java");
            writer.write(mainString);
            writer.close();

            //Inicializa o Resultado
            ExercicioCorrecao correcao = new ExercicioCorrecao(solucao.id, solucao.idExercicio, solucao.idAluno);

            //Executa os testes Dinamicos
            JarCreator jarCreator = new JarCreator(appResourcesPath, tmpPath);
            jarCreator.Prepare();
            jarCreator.Compile();
            jarCreator.Pack();
            jarCreator.Run();

            //carrega os resultados dinâmicos
            String dinamicResult = FileToString.toString(tmpPath + "\\dinamic_output.xml");
            reader = new StringReader(dinamicResult);
            jaxbContext = JAXBContext.newInstance(ExercicioCorrecao.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ExercicioCorrecao correcaoDinamica = (ExercicioCorrecao) jaxbUnmarshaller.unmarshal(reader);

            if (correcaoDinamica.erros != null && correcaoDinamica.erros.size() > 0) {

                correcao.erros.addAll(correcaoDinamica.erros);
            }

            //Executa os testes Estaticos e carrega os resultados
            StaticTestRunner staticTest = new StaticTestRunner(appResourcesPath, tmpPath);
            for (ExercicioSolucaoClasse classe : solucao.classes) {

                staticTest.RunTest(classe.NomeClasse, correcao);
            }

            //retorna os resultados
            jaxbContext = JAXBContext.newInstance(ExercicioCorrecao.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(correcao, sw);
            return Response.status(Response.Status.OK).entity(sw.toString()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
    }
}
