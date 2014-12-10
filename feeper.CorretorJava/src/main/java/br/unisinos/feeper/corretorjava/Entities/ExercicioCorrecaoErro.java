package br.unisinos.feeper.corretorjava.Entities;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gilvani
 */
public class ExercicioCorrecaoErro {

    public Integer idTeste;

    public String ErrorType;

    public String mensagemErro;

    public ExercicioCorrecaoErro() {
    }

    public ExercicioCorrecaoErro(Integer idTeste, String ErrorType, String mensagemErro) {
        this.idTeste = idTeste;
        this.ErrorType = ErrorType;
        this.mensagemErro = mensagemErro;
    }
}
