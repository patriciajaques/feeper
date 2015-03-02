/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;

import feeper.Data.entity.ExercicioClasse;
import feeper.Data.entity.ExercicioSolucao;
import feeper.Data.entity.ExercicioSolucaoClasse;
import feeper.Data.model.EStatusSolucao;
import feeper.Data.model.HibernateUtil;
import feeper.Data.model.IntegerResult;
import java.util.Date;
import java.util.List;
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

            ExercicioSolucao solucao = (ExercicioSolucao) query.list().get(0);

            ExercicioSolucaoErroService repoErros = new ExercicioSolucaoErroService();
            solucao.setErros(repoErros.getAllByIdSolucao(solucao.getId()));

            return solucao;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ExercicioSolucao> getByIdExercicio(int idExercicio, int idAluno) {
        try {

            SQLQuery query = query("select * from ExercicioSolucao where IdExercicio = :idExercicio and IdAluno = :idAluno order by ID desc").addEntity(ExercicioSolucao.class);
            query.setInteger("idExercicio", idExercicio);
            query.setInteger("idAluno", idAluno);

            List<ExercicioSolucao> data = query.list();

            ExercicioSolucaoErroService repoErros = new ExercicioSolucaoErroService();
            for (ExercicioSolucao solucao : data) {
                solucao.setErros(repoErros.getAllByIdSolucao(solucao.getId()));
            }

            return data;

        } catch (Exception e) {
            return null;
        }
    }

    public void salvarSolucao(int idAluno, int idExercicio, IntegerResult idSolucao) {

        ExercicioSolucao solucao = new ExercicioSolucao();
        solucao.setDataCadastro(new Date());
        solucao.setIdAluno(idAluno);
        solucao.setIdExercicio(idExercicio);
        solucao.setIdStatus(EStatusSolucao.AGUARDANDO);

        if (insert(solucao)) {
            idSolucao.setResult(solucao.getId());

            //realiza uma cópia das classes do exercício para a solução para manter um histórico
            ExercicioClasseService repoClassesExercicio = new ExercicioClasseService();
            ExercicioSolucaoClasseService repoClassesSolucao = new ExercicioSolucaoClasseService();

            List<ExercicioClasse> classesExercicio = repoClassesExercicio.getAllByIdExercicio(idExercicio, idAluno);

            for (ExercicioClasse classe : classesExercicio) {

                ExercicioSolucaoClasse classeSolucao = new ExercicioSolucaoClasse();
                classeSolucao.setIdSolucao(solucao.getId());
                classeSolucao.setNomeClasse(classe.getNomeClasse());
                classeSolucao.setCodigo(classe.getCodigo());
                repoClassesSolucao.insert(classeSolucao);
            }
        }
    }
}
