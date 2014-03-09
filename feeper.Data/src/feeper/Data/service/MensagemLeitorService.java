/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.MensagemLeitor;
import feeper.Data.model.HibernateUtil;
import java.util.Date;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class MensagemLeitorService extends HibernateUtil<MensagemLeitor> {
    
    public MensagemLeitorService() {
        super(MensagemLeitor.class);
    }
    
    public boolean atualizarDataLeitura(int idLeitor, char tipoLeitor)
    {
        try {
            
            SQLQuery query = query("update MensagemLeitor set DataUltimaLeitura = :dataAtual where DataUltimaLeitura is null and IdLeitor = :idLeitor and TipoLeitor = :tipoLeitor ");
            query.setInteger("idLeitor", idLeitor);
            query.setCharacter("tipoLeitor", tipoLeitor);
            query.setDate("dataAtual", new Date());
            
            query.executeUpdate();
            
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
}
