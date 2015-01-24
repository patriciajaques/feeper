/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Entities;

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

    protected Integer id;
    protected Integer idExercicio;
    protected Integer idAluno;
    protected Integer idStatus;
    protected Date dataCadastro;

    protected List<ExercicioSolucaoClasse> classes;
    protected List<ExercicioCasoTeste> testes;
    protected List<ExercicioSolucaoErro> erros;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<ExercicioSolucaoClasse> getClasses() {
        return classes;
    }

    public void setClasses(List<ExercicioSolucaoClasse> classes) {
        this.classes = classes;
    }

    public List<ExercicioCasoTeste> getTestes() {
        return testes;
    }

    public void setTestes(List<ExercicioCasoTeste> testes) {
        this.testes = testes;
    }

    public List<ExercicioSolucaoErro> getErros() {
        return erros;
    }

    public void setErros(List<ExercicioSolucaoErro> erros) {
        this.erros = erros;
    }
    
    
}
