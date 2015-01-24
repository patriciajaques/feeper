package br.unisinos.feeper.corretorjava.Creators;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTeste;
import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTestePasso;
import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTestePassoParametro;

public class DinamicTestCreator {

    public String CreateTest(ExercicioCasoTeste teste) {

        StringBuilder builder = new StringBuilder();
        builder.append(this.getImports());

        builder.append("public class test_" + teste.getId() + " {");

        builder.append(this.getGlobalVariables());
        builder.append(this.getBefore());
        builder.append(this.getTest(teste));
        builder.append(this.getAfter());

        builder.append("}");

        return builder.toString();
    }

    private String getImports() {

        StringBuilder builder = new StringBuilder();
        builder.append("import static org.junit.Assert.*;");
        builder.append("import java.io.ByteArrayOutputStream;");
        builder.append("import java.io.PrintStream;");
        builder.append("import java.util.Arrays;");
        builder.append("import java.util.Collection;");
        builder.append("import org.junit.After;");
        builder.append("import org.junit.Before;");
        builder.append("import org.junit.Test;");

        return builder.toString();
    }

    private String getGlobalVariables() {

        StringBuilder builder = new StringBuilder();
        builder.append("private PrintStream stdout = null;");
        builder.append("private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();");

        return builder.toString();
    }

    private String getBefore() {

        StringBuilder builder = new StringBuilder();
        builder.append("@Before ");
        builder.append("public void setUpStreams() {");
        builder.append("stdout = System.out;");
        builder.append("System.setOut(new PrintStream(outContent));");
        builder.append("}");

        return builder.toString();
    }

    private String getTest(ExercicioCasoTeste teste) {

        StringBuilder builder = new StringBuilder();
        builder.append("@Test ");
        builder.append("public void test() {");

        for (ExercicioCasoTestePasso passo : teste.getPassos()) {

            //escreve o passo
            if (passo.getExpectedOutputName() != null && passo.getExpectedOutputName().isEmpty() == false) {

                //guarda na variavel
                if (passo.getMethodName() == null || passo.getMethodName().isEmpty()) {
                    //construtor
                    builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = new " + passo.getObjectName() + this.getParameters(passo) + ";");

                } else {
                    //método
                    builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = " + passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo) + ";");
                }
            }
            else if (passo.getExpectedOutputValue() != null && passo.getExpectedOutputValue().isEmpty() == false) {

                //valida o retorno
                String value = passo.getExpectedOutputValue();
                //tratamento para strings
                if (passo.getExpectedOutputType().equals("String") && value.startsWith("\"") == false) {
                    value = "\"" + value + "\"";
                }

                builder.append("assertEquals(" + value + "," + passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo) + ");");
            } else {
                //somente executa
                builder.append(passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo) + ";");
            }
        }
        builder.append("}");

        return builder.toString();
    }

    private String getParameters(ExercicioCasoTestePasso passo) {

        StringBuilder builder = new StringBuilder();
        if (passo.getInputParameters() == null || passo.getInputParameters().isEmpty()) {
            builder.append("()");
        } else {
            builder.append("(");

            int nrParametros = passo.getInputParameters().size();

            for (int i = 0; i < nrParametros; i++) {
                ExercicioCasoTestePassoParametro parametro = passo.getInputParameters().get(i);

                String value = parametro.getObjectValue();
                //tratamento para strings
                if (parametro.getObjectType().equals("String") && value.startsWith("\"") == false) {
                    value = "\"" + value + "\"";
                }

                if (i < nrParametros - 1) {
                    builder.append(value + ",");
                } else {
                    builder.append(value + ")");
                }
            }
        }

        return builder.toString();
    }

    private String getAfter() {

        StringBuilder builder = new StringBuilder();
        builder.append("@After ");
        builder.append("public void cleanUpStreams() {");
        builder.append("System.setOut(stdout);");
        builder.append("}");

        return builder.toString();
    }
}
