/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.CodigoFonteMarcacao;
import feeper.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class CodigoFonteMarcacaoService extends HibernateUtil<CodigoFonteMarcacao> {
    
    public CodigoFonteMarcacaoService() {
        super(CodigoFonteMarcacao.class);
    }
    
    public CodigoFonteMarcacao getByIdCodigoFonte(int idCodigoFonte, int linha)
    {
        try {
            SQLQuery query = query("select * from CodigoFonteMarcacao where IdCodigoFonte = :idCodigoFonte and LinhaInicio = :linha and Ativo = 1 ").addEntity(CodigoFonteMarcacao.class);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            query.setInteger("linha", linha);
            return (CodigoFonteMarcacao)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public List<Object> getLinhasDuvida(int idCodigoFonte)
    {
        try {
            SQLQuery query = query("select LinhaInicio from CodigoFonteMarcacao where IdCodigoFonte = :idCodigoFonte and Ativo = 1 ");
            query.setInteger("idCodigoFonte", idCodigoFonte);
            return query.list();
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
