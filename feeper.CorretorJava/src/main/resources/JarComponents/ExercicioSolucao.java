/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gilvani
 */
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gilvani
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExercicioSolucao {

    protected int id;
    protected int idExercicio;
    protected int idAluno;
    protected int idStatus;
    protected Date dataCadastro;
    protected int errosCount;

    protected List<ExercicioSolucaoErro> erros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getErrosCount() {
        return errosCount;
    }

    public void setErrosCount(int errosCount) {
        this.errosCount = errosCount;
    }

    public List<ExercicioSolucaoErro> getErros() {
        return erros;
    }

    public void setErros(List<ExercicioSolucaoErro> erros) {
        this.erros = erros;
    }
    
    
}
