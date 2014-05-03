/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasseValidacao;
import feeper.Data.model.HibernateUtil;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author
 * fabioalves
 */
public class ExercicioClasseValidacaoService extends HibernateUtil<ExercicioClasseValidacao> {
    
    public ExercicioClasseValidacaoService() {
        super(ExercicioClasseValidacao.class);
    }
    
    public List<ExercicioClasseValidacao> getByIdExercicio(int idExercicio)
    {
        SQLQuery query = query("select * from ExercicioClasseValidacao where IdExercicio = :idExercicio").addEntity(ExercicioClasseValidacao.class);
        query.setInteger("idExercicio", idExercicio);
        return query.list();
    }
    
    public boolean exists(int id)
    {
        SQLQuery query = query("select count(*) from ExercicioClasseValidacao where ID = :id");
        query.setInteger("id", id);
        return ((BigInteger)query.uniqueResult()).intValue() >= 1;
    }
    
    public boolean deleteNotIn(int idExercicio, String concatIds)
    {
        Transaction transaction_;
        Session session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            SQLQuery query = session_.createSQLQuery("delete from ExercicioClasseValidacao where ID not in ("+ concatIds +") and IdExercicio = "+ idExercicio);
            
            query.executeUpdate();
            transaction_.commit();
            
            return true;
        } catch (HibernateException e) { 
            transaction_.rollback();
            return false;
        } finally {
            closeSession();
        }
    }
    
}
