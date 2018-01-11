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
            return data.intValue();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
}
