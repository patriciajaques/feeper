/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasse;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author gilvani
 */
public class ExercicioClasseService extends HibernateUtil<ExercicioClasse> {

    public ExercicioClasseService() {
        super(ExercicioClasse.class);
    }

    public List<ExercicioClasse> getAllByIdExercicio(int idExercicio, int idAluno) {
        try {

            SQLQuery query = query("select * from ExercicioClasse where IdExercicio = :idExercicio and IdAluno = :idAluno").addEntity(ExercicioClasse.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
}
