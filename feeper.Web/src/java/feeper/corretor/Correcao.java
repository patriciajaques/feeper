/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.corretor;

import feeper.Data.entity.ExercicioClasseValidacao;
import feeper.Data.entity.ExercicioValidacao;
import feeper.Data.entity.Resposta;
import feeper.Data.entity.RespostaClasse;
import feeper.Data.model.EStatusResposta;
import feeper.Data.model.ETipoLog;
import feeper.Data.service.ExercicioClasseValidacaoService;
import feeper.Data.service.ExercicioValidacaoService;
import feeper.Data.service.RespostaService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class Correcao implements Runnable {
    
    int idResposta;
    int timeout;
    File dir;
    
    public Correcao(int idResposta, int timeout)
    {
        this.idResposta = idResposta;
        this.timeout = timeout;
        dir = new File("stage/" + this.idResposta);
    }
    
    public void run(){
    
        // criaçao do diretório stage
        dir.mkdirs(); 
        try {
            
            RespostaService repoResposta = new RespostaService();
            ExercicioValidacaoService repoExercicioValidacao = new ExercicioValidacaoService();
            ExercicioClasseValidacaoService repoExercicioClasseValidacao = new ExercicioClasseValidacaoService();
                        
            //criar e compilar arquivos java
            Language language = null;
            List<RespostaClasse> fontes = repoResposta.getListRespostaClasse(idResposta);
            for (RespostaClasse respostaCodigoFonte : fontes)
                language = new Java(respostaCodigoFonte.getClasse(), timeout, respostaCodigoFonte.getFonte(), dir.getAbsolutePath());
            language.compile();
            
            //verifica se ocorreram erros na compilação
            String errors = compileErrors();
            if(!errors.equals(""))
            { 
                repoResposta.salvarResultadoResposta(idResposta, "Mensagem do Compilador: " + errors, EStatusResposta.ERRO_COMPILACAO, ETipoLog.EXERCICIO_COM_ERRO);
                return;
            }
               
            //buscar ExercicioValidacao
            Resposta resposta = repoResposta.getById(idResposta);
            List<ExercicioValidacao> validacoes = repoExercicioValidacao.getByIdExercicio(resposta.getIdExercicio());

            for (ExercicioValidacao exercicioValidacao : validacoes)
            {
                //criar in.txt
                createFile("in.txt", exercicioValidacao.getEntrada());

                //executa Solution.class e pega o retorno
                language.execute();
                if(language.timedout)
                {
                    repoResposta.salvarResultadoResposta(idResposta, "", EStatusResposta.TEMPO_LIMITE, ETipoLog.EXERCICIO_COM_ERRO);
                    return;
                }
                
                String saida = execMsg();
                if (!saida.trim().equals(prepareString(exercicioValidacao.getSaida()).trim()))
                {
                    repoResposta.salvarResultadoResposta(idResposta, exercicioValidacao.getMensagem(), EStatusResposta.SAIDA_INVALIDA, ETipoLog.EXERCICIO_COM_ERRO);
                    return;
                }
            }

            //buscar ExercicioClasseValidacao
            List<ExercicioClasseValidacao> classeValidacoes = repoExercicioClasseValidacao.getByIdExercicio(resposta.getIdExercicio());
            int contador = 0;
            for (ExercicioClasseValidacao exercicioClasseValidacao : classeValidacoes) {
                contador++;
                
                //renomear Solution.class
                renameFile("Solution.java", "Solution.java.bkp" + contador);
                renameFile("Solution.class", "Solution.class.bkp" + contador);
                
                //criar nova Solution.java
                String classCode = newSolutionClass(exercicioClasseValidacao.getFonte());
                
                //compilar Solution.java (ExercicioClasseValidacao)
                Language classLanguage = new Java("Solution.java", timeout, classCode, dir.getAbsolutePath());
                classLanguage.compile();
                
                //verifica se ocorreram erros na compilação
                String classErrors = compileErrors();
                if(!classErrors.equals(""))
                { 
                    repoResposta.salvarResultadoResposta(idResposta, exercicioClasseValidacao.getMensagemCompilacao() + " - Mensagem do Compilador: " + classErrors, EStatusResposta.ERRO_COMPILACAO, ETipoLog.EXERCICIO_COM_ERRO);
                    return;
                }
                
                //executa Solution.class e pega o retorno
                classLanguage.executeTestClass();
                if(classLanguage.timedout)
                {
                    repoResposta.salvarResultadoResposta(idResposta, "", EStatusResposta.TEMPO_LIMITE, ETipoLog.EXERCICIO_COM_ERRO);
                    return;
                }
                
                //caso não tenha saída cadastrada, segue adiante para próximo teste
                if (exercicioClasseValidacao.getSaida().length() == 0)
                    continue;
                
                String saida = execMsg();
                if (!saida.trim().equals(prepareString(exercicioClasseValidacao.getSaida()).trim()))
                {
                    repoResposta.salvarResultadoResposta(idResposta, exercicioClasseValidacao.getMensagem(), EStatusResposta.SAIDA_INVALIDA, ETipoLog.EXERCICIO_COM_ERRO);
                    return;
                }
            }

            //após passar em todos os testes obtemos o resultado final de sucesso
            //salvar na tabela Resposta o IdStatus
            repoResposta.salvarResultadoResposta(idResposta, "", EStatusResposta.RESOLVIDO, ETipoLog.EXERCICIO_SOLUCIONADO);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // method to return the compiler errors
    public String compileErrors() {
        String line = "";
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(dir.getAbsolutePath() + "/err.txt")));
            while((line = fin.readLine()) != null)
                content.append(line + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().trim();
    }

    // method to return the execution output
    public String execMsg() {
        String line = "";
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(dir.getAbsolutePath() + "/out.txt")));
            while((line = fin.readLine()) != null)
                content.append(line + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().trim();
    }
    
    private void createFile(String file, String contents)
    {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir.getAbsolutePath() + "/" + file, false)));
            out.write(contents);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void renameFile(String fileName, String newFileName)
    {
        try {
            File file = new File(dir.getAbsolutePath() + "/" + fileName);
            if (file.exists())
                file.renameTo(new File(dir.getAbsolutePath() + "/" + newFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String prepareString(String valor)
    {
        return valor.replace("\n\r", "\n").replace("\r\n", "\n").replace("\r", "");
    }
    
    private String newSolutionClass(String mainCode)
    {
        String code = "import java.util.*;\n" +
                    "import java.lang.*;\n" +
                    "import java.io.*;\n" +
                    "class Solution\n" +
                    "{\n" +
                    "    public static void main (String[] args) throws java.lang.Exception\n" +
                    "    {\n" +
                    mainCode +
                    "    }\n" +
                    "}";
        return code;
    }
    
}
