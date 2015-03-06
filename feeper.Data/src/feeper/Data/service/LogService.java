/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Log;
import feeper.Data.entity.TipoLog;
import feeper.Data.model.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

public class LogService extends HibernateUtil<Log> {

    public LogService() {
        super(Log.class);
    }

    public void log(int idPessoa, String msg, int idTipoLog) {
        try {

            Log log = new Log();

            log.setDataCadastro(new Date());
            log.setIdPessoa(idPessoa);
            log.setIdTipoLog(idTipoLog);
            log.setMensagem(msg);

            insert(log);

        } catch (Exception e) {
        }
    }

    public List<TipoLog> getTipoLog() {
        Transaction transaction = currentSession().beginTransaction();
        try {
            
            SQLQuery query = currentSession().createSQLQuery("select * from TipoLog order by Nome").addEntity(TipoLog.class);
            List<TipoLog> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

}
