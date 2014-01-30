/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import feeper.entity.Pessoa;
import feeper.entity.Turma;
import java.util.List;
import org.hibernate.SQLQuery;

/**
 *
 * @author
 * fabioalves
 */
public class TurmaService extends HibernateUtil<Turma> {
    
    public TurmaService() {
        super(Turma.class);
    }
    
    public List<Pessoa> getAlunos(int idTurma)
    {
        SQLQuery query = query("select P.* from TurmaPessoa TP inner join Pessoa P on P.ID = TP.IdPessoa where TP.IdTurma = :idTurma order by P.Nome").addEntity(Pessoa.class);
        query.setInteger("idTurma", idTurma);
        return query.list();
    }
    
    public boolean removeAluno(int idTurma, int idAluno)
    {
        try {
            SQLQuery query = query("delete from TurmaPessoa where IdTurma = :idTurma and IdPessoa = :idPessoa");
            query.setInteger("idTurma", idTurma);
            query.setInteger("idPessoa", idAluno);
            
            query.executeUpdate();
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
