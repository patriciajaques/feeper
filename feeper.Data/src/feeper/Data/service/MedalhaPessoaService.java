/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;
import feeper.Data.entity.Medalha;
import feeper.Data.entity.MedalhaPessoa;
import feeper.Data.model.HibernateUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/*
*  Cada medalha tem 3 niveis, bronze, parata e ouro
*/

public class MedalhaPessoaService extends HibernateUtil<MedalhaPessoa> {
    
    PessoaService pessoaService;
    MedalhaService medalhaService;
    
    public MedalhaPessoaService() {
        super(MedalhaPessoaService.class);
        this.medalhaService = new MedalhaService();
    }    
    
    /*
    * Definir o nivel de medalha do Aluno
    * Como existem diversas medalhas que o aluno pode perder como por exemplo ranking o nivel pode alterar 
    * nesse caso não deve ser retirado a medalha.
    */
    private void setNivelMedalha(Integer idAluno, Integer idMedalha, Integer nivel){
        MedalhaPessoa medalha = findByIdAlunoAndIdMedalha(idAluno, idMedalha);
        if(medalha != null){
            if(medalha.getNivel() < nivel){
                medalha.setNivel(nivel);
                update(medalha);
            }
        }else{
            medalha = new MedalhaPessoa(idMedalha, idAluno, nivel);
            insert(medalha);
        }
    }
 
    // Encontra a medalha pro aluno caso contrario retorna NULL
    private MedalhaPessoa findByIdAlunoAndIdMedalha(Integer idAluno, Integer idMedalha){
        String SQL = "select *from medalhapessoas  cc where cc.idMedalha = :idMedalha and cc.idPessoa = :idPessoa";
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(SQL).addEntity(MedalhaPessoa.class);
            query.setInteger("idPessoa", idAluno);
            query.setInteger("idMedalha", idMedalha);
            query.setMaxResults(1);
            List<MedalhaPessoa> list = query.list();
            transaction.commit();
            return list.size()>0?list.get(0):null;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    // Recupera Medalhas do Aluno 
    public List<MedalhaPessoa> findByIdAluno(Integer idAluno){
        String SQL = "select *from medalhapessoas  cc where cc.idPessoa = :idPessoa order by idMedalha asc";
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(SQL).addEntity(MedalhaPessoa.class);
            query.setInteger("idPessoa", idAluno);
            List<MedalhaPessoa> list = query.list();
            transaction.commit();
            return generateNoMedal(list);
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    /*
    Notificao recebida apois finalizar um exercicio, 
    deve se utilizar eventos futuramente.
    */
    public void notifyEnvioExercicio(Integer idAluno){
        medalhaExerciciosConcluidos(idAluno);
        medalhaExerciciosSemErros(idAluno);
        medalhaExerciciosEnviados(idAluno);
        medalhaPontuacaoObtida(idAluno);
        medalhaRankingMelhorTurma(idAluno);
        medalhaRankingMelhordoFeeper(idAluno);
    }
    
    
    /*
    Count auxiliar para fornecer medalhas, passar ID do aluno e SQL a ser executado com 
    @param :idAluno
    */
    private Integer countByIdAlunoAndSQL(int idAluno, String SQL) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(SQL);
            query.setInteger("idAluno", idAluno);
            BigInteger data =  (BigInteger) query.uniqueResult();
            transaction.commit();
            return data.intValue();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    
    ////////////////////////////////
    // Medalha de Login 
    // Conta os Logs - 
    ///////////////////////////////
    private static String SQL_LOGIN = "select count(*) from log ll where ll.IdTipoLog = 1 and IdPessoa = :idAluno";
    public void medalhaLogin(Integer idAluno){
        Integer counter = countByIdAlunoAndSQL(idAluno, SQL_LOGIN);
        Integer nivel = 0;
        if(counter > 0 && counter <3){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter >= 3 && counter < 10){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter >=10){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.LOGIN, nivel);
        }
        medalhaAtividadeDiaria(idAluno);
    }
    
    //////////////////////////////////
    // Medalha de Exercicios Concluidos
    // Conta Sucessos 4
    //////////////////////////////////
    private static String SQL_EXERCICIOS_CONCLUIDOS = "select count(distinct(IdExercicio)) from exerciciosolucao where IdStatus = 4 and IdAluno = :idAluno";
    public void medalhaExerciciosConcluidos(Integer idAluno){
        Integer counter = countByIdAlunoAndSQL(idAluno, SQL_EXERCICIOS_CONCLUIDOS);
        Integer nivel = 0;
        if(counter > 0 && counter <5){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter >= 5 && counter < 10){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter >=10){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.EXERCICIOS_CONCLUIDOS, nivel);
        }
    }
    
