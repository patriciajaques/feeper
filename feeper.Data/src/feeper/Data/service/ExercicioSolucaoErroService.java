/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioSolucaoErro;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoErroService extends HibernateUtil<ExercicioSolucaoErro> {

    public ExercicioSolucaoErroService() {
        super(ExercicioSolucaoErro.class);
    }

    public List<ExercicioSolucaoErro> getAllByIdSolucao(int idSolucao) {
        Transaction transaction = currentSession().beginTransaction();
        try {

            SQLQuery query = currentSession().createSQLQuery("select * from ExercicioSolucaoErro where IdSolucao = :idSolucao").addEntity(ExercicioSolucaoErro.class);
            query.setInteger("idSolucao", idSolucao);
            List<ExercicioSolucaoErro> data = query.list();
            transaction.commit();
            return friendLyMessage(data);
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    
    public List<ExercicioSolucaoErro> friendLyMessage(List<ExercicioSolucaoErro> list){
        for(ExercicioSolucaoErro sol : list){
            if(sol.getMensagemErro()!=null){
                String fMensagem = sol.getMensagemErro();
                fMensagem = fMensagem.replace("expected:", "Valor esperado é: ");
                fMensagem = fMensagem.replace("but was:", "Mas é: ");
                fMensagem = fMensagem.replace("java.lang.AssertionError:", "");
                fMensagem = fMensagem.replace("cannot find symbol  symbol:   method", "Metodo nao encontrado: ");
                Integer sizeOf = fMensagem.indexOf("at org.junit.Assert.fail");
                if(sizeOf !=null && sizeOf != -1){
                    String mReplace = fMensagem.substring(sizeOf, fMensagem.length());
                    fMensagem = fMensagem.replace(mReplace, "");
                }
                sol.setMensagemErro(fMensagem);
                
            }
            if(sol.getMensagemPersonalizada()!= null){
                sol.setMensagemPersonalizada(
                    sol.getMensagemPersonalizada().replace("java.lang.AssertionError:", "")
                );
            }
            if(sol.getStaticErrorType()!= null){
                sol.setStaticErrorType(
                    sol.getStaticErrorType().replace("java.lang.AssertionError:", "")
                );
            }
        }
        return list;
    }
}
