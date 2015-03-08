/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioSolucaoErro;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoErroService extends HibernateUtil<ExercicioSolucaoErro> {

    public ExercicioSolucaoErroService() {
        super(ExercicioSolucaoErro.class);
    }

    public List<ExercicioSolucaoErro> getAllByIdSolucao(int idSolucao) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from ExercicioSolucaoErro where IdSolucao = :idSolucao").addEntity(ExercicioSolucaoErro.class);
            query.setInteger("idSolucao", idSolucao);
            List<ExercicioSolucaoErro> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
}
