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
        SQLQuery query = query("select * from TurmaPessoa where IdTurma = :idTurma").addEntity(TurmaPessoa.class);
        query.setInteger("idTurma", idTurma);
        return query.list();
    }

    public List<TurmaPessoa> getByIdPessoa(int idPessoa) {
        SQLQuery query = query("select * from TurmaPessoa where IdPessoa = :idPessoa").addEntity(TurmaPessoa.class);
        query.setInteger("idPessoa", idPessoa);
        return query.list();
    }

    public boolean existsByIdTurmaIdPessoa(int idTurma, int idPessoa) {
        SQLQuery query = query("select count(*) from TurmaPessoa where IdTurma = :idTurma and IdPessoa = :idPessoa");
        query.setInteger("idTurma", idTurma);
        query.setInteger("idPessoa", idPessoa);
        Object result = query.uniqueResult();
        return result != null && ((BigInteger) result).intValue() >= 1;
    }

    public boolean insertIfNotExist(int idTurma, int idPessoa) {
        Session session = currentSession();
        Transaction transaction = session.beginTransaction();

        try {
            if (!existsByIdTurmaIdPessoa(idTurma, idPessoa)) {
                SQLQuery query = query("insert into TurmaPessoa (IdTurma, IdPessoa) values ( :idTurma , :idPessoa )");
                query.setInteger("idTurma", idTurma);
                query.setInteger("idPessoa", idPessoa);

                query.executeUpdate();
            }
            //session_.flush();
            transaction.commit();

            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean deleteAllByIdTurma(int idTurma) {
        Session session = currentSession();
        Transaction transaction = session.beginTransaction();

        try {

            SQLQuery query = query("delete from TurmaPessoa where IdTurma = :idTurma ");
            query.setInteger("idTurma", idTurma);
            query.executeUpdate();

            transaction.commit();

            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

}
