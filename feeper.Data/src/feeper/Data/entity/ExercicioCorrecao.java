package feeper.Data.entity;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gilvani
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExercicioCorrecao {

    private Integer idSolucao;
    private Integer idExercicio;
    private Integer idAluno;
    private ArrayList< ExercicioCorrecaoErro> erros;

    public ExercicioCorrecao() {
    }

    public ExercicioCorrecao(Integer idSolucao, Integer idExercicio, Integer idAluno) {
        this.idSolucao = idSolucao;
        this.idExercicio = idExercicio;
        this.idAluno = idAluno;
        this.erros = new ArrayList<>();
    }

    public Integer getIdSolucao() {
        return idSolucao;
    }

    public void setIdSolucao(Integer idSolucao) {
        this.idSolucao = idSolucao;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public ArrayList< ExercicioCorrecaoErro> getErros() {
        return erros;
    }

    public void setErros(ArrayList< ExercicioCorrecaoErro> erros) {
        this.erros = erros;
    }
}
