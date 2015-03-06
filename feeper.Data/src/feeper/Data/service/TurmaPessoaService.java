/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.TurmaPessoa;
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
public class TurmaPessoaService extends HibernateUtil<TurmaPessoa> {

    public TurmaPessoaService() {
        super(TurmaPessoa.class);
    }

    public List<TurmaPessoa> getByIdTurma(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select * from TurmaPessoa where IdTurma = :idTurma").addEntity(TurmaPessoa.class);
            query.setInteger("idTurma", idTurma);
            List<TurmaPessoa> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public List<TurmaPessoa> getByIdPessoa(int idPessoa) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select * from TurmaPessoa where IdPessoa = :idPessoa").addEntity(TurmaPessoa.class);
            query.setInteger("idPessoa", idPessoa);
            List<TurmaPessoa> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean existsByIdTurmaIdPessoa(int idTurma, int idPessoa) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select count(*) from TurmaPessoa where IdTurma = :idTurma and IdPessoa = :idPessoa");
            query.setInteger("idTurma", idTurma);
            query.setInteger("idPessoa", idPessoa);
            Object result = query.uniqueResult();
            transaction.commit();
            return result != null && ((BigInteger) result).intValue() >= 1;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public boolean insertIfNotExist(int idTurma, int idPessoa) {

        boolean exist = existsByIdTurmaIdPessoa(idTurma, idPessoa);

        if (exist == false) {
            Transaction transaction = currentSession().beginTransaction();
            try {
                SQLQuery query = currentSession().createSQLQuery("insert into TurmaPessoa (IdTurma, IdPessoa) values ( :idTurma , :idPessoa )");
                query.setInteger("idTurma", idTurma);
                query.setInteger("idPessoa", idPessoa);

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

            SQLQuery query = currentSession().createSQLQuery("delete from TurmaPessoa where IdTurma = :idTurma ");
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
