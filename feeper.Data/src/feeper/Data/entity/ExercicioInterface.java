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
public class ExercicioInterface {
    
    private Integer id; 
    private Integer idExercicio;
    
    private ExercicioInterfaceMembro[] membros;

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

    public ExercicioInterfaceMembro[] getMembros() {
        return membros;
    }

    public void setMembros(ExercicioInterfaceMembro[] membros) {
        this.membros = membros;
    }
    
    
}
