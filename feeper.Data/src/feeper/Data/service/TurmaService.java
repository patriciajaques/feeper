/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.Util;
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
    
    public List<Object> getExercicios(int idTurma)
    {
        SQLQuery query = query("select E.ID, E.Nome, TE.Visivel from TurmaExercicio TE inner join Exercicio E on E.ID = TE.IdExercicio where E.Ativo = 1 and TE.IdTurma = :idTurma order by E.Nome");
        
        query.addScalar("ID", Hibernate.INTEGER);
        query.addScalar("Nome", Hibernate.STRING);
        query.addScalar("Visivel", Hibernate.BOOLEAN);
        
        query.setInteger("idTurma", idTurma);
        
        return query.list();
    }
    
    public List<Exercicio> getExerciciosNotIn(int idTurma)
    {
        SQLQuery query = query("select E.* from Exercicio E where not exists (\n" +
                                "	select 1 from TurmaExercicio TE where TE.IdExercicio = E.ID and TE.IdTurma = :idTurma \n" +
                                ") and E.Ativo = 1").addEntity(Exercicio.class);
        
        query.setInteger("idTurma", idTurma);
        
        return query.list();
    }
    
    public List<Pessoa> getAlunos(int idTurma)
    {
        SQLQuery query = query("select P.* from TurmaPessoa TP inner join Pessoa P on P.ID = TP.IdPessoa where P.Ativo = 1 and TP.IdTurma = :idTurma order by P.Nome").addEntity(Pessoa.class);
        query.setInteger("idTurma", idTurma);
        return query.list();
    }
    
    public boolean removeExercicio(int idTurma, int idExercicio)
    {
        Transaction transaction_;
        Session session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            
            SQLQuery query = session_.createSQLQuery("delete from TurmaExercicio where IdTurma = "+ idTurma +" and IdExercicio = "+ idExercicio);
            
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
    
    public boolean visibilidadeExercicio(int idTurma, int idExercicio)
    {
        Transaction transaction_;
        Session session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            
            SQLQuery query = session_.createSQLQuery("update TurmaExercicio set Visivel = NOT(Visivel) where IdTurma = " + idTurma + " and IdExercicio = " + idExercicio);
            
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
    
    public boolean removeAluno(int idTurma, int idAluno)
    {
        Transaction transaction_;
        Session session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            
            SQLQuery query = session_.createSQLQuery("delete from TurmaPessoa where IdTurma = "+ idTurma +" and IdPessoa = "+ idAluno);
            
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
    
    public boolean enviarConvites(int idTurma)
    {
        try {
            
            PessoaService repoPessoa = new PessoaService();
            
            SQLQuery query = query("select P.* from TurmaPessoa TP inner join Pessoa P on P.ID = TP.IdPessoa and TP.IdTurma = :idTurma ").addEntity(Pessoa.class);
            query.setInteger("idTurma", idTurma);
            List<Pessoa> alunos = query.list();
            
            boolean retorno = false;
            
            for (Pessoa aluno : alunos) {
                retorno = repoPessoa.enviarConvite(aluno);
            }
            return retorno;
            
        } catch (Exception e) {
            return false;
        }
    }
    
}
