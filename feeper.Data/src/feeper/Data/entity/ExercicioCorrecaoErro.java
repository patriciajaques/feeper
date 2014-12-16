package br.unisinos.feeper.corretorjava.Entities;

import javax.xml.bind.annotation.XmlAttribute;


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

    @XmlAttribute
    public Integer idTeste;
    @XmlAttribute
    public String ErrorType;
    @XmlAttribute
    public String mensagemErro;

    public ExercicioCorrecaoErro() {
    }

    public ExercicioCorrecaoErro(Integer idTeste, String ErrorType, String mensagemErro) {
        this.idTeste = idTeste;
        this.ErrorType = ErrorType;
        this.mensagemErro = mensagemErro;
    }
}
