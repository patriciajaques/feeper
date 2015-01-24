package br.unisinos.feeper.corretorjava.Communication;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTeste;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucao;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucaoClasse;
import br.unisinos.feeper.corretorjava.Creators.DinamicTestCreator;
import br.unisinos.feeper.corretorjava.Creators.JarCreator;
import br.unisinos.feeper.corretorjava.Creators.MainCreator;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucaoErro;
import br.unisinos.feeper.corretorjava.Utils.FileToString;
import br.unisinos.feeper.corretorjava.StaticTests.StaticTestRunner;
import br.unisinos.feeper.corretorjava.Utils.EErrorType;
import br.unisinos.feeper.corretorjava.Utils.EStatusSolucao;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
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
            String alunoID = solucao.getIdAluno().toString();
            String exercicioID = solucao.getIdExercicio().toString();
            String solucaoID = solucao.getId().toString();

            String partialPath = getClass().getClassLoader().getResource("FindBugs").toURI().getPath();
            String appResourcesPath = new File(partialPath).getParentFile().getPath();
            String tmpPath = System.getProperty("user.home") + "\\Feeper\\Tmp\\" + alunoID + "\\" + exercicioID + "\\" + solucaoID;

            //inicializa a Pasta
            File dir = new File(tmpPath);
            dir.delete();
            dir.mkdirs();

            //Escreve a Solucao do aluno na pasta tmp
            for (ExercicioSolucaoClasse classe : solucao.getClasses()) {

                PrintWriter writer = new PrintWriter(tmpPath + "\\" + classe.getNomeClasse() + ".java");
                writer.write(classe.getCodigo());
                writer.close();
            }

            //Cria os Testes na pasta tmp
            DinamicTestCreator testCreator = new DinamicTestCreator();
            for (ExercicioCasoTeste teste : solucao.getTestes()) {

                String testString = testCreator.CreateTest(teste);

                PrintWriter writer = new PrintWriter(tmpPath + "\\test_" + teste.getId() + ".java");
                writer.write(testString);
                writer.close();
            }

            //Cria a main do jar na pasta tmp
            MainCreator creator = new MainCreator();
            String mainString = creator.CreateMain(solucao);
            PrintWriter writer = new PrintWriter(tmpPath + "\\Main.java");
            writer.write(mainString);
            writer.close();

            solucao.setErros(new ArrayList<ExercicioSolucaoErro>());

            //Executa os testes Dinamicos
            JarCreator jarCreator = new JarCreator(appResourcesPath, tmpPath);
            try {
                jarCreator.Prepare();
                jarCreator.Compile();
                jarCreator.Pack();
            } catch (Exception e) {
                //erro de compilação
                solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);

                ExercicioSolucaoErro erroCompilacao = new ExercicioSolucaoErro(solucao.getId(), 0, EErrorType.COMPILACAO, e.toString());
                solucao.getErros().add(erroCompilacao);
            }

            //se deu erro de compilação, nem continua
            if (solucao.getErros().isEmpty()) {

                jarCreator.Run();

                //carrega os resultados dinâmicos
                String dinamicResult = FileToString.toString(tmpPath + "\\dinamic_output.xml");
                reader = new StringReader(dinamicResult);
                jaxbContext = JAXBContext.newInstance(ExercicioSolucao.class);
                jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                ExercicioSolucao resultadoDinamico = (ExercicioSolucao) jaxbUnmarshaller.unmarshal(reader);

                if (resultadoDinamico != null && resultadoDinamico.getErros() != null) {
                    for (ExercicioSolucaoErro erroDinamico : resultadoDinamico.getErros()) {
                        solucao.getErros().add(erroDinamico);
                    }
                }

                //deu erro dinâmico ou seja não cumpriu os objetivos
                if (solucao.getErros().isEmpty() == false) {
                    solucao.setIdStatus(EStatusSolucao.RESULTADO_INVALIDO);
                } else {
                    solucao.setIdStatus(EStatusSolucao.RESOLVIDO);
                }

                //Executa os testes Estaticos e carrega os resultados
                StaticTestRunner staticTest = new StaticTestRunner(appResourcesPath, tmpPath);
                for (ExercicioSolucaoClasse classe : solucao.getClasses()) {

                    staticTest.RunTest(classe.getNomeClasse(), solucao);
                }
            }

            //retorna os resultados
            jaxbContext = JAXBContext.newInstance(ExercicioSolucao.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(solucao, sw);
            return Response.status(Response.Status.OK).entity(sw.toString()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
    }
}
