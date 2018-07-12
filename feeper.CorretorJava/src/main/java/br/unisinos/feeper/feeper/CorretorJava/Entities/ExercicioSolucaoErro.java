package br.unisinos.feeper.feeper.CorretorJava.Entities;


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

    protected int id;
    protected int idSolucao;
    protected int idCasoTeste;
    protected String mensagemPersonalizada;
    protected int errorType;
    protected String mensagemErro;
    protected String staticErrorType;
    protected int linhaErro;

    public ExercicioSolucaoErro() {
    }

    public ExercicioSolucaoErro(int idSolucao, int idCasoTeste, String mensagemPersonalizada, int ErrorType, String mensagemErro) {
        this.idSolucao = idSolucao;
        this.idCasoTeste = idCasoTeste;
        this.mensagemPersonalizada = mensagemPersonalizada;
        this.errorType = ErrorType;
        this.mensagemErro = mensagemErro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSolucao() {
        return idSolucao;
    }

    public void setIdSolucao(int idSolucao) {
        this.idSolucao = idSolucao;
    }

    public int getIdCasoTeste() {
        return idCasoTeste;
    }

    public void setIdCasoTeste(int idCasoTeste) {
        this.idCasoTeste = idCasoTeste;
    }

    public String getMensagemPersonalizada() {
        return mensagemPersonalizada;
    }

    public void setMensagemPersonalizada(String mensagemPersonalizada) {
        this.mensagemPersonalizada = mensagemPersonalizada;
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
