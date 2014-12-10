package br.unisinos.feeper.corretorjava.Entities;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
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
public class ExercicioCorrecao {

    @XmlElement
    public Integer idSolucao;
    @XmlElement
    public Integer idExercicio;
    @XmlElement
    public Integer idAluno;
    @XmlElement
    public ArrayList< ExercicioCorrecaoErro> erros;

    public ExercicioCorrecao() {
    }

    public ExercicioCorrecao(Integer idSolucao, Integer idExercicio, Integer idAluno) {
        this.idSolucao = idSolucao;
        this.idExercicio = idExercicio;
        this.idAluno = idAluno;
        this.erros = new ArrayList<>();
    }
}
