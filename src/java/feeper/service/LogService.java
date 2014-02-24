/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.Log;
import feeper.model.HibernateUtil;
import java.util.Date;

public class LogService extends HibernateUtil<Log> {
    
    public LogService() {
        super(Log.class);
    }
    
    public void log(int idPessoa, String msg, int idTipoLog)
    {
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
    
}
