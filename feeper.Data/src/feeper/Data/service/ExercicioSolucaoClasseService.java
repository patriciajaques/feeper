/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ClasseMarcacao;
import feeper.Data.entity.ExercicioSolucaoClasse;
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
 * @author gilvani
 */
public class ExercicioSolucaoClasseService extends HibernateUtil<ExercicioSolucaoClasse> {

    public ExercicioSolucaoClasseService() {
        super(ExercicioSolucaoClasse.class);
    }
    
    public List<ExercicioSolucaoClasse> getAllByIdExercicio(int idExercicio, int idAluno) {
        try {

            SQLQuery query = query("select * from ExercicioSolucaoClasse where IdExercicio = :idExercicio and IdAluno = :idAluno").addEntity(ExercicioSolucaoClasse.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Object getAnotacao(int idExercicio, int idClasse, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  CM.DataCadastro, "
                    + "  CM.Anotacao, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ClasseMarcacao CM "
                    + "  inner join ExercicioSolucaoClasse CL "
                    + "  on CL.ID = CM.IdClasse "
                    + "where "
                    + "  CL.ID = :idClasse "
                    + "  and CL.IdExercicio = :idExercicio "
                    + "  and CM.LinhaInicio = :linha "
                    + "  and CM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and CM.Ativo = 1");
            query.addScalar("DataCadastro", TimestampType.INSTANCE);
            query.addScalar("Anotacao", StringType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idClasse", idClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.ANOTACAO);

            return query.list().get(0);

        } catch (Exception e) {
            return null;
        }
    }

    public List<Object> getMarcacaoAutor(int idExercicio, int idAutor, int idClasse, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ClasseMarcacao CM "
                    + "  inner join ExercicioSolucaoClasse CL "
                    + "  on CL.ID = CM.IdClasse "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdClasseMarcacao = CM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  CL.ID = :idClasse "
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
            query.setInteger("idClasse", idClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            return query.list();

        } catch (Exception e) {
            return null;
        }
    }

    public List<Object> getMarcacaoLeitor(int idExercicio, int idClasse, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  CM.LinhaInicio "
                    + "from "
                    + "  ClasseMarcacao CM "
                    + "  inner join ExercicioSolucaoClasse CL "
                    + "  on CL.ID = CM.IdClasse "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdClasseMarcacao = CM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  CL.ID = :idClasse "
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
            query.setInteger("idClasse", idClasse);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            return query.list();

        } catch (Exception e) {
            return null;
        }
    }

    public boolean inserirAnotacao(int idExercicio, int idAutor, int idClasse, int linha, boolean publico, String texto) {
        try {

            ClasseMarcacaoService repoClasseMarcacao = new ClasseMarcacaoService();
            ClasseMarcacao classeMarcacao = repoClasseMarcacao.getByIdClasse(idClasse, linha, ETipoMarcacao.ANOTACAO);

            if (classeMarcacao == null) {
                classeMarcacao = new ClasseMarcacao();
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(new Date());
                classeMarcacao.setIdAutor(idAutor);
                classeMarcacao.setIdClasse(idClasse);
                classeMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                classeMarcacao.setLinhaInicio(linha);
                classeMarcacao.setAnotacao(texto);
                return repoClasseMarcacao.insert(classeMarcacao);
            } else {
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(new Date());
                classeMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                classeMarcacao.setAnotacao(texto);
                return repoClasseMarcacao.update(classeMarcacao);
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean inserirPergunta(int idExercicio, int idAutor, String nomeAutor, int idClasse, int linha, boolean publico, int idLeitor, String texto) {
        try {

            Date dataPergunta = new Date();

            ClasseMarcacaoService repoClasseMarcacao = new ClasseMarcacaoService();
            MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
            MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();

            ClasseMarcacao classeMarcacao = repoClasseMarcacao.getByIdClasse(idClasse, linha, ETipoMarcacao.DUVIDA);
            if (classeMarcacao == null) {
                classeMarcacao = new ClasseMarcacao();
                classeMarcacao.setAtivo(true);
                classeMarcacao.setDataCadastro(dataPergunta);
                classeMarcacao.setIdAutor(idAutor);
                classeMarcacao.setIdClasse(idClasse);
                classeMarcacao.setIdTipoMarcacao(ETipoMarcacao.DUVIDA);
                classeMarcacao.setLinhaInicio(linha);
                classeMarcacao.setAnotacao("");
                repoClasseMarcacao.insert(classeMarcacao);
            }

            MensagemCabecalho mensagemCabecalho = repoMensagemCabecalho.getByIdClasseMarcacao(classeMarcacao.getId());
            if (mensagemCabecalho == null) {
                mensagemCabecalho = new MensagemCabecalho();
                mensagemCabecalho.setAtivo(true);
                mensagemCabecalho.setDataCadastro(dataPergunta);
                mensagemCabecalho.setIdClasseMarcacao(classeMarcacao.getId());
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
                    + "<p>Endereço: <a href=\"http://feeper.jelasticlw.com.br\">http://feeper.jelasticlw.com.br</a></p>";
            html = html.replaceAll("#NOME#", destinatario.getNome());
            html = html.replaceAll("#AUTOR#", nomeAutor);

            Util.sendMail(destinatario.getEmail(), "Nova mensagem recebida!", html);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
