/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gilvani
 */
@XmlRootElement
public class ExercicioSolucao {

    @XmlAttribute
    public Integer id;
    @XmlAttribute
    public Integer idExercicio;
    @XmlAttribute
    public Integer idAluno;
    
    public ExercicioSolucaoClasse[] classes;
    public ExercicioCasoTeste[] testes;
}
