/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.MensagemCabecalho;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.model.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

/**
 *
 * @author fabioalves
 */
public class MensagemCabecalhoService extends HibernateUtil<MensagemCabecalho> {

    public MensagemCabecalhoService() {
        super(MensagemCabecalho.class);
    }

    public MensagemCabecalho getByIdExercicioClasseMarcacao(int idExercicioClasseMarcacao) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from MensagemCabecalho where IdExercicioClasseMarcacao = :idExercicioClasseMarcacao and Ativo = 1 ").addEntity(MensagemCabecalho.class);
            query.setInteger("idExercicioClasseMarcacao", idExercicioClasseMarcacao);
            List<MensagemCabecalho> data = query.list();
            transaction.commit();
            if (data.isEmpty()) {
                return null;

            } else {
                return data.get(0);
            }
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public MensagemCabecalho getByIdExercicioSolucaoClasseMarcacao(int idExercicioSolucaoClasseMarcacao) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from MensagemCabecalho where IdExercicioSolucaoClasseMarcacao = :idExercicioSolucaoClasseMarcacao and Ativo = 1 ").addEntity(MensagemCabecalho.class);
            query.setInteger("idExercicioSolucaoClasseMarcacao", idExercicioSolucaoClasseMarcacao);
            List<MensagemCabecalho> data = query.list();
            transaction.commit();
            if (data.isEmpty()) {
                return null;

            } else {
                return data.get(0);
            }
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public List<Object> getMensagensRemetente(int idPessoa, boolean apenasNovas, boolean agrupadas) {
        return getMensagens(idPessoa, ETipoLeitor.REMETENTE, apenasNovas, agrupadas);
    }

    public List<Object> getMensagensDestinatario(int idPessoa, boolean apenasNovas, boolean agrupadas) {
        return getMensagens(idPessoa, ETipoLeitor.DESTINATARIO, apenasNovas, agrupadas);
    }

