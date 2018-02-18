/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;
import feeper.Data.entity.Medalha;
import feeper.Data.entity.MedalhaPessoa;
import feeper.Data.model.HibernateUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/*
*  Cada medalha tem 3 niveis, bronze, parata e ouro
*/

public class MedalhaService extends HibernateUtil<Medalha> {
    
    
    
    public MedalhaService() {
        super(Medalha.class);
    }    
    
    public Medalha findByIdMedalhaAndNivel(Integer idMedalha, Integer nivel){
        String SQL = "select me.id, me.nome, mnd.descricao from medalha me join medalhaniveldescricao mnd on (me.id=mnd.idMedalha) where me.id = :idMedalha and mnd.nivel = :nivel";   
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(SQL).addEntity(Medalha.class);
            query.setInteger("idMedalha", idMedalha);
            query.setInteger("nivel", nivel);
            query.setMaxResults(1);
            List<Medalha> list = query.list();
            transaction.commit();
            return list.size()>0?list.get(0):null;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }    
        
    
}
    
    
    
    
    
    
    
    
    
    
    
   

