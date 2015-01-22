package br.unisinos.feeper.corretorjava.Entities;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gilvani
 */
public class ExercicioSolucaoErro {

    private Integer id;
    private Integer idSolucao;
    private Integer idCasoTeste;
    private int errorType;
    private String mensagemErro;
    private String staticErrorType;
    private int linhaErro;

    public ExercicioSolucaoErro() {
    }

    public ExercicioSolucaoErro(Integer idSolucao, Integer idCasoTeste, int ErrorType, String mensagemErro) {
        this.idSolucao = idSolucao;
        this.idCasoTeste = idCasoTeste;
        this.errorType = ErrorType;
        this.mensagemErro = mensagemErro;
    }

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

    public Integer getIdCasoTeste() {
        return idCasoTeste;
    }

    public void setIdCasoTeste(Integer idCasoTeste) {
        this.idCasoTeste = idCasoTeste;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int ErrorType) {
        this.errorType = ErrorType;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public String getStaticErrorType() {
        return staticErrorType;
    }

    public void setStaticErrorType(String staticErrorType) {
        this.staticErrorType = staticErrorType;
    }

    public int getLinhaErro() {
        return linhaErro;
    }

    public void setLinhaErro(int linhaErro) {
        this.linhaErro = linhaErro;
    }
}
