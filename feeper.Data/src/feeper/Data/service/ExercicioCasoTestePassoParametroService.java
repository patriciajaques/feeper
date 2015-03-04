/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioCasoTestePassoParametro;
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
public class ExercicioCasoTestePassoParametroService extends HibernateUtil<ExercicioCasoTestePassoParametro> {

    public ExercicioCasoTestePassoParametroService() {
        super(ExercicioCasoTestePassoParametro.class);
    }

    public List<ExercicioCasoTestePassoParametro> getByIdPasso(int idPasso) {
        SQLQuery query = query("select * from ExercicioCasoTestePassoParametro where IdPasso = :idPasso order by Ordem, ID").addEntity(ExercicioCasoTestePassoParametro.class);
        query.setInteger("idPasso", idPasso);
        return query.list();
    }

    public boolean SaveParametros(int idPasso, List<ExercicioCasoTestePassoParametro> parametros) {

        if (parametros == null) {
            return true;
        }

        List<Integer> idsParametros = new ArrayList<Integer>();

        for (int i = 0; i < parametros.size(); i++) {

            ExercicioCasoTestePassoParametro parametro = parametros.get(i);
            parametro.setIdPasso(idPasso);

            Integer parametroId = parametro.getId();
            if (parametroId != null && parametroId > 0) {
                this.update(parametro);
            } else {
                this.insert(parametro);
            }
            idsParametros.add(parametro.getId());
        }

        return this.deleteNotIn(idPasso, idsParametros);
    }

    public boolean deleteNotIn(int idPasso, List<Integer> ids) {
        Transaction transaction_;
        StatelessSession session_;

        session_ = currentSession();
        transaction_ = session_.beginTransaction();

        try {
            SQLQuery query = null;
            if (ids.isEmpty()) {
                query = session_.createSQLQuery("delete from ExercicioCasoTestePassoParametro where IdPasso = " + idPasso);
            } else {
                query = session_.createSQLQuery("delete from ExercicioCasoTestePassoParametro where ID not in (" + ids.toString().replace("[", "").replace("]", "") + ") and IdPasso = " + idPasso);
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
