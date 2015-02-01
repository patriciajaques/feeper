/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import java.util.List;

/**
 *
 * @author gilvani
 */
public class ExercicioInterface {
    
    private int id; 
    private int idExercicio;
    private String nomeClasse;
    
    private List<ExercicioInterfaceMembro> membros;

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

    public List<ExercicioInterfaceMembro> getMembros() {
        return membros;
    }

    public void setMembros(List<ExercicioInterfaceMembro> membros) {
        this.membros = membros;
    }
    
    
}
