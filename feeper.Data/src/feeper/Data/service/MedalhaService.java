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
    
}
    
    
    
    
    
    
    
    
    
    
    
   

