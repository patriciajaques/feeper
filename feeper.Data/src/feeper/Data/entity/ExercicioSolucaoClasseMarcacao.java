/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import java.util.Date;

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoClasseMarcacao {
    
    private Integer id;
     private Integer idExercicioSolucaoClasse;
     private Integer idAutor;
     private Integer idTipoMarcacao;
     private Integer linhaInicio;
     private Integer linhaFim;
     private String anotacao;
     private Date dataCadastro;
     private Boolean ativo;

    public ExercicioSolucaoClasseMarcacao() {
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdExercicioSolucaoClasse() {
        return idExercicioSolucaoClasse;
    }

    public void setIdExercicioSolucaoClasse(int idExercicioSolucaoClasse) {
        this.idExercicioSolucaoClasse = idExercicioSolucaoClasse;
    }

    public int getIdAutor() {
        return this.idAutor;
    }
    
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    public int getIdTipoMarcacao() {
        return this.idTipoMarcacao;
    }
    
    public void setIdTipoMarcacao(int idTipoMarcacao) {
        this.idTipoMarcacao = idTipoMarcacao;
    }
    public int getLinhaInicio() {
        return this.linhaInicio;
    }
    
    public void setLinhaInicio(int linhaInicio) {
        this.linhaInicio = linhaInicio;
    }
    public int getLinhaFim() {
        return this.linhaFim;
    }
    
    public void setLinhaFim(int linhaFim) {
        this.linhaFim = linhaFim;
    }
    public String getAnotacao() {
        return this.anotacao;
    }
    
    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }
    public Date getDataCadastro() {
        return this.dataCadastro;
    }
    
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public boolean isAtivo() {
        return this.ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
