/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.entity.Resposta;
import feeper.Data.entity.RespostaClasse;
import feeper.Data.model.EStatusResposta;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.IntegerResult;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

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
            
            query.addScalar("ID", IntegerType.INSTANCE);
            query.addScalar("IdAutor", IntegerType.INSTANCE);
            query.addScalar("Autor", StringType.INSTANCE);
            query.addScalar("IdExercicio", IntegerType.INSTANCE);
            query.addScalar("Exercicio", StringType.INSTANCE);
            query.addScalar("DataCadastroString", StringType.INSTANCE);
            query.addScalar("DataCadastro", TimestampType.INSTANCE);
            query.addScalar("IdStatus", IntegerType.INSTANCE);
            query.addScalar("Status", StringType.INSTANCE);
            query.addScalar("Mensagem", StringType.INSTANCE);
            
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<RespostaClasse> getListRespostaClasse(int idResposta)
    {
        try {
            
            SQLQuery query = query("select * from RespostaClasse where IdResposta = :idResposta order by Principal, ID ").addEntity(RespostaClasse.class);
            
            query.setInteger("idResposta", idResposta);
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public RespostaClasse getRespostaClasse(int id)
    {
        try {
            
            SQLQuery query = query("select * from RespostaClasse where ID = :id ").addEntity(RespostaClasse.class);
            
            query.setInteger("id", id);
            return (RespostaClasse)query.list().get(0);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean salvarResposta(int idPessoa, int idExercicio, IntegerResult idResposta)
    {
        try {

            Resposta resposta = new Resposta();
            resposta.setDataCadastro(new Date());
            resposta.setIdAutor(idPessoa);
            resposta.setIdExercicio(idExercicio);
            resposta.setIdStatus(EStatusResposta.AGUARDANDO);
            
            if (insert(resposta))
            {
                RespostaClasseService repoRespostaClasse = new RespostaClasseService();
                ExercicioSolucaoClasseService repoClasse = new ExercicioSolucaoClasseService();
                List<ExercicioSolucaoClasse> listaClasses = repoClasse.getAllByIdExercicio(idExercicio, idPessoa);
                
                for (ExercicioSolucaoClasse classe : listaClasses) {
                    RespostaClasse respostaClasse = new RespostaClasse();
                    respostaClasse.setClasse(classe.getNomeClasse());
                    respostaClasse.setFonte(classe.getCodigo());
                    respostaClasse.setIdResposta(resposta.getId());
                    if (!repoRespostaClasse.insert(respostaClasse))
                        return false;
                }
                idResposta.setResult(resposta.getId());
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public void salvarResultadoResposta(int id, String msg, int idStatus, int idTipoLog)
    {
        Resposta resposta = getById(id);
        resposta.setMensagem(msg);
        resposta.setIdStatus(idStatus);
        update(resposta);
        
        LogService repoLog = new LogService();
        repoLog.log(resposta.getIdAutor(), msg, idTipoLog);
    }
    
}
