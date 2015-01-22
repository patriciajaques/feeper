/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Entities;

import java.util.List;

/**
 *
 * @author gilvani
 */
public class ExercicioCasoTeste {

    private Integer id;
    private Integer idExercicio;
    private Boolean ativo;
    private String mensagemPersonalizada;
    private Integer ordem;
    
    //auxiliar no Angular
    private List<ExercicioCasoTestePasso> passos;

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

    public String getMensagemPersonalizada() {
        return mensagemPersonalizada;
    }

    public void setMensagemPersonalizada(String mensagemPersonalizada) {
        this.mensagemPersonalizada = mensagemPersonalizada;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public List<ExercicioCasoTestePasso> getPassos() {
        return passos;
    }

    public void setPassos(List<ExercicioCasoTestePasso> passos) {
        this.passos = passos;
    }
    
    
}
