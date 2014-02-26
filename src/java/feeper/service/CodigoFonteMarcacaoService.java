/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.CodigoFonteMarcacao;
import feeper.model.ETipoMarcacao;
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
    
    public CodigoFonteMarcacao getByIdCodigoFonte(int idCodigoFonte, int linha, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select * from CodigoFonteMarcacao "
                    + "where IdCodigoFonte = :idCodigoFonte "
                    + "and LinhaInicio = :linha "
                    + "and IdTipoMarcacao = :idTipoMarcacao "
                    + "and Ativo = 1 ").addEntity(CodigoFonteMarcacao.class);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return (CodigoFonteMarcacao)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public List<Object> getLinhasDuvida(int idCodigoFonte)
    {
        return getLinhasMarcadas(idCodigoFonte, ETipoMarcacao.DUVIDA);
    }
    public List<Object> getLinhasAnotacao(int idCodigoFonte)
    {
        return getLinhasMarcadas(idCodigoFonte, ETipoMarcacao.ANOTACAO);
    }
    private List<Object> getLinhasMarcadas(int idCodigoFonte, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select LinhaInicio from CodigoFonteMarcacao where IdCodigoFonte = :idCodigoFonte and Ativo = 1 and IdTipoMarcacao = :idTipoMarcacao ");
            query.setInteger("idCodigoFonte", idCodigoFonte);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return query.list();
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
