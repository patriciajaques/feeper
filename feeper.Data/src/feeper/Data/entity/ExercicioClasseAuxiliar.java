/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import feeper.Data.model.ClasseAssinatura.Assinatura;

/**
 *
 * @author gilvani
 */
public class ExercicioClasseAuxiliar {

    private int id;
    private int idExercicio;
    private String nomeClasse;
    private String codigo;
    private Boolean mostrarParaAluno;
    private Assinatura assinatura;

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

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getMostrarParaAluno() {
        return mostrarParaAluno;
    }

    public void setMostrarParaAluno(Boolean mostrarParaAluno) {
        this.mostrarParaAluno = mostrarParaAluno;
    }

    public Assinatura getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Assinatura assinatura) {
        this.assinatura = assinatura;
    }
}
