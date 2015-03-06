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
import org.hibernate.Transaction;

/**
 *
 * @author fabioalves
 */
public class TurmaExercicioService extends HibernateUtil<TurmaExercicio> {

    public TurmaExercicioService() {
        super(TurmaExercicio.class);
    }

    public List<TurmaExercicio> getByIdTurma(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select * from TurmaExercicio where IdTurma = :idTurma").addEntity(TurmaExercicio.class);
            query.setInteger("idTurma", idTurma);
            List<TurmaExercicio> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public List<TurmaExercicio> getByIdExercicio(int idExercicio) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select * from TurmaExercicio where IdExercicio = :idExercicio").addEntity(TurmaExercicio.class);
            query.setInteger("idExercicio", idExercicio);
            List<TurmaExercicio> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean existsByIdTurmaIdExercicio(int idTurma, int idExercicio) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select count(*) from TurmaExercicio where IdTurma = :idTurma and IdExercicio = :idExercicio");
            query.setInteger("idTurma", idTurma);
            query.setInteger("idExercicio", idExercicio);
            Object result = query.uniqueResult();
            transaction.commit();
            return result != null && ((BigInteger) result).intValue() >= 1;

        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public boolean insertIfNotExist(int idTurma, int idExercicio) {

        boolean exists = existsByIdTurmaIdExercicio(idTurma, idExercicio);

        if (exists == false) {
            Transaction transaction = currentSession().beginTransaction();
            try {
                SQLQuery query = currentSession().createSQLQuery("insert into TurmaExercicio (IdTurma, IdExercicio) values ( :idTurma , :idExercicio )");
                query.setInteger("idTurma", idTurma);
                query.setInteger("idExercicio", idExercicio);
                query.executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.err.println(e.fillInStackTrace());
                return false;
            }
        }

        return true;

    }

    public boolean deleteAllByIdTurma(int idTurma) {

        Transaction transaction = currentSession().beginTransaction();

        try {

            SQLQuery query = currentSession().createSQLQuery("delete from TurmaExercicio where IdTurma = :idTurma ");
            query.setInteger("idTurma", idTurma);
            query.executeUpdate();

            transaction.commit();

            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

}
