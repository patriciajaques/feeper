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

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoErroService extends HibernateUtil<ExercicioSolucaoErro> {

    public ExercicioSolucaoErroService() {
        super(ExercicioSolucaoErro.class);
    }
    
     public List<ExercicioSolucaoErro> getAllByIdSolucao(int idSolucao) {
        try {

            SQLQuery query = query("select * from ExercicioSolucaoErro where IdSolucao = :idSolucao").addEntity(ExercicioSolucaoErro.class);
            query.setInteger("idSolucao", idSolucao);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
}
