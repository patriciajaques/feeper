/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.controller;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCorrecao;
import feeper.Data.entity.ExercicioCasoTeste;
import feeper.Data.entity.ExercicioCasoTestePasso;
import feeper.Data.entity.ExercicioCasoTestePassoParametro;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author gilvani
 */
@Controller
@RequestMapping(value = "/teste")
public class TesteController extends ApplicationController {

    public TesteController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {

        try {

            ExercicioSolucao solucao = this.geraSolucaoExemplo();
            String xmlData = this.objectToXML(solucao);

            URL url = new URL("http://localhost:8084/feeper.CorretorJava/rest/efetuaCorrecao");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setConnectTimeout(6000000);
            connection.setReadTimeout(6000000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(xmlData);
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            
            StringReader reader = new StringReader(builder.toString());
            JAXBContext jaxbContext = JAXBContext.newInstance(ExercicioCorrecao.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ExercicioCorrecao correcao = (ExercicioCorrecao) jaxbUnmarshaller.unmarshal(reader);
            

            System.out.println("\nREST Service Invoked Successfully..");
            in.close();
        } catch (Exception e) {
            System.out.println("\nError while calling REST Service");
            System.out.println(e);
        }

        return "ok";
    }

    private ExercicioSolucao geraSolucaoExemplo() {

        ExercicioSolucao solucao = new ExercicioSolucao();

        try {
            solucao.id = 123;
            solucao.idAluno = 123;
            solucao.idExercicio = 123;
            solucao.classes = new ExercicioSolucaoClasse[1];
            solucao.classes[0] = this.getClasse();

            solucao.testes = new ExercicioCasoTeste[1];
            ExercicioCasoTeste teste = new ExercicioCasoTeste();
            teste.id = 1;
            teste.ordem = 1;

            teste.passos = new ExercicioCasoTestePasso[2];
            ExercicioCasoTestePasso passo = new ExercicioCasoTestePasso();
            passo.ObjectType = "Aluno";
            passo.ObjectName = "a1";
            passo.MethodName = "setNome";

            passo.inputParameters = new ExercicioCasoTestePassoParametro[1];
            ExercicioCasoTestePassoParametro parametro = new ExercicioCasoTestePassoParametro();
            parametro.ObjectType = "String";
            parametro.ObjectName = "nome";
            parametro.ObjectValue = "\"Gilvani Schneider\"";
            passo.inputParameters[0] = parametro;
            teste.passos[0] = passo;

            passo = new ExercicioCasoTestePasso();
            passo.ObjectType = "Aluno";
            passo.ObjectName = "a1";
            passo.MethodName = "getNome";
            passo.ExpectedOutputType = "String";
            passo.ExpectedOutputName = "nome";
            passo.ExpectedOutputValue = "\"Gilvani Schneider\"";
            teste.passos[1] = passo;

            solucao.testes[0] = teste;

        } catch (Exception e) {
            Logger.getLogger(TesteController.class.getName()).log(Level.SEVERE, null, e);
        }
        return solucao;
    }

    private ExercicioSolucaoClasse getClasse() {

        ExercicioSolucaoClasse classe = new ExercicioSolucaoClasse();
        classe.NomeClasse = "Aluno";
        StringBuilder builder = new StringBuilder();

        builder.append("import java.io.PrintWriter;");
        builder.append("public class Aluno {");
        builder.append("private String Nome;");
        builder.append("private String Matricula;");
        builder.append("private String hololo;");
        builder.append("public String getNome() {");
        builder.append("return this.Nome;");
        builder.append("}");
        builder.append("public void setNome(String nome) {");
        builder.append("this.Nome = nome;");
        builder.append("}");
        builder.append("public String getMatricula() {");
        builder.append("return this.Matricula;");
        builder.append("}");
        builder.append("public void setMatricula(String matricula) {");
        builder.append("this.Matricula = matricula;");
        builder.append("}");
        builder.append("public void imprimeNota() {");
        builder.append("if (this.Matricula == \"1176511\") {");
        builder.append("System.out.print(10);");
        builder.append("} else {");
        builder.append("System.out.print(7);");
        builder.append("}}}");

        classe.codigo = builder.toString();
        return classe;
    }

    private String objectToXML(ExercicioSolucao solucao) {

        try {
            JAXBContext context = JAXBContext.newInstance(ExercicioSolucao.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(solucao, sw);

            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
