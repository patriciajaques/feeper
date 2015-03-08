/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioCasoTeste;
import feeper.Data.entity.ExercicioCasoTestePasso;
import feeper.Data.model.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fabioalves
 */
public class ExercicioCasoTesteService extends HibernateUtil<ExercicioCasoTeste> {

    public ExercicioCasoTesteService() {
        super(ExercicioCasoTeste.class);
    }

    public List<ExercicioCasoTeste> getByIdExercicio(int idExercicio, boolean somenteAtivos) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = null;
            if (somenteAtivos) {
                query = currentSession().createSQLQuery("select * from ExercicioCasoTeste where IdExercicio = :idExercicio and Ativo = 1 order by Ordem, ID").addEntity(ExercicioCasoTeste.class);
            } else {
                query = currentSession().createSQLQuery("select * from ExercicioCasoTeste where IdExercicio = :idExercicio order by Ordem, ID").addEntity(ExercicioCasoTeste.class);
            }
            query.setInteger("idExercicio", idExercicio);

            List<ExercicioCasoTeste> data = query.list();
            transaction.commit();

            ExercicioCasoTestePassoService repoPasso = new ExercicioCasoTestePassoService();
            for (ExercicioCasoTeste casoTeste : data) {
                casoTeste.setPassos(repoPasso.getByIdCasoTeste(casoTeste.getId()));
            }

            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean SaveCasos(int idExercicio, List<ExercicioCasoTeste> casosTeste) {

        if (casosTeste == null) {
            return true;
        }

        List<Integer> idsCasosTeste = new ArrayList<Integer>();
        ExercicioCasoTestePassoService repoPasso = new ExercicioCasoTestePassoService();

        for (int i = 0; i < casosTeste.size(); i++) {

            ExercicioCasoTeste casoTeste = casosTeste.get(i);

            casoTeste.setIdExercicio(idExercicio);

            Integer casoTesteId = casoTeste.getId();
            if (casoTesteId != null && casoTesteId > 0) {
                this.update(casoTeste);
            } else {
                this.insert(casoTeste);
            }
            idsCasosTeste.add(casoTeste.getId());

            List<ExercicioCasoTestePasso> passos = casoTeste.getPassos();
            boolean sucess = repoPasso.SavePassos(casoTeste.getId(), passos);

            if (sucess == false) {
                return false;
            }
        }

        return this.deleteNotIn(idExercicio, idsCasosTeste);
    }

    public boolean deleteNotIn(int idExercicio, List<Integer> ids) {

        Transaction transaction = currentSession().beginTransaction();

        try {

            SQLQuery query = null;
            if (ids.isEmpty()) {
                query = currentSession().createSQLQuery("delete from ExercicioCasoTeste where IdExercicio = " + idExercicio);
            } else {
                query = currentSession().createSQLQuery("delete from ExercicioCasoTeste where ID not in (" + ids.toString().replace("[", "").replace("]", "") + ") and IdExercicio = " + idExercicio);
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
