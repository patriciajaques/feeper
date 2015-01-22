/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Entities;

import java.util.Date;

/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoClasse {

    private Integer id;
    private Integer idSolucao;
    private String nomeClasse;
    private String codigo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSolucao() {
        return idSolucao;
    }

    public void setIdSolucao(Integer idSolucao) {
        this.idSolucao = idSolucao;
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
}
