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
import org.hibernate.Transaction;

/**
 *
 * @author gilvani
 */
public class ExercicioClasseService extends HibernateUtil<ExercicioClasse> {

    public ExercicioClasseService() {
        super(ExercicioClasse.class);
    }

    public List<ExercicioClasse> getAllByIdExercicio(int idExercicio, int idAluno) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from ExercicioClasse where IdExercicio = :idExercicio and IdAluno = :idAluno").addEntity(ExercicioClasse.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);
            List<ExercicioClasse> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
}
