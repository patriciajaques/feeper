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
        Transaction transaction_;
        StatelessSession session_;
        
        session_ = currentSession();
        transaction_ = session_.beginTransaction();
        
        try {
            SQLQuery query = query("update MensagemLeitor set DataUltimaLeitura = '" + Util.formatDate(dataLeitura, "yyyy/MM/dd HH:mm:ss") + "' where IdLeitor = " + idLeitor + " and TipoLeitor = '" + tipoLeitor + "'");
            query.executeUpdate();
            
            //session_.flush();
            transaction_.commit();
            Boolean aa = transaction_.wasCommitted();
            
            return true;
        } catch (HibernateException e) { 
            transaction_.rollback();
            return false;
        } 
    }
    
}
