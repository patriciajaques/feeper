/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.TelaPerfil;
import feeper.Data.model.EPerfil;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/**
 *
 * @author fabioalves
 */
public class TelaPerfilService extends HibernateUtil<TelaPerfil> {

    public TelaPerfilService() {
        super(TelaPerfil.class);
    }

    public boolean verificaAcesso(int idTela, int idPessoa) {
        PessoaService pessoaService = new PessoaService();
        if(pessoaService.getById(idPessoa).getIdPerfil()== EPerfil.ADMIN){
            return true;
        }
              
        Transaction transaction = currentSession().beginTransaction();
        try {
//            SQLQuery query = currentSession().createSQLQuery("select 1 from TelaPerfil TP\n"
//                    + "inner join Pessoa P\n"
//                    + "on P.IdPerfil = TP.IdPerfil\n"
//                    + "and P.ID = :idPessoa \n"
//                    + "and TP.IdTela = :idTela ");
            SQLQuery query = currentSession().createSQLQuery("select 1 from TelaPerfil TP \n"
                    + "inner join Pessoa P\n"
                    + "on P.IdPerfil = TP.IdPerfil \n"
                    + "and P.ID = :idPessoa \n"
                    + "and TP.IdTela = :idTela ");
            query.setInteger("idTela", idTela);
            query.setInteger("idPessoa", idPessoa);
            Boolean data = query.list().size() > 0;
        
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return false;
        }
    }

}
