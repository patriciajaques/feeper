/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.model;

import java.util.Date;

/**
 *
 * @author
 * fabioalves
 */
public class MarcacaoMensagem {
    private int idPessoa;
    private String nome;
    private Date dataCadastro;
    private String texto;
    private boolean publico;
    private int linhaInicio;

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public int getLinhaInicio() {
        return linhaInicio;
    }

    public void setLinhaInicio(int linhaInicio) {
        this.linhaInicio = linhaInicio;
    }
}
