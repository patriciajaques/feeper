/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Exercicio;
import feeper.entity.ExercicioValidacao;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
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
    
}
