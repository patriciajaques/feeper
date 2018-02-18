/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasseAuxiliar;
import feeper.Data.entity.ExercicioPontos;
import feeper.Data.model.HibernateUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;


public class ExercicioPontosService extends HibernateUtil<ExercicioPontos> {
    
    public static Integer PONTOS_ACERTO = 100;
    public static Integer PONTOS_ERRO = 5;
    
    public ExercicioPontosService() {
        super(ExercicioPontos.class);
    }    
    
    public Integer getPointsByIdPessoa(int idAluno) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select CAST(sum(pontos)AS SIGNED) from ExercicioPontos where idAluno = :idAluno");
            query.setInteger("idAluno", idAluno);
            BigInteger data =  (BigInteger) query.uniqueResult();
            transaction.commit();
            if(data == null)
                return 0;
            return data.intValue();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    public Integer exercicioPontuacaoIdPessoa(Integer idAluno, Integer idExercicio){
        
        String SQL_QUERY = "select count(*) from contador_solucao_errada where "
                + "idAluno = :idAluno and "
                + "idExercicio = :idExercicio and idstatus in(3,2)";
        
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(SQL_QUERY);
            query.setInteger("idAluno", idAluno);
            query.setInteger("idExercicio", idExercicio);
            BigInteger data =  (BigInteger) query.uniqueResult();
            transaction.commit();
            
            Integer count = 0;
            if(data == null)
                count = 0;
            else
                count = data.intValue();
            
            Integer pontosErro = (count*PONTOS_ERRO);
            if(pontosErro>30)
                pontosErro = 30;
            
            return PONTOS_ACERTO - pontosErro;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
}