    public List<Object> getMensagens(int idPessoa, char tipoLeitor, boolean apenasNovas, boolean agrupadas) {
        //Apenas para testes ja que não é usado, depois ver em producao
       
        
        Transaction transaction = currentSession().beginTransaction();
        try {

            String sql = "select "
                    + "  ECM.IdAutor, "
                    + "  PA.Nome AS Autor, "
                    + "  MC.ID AS IdMensagemCabecalho, "
                    + "  MC.Publico, "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  ECM.LinhaInicio, "
                    + "  ECL.ID AS IdClasse, "
                    + "  ECL.NomeClasse, "
                    + "  E.ID AS IdExercicio, "
                    + "  E.Nome AS Exercicio, "
                    + "  ECL.IdAluno, "
                    + "  0 AS Resposta, ";

            if (agrupadas) {
                sql += "  GET_TIMEDURATION(MAX(M.DataCadastro)) AS DataCadastro, "
                        + "  MAX(M.DataCadastro) AS DataCadastroOrder, "
                        + "  CASE WHEN MAX(M.DataCadastro) >= IFNULL(ML.DataUltimaLeitura, MAX(M.DataCadastro)) THEN 1 ELSE 0 END AS NovaMensagem, "
                        + "  (SELECT Texto FROM Mensagem WHERE ID = MIN(M.ID)) AS Texto ";
            } else {
                sql += "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                        + "  M.DataCadastro AS DataCadastroOrder, "
                        + "  CASE WHEN M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) THEN 1 ELSE 0 END AS NovaMensagem, "
                        + "  M.Texto ";
            }

            sql += "from "
                    + "  MensagemCabecalho MC "
                    + "  inner join MensagemLeitor ML "
                    + "  on ML.IdMensagemCabecalho = MC.ID "
                    + "  and ML.IdLeitor = :idPessoa "
                    + "  and ML.Ativo = 1 ";
            if (tipoLeitor != 'A') {
                sql += "  and ML.TipoLeitor = :tipoLeitor ";
            }

            sql += "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "  inner join ExercicioClasseMarcacao ECM "
                    + "  on ECM.ID = MC.IdExercicioClasseMarcacao "
                    + "  and ECM.Ativo = 1 "
                    + "  inner join ExercicioClasse ECL "
                    + "  on ECL.ID = ECM.IdExercicioClasse "
                    + "  inner join Exercicio E "
                    + "  on E.ID = ECL.IdExercicio "
                    + "  and E.Ativo = 1 "
                    + "  inner join Pessoa PA "
                    + "  on PA.ID = ECM.IdAutor "
                    + "  and PA.Ativo = 1 "
                    + "where "
                    + "  MC.Ativo = 1 ";

            if (apenasNovas) {
                sql += "  and M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) ";
            }

            if (agrupadas) {
                sql += "group by MC.ID ";
            }

            sql += "union all select "
                    + "  ESCM.IdAutor, "
                    + "  PA.Nome AS Autor, "
                    + "  MC.ID AS IdMensagemCabecalho, "
                    + "  MC.Publico, "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  ESCM.LinhaInicio, "
                    + "  ESC.ID AS IdClasse, "
                    + "  ESC.NomeClasse, "
                    + "  E.ID AS IdExercicio, "
                    + "  E.Nome AS Exercicio, "
                    + "  ES.IdAluno, "
                    + "  1 AS Resposta, ";

            if (agrupadas) {
                sql += "  GET_TIMEDURATION(MAX(M.DataCadastro)) AS DataCadastro, "
                        + "  MAX(M.DataCadastro) AS DataCadastroOrder, "
                        + "  CASE WHEN MAX(M.DataCadastro) >= IFNULL(ML.DataUltimaLeitura, MAX(M.DataCadastro)) THEN 1 ELSE 0 END AS NovaMensagem, "
                        + "  (SELECT Texto FROM Mensagem WHERE ID = MIN(M.ID)) AS Texto ";
            } else {
                sql += "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                        + "  M.DataCadastro AS DataCadastroOrder, "
                        + "  CASE WHEN M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) THEN 1 ELSE 0 END AS NovaMensagem, "
                        + "  M.Texto ";
            }

            sql += "from "
                    + "  MensagemCabecalho MC "
                    + "  inner join MensagemLeitor ML "
                    + "  on ML.IdMensagemCabecalho = MC.ID "
                    + "  and ML.IdLeitor = :idPessoa "
                    + "  and ML.Ativo = 1 ";
            if (tipoLeitor != 'A') {
                sql += "  and ML.TipoLeitor = :tipoLeitor ";
            }

            sql += "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "  inner join ExercicioSolucaoClasseMarcacao ESCM "
                    + "  on ESCM.ID = MC.IdExercicioSolucaoClasseMarcacao "
                    + "  and ESCM.Ativo = 1 "
                    + "  inner join ExercicioSolucaoClasse ESC "
                    + "  on ESC.ID = ESCM.IdExercicioSolucaoClasse "
                    + "  inner join ExercicioSolucao ES "
                    + "  on ES.ID = ESC.IdSolucao "
                    + "  inner join Exercicio E "
                    + "  on E.ID = ES.IdExercicio "
                    + "  and E.Ativo = 1 "
                    + "  inner join Pessoa PA "
                    + "  on PA.ID = ESCM.IdAutor "
                    + "  and PA.Ativo = 1 "
                    + "where "
                    + "  MC.Ativo = 1 ";

            if (apenasNovas) {
                sql += "  and M.DataCadastro >= IFNULL(ML.DataUltimaLeitura, M.DataCadastro) ";
            }

            if (agrupadas) {
                sql += "group by MC.ID ";
            }

            sql += " order by DataCadastroOrder DESC ";

            SQLQuery query = currentSession().createSQLQuery(sql);

            query.addScalar("IdAutor", IntegerType.INSTANCE);
            query.addScalar("Autor", StringType.INSTANCE);
            query.addScalar("IdMensagemCabecalho", IntegerType.INSTANCE);
            query.addScalar("Publico", BooleanType.INSTANCE);
            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("Nome", StringType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.addScalar("IdClasse", IntegerType.INSTANCE);
            query.addScalar("NomeClasse", StringType.INSTANCE);
            query.addScalar("IdExercicio", IntegerType.INSTANCE);
            query.addScalar("Exercicio", StringType.INSTANCE);
            query.addScalar("IdAluno", IntegerType.INSTANCE);
            query.addScalar("Resposta", BooleanType.INSTANCE);
            query.addScalar("DataCadastro", StringType.INSTANCE);
            query.addScalar("DataCadastroOrder", DateType.INSTANCE);
            query.addScalar("NovaMensagem", IntegerType.INSTANCE);
            query.addScalar("Texto", StringType.INSTANCE);

            query.setInteger("idPessoa", idPessoa);

            if (tipoLeitor != 'A') {
                query.setCharacter("tipoLeitor", tipoLeitor);
            }

            List<Object> data = query.list();
            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

}
