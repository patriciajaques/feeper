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
import br.unisinos.feeper.corretorjava.Utils.CompilationException;
import br.unisinos.feeper.corretorjava.Utils.FeeperCompiler;
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
            
            for (ExercicioSolucaoClasse classe : solucao.getClasses()) {
                classe.setCodigo(classe.getCodigo().replaceAll("#n", "\n").replaceAll("#r", "\r"));
            }
            
            String alunoID = String.valueOf(solucao.getIdAluno());
            String exercicioID = String.valueOf(solucao.getIdExercicio());
            String solucaoID = String.valueOf(solucao.getId());
            
            String partialPath = getClass().getClassLoader().getResource("FindBugs").toURI().getPath();
            String appResourcesPath = new File(partialPath).getParentFile().getPath();
            String tmpPath = System.getProperty("user.home") + "\\Feeper\\Tmp\\" + alunoID + "\\" + exercicioID + "\\" + solucaoID;

            //inicializa a Pasta
            File dir = new File(tmpPath);
            dir.delete();
            dir.mkdirs();
            
            solucao.setErros(new ArrayList<ExercicioSolucaoErro>());

            //Escreve a Solucao do aluno na pasta tmp e compila
            FeeperCompiler compiler = new FeeperCompiler();
            try {
                for (ExercicioSolucaoClasse classe : solucao.getClasses()) {
                    
                    String fileName = tmpPath + "\\" + classe.getNomeClasse() + ".java";
                    PrintWriter writer = new PrintWriter(fileName);
                    writer.write(classe.getCodigo());
                    writer.close();
                    //compila as classes do aluno
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
                classPathFileNames.add(tmpPath + "\\junit.jar");
                classPathFileNames.add(tmpPath + "\\org.hamcrest.core.jar");
                
                for (ExercicioCasoTeste teste : solucao.getTestes()) {
                    try {
                        
                        String testString = testCreator.CreateTest(teste);
                        String fileName = tmpPath + "\\test_" + teste.getId() + ".java";
                        
                        PrintWriter writer = new PrintWriter(fileName);
                        writer.write(testString);
                        writer.close();

                        //compila cada teste separado
                        compiler.CompileTest(fileName, classPathFileNames);
                    } catch (CompilationException e) {
                        solucao.setIdStatus(EStatusSolucao.ERRO_COMPILACAO);
                        ExercicioSolucaoErro erroDinamico = new ExercicioSolucaoErro(solucao.getId(), teste.getId(), teste.getMensagemPersonalizada(), EErrorType.DINAMICO, e.getMessage());
                        solucao.getErros().add(erroDinamico);
                    }
                }
                //se deu erro de compilação nos testes, nem continua
                if (solucao.getErros().isEmpty()) {

                    //Cria a main do jar na pasta tmp
                    MainCreator creator = new MainCreator();
                    String mainString = creator.CreateMain(solucao);
                    PrintWriter writer = new PrintWriter(tmpPath + "\\Main.java");
                    writer.write(mainString);
                    writer.close();

                    //Executa os testes Dinamicos
                    jarCreator.CompileMain();
                    jarCreator.Pack();
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
            }
            
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
