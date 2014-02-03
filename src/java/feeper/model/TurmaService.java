/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Pessoa;
import feeper.entity.Turma;
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
public class TurmaService extends HibernateUtil<Turma> {
    
    public TurmaService() {
        super(Turma.class);
    }
    
    public List<Pessoa> getAlunos(int idTurma)
    {
        SQLQuery query = query("select P.* from TurmaPessoa TP inner join Pessoa P on P.ID = TP.IdPessoa where TP.IdTurma = :idTurma order by P.Nome").addEntity(Pessoa.class);
        query.setInteger("idTurma", idTurma);
        return query.list();
    }
    
    public boolean removeAluno(int idTurma, int idAluno)
    {
        Transaction transaction_;
        Session session_;
        
        session_ = getSession();
        transaction_ = session_.beginTransaction();
        
        try {
            
            SQLQuery query = session_.createSQLQuery("delete from TurmaPessoa where IdTurma = "+ idTurma +" and IdPessoa = "+ idAluno);
            
            query.executeUpdate();
            return true;
            
        } catch (HibernateException e) { 
            transaction_.rollback();
            return false;
        } finally {
            session_.close();
        }
    }
    
}
