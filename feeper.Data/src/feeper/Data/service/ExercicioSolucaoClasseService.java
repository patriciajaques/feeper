/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoClasseService extends HibernateUtil<ExercicioSolucaoClasse> {

    public ExercicioSolucaoClasseService() {
        super(ExercicioSolucaoClasse.class);
    }

    public List<ExercicioSolucaoClasse> getbyIdSolucao(int idSolucao) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from ExercicioSolucaoClasse where IdSolucao = :idSolucao order by ID ").addEntity(ExercicioSolucaoClasse.class);

            query.setInteger("idSolucao", idSolucao);
            List<ExercicioSolucaoClasse> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
}
