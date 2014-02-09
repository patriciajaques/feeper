/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.CodigoFonte;
import feeper.entity.CodigoFonteResultado;
import org.hibernate.SQLQuery;

public class CodigoFonteService extends HibernateUtil<CodigoFonte> {
    
    public CodigoFonteService() {
        super(CodigoFonte.class);
    }
    
    public CodigoFonte getByIdExercicio(int idExercicio, int idPessoa)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdExercicio = :idExercicio and IdAutor = :idPessoa ").addEntity(CodigoFonte.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idPessoa", idPessoa);
            return (CodigoFonte)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public CodigoFonteResultado getResultado(int idCodigoFonte)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonteResultado where IdCodigoFonte = :idCodigoFonte order by ID desc limit 1").addEntity(CodigoFonte.class);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            return (CodigoFonteResultado)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
}
