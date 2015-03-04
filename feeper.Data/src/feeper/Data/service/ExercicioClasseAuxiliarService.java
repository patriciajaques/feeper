/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasseAuxiliar;
import feeper.Data.model.HibernateUtil;
import static feeper.Data.model.HibernateUtil.currentSession;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
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
        try {

            SQLQuery query = query("select * from ExercicioClasseAuxiliar where IdExercicio = :idExercicio").addEntity(ExercicioClasseAuxiliar.class);
            query.setInteger("idExercicio", idExercicio);
            return query.list();
        } catch (Exception e) {
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
        Transaction transaction_;
        StatelessSession session_;

        session_ = currentSession();
        transaction_ = session_.beginTransaction();

        try {

            SQLQuery query = null;
            if (ids.isEmpty()) {
                query = session_.createSQLQuery("delete from ExercicioClasseAuxiliar where IdExercicio = " + idExercicio);
            } else {
                query = session_.createSQLQuery("delete from ExercicioClasseAuxiliar where ID not in (" + ids.toString().replace("[", "").replace("]", "") + ") and IdExercicio = " + idExercicio);
            }

            query.executeUpdate();
            transaction_.commit();

            return true;
        } catch (HibernateException e) {
            transaction_.rollback();
            return false;
        }
    }
}
