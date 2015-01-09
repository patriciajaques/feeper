/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.model.EStatusResposta;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.IntegerResult;
import java.util.Date;
import org.hibernate.SQLQuery;

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoService extends HibernateUtil<ExercicioSolucao> {

    public static final String CODIGO_PADRAO_CLASSE = "/* package qualquer; // Não coloque nome no package */\n"
            + "\n"
            + "import java.util.*;\n"
            + "import java.lang.*;\n"
            + "import java.io.*;\n"
            + "\n"
            + "class #@#CLASSE#@#\n"
            + "{\n"
            + "   // Coloque aqui o seu código\n"
            + "}";
    
    public ExercicioSolucaoService() {
        super(ExercicioSolucao.class);
    }

    public ExercicioSolucao getLastByIdExercicio(int idExercicio, int idAluno) {
        try {

            SQLQuery query = query("select * from ExercicioSolucao where IdExercicio = :idExercicio and IdAluno = :idAluno order by ID desc limit 1").addEntity(ExercicioSolucao.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);

            return (ExercicioSolucao) query.list().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean salvarSolucao(int idPessoa, int idExercicio, IntegerResult idSolucao) {
        try {

            ExercicioSolucao solucao = new ExercicioSolucao();
            solucao.setDataCadastro(new Date());
            solucao.setIdAluno(idPessoa);
            solucao.setIdExercicio(idExercicio);
            solucao.setIdStatus(EStatusResposta.AGUARDANDO);

            if (insert(solucao)) {            
                idSolucao.setResult(solucao.getId());
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
