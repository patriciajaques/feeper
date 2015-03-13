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

        for (int i = 0; i < teste.getPassos().size(); i++) {

            ExercicioCasoTestePasso passo = teste.getPassos().get(i);

            //escreve o passo
            if (passo.getOperationType() == 1) {//Atribuicao

                builder.append(this.GetAtribuicao(teste, passo, i));

            } else if (passo.getOperationType() == 2) {//Execução 

                builder.append(this.GetExecucao(passo));

            } else if (passo.getOperationType() == 3) {//Verificação

                builder.append(this.GetVerificacao(passo));

            } else if (passo.getOperationType() == 4) {//While

                builder.append(this.GetWhile(teste, passo, i));

            } else if (passo.getOperationType() == 6) {//Fim Laço

                builder.append(this.GetFimLaco(passo));
            }
        }

        builder.append("}");

        return builder.toString();
    }

    private String getParameters(ExercicioCasoTestePasso passo) {

        StringBuilder builder = new StringBuilder();
        if (passo.getObjectName().indexOf("[") > 0 && (passo.getMethodName() == null || passo.getMethodName().isEmpty())) {
            builder.append("");
        } else if (passo.getInputParameters() == null || passo.getInputParameters().isEmpty()) {
            builder.append("()");
        } else {
            builder.append("(");

            int nrParametros = passo.getInputParameters().size();

            for (int i = 0; i < nrParametros; i++) {
                ExercicioCasoTestePassoParametro parametro = passo.getInputParameters().get(i);

                //tratamento para dados
                String value = trataDados(parametro.getObjectType(), parametro.getObjectValue());

                if (i < nrParametros - 1) {
                    builder.append(value + ",");
                } else {
                    builder.append(value + ")");
                }
            }
        }

        return builder.toString();
    }

    private String GetAtribuicao(ExercicioCasoTeste teste, ExercicioCasoTestePasso passo, int passoIndex) {

        StringBuilder builder = new StringBuilder();
        if (passo.getMethodName() == null || passo.getMethodName().isEmpty()) {
            //construtor ou primitivo ou designação
            Boolean isFirstPrevDeclared = isObjectDeclared(teste, passo.getExpectedOutputName(), passoIndex);
            Boolean isSecondPrevDeclared = isObjectDeclared(teste, passo.getObjectName(), passoIndex);

            if (isFirstPrevDeclared && isSecondPrevDeclared) {
                //seta um objeto para outro;
                builder.append(passo.getExpectedOutputName() + " = " + passo.getObjectName() + ";");
            } else if (isSecondPrevDeclared) {
                //passa de um objeto para um novo
                builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = " + passo.getObjectName() + ";");
            } else if (isFirstPrevDeclared) {
                //mexe em um array ou cria um novo objeto
                String objectType = getObjectType(teste, passo.getExpectedOutputName(), passoIndex);

                Boolean needsNew = needsNewAcessor(objectType);

                if (needsNew == true) {
                    builder.append(passo.getExpectedOutputName() + " = new " + passo.getObjectName() + this.getParameters(passo) + ";");
                } else {
                    String value = trataDados(objectType, passo.getObjectName());
                    builder.append(passo.getExpectedOutputName() + " = " + value + ";");
                }
            } else {
                //Cria 2 objetos novos
                Boolean needsNew = needsNewAcessor(passo.getExpectedOutputType());
                if (needsNew == true) {
                    builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = new " + passo.getObjectName() + this.getParameters(passo) + ";");
                } else {
                    String value = trataDados(passo.getExpectedOutputType(), passo.getObjectName());
                    builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = " + value + ";");
                }
            }
        } else {

            //método
            Boolean isFirstPrevDeclared = isObjectDeclared(teste, passo.getExpectedOutputName(), passoIndex);
            Boolean isSecondPrevDeclared = isObjectDeclared(teste, passo.getObjectName(), passoIndex);
            if (isFirstPrevDeclared && isSecondPrevDeclared) {
                builder.append(passo.getExpectedOutputName() + " = " + passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo) + ";");
            } else if (isSecondPrevDeclared) {
                //passa de um objeto para um novo
                builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = " + passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo) + ";");
            } else if (isFirstPrevDeclared) {
                // mexe em um array ou cria um novo objeto
                String objectType = getObjectType(teste, passo.getExpectedOutputName(), passoIndex);

                Boolean needsNew = needsNewAcessor(objectType);

                if (needsNew == true) {
                    builder.append(passo.getExpectedOutputName() + " = new " + passo.getObjectName() + "()" + "." + passo.getMethodName() + this.getParameters(passo) + ";");
                } else {
                    String value = trataDados(objectType, passo.getObjectName());
                    builder.append(passo.getExpectedOutputName() + " = " + value + "." + passo.getMethodName() + this.getParameters(passo) + ";");
                }
            } else {
                //Cria 2 objetos novo
                Boolean needsNew = needsNewAcessor(passo.getExpectedOutputType());
                if (needsNew == true) {
                    builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = new " + passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo) + ";");
                } else {
                    String value = trataDados(passo.getExpectedOutputType(), passo.getObjectName());
                    builder.append(passo.getExpectedOutputType() + " " + passo.getExpectedOutputName() + " = " + value + "." + passo.getMethodName() + this.getParameters(passo) + ";");
                }
            }
        }
        return builder.toString();
    }

    private String GetExecucao(ExercicioCasoTestePasso passo) {
        return passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo) + ";";
    }

    private String GetVerificacao(ExercicioCasoTestePasso passo) {

        String first = "";
        String second = "";
        if ((passo.getExpectedOutputType() == null || passo.getExpectedOutputType().isEmpty()) && (passo.getMethodName() == null || passo.getMethodName().isEmpty())) {
            //compara 2 objetos
            first = passo.getExpectedOutputName();

            if (passo.getObjectName().equals("System.Out")) {
                second = "outContent.toString().replaceAll(\"\\r\\n\", \"\").replaceAll(\"\\r\", \"\")";
            } else {
                second = passo.getObjectName();
            }
        } else if (passo.getMethodName() == null || passo.getMethodName().isEmpty()) {

            //compara valor com objeto
            first = trataDados(passo.getExpectedOutputType(), passo.getExpectedOutputName());
            if (passo.getObjectName().equals("System.Out") && first.startsWith("\"") == false) {
                //tratamento para strings
                first = "\"" + first + "\"";
            }

            if (passo.getObjectName().equals("System.Out")) {
                second = "outContent.toString().replaceAll(\"\\r\\n\", \"\").replaceAll(\"\\r\", \"\")";
            } else {
                second = passo.getObjectName();
            }
        } else {

            //compara valor com retorno do Método
            first = trataDados(passo.getExpectedOutputType(), passo.getExpectedOutputName());
            if (passo.getObjectName().equals("System.Out") && first.startsWith("\"") == false) {
                //tratamento para strings
                first = "\"" + first + "\"";
            }

            second = passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo);
        }

        return "assertEquals(((Object)" + first + "),((Object)" + second + "));";
    }

    private String GetWhile(ExercicioCasoTeste teste, ExercicioCasoTestePasso passo, int passoIndex) {

        String first = "";
        String second = "";
        if ((passo.getExpectedOutputType() == null || passo.getExpectedOutputType().isEmpty()) && (passo.getMethodName() == null || passo.getMethodName().isEmpty())) {
            //compara 2 objetos
            first = passo.getExpectedOutputName();

            if (passo.getObjectName().equals("System.Out")) {
                second = "outContent.toString().replaceAll(\"\\r\\n\", \"\").replaceAll(\"\\r\", \"\")";
            } else {
                second = passo.getObjectName();
            }
        } else if (passo.getMethodName() == null || passo.getMethodName().isEmpty()) {

            //compara valor com objeto
            first = trataDados(passo.getExpectedOutputType(), passo.getExpectedOutputName());
            if (passo.getObjectName().equals("System.Out") && first.startsWith("\"") == false) {
                //tratamento para strings
                first = "\"" + first + "\"";
            }

            if (passo.getObjectName().equals("System.Out")) {
                second = "outContent.toString().replaceAll(\"\\r\\n\", \"\").replaceAll(\"\\r\", \"\")";
            } else {
                second = passo.getObjectName();
            }
        } else {

            //compara valor com retorno do Método
            first = trataDados(passo.getExpectedOutputType(), passo.getExpectedOutputName());
            if (passo.getObjectName().equals("System.Out") && first.startsWith("\"") == false) {
                //tratamento para strings
                first = "\"" + first + "\"";
            }

            second = passo.getObjectName() + "." + passo.getMethodName() + this.getParameters(passo);
        }

        if (passo.getComparisionType() == 1) {//==

            return "while(" + first + ".equals(" + second + ")){";

        } else if (passo.getComparisionType() == 2) {//!=

            return "while(" + first + ".equals(" + second + ")==false){";

        } else if (passo.getComparisionType() == 3) {//>

            return "while(" + first + " > " + second + "){";

        } else if (passo.getComparisionType() == 4) {//>=

            return "while(" + first + " >= " + second + "){";

        } else if (passo.getComparisionType() == 5) {//<

            return "while(" + first + " < " + second + "){";

        } else if (passo.getComparisionType() == 6) {//<=

            return "while(" + first + " <= " + second + "){";
        }

        return "{";
    }

    private String GetFimLaco(ExercicioCasoTestePasso passo) {

        return "}";
    }

    private String getObjectType(ExercicioCasoTeste teste, String objectName, int endIndex) {

        if (objectName.indexOf("[") > 0) {
            objectName = objectName.substring(0, objectName.indexOf("["));
        }

        for (int i = 0; i <= endIndex; i++) {
            ExercicioCasoTestePasso passo = teste.getPassos().get(i);
            if (passo.getExpectedOutputName() != null && passo.getExpectedOutputName().equals(objectName)) {
                return passo.getExpectedOutputType();
            }
        }

        return "";
    }

    private Boolean isObjectDeclared(ExercicioCasoTeste teste, String objectName, int endIndex) {

        if (objectName.indexOf("[") > 0) {
            objectName = objectName.substring(0, objectName.indexOf("["));
        }

        for (int i = 0; i < endIndex; i++) {
            ExercicioCasoTestePasso passo = teste.getPassos().get(i);

            if (passo.getExpectedOutputName() != null && passo.getExpectedOutputName().equals(objectName)) {
                return true;
            }
        }

        return false;
    }

    private Boolean needsNewAcessor(String objectType) {

        switch (objectType.toLowerCase()) {
            case "string":
            case "short":
            case "int":
            case "integer":
            case "long":
            case "double":
            case "float":
            case "boolean":
                return false;

        }
        return true;
    }

    private String trataDados(String dataType, String dataValue) {

        if (dataType.equals("String") && dataValue.startsWith("\"") == false) {
            dataValue = "\"" + dataValue + "\"";
        } else if (dataType.equals("Double") && dataValue.indexOf(".") < 0) {
            dataValue = dataValue + ".0";
        }

        return dataValue;
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