    //////////////////////////////////
    // Medalha de Exercicios Enviados
    // Conta numero de exercicios enviados
    //////////////////////////////////
    private static String SQL_EXERCICIOS_ENVIADOS = "select count(*) from exerciciosolucao dd where dd.IdAluno = :idAluno";
    public void medalhaExerciciosEnviados(Integer idAluno){
        Integer counter = countByIdAlunoAndSQL(idAluno, SQL_EXERCICIOS_ENVIADOS);
        Integer nivel = 0;
        if(counter > 0 && counter <10){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter >= 10 && counter < 25){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter >=25){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.EXERCICIOS_ENVIADOS, nivel);
        }
    }
    //////////////////////////////////
    // 4 - Medalha de Exercicios Sem erros
    // Conta numero de exercicios que estão corretos e nunca tiveram erros
    //////////////////////////////////
    private static String SQL_EXERCICIOS_SEM_ERROS = 
            "select count(distinct(idExercicio)) from exerciciosolucao dd \n" +
            "where dd.IdAluno = :idAluno \n" +
            "and not exists \n" +
            "(\n" +
            "select *from exerciciosolucao ee where 1=1\n" +
            "and ee.IdAluno = dd.IdAluno \n" +
            "and ee.IdExercicio = dd.idExercicio\n" +
            "and ee.idStatus = 2\n" +
            ")";
    public void medalhaExerciciosSemErros(Integer idAluno){
        Integer counter = countByIdAlunoAndSQL(idAluno, SQL_EXERCICIOS_SEM_ERROS);
        Integer nivel = 0;
        if(counter > 0 && counter <3){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter >= 3 && counter < 7){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter >=7){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.EXERCICIOS_SEM_ERROS, nivel);
        }
    }
    
    //////////////////////////////////
    // 5 --- Medalha de Login Repitido
    // Conta numero de logins repetidos // melhorar para colocar num SQL
    //////////////////////////////////
    private static String SQL_ATIVIDADE_DIARIA = 
            "select \n" +
