/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.Resposta;
import feeper.model.HibernateUtil;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class RespostaService extends HibernateUtil<Resposta> {
    
    public RespostaService() {
        super(Resposta.class);
    }
    
    public Resposta getLastByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select * from Resposta where IdExercicio = :idExercicio and IdAutor = :idAutor order by ID desc limit 1").addEntity(Resposta.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return (Resposta)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
}
