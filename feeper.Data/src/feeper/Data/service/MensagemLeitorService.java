/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.MensagemLeitor;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.Util;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

/**
 *
 * @author
 * fabioalves
 */
public class MensagemLeitorService extends HibernateUtil<MensagemLeitor> {
    
    public MensagemLeitorService() {
        super(MensagemLeitor.class);
    }
    
    public boolean atualizarDataLeitura(int idLeitor, char tipoLeitor, Date dataLeitura)
    {
        Session session = currentSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            SQLQuery query = query("update MensagemLeitor set DataUltimaLeitura = '" + Util.formatDate(dataLeitura, "yyyy/MM/dd HH:mm:ss") + "' where IdLeitor = " + idLeitor + " and TipoLeitor = '" + tipoLeitor + "'");
            query.executeUpdate();
            
            transaction.commit();
            
            return true;
        } catch (HibernateException e) { 
            transaction.rollback();
            return false;
        } 
    }
    
}
