/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioCasoTestePasso;
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
public class ExercicioCasoTestePassoService extends HibernateUtil<ExercicioCasoTestePasso> {

    public ExercicioCasoTestePassoService() {
        super(ExercicioCasoTestePasso.class);
    }

    public List<ExercicioCasoTestePasso> getByIdCasoTeste(int idCasoTeste) {
        SQLQuery query = query("select * from ExercicioCasoTestePasso where IdCasoTeste = :idCasoTeste order by Ordem, ID").addEntity(ExercicioCasoTestePasso.class);
        query.setInteger("idCasoTeste", idCasoTeste);

        List<ExercicioCasoTestePasso> data = query.list();

        ExercicioCasoTestePassoParametroService repoParametro = new ExercicioCasoTestePassoParametroService();
        for (ExercicioCasoTestePasso passo : data) {
            passo.setInputParameters(repoParametro.getByIdPasso(passo.getId()));
        }

        return data;
    }

    public boolean SavePassos(int idCasoTeste, List<ExercicioCasoTestePasso> passos) {

        if (passos == null) {    
            return true;
        }
        List<Integer> idsPassos = new ArrayList<Integer>();
        ExercicioCasoTestePassoParametroService repoParametro = new ExercicioCasoTestePassoParametroService();

        for (int i = passos.size() - 1; i >= 0; i--) {

            ExercicioCasoTestePasso passo = passos.get(i);
            if ((passo.getExpectedOutputType() == null || passo.getExpectedOutputType().isEmpty())
                    && (passo.getExpectedOutputName() == null || passo.getExpectedOutputName().isEmpty())
                    && (passo.getExpectedOutputValue() == null || passo.getExpectedOutputValue().isEmpty())
                    && (passo.getObjectName() == null || passo.getObjectName().isEmpty())) {

                passos.remove(i);
            }
        }

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
        Transaction transaction_;
        StatelessSession session_;

        session_ = currentSession();
        transaction_ = session_.beginTransaction();

        try {
            SQLQuery query = null;
            if (ids.isEmpty()) {
                query = session_.createSQLQuery("delete from ExercicioCasoTestePasso where IdCasoTeste = " + idCasoTeste);
            } else {
                query = session_.createSQLQuery("delete from ExercicioCasoTestePasso where ID not in (" + ids.toString().replace("[", "").replace("]", "") + ") and IdCasoTeste = " + idCasoTeste);
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
