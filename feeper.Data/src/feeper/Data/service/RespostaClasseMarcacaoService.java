/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.RespostaClasseMarcacao;
import feeper.Data.model.ETipoMarcacao;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class RespostaClasseMarcacaoService extends HibernateUtil<RespostaClasseMarcacao> {
    
    public RespostaClasseMarcacaoService() {
        super(RespostaClasseMarcacao.class);
    }
    
    public RespostaClasseMarcacao getByIdRespostaClasse(int idRespostaClasse, int linha, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select * from RespostaClasseMarcacao "
                    + "where IdRespostaClasse = :idRespostaClasse "
                    + "and LinhaInicio = :linha "
                    + "and IdTipoMarcacao = :idTipoMarcacao "
                    + "and Ativo = 1 ").addEntity(RespostaClasseMarcacao.class);
            query.setInteger("idRespostaClasse", idRespostaClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return (RespostaClasseMarcacao)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public List<Object> getLinhasDuvida(int idRespostaClasse)
    {
        return getLinhasMarcadas(idRespostaClasse, ETipoMarcacao.DUVIDA);
    }
    public List<Object> getLinhasAnotacao(int idRespostaClasse)
    {
        return getLinhasMarcadas(idRespostaClasse, ETipoMarcacao.ANOTACAO);
    }
    private List<Object> getLinhasMarcadas(int idRespostaClasse, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select LinhaInicio from RespostaClasseMarcacao where IdRespostaClasse = :idRespostaClasse and Ativo = 1 and IdTipoMarcacao = :idTipoMarcacao ");
            query.setInteger("idRespostaClasse", idRespostaClasse);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return query.list();
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
