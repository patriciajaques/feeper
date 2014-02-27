/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.Exercicio;
import feeper.entity.ExercicioValidacao;
import feeper.model.HibernateUtil;
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
    
    public Exercicio getMeuExercicio(int idTurma, int idExercicio)
    {
        try {
            SQLQuery query = query("select " +
                                    "  E.* " +
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
                                    "  and T.Ativo = 1").addEntity(Exercicio.class);
            query.setInteger("idTurma", idTurma);
            query.setInteger("idExercicio", idExercicio);

            return (Exercicio)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
}
