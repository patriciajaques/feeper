/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasseAuxiliar;
import feeper.Data.model.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author gilvani
 */
public class ExercicioClasseAuxiliarService extends HibernateUtil<ExercicioClasseAuxiliar> {

    public ExercicioClasseAuxiliarService() {
        super(ExercicioClasseAuxiliar.class);
    }

    public List<ExercicioClasseAuxiliar> getAllByIdExercicio(int idExercicio) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from ExercicioClasseAuxiliar where IdExercicio = :idExercicio").addEntity(ExercicioClasseAuxiliar.class);
            query.setInteger("idExercicio", idExercicio);
            List<ExercicioClasseAuxiliar> data = query.list();
            transaction.commit();

            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean SaveClasses(int idExercicio, List<ExercicioClasseAuxiliar> classes) {

        if (classes == null) {
            return true;
        }

        List<Integer> idsClasses = new ArrayList<Integer>();

        for (int i = 0; i < classes.size(); i++) {

            ExercicioClasseAuxiliar classe = classes.get(i);
            classe.setIdExercicio(idExercicio);

            Integer classeId = classe.getId();
            if (classeId != null && classeId > 0) {
                this.update(classe);
            } else {
                this.insert(classe);
            }
            idsClasses.add(classe.getId());
        }

        return this.deleteNotIn(idExercicio, idsClasses);
    }

    public boolean deleteNotIn(int idExercicio, List<Integer> ids) {

        Session session = currentSession();
        Transaction transaction = session.beginTransaction();

        try {

            SQLQuery query = null;
            if (ids.isEmpty()) {
                query = session.createSQLQuery("delete from ExercicioClasseAuxiliar where IdExercicio = " + idExercicio);
            } else {
                query = session.createSQLQuery("delete from ExercicioClasseAuxiliar where ID not in (" + ids.toString().replace("[", "").replace("]", "") + ") and IdExercicio = " + idExercicio);
            }

            query.executeUpdate();
            transaction.commit();

            return true;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }
}
