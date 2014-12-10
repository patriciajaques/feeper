/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gilvani
 */
@XmlRootElement
public class ExercicioSolucao {

    public Integer id;
    @XmlElement
    public Integer idExercicio;
    @XmlElement
    public Integer idAluno;
    @XmlElement
    public ExercicioSolucaoClasse[] classes;
    @XmlElement
    public ExercicioCasoTeste[] testes;
}
