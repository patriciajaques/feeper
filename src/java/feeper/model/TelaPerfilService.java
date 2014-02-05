/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.TelaPerfil;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class TelaPerfilService extends HibernateUtil<TelaPerfil> {
    
    public TelaPerfilService() {
        super(TelaPerfil.class);
    }
    
    public boolean verificaAcesso(int idTela, int idPessoa)
    {
        SQLQuery query = query("select 1 from TelaPerfil TP\n" +
                                "inner join Pessoa P\n" +
                                "on P.IdPerfil = TP.IdPerfil\n" +
                                "and P.ID = :idPessoa \n" +
                                "and TP.IdTela = :idTela ");
        query.setInteger("idTela", idTela);
        query.setInteger("idPessoa", idPessoa);
        return query.list().size() > 0;
    }
    
}
