package br.unisinos.feeper.corretorjava.Creators;

import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTeste;
import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTestePasso;
import br.unisinos.feeper.corretorjava.Entities.ExercicioCasoTestePassoParametro;
import java.util.ArrayList;

public class DinamicTestCreator {

    public String CreateTest(ExercicioCasoTeste teste) {

        StringBuilder builder = new StringBuilder();
        builder.append(this.getImports());

        builder.append("public class test_" + teste.id + " {");

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

        ArrayList objetosInicializados = new ArrayList<String>();
        for (ExercicioCasoTestePasso passo : teste.passos) {

            //caso objeto não inicializado, usa construtor padrão
            if (objetosInicializados.contains(passo.ObjectName) == false) {
                builder.append(passo.ObjectType + " " + passo.ObjectName + " = new " + passo.ObjectType + "();");
                objetosInicializados.add(passo.ObjectName);
            }

            //escreve o passo
            if (passo.ExpectedOutputValue == null || passo.ExpectedOutputValue.isEmpty()) {
                builder.append(passo.ObjectName + "." + passo.MethodName + this.getParameters(passo) + ";");
            } else {
                if (passo.ExpectedOutputName == null || passo.ExpectedOutputName.isEmpty()) {
                    //Não guarda somente valida
                    builder.append("assertEquals(");
                    builder.append(passo.ObjectName + "." + passo.MethodName + this.getParameters(passo));
                    builder.append("," + passo.ExpectedOutputValue + ");");

                } else {
                    //guarda e valida
                    builder.append(passo.ExpectedOutputType + " " + passo.ExpectedOutputName + " = " + passo.ObjectName + "." + passo.MethodName + this.getParameters(passo) + ";");
                    objetosInicializados.add(passo.ExpectedOutputName);

                    builder.append("assertEquals(" + passo.ExpectedOutputName + "," + passo.ExpectedOutputValue + ");");
                }
            }
        }
        builder.append("}");

        return builder.toString();
    }

    private String getParameters(ExercicioCasoTestePasso passo) {

        StringBuilder builder = new StringBuilder();
        if (passo.inputParameters == null || passo.inputParameters.length == 0) {
            builder.append("()");
        } else {
            builder.append("(");
            for (int i = 0; i < passo.inputParameters.length; i++) {
                ExercicioCasoTestePassoParametro parametro = passo.inputParameters[i];

                if (i < passo.inputParameters.length - 1) {
                    builder.append(parametro.ObjectValue + ",");
                } else {
                    builder.append(parametro.ObjectValue + ")");
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
