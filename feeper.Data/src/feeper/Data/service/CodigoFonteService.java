/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.CodigoFonte;
import feeper.Data.entity.CodigoFonteMarcacao;
import feeper.Data.entity.Mensagem;
import feeper.Data.entity.MensagemCabecalho;
import feeper.Data.entity.MensagemLeitor;
import feeper.Data.model.ETipoLeitor;
import feeper.Data.model.ETipoMarcacao;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.MarcacaoMensagem;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CodigoFonteService extends HibernateUtil<CodigoFonte> {
    
    public static final String CODIGO_PADRAO_PRINCIPAL = "/* package qualquer; // Não coloque nome no package */\n" +
                                                        "\n" +
                                                        "import java.util.*;\n" +
                                                        "import java.lang.*;\n" +
                                                        "import java.io.*;\n" +
                                                        "\n" +
                                                        "/* O nome da classe deve ser \"Solution\" */\n" +
                                                        "class Solution\n" +
                                                        "{\n" +
                                                        "    public static void main (String[] args) throws java.lang.Exception\n" +
                                                        "    {\n" +
                                                        "        // Coloque aqui o seu código\n" +
                                                        "    }\n" +
                                                        "}";
    public static final String CODIGO_PADRAO_CLASSE = "/* package qualquer; // Não coloque nome no package */\n" +
                                                        "\n" +
                                                        "import java.util.*;\n" +
                                                        "import java.lang.*;\n" +
                                                        "import java.io.*;\n" +
                                                        "\n" +
                                                        "class #@#CLASSE#@#\n" +
                                                        "{\n" +
                                                        "   // Coloque aqui o seu código\n" +
                                                        "}";
    
    public CodigoFonteService() {
        super(CodigoFonte.class);
    }
    
    public List<CodigoFonte> getAllByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdExercicio = :idExercicio and IdAutor = :idAutor order by Principal desc ").addEntity(CodigoFonte.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }
    
    public CodigoFonte getPrincipalByIdExercicio(int idExercicio, int idAutor)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdExercicio = :idExercicio and IdAutor = :idAutor and Principal = 1").addEntity(CodigoFonte.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            return (CodigoFonte)query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean deleteNotIn(int idExercicio, int idAutor, String concatIds)
    {
        Transaction transaction_;
        Session session_;
        
        session_ = getSession();
        transaction_ = session_.beginTransaction();
        
        try {
            SQLQuery query = session_.createSQLQuery("delete from CodigoFonte where ID not in ("+ concatIds +") and IdExercicio = "+ idExercicio +" and IdAutor = " + idAutor);
            
            query.executeUpdate();
            
            return true;
        } catch (HibernateException e) { 
            transaction_.rollback();
            return false;
        } finally {
            session_.close();
        }
    }
    
    public Object getByIdExercicio(int idExercicio, int idAutor, int idCodigoFonte)
    {
        try {
            
            SQLQuery query = query("select ID, Fonte, Classe, Principal, '' AS Marcacao, '' AS Anotacao, Favorito from CodigoFonte where IdExercicio = :idExercicio and IdAutor = :idAutor and ID = :idCodigoFonte ");
            query.addScalar("ID", Hibernate.INTEGER);
            query.addScalar("Fonte", Hibernate.STRING);
            query.addScalar("Classe", Hibernate.STRING);
            query.addScalar("Principal", Hibernate.BOOLEAN);
            query.addScalar("Marcacao", Hibernate.STRING);
            query.addScalar("Anotacao", Hibernate.STRING);
            query.addScalar("Favorito", Hibernate.BOOLEAN);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            return query.list().get(0);
        } catch (Exception e) {
            return "";
        }
    }
    
    public CodigoFonte getById(int idExercicio, int idAutor, int idCodigoFonte)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdExercicio = :idExercicio and IdAutor = :idAutor and ID = :idCodigoFonte ").addEntity(CodigoFonte.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            
            return (CodigoFonte)query.list().get(0);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public CodigoFonte getById(int idExercicio, int idCodigoFonte)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdExercicio = :idExercicio and ID = :idCodigoFonte ").addEntity(CodigoFonte.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            
            return (CodigoFonte)query.list().get(0);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public void inserirCodigoPrincipal(int idExercicio, int idPessoa)
    {
        if (getPrincipalByIdExercicio(idExercicio, idPessoa) == null)
        {
            CodigoFonte entity = new CodigoFonte();
            entity.setIdAutor(idPessoa);
            entity.setIdExercicio(idExercicio);
            entity.setClasse("Solution.java");
            entity.setFonte(CODIGO_PADRAO_PRINCIPAL);
            entity.setPrincipal(true);
            entity.setDataCadastro(new Date());
            insert(entity);
        }
    }
    
    public Object getAnotacao(int idExercicio, int idCodigoFonte, int linha)
    {
        try {
            
            SQLQuery query = query("select " +
                                "  CFM.DataCadastro, " +
                                "  CFM.Anotacao, " +
                                "  CFM.LinhaInicio " +
                                "from " +
                                "  CodigoFonteMarcacao CFM " +
                                "  inner join CodigoFonte CF " +
                                "  on CF.ID = CFM.IdCodigoFonte " +
                                "where " +
                                "  CF.ID = :idCodigoFonte " +
                                "  and CF.IdExercicio = :idExercicio " +
                                "  and CFM.LinhaInicio = :linha " +
                                "  and CFM.IdTipoMarcacao = :idTipoMarcacao " +
                                "  and CFM.Ativo = 1");
            query.addScalar("DataCadastro", Hibernate.TIMESTAMP);
            query.addScalar("Anotacao", Hibernate.STRING);
            query.addScalar("LinhaInicio", Hibernate.INTEGER);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.ANOTACAO);
            
            return query.list().get(0);
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Object> getMarcacaoAutor(int idExercicio, int idAutor, int idCodigoFonte, int linha)
    {
        try {
            
            SQLQuery query = query("select " +
                                "  M.IdPessoa, " +
                                "  P.Nome, " +
                                "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, " +
                                "  M.Texto, " +
                                "  MC.Publico, " +
                                "  CFM.LinhaInicio " +
                                "from " +
                                "  CodigoFonteMarcacao CFM " +
                                "  inner join CodigoFonte CF " +
                                "  on CF.ID = CFM.IdCodigoFonte " +
                                "  inner join MensagemCabecalho MC " +
                                "  on MC.IdCodigoFonteMarcacao = CFM.ID " +
                                "  and MC.Ativo = 1 " +
                                "  inner join Mensagem M " +
                                "  on M.IdMensagemCabecalho = MC.ID " +
                                "  and M.Ativo = 1 " +
                                "  inner join Pessoa P " +
                                "  on P.ID = M.IdPessoa " +
                                "  and P.Ativo = 1 " +
                                "where " +
                                "  CF.ID = :idCodigoFonte " +
                                "  and CF.IdExercicio = :idExercicio " +
                                "  and CF.IdAutor = :idAutor " +
                                "  and CFM.LinhaInicio = :linha " +
                                "  and CFM.IdTipoMarcacao = :idTipoMarcacao " +
                                "  and CFM.Ativo = 1 " + 
                                "order by M.ID");
            query.addScalar("IdPessoa", Hibernate.INTEGER);
            query.addScalar("Nome", Hibernate.STRING);
            query.addScalar("DataCadastro", Hibernate.STRING);
            query.addScalar("Texto", Hibernate.STRING);
            query.addScalar("Publico", Hibernate.BOOLEAN);
            query.addScalar("LinhaInicio", Hibernate.INTEGER);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAutor", idAutor);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);
            
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Object> getMarcacaoLeitor(int idExercicio, int idCodigoFonte, int linha)
    {
        try {
            
            SQLQuery query = query("select " +
                                "  M.IdPessoa, " +
                                "  P.Nome, " +
                                "  GET_TIMEDURATION(M.DataCadastro) AS DataCadastro, " +
                                "  M.Texto, " +
                                "  MC.Publico, " +
                                "  CFM.LinhaInicio " +
                                "from " +
                                "  CodigoFonteMarcacao CFM " +
                                "  inner join CodigoFonte CF " +
                                "  on CF.ID = CFM.IdCodigoFonte " +
                                "  inner join MensagemCabecalho MC " +
                                "  on MC.IdCodigoFonteMarcacao = CFM.ID " +
                                "  and MC.Ativo = 1 " +
                                "  inner join Mensagem M " +
                                "  on M.IdMensagemCabecalho = MC.ID " +
                                "  and M.Ativo = 1 " +
                                "  inner join Pessoa P " +
                                "  on P.ID = M.IdPessoa " +
                                "  and P.Ativo = 1 " +
                                "where " +
                                "  CF.ID = :idCodigoFonte " +
                                "  and CF.IdExercicio = :idExercicio " +
                                "  and CFM.LinhaInicio = :linha " +
                                "  and CFM.IdTipoMarcacao = :idTipoMarcacao " +
                                "  and CFM.Ativo = 1 " + 
                                "order by M.ID");
            query.addScalar("IdPessoa", Hibernate.INTEGER);
            query.addScalar("Nome", Hibernate.STRING);
            query.addScalar("DataCadastro", Hibernate.STRING);
            query.addScalar("Texto", Hibernate.STRING);
            query.addScalar("Publico", Hibernate.BOOLEAN);
            query.addScalar("LinhaInicio", Hibernate.INTEGER);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idCodigoFonte", idCodigoFonte);
            query.setInteger("linha", linha);
            query.setInteger("idTipoMarcacao", ETipoMarcacao.DUVIDA);
            
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean inserirAnotacao(int idExercicio, int idAutor, int idCodigoFonte, int linha, boolean publico, String texto)
    {
        try {
            
            CodigoFonteMarcacaoService repoCodigoFonteMarcacao = new CodigoFonteMarcacaoService();
            CodigoFonteMarcacao codigoFonteMarcacao = repoCodigoFonteMarcacao.getByIdCodigoFonte(idCodigoFonte, linha, ETipoMarcacao.ANOTACAO);
            
            if (codigoFonteMarcacao == null)
            {
                codigoFonteMarcacao = new CodigoFonteMarcacao();
                codigoFonteMarcacao.setAtivo(true);
                codigoFonteMarcacao.setDataCadastro(new Date());
                codigoFonteMarcacao.setIdAutor(idAutor);
                codigoFonteMarcacao.setIdCodigoFonte(idCodigoFonte);
                codigoFonteMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                codigoFonteMarcacao.setLinhaInicio(linha);
                codigoFonteMarcacao.setAnotacao(texto);
                return repoCodigoFonteMarcacao.insert(codigoFonteMarcacao);
            }
            else
            {
                codigoFonteMarcacao.setAtivo(true);
                codigoFonteMarcacao.setDataCadastro(new Date());
                codigoFonteMarcacao.setIdTipoMarcacao(ETipoMarcacao.ANOTACAO);
                codigoFonteMarcacao.setAnotacao(texto);
                return repoCodigoFonteMarcacao.update(codigoFonteMarcacao);
            }
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean inserirPergunta(int idExercicio, int idAutor, int idCodigoFonte, int linha, boolean publico, int idLeitor, String texto)
    {
        try {
            
            CodigoFonteMarcacaoService repoCodigoFonteMarcacao = new CodigoFonteMarcacaoService();
            CodigoFonteMarcacao codigoFonteMarcacao = repoCodigoFonteMarcacao.getByIdCodigoFonte(idCodigoFonte, linha, ETipoMarcacao.DUVIDA);
            
            if (codigoFonteMarcacao == null)
            {
                codigoFonteMarcacao = new CodigoFonteMarcacao();
                codigoFonteMarcacao.setAtivo(true);
                codigoFonteMarcacao.setDataCadastro(new Date());
                codigoFonteMarcacao.setIdAutor(idAutor);
                codigoFonteMarcacao.setIdCodigoFonte(idCodigoFonte);
                codigoFonteMarcacao.setIdTipoMarcacao(ETipoMarcacao.DUVIDA);
                codigoFonteMarcacao.setLinhaInicio(linha);
                codigoFonteMarcacao.setAnotacao("");
                repoCodigoFonteMarcacao.insert(codigoFonteMarcacao);
            }
            
            MensagemCabecalhoService repoMensagemCabecalho = new MensagemCabecalhoService();
            MensagemCabecalho mensagemCabecalho = repoMensagemCabecalho.getByIdCodigoFonteMarcacao(codigoFonteMarcacao.getId());
            
            if (mensagemCabecalho == null)
            {
                mensagemCabecalho = new MensagemCabecalho();
                mensagemCabecalho.setAtivo(true);
                mensagemCabecalho.setDataCadastro(new Date());
                mensagemCabecalho.setIdCodigoFonteMarcacao(codigoFonteMarcacao.getId());
                mensagemCabecalho.setPublico(publico);
                repoMensagemCabecalho.insert(mensagemCabecalho);
                
                MensagemLeitorService repoMensagemLeitor = new MensagemLeitorService();
                MensagemLeitor mensagemLeitor = new MensagemLeitor();
                mensagemLeitor.setAtivo(true);
                mensagemLeitor.setIdLeitor(idLeitor);
                mensagemLeitor.setIdMensagemCabecalho(mensagemCabecalho.getId());
                mensagemLeitor.setTipoLeitor(ETipoLeitor.DESTINATARIO);
                repoMensagemLeitor.insert(mensagemLeitor);
                
                mensagemLeitor = new MensagemLeitor();
                mensagemLeitor.setAtivo(true);
                mensagemLeitor.setDataUltimaLeitura(new Date());
                mensagemLeitor.setIdLeitor(idAutor);
                mensagemLeitor.setIdMensagemCabecalho(mensagemCabecalho.getId());
                mensagemLeitor.setTipoLeitor(ETipoLeitor.REMETENTE);
                repoMensagemLeitor.insert(mensagemLeitor);
            }
            
            MensagemService repoMensagem = new MensagemService();
            Mensagem mensagem = new Mensagem();
            mensagem.setAtivo(true);
            mensagem.setDataCadastro(new Date());
            mensagem.setIdMensagemCabecalho(mensagemCabecalho.getId());
            mensagem.setIdPessoa(idAutor);
            mensagem.setTexto(texto);
            repoMensagem.insert(mensagem);
            
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<CodigoFonte> getMeusCodigosFavoritos(int idPessoa)
    {
        try {
            
            SQLQuery query = query("select * from CodigoFonte where IdAutor = :idPessoa and Favorito = 1 order by DataCadastro desc").addEntity(CodigoFonte.class);
            query.setInteger("idPessoa", idPessoa);
            return query.list();
            
        } catch (Exception e) {
            return null;
        }
    }
    
}
