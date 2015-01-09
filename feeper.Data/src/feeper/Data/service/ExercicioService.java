/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Exercicio;
import feeper.Data.entity.ExercicioClasseValidacao;
import feeper.Data.entity.ExercicioValidacao;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.Turma;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

public class ExercicioService extends HibernateUtil<Exercicio> {

    public ExercicioService() {
        super(Exercicio.class);
    }

    public Pessoa getAutor(int idExercicio) {
        Exercicio exercicio = this.getById(idExercicio);
        PessoaService repoPessoa = new PessoaService();
        return repoPessoa.getById(exercicio.getIdAutor());
    }

    public List<ExercicioValidacao> getValidacoes(int idExercicio) {
        SQLQuery query = query("select EV.* from ExercicioValidacao EV inner join Exercicio E on E.ID = EV.IdExercicio where E.ID = :idExercicio order by EV.Ordem").addEntity(ExercicioValidacao.class);
        query.setInteger("idExercicio", idExercicio);
        return query.list();
    }

    public List<ExercicioClasseValidacao> getClassesValidacao(int idExercicio) {
        SQLQuery query = query("select EV.* from ExercicioClasseValidacao EV inner join Exercicio E on E.ID = EV.IdExercicio where E.ID = :idExercicio order by EV.Ordem").addEntity(ExercicioClasseValidacao.class);
        query.setInteger("idExercicio", idExercicio);
        return query.list();
    }

    public List<Exercicio> getExercioByTurma(int idTurma) {
        try {

            SQLQuery query = query("select "
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

            return query.list();

        } catch (Exception e) {
            return null;
        }
    }

    public Exercicio getMeuExercicio(int idTurma, int idExercicio) {
        try {
            SQLQuery query = query("select "
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

            return (Exercicio) query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }

}
