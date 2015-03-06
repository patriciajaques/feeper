/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 *
 * @author fabioalves
 */
public class TurmaService extends HibernateUtil<Turma> {

    public TurmaService() {
        super(Turma.class);
    }

    public List<Object> getExercicios(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select E.ID, E.Nome, TE.Visivel from TurmaExercicio TE inner join Exercicio E on E.ID = TE.IdExercicio where E.Ativo = 1 and TE.IdTurma = :idTurma order by E.Nome");

            query.addScalar("ID", IntegerType.INSTANCE);
            query.addScalar("Nome", StringType.INSTANCE);
            query.addScalar("Visivel", BooleanType.INSTANCE);
            query.setInteger("idTurma", idTurma);
            List<Object> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public List<Exercicio> getExerciciosNotIn(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select E.* from Exercicio E where not exists (\n"
                    + "	select 1 from TurmaExercicio TE where TE.IdExercicio = E.ID and TE.IdTurma = :idTurma \n"
                    + ") and E.Ativo = 1").addEntity(Exercicio.class);

            query.setInteger("idTurma", idTurma);

            List<Exercicio> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public Pessoa getProfessor(int idTurma) {
        Turma turma = this.getById(idTurma);
        PessoaService repoPessoa = new PessoaService();
        return repoPessoa.getById(turma.getIdProfessor());
    }

    public List<Pessoa> getAlunos(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select P.* from TurmaPessoa TP inner join Pessoa P on P.ID = TP.IdPessoa where P.Ativo = 1 and TP.IdTurma = :idTurma order by P.Nome").addEntity(Pessoa.class);
            query.setInteger("idTurma", idTurma);
            List<Pessoa> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean removeExercicio(int idTurma, int idExercicio) {
        Transaction transaction = currentSession().beginTransaction();

        try {

            SQLQuery query = currentSession().createSQLQuery("delete from TurmaExercicio where IdTurma = " + idTurma + " and IdExercicio = " + idExercicio);

            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public boolean visibilidadeExercicio(int idTurma, int idExercicio) {

        Transaction transaction = currentSession().beginTransaction();

        try {

            SQLQuery query = currentSession().createSQLQuery("update TurmaExercicio set Visivel = NOT(Visivel) where IdTurma = " + idTurma + " and IdExercicio = " + idExercicio);

            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public boolean removeAluno(int idTurma, int idAluno) {
        Transaction transaction = currentSession().beginTransaction();

        try {

            SQLQuery query = currentSession().createSQLQuery("delete from TurmaPessoa where IdTurma = " + idTurma + " and IdPessoa = " + idAluno);

            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public List<Turma> getTurmasByIdPessoa(int idPessoa) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select T.* from Turma T inner join TurmaPessoa TP on T.ID = TP.IdTurma where TP.IdPessoa = :idPessoa and T.Ativo = 1 "
                    + "union all "
                    + "select T.* from Turma T where T.IdProfessor = :idPessoa and T.Ativo = 1 "
                    + "order by Nome").addEntity(Turma.class);
            query.setInteger("idPessoa", idPessoa);
            List<Turma> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean verificaProfessorDoAluno(int idProfessor, int idAluno) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select T.* from Turma T inner join TurmaPessoa TP on TP.IdTurma = T.ID "
                    + "where T.IdProfessor = :idProfessor and TP.IdPessoa = :idAluno and T.Ativo = 1").addEntity(Turma.class);
            query.setInteger("idProfessor", idProfessor);
            query.setInteger("idAluno", idAluno);

            boolean data = query.list().size() > 0;
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

    public List<Object> getGradeResultadosTurma(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select "
                    + "  P.ID as IdPessoa, "
                    + "  P.Nome as NomePessoa, "
                    + "  E.ID as IdExercicio, "
                    + "  E.Nome as NomeExercicio, "
                    + "  RRR.ID as IdSolucao, "
                    + "  RRR.IdStatus as IdStatusSolucao, "
                    + "  RRR.ErrosCount "
                    + "from "
                    + "  Turma T "
                    + "  inner join TurmaPessoa TP "
                    + "  on TP.IdTurma = T.ID "
                    + "  inner join Pessoa P "
                    + "  on P.ID = TP.IdPessoa "
                    + "  inner join TurmaExercicio TE "
                    + "  on TE.IdTurma = T.ID "
                    + "  inner join Exercicio E "
                    + "  on E.ID = TE.IdExercicio "
                    + "  left join ( "
                    + "    select ES.* "
                    + "    from ExercicioSolucao ES "
                    + "    inner join ( "
                    + "      select IdExercicio, IdAluno, max(DataCadastro) as DataCadastro  "
                    + "      from ExercicioSolucao "
                    + "      group by IdExercicio, IdAluno "
                    + "    ) RR "
                    + "    on RR.IdExercicio = ES.IdExercicio "
                    + "    and RR.IdAluno = ES.IdAluno "
                    + "    and RR.DataCadastro = ES.DataCadastro "
                    + "  ) RRR "
                    + "  on RRR.IdExercicio = E.ID "
                    + "  and RRR.IdAluno = P.ID "
                    + "where "
                    + "  T.Ativo = 1 "
                    + "  and P.Ativo = 1 "
                    + "  and T.ID = :idTurma "
                    + "  and TE.Visivel = 1 "
                    + "  and E.Ativo = 1 "
                    + "order by "
                    + "  P.Nome, "
                    + "  E.ID");

            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("NomePessoa", StringType.INSTANCE);
            query.addScalar("IdExercicio", IntegerType.INSTANCE);
            query.addScalar("NomeExercicio", StringType.INSTANCE);
            query.addScalar("IdSolucao", StringType.INSTANCE);
            query.addScalar("IdStatusSolucao", IntegerType.INSTANCE);
            query.addScalar("ErrosCount", IntegerType.INSTANCE);

            query.setInteger("idTurma", idTurma);

            List<Object> data = query.list();
            transaction.commit();
            return data;

        } catch (HibernateException e) {
            transaction.rollback();
            return null;
        }
    }

    public List<Object> getGradeResultadosAluno(int idTurma, int idAluno) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select "
                    + "  P.ID as IdPessoa, "
                    + "  P.Nome as NomePessoa, "
                    + "  E.ID as IdExercicio, "
                    + "  E.Nome as NomeExercicio, "
                    + "  RRR.ID as IdSolucao, "
                    + "  RRR.IdStatus as IdStatusSolucao, "
                    + "  RRR.ErrosCount "
                    + "from "
                    + "  Turma T "
                    + "  inner join TurmaPessoa TP "
                    + "  on TP.IdTurma = T.ID "
                    + "  inner join Pessoa P "
                    + "  on P.ID = TP.IdPessoa "
                    + "  inner join TurmaExercicio TE "
                    + "  on TE.IdTurma = T.ID "
                    + "  inner join Exercicio E "
                    + "  on E.ID = TE.IdExercicio "
                    + "  left join ( "
                    + "    select ES.* "
                    + "    from ExercicioSolucao ES "
                    + "    inner join ( "
                    + "      select IdExercicio, IdAluno, max(DataCadastro) as DataCadastro  "
                    + "      from ExercicioSolucao "
                    + "      group by IdExercicio, IdAluno "
                    + "    ) RR "
                    + "    on RR.IdExercicio = ES.IdExercicio "
                    + "    and RR.IdAluno = ES.IdAluno "
                    + "    and RR.DataCadastro = ES.DataCadastro "
                    + "  ) RRR "
                    + "  on RRR.IdExercicio = E.ID "
                    + "  and RRR.IdAluno = P.ID "
                    + "where "
                    + "  T.Ativo = 1 "
                    + "  and P.Ativo = 1 "
                    + "  and T.ID = :idTurma "
                    + "  and P.ID = :idAluno "
                    + "  and TE.Visivel = 1 "
                    + "  and E.Ativo = 1 "
                    + "order by "
                    + "  P.Nome, "
                    + "  E.ID");

            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("NomePessoa", StringType.INSTANCE);
            query.addScalar("IdExercicio", IntegerType.INSTANCE);
            query.addScalar("NomeExercicio", StringType.INSTANCE);
            query.addScalar("IdSolucao", IntegerType.INSTANCE);
            query.addScalar("IdStatusSolucao", IntegerType.INSTANCE);
            query.addScalar("ErrosCount", IntegerType.INSTANCE);

            query.setInteger("idTurma", idTurma);
            query.setInteger("idAluno", idAluno);

            List<Object> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean enviarConvites(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            PessoaService repoPessoa = new PessoaService();

            SQLQuery query = currentSession().createSQLQuery("select P.* from TurmaPessoa TP inner join Pessoa P on P.ID = TP.IdPessoa and TP.IdTurma = :idTurma ").addEntity(Pessoa.class);
            query.setInteger("idTurma", idTurma);
            List<Pessoa> alunos = query.list();
            transaction.commit();
            boolean retorno = false;

            for (Pessoa aluno : alunos) {
                retorno = repoPessoa.enviarConvite(aluno);
            }
            return retorno;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

}
