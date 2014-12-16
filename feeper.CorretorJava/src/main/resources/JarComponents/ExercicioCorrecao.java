
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExercicioCorrecao {

    public Integer idSolucao;
    public Integer idExercicio;
    public Integer idAluno;
    public ArrayList<ExercicioCorrecaoErro> erros;

    public ExercicioCorrecao() {
    }

    public ExercicioCorrecao(Integer idSolucao, Integer idExercicio, Integer idAluno) {
        this.idSolucao = idSolucao;
        this.idExercicio = idExercicio;
        this.idAluno = idAluno;
        this.erros = new ArrayList<>();
    }
}
