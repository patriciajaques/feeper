/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.Pessoa;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

public class ExercicioService extends HibernateUtil<Exercicio> {

    public ExercicioService() {
        super(Exercicio.class);
    }

    public Pessoa getAutor(int idExercicio) {
        Exercicio exercicio = this.getById(idExercicio);
        PessoaService repoPessoa = new PessoaService();
        return repoPessoa.getById(exercicio.getIdAutor());
    }

    public List<Exercicio> getExercioByTurma(int idTurma) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select "
                    + "  E.* "
                    + "from  "
                    + "  TurmaExercicio TE "
                    + "  inner join Exercicio E "
                    + "  on E.ID = TE.IdExercicio "
                    + "  inner join Turma T "
                    + "  on T.ID = TE.IdTurma "
                    + "where "
                    + "  TE.Visivel = 1 "
                    + "  and TE.IdTurma = :idTurma  "
                    + "  and E.Ativo = 1 "
                    + "  and T.Ativo = 1").addEntity(Exercicio.class);
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

    public Exercicio getMeuExercicio(int idTurma, int idExercicio) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select "
                    + "  E.* "
                    + "from  "
                    + "  TurmaExercicio TE "
                    + "  inner join Exercicio E "
                    + "  on E.ID = TE.IdExercicio "
                    + "  inner join Turma T "
                    + "  on T.ID = TE.IdTurma "
                    + "where "
                    + "  TE.Visivel = 1 "
                    + "  and TE.IdExercicio = :idExercicio  "
                    + "  and TE.IdTurma = :idTurma  "
                    + "  and T.Ativo = 1").addEntity(Exercicio.class);
            query.setInteger("idTurma", idTurma);
            query.setInteger("idExercicio", idExercicio);

            List<Exercicio> data = query.list();
            transaction.commit();
            if (data.isEmpty()) {
                return null;
            } else {
                return data.get(0);
            }
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

}
