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

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoClasseService extends HibernateUtil<ExercicioSolucaoClasse> {

    public ExercicioSolucaoClasseService() {
        super(ExercicioSolucaoClasse.class);
    }
    
    public List<ExercicioSolucaoClasse> getbyIdSolucao(int idSolucao) {
        try {

            SQLQuery query = query("select * from ExercicioSolucaoClasse where IdSolucao = :idSolucao order by ID ").addEntity(ExercicioSolucaoClasse.class);

            query.setInteger("idSolucao", idSolucao);
            return query.list();

        } catch (Exception e) {
            return null;
        }
    }
}
