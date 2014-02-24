/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.MensagemCabecalho;
import feeper.model.HibernateUtil;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class MensagemCabecalhoService extends HibernateUtil<MensagemCabecalho> {
    
    public MensagemCabecalhoService() {
        super(MensagemCabecalho.class);
    }
    
    public MensagemCabecalho getByIdCodigoFonteMarcacao(int idCodigoFonteMarcacao)
    {
        try {
            SQLQuery query = query("select * from MensagemCabecalho where IdCodigoFonteMarcacao = :idCodigoFonteMarcacao and Ativo = 1 ").addEntity(MensagemCabecalho.class);
            query.setInteger("idCodigoFonteMarcacao", idCodigoFonteMarcacao);
            return (MensagemCabecalho)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
