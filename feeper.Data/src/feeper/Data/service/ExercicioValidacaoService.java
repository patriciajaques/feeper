/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioValidacao;
import feeper.Data.model.HibernateUtil;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;


/**
 *
 * @author
 * fabioalves
 */
public class ExercicioValidacaoService extends HibernateUtil<ExercicioValidacao> {
    
    public ExercicioValidacaoService() {
        super(ExercicioValidacao.class);
    }
    
    public List<ExercicioValidacao> getByIdExercicio(int idExercicio)
    {
        SQLQuery query = query("select * from ExercicioValidacao where IdExercicio = :idExercicio order by Ordem, ID").addEntity(ExercicioValidacao.class);
        query.setInteger("idExercicio", idExercicio);
        return query.list();
    }
    
    public boolean exists(int id)
    {
        SQLQuery query = query("select count(*) from ExercicioValidacao where ID = :id");
        query.setInteger("id", id);
        return ((BigInteger)query.uniqueResult()).intValue() >= 1;
    }
    
    public boolean deleteNotIn(int idExercicio, String concatIds)
    {
        Transaction transaction_;
        StatelessSession session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            SQLQuery query = session_.createSQLQuery("delete from ExercicioValidacao where ID not in ("+ concatIds +") and IdExercicio = "+ idExercicio);
            
            query.executeUpdate();
            transaction_.commit();
            
            return true;
        } catch (HibernateException e) { 
            transaction_.rollback();
            return false;
        } 
    }
    
}
