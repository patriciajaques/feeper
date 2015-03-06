/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasseMarcacao;
import feeper.Data.entity.Mensagem;
import feeper.Data.entity.MensagemCabecalho;
import feeper.Data.entity.MensagemLeitor;
import feeper.Data.entity.Pessoa;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.model.ETipoMarcacao;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.Util;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

/**
 *
 * @author gilvani
 */
public class ExercicioClasseMarcacaoService extends HibernateUtil<ExercicioClasseMarcacao> {

    public ExercicioClasseMarcacaoService() {
        super(ExercicioClasseMarcacao.class);
    }

    public ExercicioClasseMarcacao getByIdExercicioClasse(int idExercicioClasse, int linha, int idTipoMarcacao) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from ExercicioClasseMarcacao "
                    + "where IdExercicioClasse = :idExercicioClasse "
                    + "and LinhaInicio = :linha "
                    + "and IdTipoMarcacao = :idTipoMarcacao "
                    + "and Ativo = 1 ").addEntity(ExercicioClasseMarcacao.class);
            query.setInteger("idExercicioClasse", idExercicioClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            List<ExercicioClasseMarcacao> data = query.list();
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

    public Object getAnotacao(int idExercicio, int idExercicioClasse, int linha) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select "
                    + "  CM.DataCadastro, "
                    + "  CM.Anotacao, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ExercicioClasseMarcacao CM "
                    + "  inner join ExercicioClasse CL "
                    + "  on CL.ID = CM.IdExercicioClasse "
                    + "where "
                    + "  CL.ID = :idExercicioClasse "
                    + "  and CL.IdExercicio = :idExercicio "
                    + "  and CM.LinhaInicio = :linha "
                    + "  and CM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and CM.Ativo = 1");
            query.addScalar("DataCadastro", TimestampType.INSTANCE);
            query.addScalar("Anotacao", StringType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idExercicioClasse", idExercicioClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.ANOTACAO);

            List<Object> data = query.list();
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

    public List<Object> getMarcacaoAutor(int idExercicio, int idAutor, int idExercicioClasse, int linha) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ExercicioClasseMarcacao CM "
                    + "  inner join ExercicioClasse CL "
                    + "  on CL.ID = CM.IdExercicioClasse "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdExercicioClasseMarcacao = CM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  CL.ID = :idExercicioClasse "
                    + "  and CL.IdExercicio = :idExercicio "
                    + "  and CL.IdAluno = :idAutor "
                    + "  and CM.LinhaInicio = :linha "
                    + "  and CM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and CM.Ativo = 1 "
                    + "order by M.ID");
            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("Nome", StringType.INSTANCE);
            query.addScalar("DataCadastro", StringType.INSTANCE);
            query.addScalar("Texto", StringType.INSTANCE);
            query.addScalar("Publico", BooleanType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            query.setInteger("idExercicioClasse", idExercicioClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            List<Object> data = query.list();

            transaction.commit();
            return data;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public List<Object> getMarcacaoLeitor(int idExercicio, int idExercicioClasse, int linha) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ExercicioClasseMarcacao CM "
                    + "  inner join ExercicioClasse CL "
                    + "  on CL.ID = CM.IdExercicioClasse "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdExercicioClasseMarcacao = CM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  CL.ID = :idExercicioClasse "
                    + "  and CL.IdExercicio = :idExercicio "
                    + "  and CM.LinhaInicio = :linha "
                    + "  and CM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and CM.Ativo = 1 "
                    + "order by M.ID");
            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("Nome", StringType.INSTANCE);
            query.addScalar("DataCadastro", StringType.INSTANCE);
            query.addScalar("Texto", StringType.INSTANCE);
            query.addScalar("Publico", BooleanType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idExercicioClasse", idExercicioClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            List<Object> data = query.list();

            transaction.commit();
            return data;

        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }

    public boolean inserirAnotacao(int idExercicio, int idAutor, int idExercicioClasse, int linha, boolean publico, String texto) {
        try {

            ExercicioClasseMarcacao classeMarcacao = this.getByIdExercicioClasse(idExercicioClasse, linha, ETipoMarcacao.ANOTACAO);

            if (classeMarcacao == null) {
                classeMarcacao = new ExercicioClasseMarcacao();
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(new Date());
                classeMarcacao.setIdAutor(idAutor);
                classeMarcacao.setIdExercicioClasse(idExercicioClasse);
                classeMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                classeMarcacao.setLinhaInicio(linha);
                classeMarcacao.setAnotacao(texto);
                return this.insert(classeMarcacao);
            } else {
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(new Date());
                classeMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                classeMarcacao.setAnotacao(texto);
                return this.update(classeMarcacao);
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean inserirPergunta(int idExercicio, int idAutor, String nomeAutor, int idExercicioClasse, int linha, boolean publico, int idLeitor, String texto) {
        try {

            Date dataPergunta = new Date();

            MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
            MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();

            ExercicioClasseMarcacao classeMarcacao = this.getByIdExercicioClasse(idExercicioClasse, linha, ETipoMarcacao.DUVIDA);
            if (classeMarcacao == null) {
                classeMarcacao = new ExercicioClasseMarcacao();
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(dataPergunta);
                classeMarcacao.setIdAutor(idAutor);
                classeMarcacao.setIdExercicioClasse(idExercicioClasse);
                classeMarcacao.setIdTipoMarcacao(ETipoMarcacao.DUVIDA);
                classeMarcacao.setLinhaInicio(linha);
                classeMarcacao.setAnotacao("");
                this.insert(classeMarcacao);
            }

            MensagemCabecalho mensagemCabecalho = repoMensagemCabecalho.getByIdExercicioClasseMarcacao(classeMarcacao.getId());
            if (mensagemCabecalho == null) {
                mensagemCabecalho = new MensagemCabecalho();
                mensagemCabecalho.setAtivo(true);
                mensagemCabecalho.setDataCadastro(dataPergunta);
                mensagemCabecalho.setIdExercicioClasseMarcacao(classeMarcacao.getId());
                mensagemCabecalho.setPublico(publico);
                repoMensagemCabecalho.insert(mensagemCabecalho);

                MensagemLeitor mensagemLeitor = new MensagemLeitor();
                mensagemLeitor.setAtivo(true);
                mensagemLeitor.setIdLeitor(idLeitor);
                mensagemLeitor.setIdMensagemCabecalho(mensagemCabecalho.getId());
                mensagemLeitor.setTipoLeitor(ETipoLeitor.DESTINATARIO);
                repoMensagemLeitor.insert(mensagemLeitor);

                mensagemLeitor = new MensagemLeitor();
                mensagemLeitor.setAtivo(true);
                mensagemLeitor.setDataUltimaLeitura(dataPergunta);
                mensagemLeitor.setIdLeitor(idAutor);
                mensagemLeitor.setIdMensagemCabecalho(mensagemCabecalho.getId());
                mensagemLeitor.setTipoLeitor(ETipoLeitor.REMETENTE);
                repoMensagemLeitor.insert(mensagemLeitor);
            } else {
                repoMensagemLeitor.atualizarDataLeitura(idAutor, ETipoLeitor.REMETENTE, dataPergunta);
                repoMensagemLeitor.atualizarDataLeitura(idAutor, ETipoLeitor.DESTINATARIO, dataPergunta);
            }

            MensagemService repoMensagem = new MensagemService();
            Mensagem mensagem = new Mensagem();
            mensagem.setAtivo(true);
            mensagem.setDataCadastro(dataPergunta);
            mensagem.setIdMensagemCabecalho(mensagemCabecalho.getId());
            mensagem.setIdPessoa(idAutor);
            mensagem.setTexto(texto);
            repoMensagem.insert(mensagem);

            PessoaService pessoaService = new PessoaService();
            Pessoa destinatario = pessoaService.getById(idLeitor);

            String html = "<p>Olá #NOME#,</p>\n"
                    + "<p>Você recebeu uma nova mensagem de #AUTOR#.<br>Acesse o <i>feeper</i> para visualizá-la.</p>\n"
                    + "<p>Endereço: <a href=\"" + Util.serverUrl + "\">" + Util.serverUrl + "</a></p>";
            html = html.replaceAll("#NOME#", destinatario.getNome());
            html = html.replaceAll("#AUTOR#", nomeAutor);

            Util.sendMail(destinatario.getEmail(), "Nova mensagem recebida!", html);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<Object> getLinhasDuvida(int idExercicioClasse) {
        return getLinhasMarcadas(idExercicioClasse, ETipoMarcacao.DUVIDA);
    }

    public List<Object> getLinhasAnotacao(int idExercicioClasse) {
        return getLinhasMarcadas(idExercicioClasse, ETipoMarcacao.ANOTACAO);
    }

    private List<Object> getLinhasMarcadas(int idExercicioClasse, int idTipoMarcacao) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select LinhaInicio from ExercicioClasseMarcacao where IdExercicioClasse = :idExercicioClasse and Ativo = 1 and IdTipoMarcacao = :idTipoMarcacao ");
            query.setInteger("idExercicioClasse", idExercicioClasse);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
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
