/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioSolucaoClasseMarcacao;
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
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

/**
 *
 * @author fabioalves
 */
public class ExercicioSolucaoClasseMarcacaoService extends HibernateUtil<ExercicioSolucaoClasseMarcacao> {

    public ExercicioSolucaoClasseMarcacaoService() {
        super(ExercicioSolucaoClasseMarcacao.class);
    }

    public ExercicioSolucaoClasseMarcacao getByIdExercicioSolucaoClasse(int idExercicioSolucaoClasse, int linha, int idTipoMarcacao) {
        try {
            SQLQuery query = query("select * from ExercicioSolucaoClasseMarcacao "
                    + "where IdExercicioSolucaoClasse = :idExercicioSolucaoClasse "
                    + "and LinhaInicio = :linha "
                    + "and IdTipoMarcacao = :idTipoMarcacao "
                    + "and Ativo = 1 ").addEntity(ExercicioSolucaoClasseMarcacao.class);
            query.setInteger("idExercicioSolucaoClasse", idExercicioSolucaoClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return (ExercicioSolucaoClasseMarcacao) query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public Object getAnotacao(int idExercicioSolucaoClasse, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  CM.DataCadastro, "
                    + "  CM.Anotacao, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ExercicioSolucaoClasseMarcacao CM "
                    + "  inner join ExercicioSolucaoClasse CL "
                    + "  on CL.ID = CM.IdExercicioSolucaoClasse "
                    + "where "
                    + "  CL.ID = :idExercicioSolucaoClasse "
                    + "  and CM.LinhaInicio = :linha "
                    + "  and CM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and CM.Ativo = 1");
            query.addScalar("DataCadastro", TimestampType.INSTANCE);
            query.addScalar("Anotacao", StringType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicioSolucaoClasse", idExercicioSolucaoClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.ANOTACAO);

            return query.list().get(0);

        } catch (Exception e) {
            return null;
        }
    }

    public List<Object> getMarcacaoAutor(int idAutor, int idExercicioSolucaoClasse, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ExercicioSolucaoClasseMarcacao CM "
                    + "  inner join ExercicioSolucaoClasse CL "
                    + "  on CL.ID = CM.IdExercicioSolucaoClasse "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdExercicioSolucaoClasseMarcacao = CM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  CL.ID = :idExercicioSolucaoClasse "
                    + "  and CM.IdAutor = :idAutor "
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
            query.setInteger("idAutor", idAutor);
            query.setInteger("idExercicioSolucaoClasse", idExercicioSolucaoClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            return query.list();

        } catch (Exception e) {
            return null;
        }
    }

    public List<Object> getMarcacaoLeitor(int idExercicioSolucaoClasse, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ExercicioSolucaoClasseMarcacao CM "
                    + "  inner join ExercicioSolucaoClasse CL "
                    + "  on CL.ID = CM.IdExercicioSolucaoClasse "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdExercicioSolucaoClasseMarcacao = CM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  CL.ID = :idExercicioSolucaoClasse "
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
            query.setInteger("idExercicioSolucaoClasse", idExercicioSolucaoClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            return query.list();

        } catch (Exception e) {
            return null;
        }
    }

    public boolean inserirAnotacao(int idAutor, int idExercicioSolucaoClasse, int linha, String texto) {
        try {

            ExercicioSolucaoClasseMarcacao classeMarcacao = this.getByIdExercicioSolucaoClasse(idExercicioSolucaoClasse, linha, ETipoMarcacao.ANOTACAO);

            if (classeMarcacao == null) {
                classeMarcacao = new ExercicioSolucaoClasseMarcacao();
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(new Date());
                classeMarcacao.setIdAutor(idAutor);
                classeMarcacao.setIdExercicioSolucaoClasse(idExercicioSolucaoClasse);
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

    public boolean inserirPergunta(int idAutor, String nomeAutor, int idExercicioSolucaoClasse, int linha, boolean publico, int idLeitor, String texto) {
        try {

            Date dataPergunta = new Date();

            MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
            MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();

            ExercicioSolucaoClasseMarcacao classeMarcacao = this.getByIdExercicioSolucaoClasse(idExercicioSolucaoClasse, linha, ETipoMarcacao.DUVIDA);
            if (classeMarcacao == null) {
                classeMarcacao = new ExercicioSolucaoClasseMarcacao();
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(dataPergunta);
                classeMarcacao.setIdAutor(idAutor);
                classeMarcacao.setIdExercicioSolucaoClasse(idExercicioSolucaoClasse);
                classeMarcacao.setIdTipoMarcacao(ETipoMarcacao.DUVIDA);
                classeMarcacao.setLinhaInicio(linha);
                classeMarcacao.setAnotacao("");
                this.insert(classeMarcacao);
            }

            MensagemCabecalho mensagemCabecalho = repoMensagemCabecalho.getByIdExercicioSolucaoClasseMarcacao(classeMarcacao.getId());
            if (mensagemCabecalho == null) {
                mensagemCabecalho = new MensagemCabecalho();
                mensagemCabecalho.setAtivo(true);
                mensagemCabecalho.setDataCadastro(dataPergunta);
                mensagemCabecalho.setIdExercicioSolucaoClasseMarcacao(classeMarcacao.getId());
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

    public List<Object> getLinhasDuvida(int idExercicioSolucaoClasse) {
        return getLinhasMarcadas(idExercicioSolucaoClasse, ETipoMarcacao.DUVIDA);
    }

    public List<Object> getLinhasAnotacao(int idExercicioSolucaoClasse) {
        return getLinhasMarcadas(idExercicioSolucaoClasse, ETipoMarcacao.ANOTACAO);
    }

    private List<Object> getLinhasMarcadas(int idExercicioSolucaoClasse, int idTipoMarcacao) {
        try {
            SQLQuery query = query("select LinhaInicio from ExercicioSolucaoClasseMarcacao where IdExercicioSolucaoClasse = :idExercicioSolucaoClasse and Ativo = 1 and IdTipoMarcacao = :idTipoMarcacao ");
            query.setInteger("idExercicioSolucaoClasse", idExercicioSolucaoClasse);
            query.setInteger("idTipoMarcacao", idTipoMarcacao);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

}
