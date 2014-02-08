/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Exercicio;
import feeper.entity.ExercicioValidacao;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.SQLQuery;

public class ExercicioService extends HibernateUtil<Exercicio> {
    
    public ExercicioService() {
        super(Exercicio.class);
    }
    
    public List<ExercicioValidacao> getValidacoes(int idExercicio)
    {
        SQLQuery query = query("select EV.* from ExercicioValidacao EV inner join Exercicio E on E.ID = EV.IdExercicio where E.ID = :idExercicio").addEntity(ExercicioValidacao.class);
        query.setInteger("idExercicio", idExercicio);
        return query.list();
    }
    
    public boolean isMeuExercicio(int idTurma, int idNivelDificuldade, int idExercicio)
    {
        SQLQuery query = query("select " +
                                "  1  " +
                                "from  " +
                                "  TurmaExercicio TE " +
                                "  inner join Exercicio E " +
                                "  on E.ID = TE.IdExercicio " +
                                "  inner join Turma T " +
                                "  on T.ID = TE.IdTurma " +
                                "where " +
                                "  TE.Visivel = 1 " +
                                "  and TE.IdExercicio = :idExercicio  " +
                                "  and TE.IdTurma = :idTurma  " +
                                "  and E.IdNivelDificuldade <= :idNivelDificuldade " +
                                "  and T.Ativo = 1");
        query.setInteger("idTurma", idTurma);
        query.setInteger("idExercicio", idExercicio);
        query.setInteger("idNivelDificuldade", idNivelDificuldade);
        Object result = query.uniqueResult();
        return result != null && ((BigInteger)result).intValue() >= 1;
    }
    
}
