/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.RespostaCodigoFonteMarcacao;
import feeper.Data.model.ETipoMarcacao;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class RespostaCodigoFonteMarcacaoService extends HibernateUtil<RespostaCodigoFonteMarcacao> {
    
    public RespostaCodigoFonteMarcacaoService() {
        super(RespostaCodigoFonteMarcacao.class);
    }
    
    public RespostaCodigoFonteMarcacao getByIdRespostaCodigoFonte(int idRespostaCodigoFonte, int linha, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select * from RespostaCodigoFonteMarcacao "
                    + "where IdRespostaCodigoFonte = :idRespostaCodigoFonte "
                    + "and LinhaInicio = :linha "
                    + "and IdTipoMarcacao = :idTipoMarcacao "
                    + "and Ativo = 1 ").addEntity(RespostaCodigoFonteMarcacao.class);
            query.setInteger("idRespostaCodigoFonte", idRespostaCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return (RespostaCodigoFonteMarcacao)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public List<Object> getLinhasDuvida(int idRespostaCodigoFonte)
    {
        return getLinhasMarcadas(idRespostaCodigoFonte, ETipoMarcacao.DUVIDA);
    }
    public List<Object> getLinhasAnotacao(int idRespostaCodigoFonte)
    {
        return getLinhasMarcadas(idRespostaCodigoFonte, ETipoMarcacao.ANOTACAO);
    }
    private List<Object> getLinhasMarcadas(int idRespostaCodigoFonte, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select LinhaInicio from RespostaCodigoFonteMarcacao where IdRespostaCodigoFonte = :idRespostaCodigoFonte and Ativo = 1 and IdTipoMarcacao = :idTipoMarcacao ");
            query.setInteger("idRespostaCodigoFonte", idRespostaCodigoFonte);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return query.list();
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
