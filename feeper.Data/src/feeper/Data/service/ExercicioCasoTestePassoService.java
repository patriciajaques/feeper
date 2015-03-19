/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioCasoTestePasso;
import feeper.Data.entity.ExercicioCasoTestePassoParametro;
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
public class ExercicioCasoTestePassoService extends HibernateUtil<ExercicioCasoTestePasso> {

    public ExercicioCasoTestePassoService() {
        super(ExercicioCasoTestePasso.class);
    }

    public List<ExercicioCasoTestePasso> getByIdCasoTeste(int idCasoTeste) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select * from ExercicioCasoTestePasso where IdCasoTeste = :idCasoTeste order by Ordem, ID").addEntity(ExercicioCasoTestePasso.class);
            query.setInteger("idCasoTeste", idCasoTeste);

            List<ExercicioCasoTestePasso> data = query.list();
            transaction.commit();

            ExercicioCasoTestePassoParametroService repoParametro = new ExercicioCasoTestePassoParametroService();
            for (ExercicioCasoTestePasso passo : data) {
                passo.setInputParameters(repoParametro.getByIdPasso(passo.getId()));
            }

            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean SavePassos(int idCasoTeste, List<ExercicioCasoTestePasso> passos) {

        if (passos == null) {
            return true;
        }
        List<Integer> idsPassos = new ArrayList<Integer>();
        ExercicioCasoTestePassoParametroService repoParametro = new ExercicioCasoTestePassoParametroService();

        for (int i = 0; i < passos.size(); i++) {

            ExercicioCasoTestePasso passo = passos.get(i);
            passo.setIdCasoTeste(idCasoTeste);

            Integer passoId = passo.getId();
            if (passoId != null && passoId > 0) {
                this.update(passo);
            } else {
                this.insert(passo);
            }
            idsPassos.add(passo.getId());

            List<ExercicioCasoTestePassoParametro> parametros = passo.getInputParameters();

            if (parametros != null) {
                boolean sucess = repoParametro.SaveParametros(passo.getId(), parametros);

                if (sucess == false) {
                    return false;
                }
            }
        }

        return this.deleteNotIn(idCasoTeste, idsPassos);
    }

    public boolean deleteNotIn(int idCasoTeste, List<Integer> ids) {

        Transaction transaction = currentSession().beginTransaction();

        try {
            SQLQuery query = null;
            if (ids.isEmpty()) {
                query = currentSession().createSQLQuery("delete from ExercicioCasoTestePasso where IdCasoTeste = " + idCasoTeste);
            } else {
                query = currentSession().createSQLQuery("delete from ExercicioCasoTestePasso where ID not in (" + ids.toString().replace("[", "").replace("]", "") + ") and IdCasoTeste = " + idCasoTeste);
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
