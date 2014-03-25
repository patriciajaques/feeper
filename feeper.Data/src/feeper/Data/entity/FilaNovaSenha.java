/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import java.util.Date;

/**
 *
 * @author
 * fabioalves
 */
public class FilaNovaSenha implements java.io.Serializable {
 
    private Integer id;
    private int idPessoa;
    private String chave;
    private Date dataCadastro;
    
    public FilaNovaSenha() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    

    
}
