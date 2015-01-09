/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

/**
 *
 * @author gilvani
 */
public class ExercicioCasoTeste {

    private Integer id;
    private Integer idExercicio;
    private Boolean ativo;
    private Integer ordem; 
    private ExercicioCasoTestePasso[] passos;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public ExercicioCasoTestePasso[] getPassos() {
        return passos;
    }

    public void setPassos(ExercicioCasoTestePasso[] passos) {
        this.passos = passos;
    }
    
    
}
