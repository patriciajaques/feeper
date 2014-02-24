/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.TurmaPessoa;
import feeper.model.HibernateUtil;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class TurmaPessoaService extends HibernateUtil<TurmaPessoa> {
    
    public TurmaPessoaService() {
        super(TurmaPessoa.class);
    }
    
    public List<TurmaPessoa> getByIdTurma(int idTurma)
    {
        SQLQuery query = query("select * from TurmaPessoa where IdTurma = :idTurma").addEntity(TurmaPessoa.class);
        query.setInteger("idTurma", idTurma);
        return query.list();
    }
    
    public List<TurmaPessoa> getByIdPessoa(int idPessoa)
    {
        SQLQuery query = query("select * from TurmaPessoa where IdPessoa = :idPessoa").addEntity(TurmaPessoa.class);
        query.setInteger("idPessoa", idPessoa);
        return query.list();
    }
    
    public boolean existsByIdTurmaIdPessoa(int idTurma, int idPessoa)
    {
        SQLQuery query = query("select count(*) from TurmaPessoa where IdTurma = :idTurma and IdPessoa = :idPessoa");
        query.setInteger("idTurma", idTurma);
        query.setInteger("idPessoa", idPessoa);
        Object result = query.uniqueResult();
        return result != null && ((BigInteger)result).intValue() >= 1;
    }
    
    public boolean insertIfNotExist(int idTurma, int idPessoa)
    {
        try {
            if (!existsByIdTurmaIdPessoa(idTurma, idPessoa))
            {
                SQLQuery query = query("insert into TurmaPessoa (IdTurma, IdPessoa) values ( :idTurma , :idPessoa )");
                query.setInteger("idTurma", idTurma);
                query.setInteger("idPessoa", idPessoa);

                query.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
