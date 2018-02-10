/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.service;
import feeper.Data.entity.ExercicioPontos;
import feeper.Data.entity.Ranking;
import feeper.Data.model.HibernateUtil;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;

public class RankingService extends HibernateUtil<ExercicioPontos> {
    
    private static String RANKING_BY_TURMA = 
            "select p.id as idAluno, coalesce(sum(ep.pontos),0) as sum_pontos from turmapessoa pp join pessoa p on(pp.idpessoa = p.id) left join exerciciopontos ep on(ep.idAluno = p.id) where pp.idturma = :idTurma group by p.id order by sum_pontos desc";
    
    PessoaService pessoaService;
    public RankingService() {
        super(RankingService.class);
        pessoaService = new PessoaService();
    }    
    
    public List<Ranking> findTurmaRanking(Integer turma) {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery(RANKING_BY_TURMA).addEntity(Ranking.class);
            query.setInteger("idTurma", turma);
            List<Ranking> ranking  =  query.list();
            transaction.commit();
            for(int i =0;i<ranking.size();i++){
                ranking.get(i).setPessoa(pessoaService.getById(ranking.get(i).getId()));
                ranking.get(i).setPosicao(i+1);
            }        
            return ranking;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
    
    public List<Ranking> findGlobalRanking() {
        Transaction transaction = currentSession().beginTransaction();
        try {
            SQLQuery query = currentSession().createSQLQuery("select *from ranking_global").addEntity(Ranking.class);
            List<Ranking> ranking  =  query.list();
            transaction.commit();
            for(int i =0;i<ranking.size();i++){
                ranking.get(i).setPessoa(pessoaService.getById(ranking.get(i).getId()));
                ranking.get(i).setPosicao(i+1);
            }        
            return ranking;
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.fillInStackTrace());
            return null;
        }
    }
}
