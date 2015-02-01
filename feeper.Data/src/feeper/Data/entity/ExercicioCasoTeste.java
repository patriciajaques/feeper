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
public class ExercicioCasoTeste {

    private int id;
    private int idExercicio;
    private Boolean ativo;
    private String mensagemPersonalizada;
    private int ordem;
    
    //auxiliar no Angular
    private List<ExercicioCasoTestePasso> passos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getMensagemPersonalizada() {
        return mensagemPersonalizada;
    }

    public void setMensagemPersonalizada(String mensagemPersonalizada) {
        this.mensagemPersonalizada = mensagemPersonalizada;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public List<ExercicioCasoTestePasso> getPassos() {
        return passos;
    }

    public void setPassos(List<ExercicioCasoTestePasso> passos) {
        this.passos = passos;
    }
    
    
}
