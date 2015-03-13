/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Creators;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTeste;
import br.unisinos.feeper.corretorjava.Entities.ExercicioSolucao;
import br.unisinos.feeper.corretorjava.Utils.EErrorType;

/**
 *
 * @author gilvani
 */
public class MainCreator {

    public String CreateMain(ExercicioSolucao solucao) {

        StringBuilder builder = new StringBuilder();
        builder.append(this.getImports());

        builder.append("public class Main {");
        builder.append("public static void main(String[] args) {");

        builder.append(this.getTests(solucao));

        builder.append("}");

        return builder.toString();
    }

    private String getImports() {

        StringBuilder builder = new StringBuilder();
        builder.append("import java.util.List;");
        builder.append("import java.util.ArrayList;");
        builder.append("import org.junit.runner.JUnitCore;");
        builder.append("import org.junit.runner.Result;");
        builder.append("import org.junit.runner.notification.Failure;");
        builder.append("import java.io.File;");
        builder.append("import javax.xml.bind.JAXBContext;");
        builder.append("import javax.xml.bind.Marshaller;");

        return builder.toString();
    }

    private String getTests(ExercicioSolucao solucao) {

        StringBuilder builder = new StringBuilder();
        builder.append("try {");
        builder.append("JUnitCore junit = new JUnitCore();");
        builder.append("List<ExercicioSolucaoErro> erros = new ArrayList<ExercicioSolucaoErro>();");
        builder.append("Result result = null;");
        builder.append("List<Failure> failures = null;");

        for (ExercicioCasoTeste teste : solucao.getTestes()) {
            builder.append("result = junit.run(test_" + teste.getId() + ".class);");
            builder.append("failures = result.getFailures();");

            builder.append("for (Failure failure : failures) {");
            String mensagem = teste.getMensagemPersonalizada() == null ? "" : teste.getMensagemPersonalizada();
            builder.append("String message = failure.getTrace().contains(\"java.lang.AssertionError\")? \"O resultado obtido difere do esperado\" : failure.getTrace();");
            builder.append("ExercicioSolucaoErro erro = new ExercicioSolucaoErro(" + solucao.getId() + "," + teste.getId() + ",\"" + mensagem.replace("\"", "") + "\"," + ((int) EErrorType.DINAMICO) + ",message);");

            builder.append("erros.add(erro);");
            builder.append("break;");//retorna somente 1 erro dinâmico para reduzir a carga cognitiva;
            builder.append("}");
        }

        builder.append("ExercicioSolucao solucao = new ExercicioSolucao();");
        builder.append("solucao.setErros(erros);");
        builder.append("JAXBContext context = JAXBContext.newInstance(ExercicioSolucao.class);");
        builder.append("Marshaller m = context.createMarshaller();");
        builder.append("m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);");
        builder.append("File file = new File(\"dinamic_output.xml\");");
        builder.append("m.marshal(solucao, file);");
        builder.append("}catch (Exception e) {e.printStackTrace();}");
        builder.append("}");

        return builder.toString();
    }
}
