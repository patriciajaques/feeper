/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.corretor;

import feeper.Data.entity.ExercicioValidacao;
import feeper.Data.entity.Resposta;
import feeper.Data.entity.RespostaCodigoFonte;
import feeper.Data.model.EStatusResposta;
import feeper.Data.model.ETipoLog;
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
                        
            //criar e compilar arquivos java
            Language language = null;
            List<RespostaCodigoFonte> fontes = repoResposta.getListRespostaCodigoFonte(idResposta);
            for (RespostaCodigoFonte respostaCodigoFonte : fontes)
                language = new Java(respostaCodigoFonte.getClasse(), timeout, respostaCodigoFonte.getFonte(), dir.getAbsolutePath());
            language.compile();
            
            // verifica se ocorreram erros na compilação
            String errors = compileErrors();
            if(!errors.equals(""))
            { 
                repoResposta.salvarResultadoResposta(idResposta, errors, EStatusResposta.ERRO_COMPILACAO, ETipoLog.EXERCICIO_COM_ERRO);
                return;
            }
               
            //buscar ExercicioValidacao
            Resposta resposta = repoResposta.getById(idResposta);
            List<ExercicioValidacao> validacoes = repoExercicioValidacao.getByIdExercicio(resposta.getIdExercicio());

            boolean sucesso = false;
            
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
                
                sucesso = true;
            }

            //buscar ExercicioClasseValidacao
            //fazer um loop
            //  apagar Solution.class
            //  compilar Solution.java (ExercicioClasseValidacao)
            //  executar Solution.class
            //  erro? salvar na tabela Resposta o IdStatus. Finalizar

            //após todos os testes obtemos o resultado final
            //salvar na tabela Resposta o IdStatus
                
            if (sucesso)
            {
                repoResposta.salvarResultadoResposta(idResposta, "", EStatusResposta.RESOLVIDO, ETipoLog.EXERCICIO_SOLUCIONADO);
            }
            
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
    
    private static String prepareString(String valor)
    {
        return valor.replace("\n\r", "\n").replace("\r\n", "\n").replace("\r", "");
    }
    
}
