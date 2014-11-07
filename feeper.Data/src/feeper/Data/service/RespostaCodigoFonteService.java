/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.Mensagem;
import feeper.Data.entity.MensagemCabecalho;
import feeper.Data.entity.MensagemLeitor;
import feeper.Data.entity.Pessoa;
import feeper.Data.entity.RespostaCodigoFonte;
import feeper.Data.entity.RespostaCodigoFonteMarcacao;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.model.ETipoMarcacao;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.Util;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

/**
 *
 * @author fabioalves
 */
public class RespostaCodigoFonteService extends HibernateUtil<RespostaCodigoFonte> {

    public RespostaCodigoFonteService() {
        super(RespostaCodigoFonte.class);
    }

    public Object getAnotacao(int idExercicio, int idRespostaCodigoFonte, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  RCFM.DataCadastro, "
                    + "  RCFM.Anotacao, "
                    + "  RCFM.LinhaInicio "
                    + "from "
                    + "  RespostaCodigoFonteMarcacao RCFM "
                    + "  inner join RespostaCodigoFonte RCF "
                    + "  on RCF.ID = RCFM.IdRespostaCodigoFonte "
                    + "  inner join Resposta R "
                    + "  on R.ID = RCF.IdResposta "
                    + "where "
                    + "  RCFM.IdRespostaCodigoFonte = :idRespostaCodigoFonte "
                    + "  and R.IdExercicio = :idExercicio "
                    + "  and RCFM.LinhaInicio = :linha "
                    + "  and RCFM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and RCFM.Ativo = 1");
            query.addScalar("DataCadastro", TimestampType.INSTANCE);
            query.addScalar("Anotacao", StringType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idRespostaCodigoFonte", idRespostaCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.ANOTACAO);

            return query.list().get(0);

        } catch (Exception e) {
            return null;
        }
    }

    public List<Object> getMarcacaoAutor(int idExercicio, int idAutor, int idRespostaCodigoFonte, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  RCFM.LinhaInicio "
                    + "from "
                    + "  RespostaCodigoFonteMarcacao RCFM "
                    + "  inner join RespostaCodigoFonte RCF "
                    + "  on RCF.ID = RCFM.IdRespostaCodigoFonte "
                    + "  inner join Resposta R "
                    + "  on R.ID = RCF.IdResposta "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdRespostaCodigoFonteMarcacao = RCFM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  RCFM.IdRespostaCodigoFonte = :idRespostaCodigoFonte "
                    + "  and R.IdExercicio = :idExercicio "
                    + "  and R.IdAutor = :idAutor "
                    + "  and RCFM.LinhaInicio = :linha "
                    + "  and RCFM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and RCFM.Ativo = 1 "
                    + "order by M.ID");
            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("Nome", StringType.INSTANCE);
            query.addScalar("DataCadastro", StringType.INSTANCE);
            query.addScalar("Texto", StringType.INSTANCE);
            query.addScalar("Publico", BooleanType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            query.setInteger("idRespostaCodigoFonte", idRespostaCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            return query.list();

        } catch (Exception e) {
            return null;
        }
    }

    public List<Object> getMarcacaoLeitor(int idExercicio, int idRespostaCodigoFonte, int linha) {
        try {

            SQLQuery query = query("select "
                    + "  M.IdPessoa, "
                    + "  P.Nome, "
                    + "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, "
                    + "  M.Texto, "
                    + "  MC.Publico, "
                    + "  RCFM.LinhaInicio "
                    + "from "
                    + "  RespostaCodigoFonteMarcacao RCFM "
                    + "  inner join RespostaCodigoFonte RCF "
                    + "  on RCF.ID = RCFM.IdRespostaCodigoFonte "
                    + "  inner join Resposta R "
                    + "  on R.ID = RCF.IdResposta "
                    + "  inner join MensagemCabecalho MC "
                    + "  on MC.IdRespostaCodigoFonteMarcacao = RCFM.ID "
                    + "  and MC.Ativo = 1 "
                    + "  inner join Mensagem M "
                    + "  on M.IdMensagemCabecalho = MC.ID "
                    + "  and M.Ativo = 1 "
                    + "  inner join Pessoa P "
                    + "  on P.ID = M.IdPessoa "
                    + "  and P.Ativo = 1 "
                    + "where "
                    + "  RCFM.IdRespostaCodigoFonte = :idRespostaCodigoFonte "
                    + "  and R.IdExercicio = :idExercicio "
                    + "  and RCFM.LinhaInicio = :linha "
                    + "  and RCFM.IdTipoMarcacao = :idTipoMarcacao "
                    + "  and RCFM.Ativo = 1 "
                    + "order by M.ID");
            query.addScalar("IdPessoa", IntegerType.INSTANCE);
            query.addScalar("Nome", StringType.INSTANCE);
            query.addScalar("DataCadastro", StringType.INSTANCE);
            query.addScalar("Texto", StringType.INSTANCE);
            query.addScalar("Publico", BooleanType.INSTANCE);
            query.addScalar("LinhaInicio", IntegerType.INSTANCE);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idRespostaCodigoFonte", idRespostaCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);

            return query.list();

        } catch (Exception e) {
            return null;
        }
    }

    public boolean inserirAnotacao(int idExercicio, int idAutor, int idRespostaCodigoFonte, int linha, String texto) {
        try {

            RespostaCodigoFonteMarcacaoService repoRespostaCodigoFonteMarcacao = new RespostaCodigoFonteMarcacaoService();
            RespostaCodigoFonteMarcacao respostaCodigoFonteMarcacao = repoRespostaCodigoFonteMarcacao.getByIdRespostaCodigoFonte(idRespostaCodigoFonte, linha, ETipoMarcacao.ANOTACAO);

            if (respostaCodigoFonteMarcacao == null) {
                respostaCodigoFonteMarcacao = new RespostaCodigoFonteMarcacao();
                respostaCodigoFonteMarcacao.setAtivo(true);
                respostaCodigoFonteMarcacao.setDataCadastro(new Date());
                respostaCodigoFonteMarcacao.setIdAutor(idAutor);
                respostaCodigoFonteMarcacao.setIdRespostaCodigoFonte(idRespostaCodigoFonte);
                respostaCodigoFonteMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                respostaCodigoFonteMarcacao.setLinhaInicio(linha);
                respostaCodigoFonteMarcacao.setAnotacao("");
                return repoRespostaCodigoFonteMarcacao.insert(respostaCodigoFonteMarcacao);
            } else {
                respostaCodigoFonteMarcacao.setAtivo(true);
                respostaCodigoFonteMarcacao.setDataCadastro(new Date());
                respostaCodigoFonteMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                respostaCodigoFonteMarcacao.setAnotacao(texto);
                return repoRespostaCodigoFonteMarcacao.update(respostaCodigoFonteMarcacao);
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean inserirPergunta(int idExercicio, int idAutor, String nomeAutor, int idRespostaCodigoFonte, int linha, int idLeitor, String texto) {
        try {

            Date dataPergunta = new Date();

            RespostaCodigoFonteMarcacaoService repoRespostaCodigoFonteMarcacao = new RespostaCodigoFonteMarcacaoService();
            MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
            MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();

            RespostaCodigoFonteMarcacao respostaCodigoFonteMarcacao = repoRespostaCodigoFonteMarcacao.getByIdRespostaCodigoFonte(idRespostaCodigoFonte, linha, ETipoMarcacao.DUVIDA);
            if (respostaCodigoFonteMarcacao == null) {
                respostaCodigoFonteMarcacao = new RespostaCodigoFonteMarcacao();
                respostaCodigoFonteMarcacao.setAtivo(true);
                respostaCodigoFonteMarcacao.setDataCadastro(dataPergunta);
                respostaCodigoFonteMarcacao.setIdAutor(idAutor);
                respostaCodigoFonteMarcacao.setIdRespostaCodigoFonte(idRespostaCodigoFonte);
                respostaCodigoFonteMarcacao.setIdTipoMarcacao(ETipoMarcacao.DUVIDA);
                respostaCodigoFonteMarcacao.setLinhaInicio(linha);
                respostaCodigoFonteMarcacao.setAnotacao("");
                repoRespostaCodigoFonteMarcacao.insert(respostaCodigoFonteMarcacao);
            }

            MensagemCabecalho mensagemCabecalho = repoMensagemCabecalho.getByIdRespostaCodigoFonteMarcacao(respostaCodigoFonteMarcacao.getId());
            if (mensagemCabecalho == null) {
                mensagemCabecalho = new MensagemCabecalho();
                mensagemCabecalho.setAtivo(true);
                mensagemCabecalho.setDataCadastro(dataPergunta);
                mensagemCabecalho.setIdRespostaCodigoFonteMarcacao(respostaCodigoFonteMarcacao.getId());
                mensagemCabecalho.setPublico(false);
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
