/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
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
        SQLQuery query = query("select P.* from TurmaPessoa TP inner join Pessoa P on P.ID = TP.IdPessoa where P.Ativo = 1 and TP.IdTurma = :idTurma order by P.Nome").addEntity(Pessoa.class);
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
    
    public List<Turma> getTurmasByIdPessoa(int idPessoa)
    {
        SQLQuery query = query("select T.* from Turma T inner join TurmaPessoa TP on T.ID = TP.IdTurma where TP.IdPessoa = :idPessoa and T.Ativo = 1 " +
                                "union all " +
                                "select T.* from Turma T where T.IdProfessor = :idPessoa and T.Ativo = 1 " +
                                "order by Nome").addEntity(Turma.class);
        query.setInteger("idPessoa", idPessoa);
        return query.list();
    }
    
    public boolean verificaProfessorDoAluno(int idProfessor, int idAluno)
    {
        try {
            
            SQLQuery query = query("select T.* from Turma T inner join TurmaPessoa TP on TP.IdTurma = T.ID " +
                                    "where T.IdProfessor = :idProfessor and TP.IdPessoa = :idAluno and T.Ativo = 1").addEntity(Turma.class);
            query.setInteger("idProfessor", idProfessor);
            query.setInteger("idAluno", idAluno);
            
            return query.list().size() > 0;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Object> getGradeResultadosTurma(int idTurma)
    {
        try {
            
            SQLQuery query = query("select " +
                                    "  P.ID as IdPessoa, " +
                                    "  P.Nome as NomePessoa, " +
                                    "  E.ID as IdExercicio, " +
                                    "  E.Nome as NomeExercicio, " +
                                    "  RRR.ID as IdResposta, " +
                                    "  RRR.IdStatus as IdStatusResposta " +
                                    "from " +
                                    "  Turma T " +
                                    "  inner join TurmaPessoa TP " +
                                    "  on TP.IdTurma = T.ID " +
                                    "  inner join Pessoa P " +
                                    "  on P.ID = TP.IdPessoa " +
                                    "  inner join TurmaExercicio TE " +
                                    "  on TE.IdTurma = T.ID " +
                                    "  inner join Exercicio E " +
                                    "  on E.ID = TE.IdExercicio " +
                                    "  left join ( " +
                                    "    select R.* from Resposta R " +
                                    "    inner join ( " +
                                    "      select IdExercicio, IdAutor, max(DataCadastro) as DataCadastro  " +
                                    "      from Resposta " +
                                    "      group by IdExercicio, IdAutor " +
                                    "    ) RR " +
                                    "    on RR.IdExercicio = R.IdExercicio " +
                                    "    and RR.IdAutor = R.IdAutor " +
                                    "    and RR.DataCadastro = R.DataCadastro " +
                                    "  ) RRR " +
                                    "  on RRR.IdExercicio = E.ID " +
                                    "  and RRR.IdAutor = P.ID " +
                                    "where " +
                                    "  T.Ativo = 1 " +
                                    "  and P.Ativo = 1 " +
                                    "  and T.ID = :idTurma " +
                                    "  and TE.Visivel = 1 " +
                                    "  and E.Ativo = 1 " +
                                    "order by " +
                                    "  P.Nome, " +
                                    "  E.ID");
            
            query.addScalar("IdPessoa", Hibernate.INTEGER);
            query.addScalar("NomePessoa", Hibernate.STRING);
            query.addScalar("IdExercicio", Hibernate.INTEGER);
            query.addScalar("NomeExercicio", Hibernate.STRING);
            query.addScalar("IdResposta", Hibernate.INTEGER);
            query.addScalar("IdStatusResposta", Hibernate.INTEGER);
            
            query.setInteger("idTurma", idTurma);
            
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Object> getGradeResultadosAluno(int idTurma, int idAluno)
    {
        try {
            
            SQLQuery query = query("select " +
                                    "  P.ID as IdPessoa, " +
                                    "  P.Nome as NomePessoa, " +
                                    "  E.ID as IdExercicio, " +
                                    "  E.Nome as NomeExercicio, " +
                                    "  RRR.ID as IdResposta, " +
                                    "  RRR.IdStatus as IdStatusResposta " +
                                    "from " +
                                    "  Turma T " +
                                    "  inner join TurmaPessoa TP " +
                                    "  on TP.IdTurma = T.ID " +
                                    "  inner join Pessoa P " +
                                    "  on P.ID = TP.IdPessoa " +
                                    "  inner join TurmaExercicio TE " +
                                    "  on TE.IdTurma = T.ID " +
                                    "  inner join Exercicio E " +
                                    "  on E.ID = TE.IdExercicio " +
                                    "  left join ( " +
                                    "    select R.* from Resposta R " +
                                    "    inner join ( " +
                                    "      select IdExercicio, IdAutor, max(DataCadastro) as DataCadastro  " +
                                    "      from Resposta " +
                                    "      group by IdExercicio, IdAutor " +
                                    "    ) RR " +
                                    "    on RR.IdExercicio = R.IdExercicio " +
                                    "    and RR.IdAutor = R.IdAutor " +
                                    "    and RR.DataCadastro = R.DataCadastro " +
                                    "  ) RRR " +
                                    "  on RRR.IdExercicio = E.ID " +
                                    "  and RRR.IdAutor = P.ID " +
                                    "where " +
                                    "  T.Ativo = 1 " +
                                    "  and P.Ativo = 1 " +
                                    "  and T.ID = :idTurma " +
                                    "  and P.ID = :idAluno " +
                                    "  and TE.Visivel = 1 " +
                                    "  and E.Ativo = 1 " +
                                    "order by " +
                                    "  P.Nome, " +
                                    "  E.ID");
            
            query.addScalar("IdPessoa", Hibernate.INTEGER);
            query.addScalar("NomePessoa", Hibernate.STRING);
            query.addScalar("IdExercicio", Hibernate.INTEGER);
            query.addScalar("NomeExercicio", Hibernate.STRING);
            query.addScalar("IdResposta", Hibernate.INTEGER);
            query.addScalar("IdStatusResposta", Hibernate.INTEGER);
            
            query.setInteger("idTurma", idTurma);
            query.setInteger("idAluno", idAluno);
            
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
}
