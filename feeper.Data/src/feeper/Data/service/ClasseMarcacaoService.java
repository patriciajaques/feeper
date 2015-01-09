/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ClasseMarcacao;
import feeper.Data.model.ETipoMarcacao;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class ClasseMarcacaoService extends HibernateUtil<ClasseMarcacao> {
    
    public ClasseMarcacaoService() {
        super(ClasseMarcacao.class);
    }
    
    public ClasseMarcacao getByIdClasse(int idClasse, int linha, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select * from ClasseMarcacao "
                    + "where IdClasse = :idClasse "
                    + "and LinhaInicio = :linha "
                    + "and IdTipoMarcacao = :idTipoMarcacao "
                    + "and Ativo = 1 ").addEntity(ClasseMarcacao.class);
            query.setInteger("idClasse", idClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return (ClasseMarcacao)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public List<Object> getLinhasDuvida(int idClasse)
    {
        return getLinhasMarcadas(idClasse, ETipoMarcacao.DUVIDA);
    }
    public List<Object> getLinhasAnotacao(int idClasse)
    {
        return getLinhasMarcadas(idClasse, ETipoMarcacao.ANOTACAO);
    }
    private List<Object> getLinhasMarcadas(int idClasse, int idTipoMarcacao)
    {
        try {
            SQLQuery query = query("select LinhaInicio from ClasseMarcacao where IdClasse = :idClasse and Ativo = 1 and IdTipoMarcacao = :idTipoMarcacao ");
            query.setInteger("idClasse", idClasse);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return query.list();
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
