/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.entity;

/**
 *
 * @author
 * fabioalves
 */
public class UploadTemp implements java.io.Serializable {
 
    private Integer id;
    private byte[] arquivo;
    
    public UploadTemp() {
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }
    
}
