/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.TurmaExercicio;
import feeper.Data.model.HibernateUtil;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

/**
 *
 * @author
 * fabioalves
 */
public class TurmaExercicioService extends HibernateUtil<TurmaExercicio> {
    
    public TurmaExercicioService() {
        super(TurmaExercicio.class);
    }
    
    public List<TurmaExercicio> getByIdTurma(int idTurma)
    {
        SQLQuery query = query("select * from TurmaExercicio where IdTurma = :idTurma").addEntity(TurmaExercicio.class);
        query.setInteger("idTurma", idTurma);
        return query.list();
    }
    
    public List<TurmaExercicio> getByIdExercicio(int idExercicio)
    {
        SQLQuery query = query("select * from TurmaExercicio where IdExercicio = :idExercicio").addEntity(TurmaExercicio.class);
        query.setInteger("idExercicio", idExercicio);
        return query.list();
    }
    
    public boolean existsByIdTurmaIdExercicio(int idTurma, int idExercicio)
    {
        SQLQuery query = query("select count(*) from TurmaExercicio where IdTurma = :idTurma and IdExercicio = :idExercicio");
        query.setInteger("idTurma", idTurma);
        query.setInteger("idExercicio", idExercicio);
        Object result = query.uniqueResult();
        return result != null && ((BigInteger)result).intValue() >= 1;
    }
    
    public boolean insertIfNotExist(int idTurma, int idExercicio)
    {
        Transaction transaction_;
        StatelessSession session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            if (!existsByIdTurmaIdExercicio(idTurma, idExercicio))
            {
                SQLQuery query = query("insert into TurmaExercicio (IdTurma, IdExercicio) values ( :idTurma , :idExercicio )");
                query.setInteger("idTurma", idTurma);
                query.setInteger("idExercicio", idExercicio);
                query.executeUpdate();
            }
            //session_.flush();
            transaction_.commit();
            
            return true;
        } catch (Exception e) {
            transaction_.rollback();
            return false;
        } 
    }
    
    public boolean deleteAllByIdTurma(int idTurma)
    {
        Transaction transaction_;
        StatelessSession session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            
            SQLQuery query = query("delete from TurmaExercicio where IdTurma = :idTurma ");
            query.setInteger("idTurma", idTurma);
            query.executeUpdate();
            
            //session_.flush();
            transaction_.commit();
            
            return true;
            
        } catch (Exception e) {
            transaction_.rollback();
            return false;
        } 
    }
    
}
