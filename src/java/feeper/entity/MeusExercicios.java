/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.entity;

import java.util.Date;

/**
 *
 * @author
 * fabioalves
 */
public class MeusExercicios {
    
    private int id;
    private String nome;
    private byte[] descricao;
    private String descricaoHtml;
    private String turma;
    private Date dataUltimaAlteracao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricaoHtml() {
        return descricaoHtml;
    }

    public void setDescricaoHtml(String descricaoHtml) {
        this.descricaoHtml = descricaoHtml;
    }

    public byte[] getDescricao() {
        return descricao;
    }

    public void setDescricao(byte[] descricao) {
        this.descricao = descricao;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
    
}
