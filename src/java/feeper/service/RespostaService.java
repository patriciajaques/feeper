/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.Resposta;
import feeper.entity.RespostaCodigoFonte;
//import feeper.entity.RespostaCodigoFonte;
import feeper.model.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class RespostaService extends HibernateUtil<Resposta> {
    
    public RespostaService() {
        super(Resposta.class);
    }
    
    public Resposta getLastByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select * from Resposta where IdExercicio = :idExercicio and IdAutor = :idAutor order by ID desc limit 1").addEntity(Resposta.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return (Resposta)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Resposta> getByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select * from Resposta where IdExercicio = :idExercicio and IdAutor = :idAutor order by ID desc").addEntity(Resposta.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Object> getDetailsByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select " +
                                    "  R.ID, " +
                                    "  PA.ID AS IdAutor, " +
                                    "  PA.Nome AS Autor, " +
                                    "  E.ID AS IdExercicio, " +
                                    "  E.Nome AS Exercicio, " +
                                    "  GET_TIMEDURATION(R.DataCadastro) AS DataCadastroString, " +
                                    "  R.DataCadastro, " +
                                    "  SR.ID AS IdStatus, " +
                                    "  SR.Nome AS Status, " +
                                    "  R.Mensagem " +
                                    "from  " +
                                    "  Resposta R " +
                                    "  inner join Pessoa PA " +
                                    "  on PA.ID = R.IdAutor " +
                                    "  and PA.Ativo = 1 " +
                                    "  inner join Exercicio E " +
                                    "  on E.ID = R.IdExercicio " +
                                    "  and E.Ativo = 1 " +
                                    "  inner join StatusResposta SR " +
                                    "  on SR.ID = R.IdStatus " +
                                    "where  " +
                                    "  R.IdExercicio = :idExercicio " +
                                    "  and R.IdAutor = :idAutor " +
                                    "order by R.ID desc");
            
            query.addScalar("ID", Hibernate.INTEGER);
            query.addScalar("IdAutor", Hibernate.INTEGER);
            query.addScalar("Autor", Hibernate.STRING);
            query.addScalar("IdExercicio", Hibernate.INTEGER);
            query.addScalar("Exercicio", Hibernate.STRING);
            query.addScalar("DataCadastroString", Hibernate.STRING);
            query.addScalar("DataCadastro", Hibernate.DATE);
            query.addScalar("IdStatus", Hibernate.INTEGER);
            query.addScalar("Status", Hibernate.STRING);
            query.addScalar("Mensagem", Hibernate.STRING);
            
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<RespostaCodigoFonte> getListRespostaCodigoFonte(int idResposta)
    {
        try {
            
            SQLQuery query = query("select * from RespostaCodigoFonte where IdResposta = :idResposta order by Principal, ID ").addEntity(RespostaCodigoFonte.class);
            
            query.setInteger("idResposta", idResposta);
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public RespostaCodigoFonte getRespostaCodigoFonte(int id)
    {
        try {
            
            SQLQuery query = query("select * from RespostaCodigoFonte where ID = :id ").addEntity(RespostaCodigoFonte.class);
            
            query.setInteger("id", id);
            return (RespostaCodigoFonte)query.list().get(0);
            
        } catch (Exception e) {
            return null;
        }
    }
    
}
