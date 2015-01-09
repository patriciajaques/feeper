package feeper.Data.entity;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gilvani
 */
public class ExercicioCorrecaoErro {

    private Integer idTeste;
    private String ErrorType;
    private String mensagemErro;

    public ExercicioCorrecaoErro() {
    }

    public ExercicioCorrecaoErro(Integer idTeste, String ErrorType, String mensagemErro) {
        this.idTeste = idTeste;
        this.ErrorType = ErrorType;
        this.mensagemErro = mensagemErro;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public Integer getIdTeste() {
        return idTeste;
    }

    public void setIdTeste(Integer idTeste) {
        this.idTeste = idTeste;
    }

    public String getErrorType() {
        return ErrorType;
    }

    public void setErrorType(String ErrorType) {
        this.ErrorType = ErrorType;
    }
}
