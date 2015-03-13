package br.unisinos.feeper.corretorjava.Communication;

import br.unisinos.feeper.corretorjava.Creators.ClassCreator;
import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTeste;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucao;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucaoClasse;
import br.unisinos.feeper.corretorjava.Creators.DinamicTestCreator;
import br.unisinos.feeper.corretorjava.Creators.JarCreator;
import br.unisinos.feeper.corretorjava.Creators.MainCreator;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucaoErro;
import br.unisinos.feeper.corretorjava.StaticTests.StaticTestRunner;
import br.unisinos.feeper.corretorjava.Utils.CompilationException;
import br.unisinos.feeper.corretorjava.Utils.FeeperCompiler;
import br.unisinos.feeper.corretorjava.Utils.EErrorType;
import br.unisinos.feeper.corretorjava.Utils.EStatusSolucao;
import br.unisinos.feeper.corretorjava.Utils.FileUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
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
            Charset charset = Charset.forName("UTF8");

            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData, charset));
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

            for (ExercicioSolucaoClasse classe : solucao.getClasses()) {
                classe.setCodigo(classe.getCodigo().replaceAll("#n", "\n").replaceAll("#r", "\r"));
            }
            solucao.setErros(new ArrayList<ExercicioSolucaoErro>());

            String alunoID = String.valueOf(solucao.getIdAluno());
            String exercicioID = String.valueOf(solucao.getIdExercicio());
            String solucaoID = String.valueOf(solucao.getId());

            String partialPath = getClass().getClassLoader().getResource("FindBugs").toURI().getPath();
            String appResourcesPath = new File(partialPath).getParentFile().getPath();
            String tmpPath = System.getProperty("user.home") + File.separator + "Feeper" + File.separator + "temp" + File.separator + alunoID + File.separator + exercicioID + File.separator + solucaoID;

            //inicializa a Pasta
            File dir = new File(tmpPath);

            try {

                FileUtils.deleteDirectory(dir);
                dir.mkdirs();

                ClassCreator classCreator = new ClassCreator();
                //Escreve a Solucao do aluno na pasta tmp
                for (ExercicioSolucaoClasse classe : solucao.getClasses()) {

                    String fileName = tmpPath + File.separator + classe.getNomeClasse() + ".java";
                    Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));

                    String codigoAdaptado = classCreator.getCodigoAdaptado(classe.getCodigo());
                    writer.write(codigoAdaptado);
                    writer.close();
                }

                //compila as classes do aluno
                FeeperCompiler compiler = new FeeperCompiler();
                try {
                    for (ExercicioSolucaoClasse classe : solucao.getClasses()) {
                        String fileName = tmpPath + File.separator + classe.getNomeClasse() + ".java";
                        compiler.CompileClass(fileName);

                    }
                } catch (CompilationException e) {
                    solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);
                    ExercicioSolucaoErro erroCompilacao = new ExercicioSolucaoErro(solucao.getId(), -1, "A solu&ccedil;&atilde;o submetida possui erros de compila&ccedil;&atilde;o", EErrorType.COMPILACAO, e.getMessage());
                    erroCompilacao.setLinhaErro((int) e.getLine());
                    solucao.getErros().add(erroCompilacao);
                }

                //se deu erro de compilação nas classes, nem continua
                if (solucao.getErros().isEmpty()) {

                    //Move Pré-requisitos
                    JarCreator jarCreator = new JarCreator(appResourcesPath, tmpPath);
                    jarCreator.Prepare();

                    //Cria os Testes na pasta tmp
                    DinamicTestCreator testCreator = new DinamicTestCreator();

                    ArrayList<String> classPathFileNames = new ArrayList<>();
                    classPathFileNames.add(tmpPath + File.separator + "junit.jar");
                    classPathFileNames.add(tmpPath + File.separator + "org.hamcrest.core.jar");

                    for (ExercicioCasoTeste teste : solucao.getTestes()) {
                        try {

                            String testString = testCreator.CreateTest(teste);
                            String fileName = tmpPath + File.separator + "test_" + teste.getId() + ".java";

                            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
                            writer.write(testString);
                            writer.close();

                            //compila cada teste separado
                            compiler.CompileTest(fileName, classPathFileNames);
                        } catch (CompilationException e) {
                            solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);
                            ExercicioSolucaoErro erroDinamico = new ExercicioSolucaoErro(solucao.getId(), teste.getId(), teste.getMensagemCompilacao(), EErrorType.DINAMICO, e.getMessage());
                            solucao.getErros().add(erroDinamico);
                        }
                    }
                    //se deu erro de compilação nos testes, não executa os testes dinâmicos
                    if (solucao.getErros().isEmpty()) {

                        //Cria a main do jar na pasta tmp
                        MainCreator creator = new MainCreator();
                        String mainString = creator.CreateMain(solucao);
                        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpPath + File.separator + "Main.java"), "UTF-8"));
                        writer.write(mainString);
                        writer.close();

                        //Executa os testes Dinamicos
                        jarCreator.CompileMain();
                        jarCreator.Pack();
                        jarCreator.Run();

                        //carrega os resultados dinâmicos
                        String dinamicResult = FileUtils.toString(tmpPath + File.separator + "dinamic_output.xml");
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
                    }

                    //Executa os testes Estaticos e carrega os resultados
                    StaticTestRunner staticTest = new StaticTestRunner(appResourcesPath, tmpPath);
                    for (ExercicioSolucaoClasse classe : solucao.getClasses()) {

                        staticTest.RunTest(classe.getNomeClasse(), solucao);
                    }

                }

            } catch (Exception e) {
                solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);
                ExercicioSolucaoErro erroCompilacao = new ExercicioSolucaoErro(solucao.getId(), -1, "A solu&ccedil;&atilde;o submetida possui erros de compila&ccedil;&atilde;o", EErrorType.COMPILACAO, e.getMessage());
                solucao.getErros().add(erroCompilacao);
            }

            FileUtils.deleteDirectory(dir);

            solucao.setErrosCount(solucao.getErros().size());

            //retorna os resultados
            jaxbContext = JAXBContext.newInstance(ExercicioSolucao.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(solucao, sw);
            return Response.status(Response.Status.OK).entity(sw.toString()).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
        }
    }
}
