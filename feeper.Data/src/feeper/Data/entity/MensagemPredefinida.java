/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

/**
 *
 * @author gilvani
 */
public class MensagemPredefinida {

    private int id;
    private int idAutor;
    private String mensagemPredefinida;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getMensagemPredefinida() {
        return mensagemPredefinida;
    }

    public void setMensagemPredefinida(String mensagemPredefinida) {
        this.mensagemPredefinida = mensagemPredefinida;
    }
    
    
}
