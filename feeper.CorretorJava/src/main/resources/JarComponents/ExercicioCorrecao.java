
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExercicioCorrecao {

    @XmlElement
    public Integer idSolucao;
    @XmlElement
    public Integer idExercicio;
    @XmlElement
    public Integer idAluno;
    @XmlElement
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
