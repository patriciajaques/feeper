/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.MensagemCabecalho;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 *
 * @author
 * fabioalves
 */
public class MensagemCabecalhoService extends HibernateUtil<MensagemCabecalho> {
    
    public MensagemCabecalhoService() {
        super(MensagemCabecalho.class);
    }
    
    public MensagemCabecalho getByIdCodigoFonteMarcacao(int idCodigoFonteMarcacao)
    {
        try {
            SQLQuery query = query("select * from MensagemCabecalho where IdCodigoFonteMarcacao = :idCodigoFonteMarcacao and Ativo = 1 ").addEntity(MensagemCabecalho.class);
            query.setInteger("idCodigoFonteMarcacao", idCodigoFonteMarcacao);
            return (MensagemCabecalho)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public MensagemCabecalho getByIdRespostaCodigoFonteMarcacao(int idRespostaCodigoFonteMarcacao)
    {
        try {
            SQLQuery query = query("select * from MensagemCabecalho where IdRespostaCodigoFonteMarcacao = :idRespostaCodigoFonteMarcacao and Ativo = 1 ").addEntity(MensagemCabecalho.class);
            query.setInteger("idRespostaCodigoFonteMarcacao", idRespostaCodigoFonteMarcacao);
            return (MensagemCabecalho)query.list().get(0);
        }
        catch(Exception e) {
            return null;
        }
    }
    
    public List<Object> getMensagensRemetente(int idPessoa, boolean apenasNovas, boolean agrupadas)
    {
        return getMensagens(idPessoa, ETipoLeitor.REMETENTE, apenasNovas, agrupadas);
    }
    
    public List<Object> getMensagensDestinatario(int idPessoa, boolean apenasNovas, boolean agrupadas)
    {
        return getMensagens(idPessoa, ETipoLeitor.DESTINATARIO, apenasNovas, agrupadas);
    }
    
    public List<Object> getMensagens(int idPessoa, char tipoLeitor, boolean apenasNovas, boolean agrupadas)
    {
        try {
            
            String sql = "select " +
                        "  CFM.IdAutor, " +
                        "  PA.Nome AS Autor, " +
                        "  MC.ID AS IdMensagemCabecalho, " +
                        "  MC.Publico, " +
                        "  M.IdPessoa, " +
                        "  P.Nome, " +
                        "  CFM.LinhaInicio, " +
                        "  CF.ID AS IdCodigoFonte, " +
                        "  CF.Classe, " +
                        "  E.ID AS IdExercicio, " +
                        "  E.Nome AS Exercicio, " +
                        "  CF.IdAutor AS IdAluno, " +
                        "  0 AS Resposta, ";
            
            if (agrupadas)
                sql +=  "  GET_TIMEDURATION(MAX(M.DataCadastro)) AS DataCadastro, " +
                        "  MAX(M.DataCadastro) AS DataCadastroOrder, " +
                        "  CASE WHEN MAX(M.DataCadastro) >= IFNULL(ML.DataUltimaLeitura, MAX(M.DataCadastro)) THEN 1 ELSE 0 END AS NovaMensagem, " +
                        "  (SELECT Texto FROM Mensagem WHERE ID = MIN(M.ID)) AS Texto ";
            else
                sql +=  "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, " +
                        "  M.DataCadastro AS DataCadastroOrder, " +
                        "  CASE WHEN M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) THEN 1 ELSE 0 END AS NovaMensagem, " +
                        "  M.Texto ";
                    
            sql +=      "from " +
                        "  MensagemCabecalho MC " +
                        "  inner join MensagemLeitor ML " +
                        "  on ML.IdMensagemCabecalho = MC.ID " +
                        "  and ML.IdLeitor = :idPessoa " +
                        "  and ML.Ativo = 1 ";
            if (tipoLeitor != 'A')
                sql += "  and ML.TipoLeitor = :tipoLeitor ";
            
            sql +=      "  inner join Mensagem M " +
                        "  on M.IdMensagemCabecalho = MC.ID " +
                        "  and M.Ativo = 1 " +
                        "  inner join Pessoa P " +
                        "  on P.ID = M.IdPessoa " +
                        "  and P.Ativo = 1 " +
                        "  inner join CodigoFonteMarcacao CFM " +
                        "  on CFM.ID = MC.IdCodigoFonteMarcacao " +
                        "  and CFM.Ativo = 1 " +
                        "  inner join CodigoFonte CF " +
                        "  on CF.ID = CFM.IdCodigoFonte " +
                        "  inner join Exercicio E " +
                        "  on E.ID = CF.IdExercicio " +
                        "  and E.Ativo = 1 " +
                        "  inner join Pessoa PA " +
                        "  on PA.ID = CFM.IdAutor " +
                        "  and PA.Ativo = 1 " +
                        "where " +
                        "  MC.Ativo = 1 ";
            
            if (apenasNovas)
                sql += "  and M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) ";
            
            if (agrupadas)
                sql += "group by MC.ID ";
            
            sql += "union all select " +
                        "  RCFM.IdAutor, " +
                        "  PA.Nome AS Autor, " +
                        "  MC.ID AS IdMensagemCabecalho, " +
                        "  MC.Publico, " +
                        "  M.IdPessoa, " +
                        "  P.Nome, " +
                        "  RCFM.LinhaInicio, " +
                        "  RCF.ID AS IdCodigoFonte, " +
                        "  RCF.Classe, " +
                        "  E.ID AS IdExercicio, " +
                        "  E.Nome AS Exercicio, " +
                        "  R.IdAutor AS IdAluno, " +
                        "  1 AS Resposta, ";
            
            if (agrupadas)
                sql +=  "  GET_TIMEDURATION(MAX(M.DataCadastro)) AS DataCadastro, " +
                        "  MAX(M.DataCadastro) AS DataCadastroOrder, " +
                        "  CASE WHEN MAX(M.DataCadastro) >= IFNULL(ML.DataUltimaLeitura, MAX(M.DataCadastro)) THEN 1 ELSE 0 END AS NovaMensagem, " +
                        "  (SELECT Texto FROM Mensagem WHERE ID = MIN(M.ID)) AS Texto ";
            else
                sql +=  "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, " +
                        "  M.DataCadastro AS DataCadastroOrder, " +
                        "  CASE WHEN M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) THEN 1 ELSE 0 END AS NovaMensagem, " +
                        "  M.Texto ";
                    
            sql +=      "from " +
                        "  MensagemCabecalho MC " +
                        "  inner join MensagemLeitor ML " +
                        "  on ML.IdMensagemCabecalho = MC.ID " +
                        "  and ML.IdLeitor = :idPessoa " +
                        "  and ML.Ativo = 1 ";
            if (tipoLeitor != 'A')
                sql += "  and ML.TipoLeitor = :tipoLeitor ";
            
            sql +=      "  inner join Mensagem M " +
                        "  on M.IdMensagemCabecalho = MC.ID " +
                        "  and M.Ativo = 1 " +
                        "  inner join Pessoa P " +
                        "  on P.ID = M.IdPessoa " +
                        "  and P.Ativo = 1 " +
                        "  inner join RespostaCodigoFonteMarcacao RCFM " +
                        "  on RCFM.ID = MC.IdRespostaCodigoFonteMarcacao " +
                        "  and RCFM.Ativo = 1 " +
                        "  inner join RespostaCodigoFonte RCF " +
                        "  on RCF.ID = RCFM.IdRespostaCodigoFonte " +
                        "  inner join Resposta R " +
                        "  on R.ID = RCF.IdResposta " +
                        "  inner join Exercicio E " +
                        "  on E.ID = R.IdExercicio " +
                        "  and E.Ativo = 1 " +
                        "  inner join Pessoa PA " +
                        "  on PA.ID = RCFM.IdAutor " +
                        "  and PA.Ativo = 1 " +
                        "where " +
                        "  MC.Ativo = 1 ";
            
            if (apenasNovas)
                sql += "  and M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) ";
            
            
            if (agrupadas)
                sql += "group by MC.ID ";
            
            sql += " order by DataCadastroOrder DESC ";
            
            SQLQuery query = query(sql);
            
            query.addScalar("IdAutor", IntegerType.INSTANCE);
            query.addScalar("Autor", StringType.INSTANCE);
            query.addScalar("IdMensagemCabecalho", IntegerType.INSTANCE);
            query.addScalar("Publico", BooleanType.INSTANCE);
            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("Nome", StringType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.addScalar("IdCodigoFonte", IntegerType.INSTANCE);
            query.addScalar("Classe", StringType.INSTANCE);
            query.addScalar("IdExercicio", IntegerType.INSTANCE);
            query.addScalar("Exercicio", StringType.INSTANCE);
            query.addScalar("IdAluno", IntegerType.INSTANCE);
            query.addScalar("Resposta", BooleanType.INSTANCE);
            query.addScalar("DataCadastro", StringType.INSTANCE);
            query.addScalar("DataCadastroOrder", DateType.INSTANCE);
            query.addScalar("NovaMensagem", IntegerType.INSTANCE);
            query.addScalar("Texto", StringType.INSTANCE);
            
            query.setInteger("idPessoa", idPessoa);
            
            if (tipoLeitor != 'A')
                query.setCharacter("tipoLeitor", tipoLeitor);
            
            return query.list();
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
