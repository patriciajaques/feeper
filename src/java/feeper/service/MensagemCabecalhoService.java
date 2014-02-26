/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.service;

import feeper.entity.MensagemCabecalho;
import feeper.model.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

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
    
    public List<Object> getMensagensDestinatario(int idPessoa)
    {
        try {
            SQLQuery query = query("select " +
                                "  M.IdPessoa, " +
                                "  P.Nome, " +
                                "  M.DataCadastro, " +
                                "  M.Texto, " +
                                "  MC.Publico, " +
                                "  CFM.LinhaInicio, " +
                                "  CFM.IdCodigoFonte " +
                                "from " +
                                "  MensagemCabecalho MC " +
                                "  inner join MensagemLeitor ML " +
                                "  on ML.IdMensagemCabecalho = MC.ID " +
                                "  and ML.IdLeitor = :idPessoa " +
                                "  and ML.Ativo = 1 " +
                                "  and ML.TipoLeitor = 'D' " +
                                "  inner join Mensagem M " +
                                "  on M.IdMensagemCabecalho = MC.ID " +
                                "  and M.Ativo = 1 " +
                                "  inner join Pessoa P " +
                                "  on P.ID = M.IdPessoa " +
                                "  and P.Ativo = 1 " +
                                "  inner join CodigoFonteMarcacao CFM " +
                                "  on CFM.ID = MC.IdCodigoFonteMarcacao " +
                                "  and CFM.Ativo = 1 " +
                                "where " +
                                "  MC.ID = 1 " +
                                "  and MC.Ativo = 1");
            query.addScalar("IdPessoa", Hibernate.INTEGER);
            query.addScalar("Nome", Hibernate.STRING);
            query.addScalar("DataCadastro", Hibernate.DATE);
            query.addScalar("Texto", Hibernate.STRING);
            query.addScalar("Publico", Hibernate.BOOLEAN);
            query.addScalar("LinhaInicio", Hibernate.INTEGER);
            query.addScalar("IdCodigoFonte", Hibernate.INTEGER);
            query.setInteger("idPessoa", idPessoa);
            
            return query.list();
        }
        catch(Exception e) {
            return null;
        }
    }
    
}