"CAST(DataCadastro AS DATE) as dd\n" +
"from log ee\n" +
"where ee.idPessoa = :idAluno and IdTipoLog = 1\n" +
"group by DAY(DataCadastro)\n" +
"order by DataCadastro asc";
    public void medalhaAtividadeDiaria(Integer idAluno){
        Integer counter = countAtividadeDiaria(idAluno, SQL_ATIVIDADE_DIARIA);
        Integer nivel = 0;
        if(counter > 0 && counter <3){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter >= 3 && counter < 7){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter >=7){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.ATIVIDADE_DIARIA, nivel);
        }
    }
    
    private Integer countAtividadeDiaria(int idAluno, String SQL) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(SQL);
            query.setInteger("idAluno", idAluno);
            List<Date> list = query.list();
            transaction.commit();
            
            Integer maxCount = 1;
            Integer count = 1;
            // Counta dias
            if(list.size()>1){
                for(int i = 1; i<list.size();i++){
                    long diff = list.get(i).getTime() - list.get(i-1).getTime();
                    long days =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);    
                    if(days == 1){
                        count++;
                    }else{
                        maxCount = count;
                        count = 1;
                    }
                    
                }
            }
            
            return maxCount;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    
    ///////////////////////////////////
    // 6 --- Medalha De Pontuação Obtida
    // Pontuacao Obtida fornece medalha
    //////////////////////////////////
    
    public void medalhaPontuacaoObtida(Integer idAluno){
        ExercicioPontosService exercicioPontosService = new ExercicioPontosService();
        Integer counter = exercicioPontosService.getPointsByIdPessoa(idAluno);
        Integer nivel = 0;
        if(counter > 300 && counter <1000){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter >= 1000 && counter < 2000){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter >=2000){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.PONTUACAO_OBTIDA, nivel);
        }
    }
    
    
    
   //////////////////////////////////
    // 7 --- Ranking melhor da Turma, pega melhor posicao do aluno em diferentes turmas.
    // Conta numero de exercicios que estão corretos e nunca tiveram erros
    //////////////////////////////////
    private static String SQL_RANKING_MELHOR_DA_TURMA = 
            "select min(ranking)+1 from ranking_local_leaderboard where idAluno = :idAluno";
    public void medalhaRankingMelhorTurma(Integer idAluno){
        Integer counter = countByIdAlunoAndSQL(idAluno, SQL_RANKING_MELHOR_DA_TURMA);
        Integer nivel = 0;
        if(counter > 3 && counter <=10){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter > 1 && counter <= 3){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter == 1){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.RANKING_MELHOR_DA_TURMA, nivel);
        }
    } 
   
    //////////////////////////////////
    // 8 --- Medalha de Exercicios Sem erros
    // Conta numero de exercicios que estão corretos e nunca tiveram erros
    //////////////////////////////////
    private static String SQL_RANKING_MELHOR_DO_FEEPER = 
            "select (count+1) from ranking_global_leaderboard rgl where rgl.idAluno = :idAluno";
    public void medalhaRankingMelhordoFeeper(Integer idAluno){
        Integer counter = countByIdAlunoAndSQL(idAluno, SQL_RANKING_MELHOR_DO_FEEPER);
        Integer nivel = 0;
        if(counter > 3 && counter <=10){
            nivel = Medalha.NIVEL_BRONZE;
        }else if(counter > 1 && counter <= 3){
            nivel = Medalha.NIVEL_PRATA;
        }else if(counter == 1){
            nivel  = Medalha.NIVEL_OURO;
        }
        if(nivel > 0){
            setNivelMedalha(idAluno, Medalha.RANKING_MELHOR_DO_FEEPER, nivel);
        }
    } 
    
    
    public List<MedalhaPessoa> listMedalhas(Integer idAluno){
        String SQL = "select *from medalhapessoas where idPessoa = :idAluno order by idMedalha asc";
        
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(SQL).addEntity(MedalhaPessoa.class);
            query.setInteger("idAluno", idAluno);
            List<MedalhaPessoa> list = query.list();
            transaction.commit();
            
            
            return generateNoMedal(list);
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    public List<MedalhaPessoa> generateNoMedal(List<MedalhaPessoa> list){
        
        ArrayList<Integer> lista = new ArrayList<>();
        lista.add(1);lista.add(2);lista.add(3);lista.add(4);
        lista.add(5);lista.add(6);lista.add(7);lista.add(8); 
        for(int i =0;i<lista.size();i++){
            if(
                    list.size()<=i  ||
                    lista.get(i) != list.get(i).getIdMedalha()
                    ){
                list.add(i, new MedalhaPessoa(lista.get(i), 0, 0));
            }
        }
        
        for(MedalhaPessoa medalha: list){
            Medalha med = medalhaService.findByIdMedalhaAndNivel(medalha.getIdMedalha(), medalha.getNivel());
            medalha.setMedalha(med);
        }
        
        return list;
    }
    
    
    
}
    
    
    
    
    
    
    
    
    
    
    
   

