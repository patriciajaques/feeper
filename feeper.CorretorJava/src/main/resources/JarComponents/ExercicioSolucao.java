import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExercicioSolucao {

    protected List<ExercicioSolucaoErro> erros;

    public List<ExercicioSolucaoErro> getErros() {
        return erros;
    }

    public void setErros(List<ExercicioSolucaoErro> erros) {
        this.erros = erros;
    }
    
    
}
