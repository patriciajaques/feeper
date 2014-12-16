/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Entities;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author gilvani
 */
public class ExercicioCasoTeste {

    @XmlAttribute
    public Integer id;
    @XmlAttribute
    public Integer idExercicio;
    @XmlAttribute
    public Boolean ativo;
    @XmlAttribute
    public Integer ordem; 
    
    public ExercicioCasoTestePasso[] passos;
}
