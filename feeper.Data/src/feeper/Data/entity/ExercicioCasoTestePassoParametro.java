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
public class ExercicioCasoTestePassoParametro {
    
    private Integer id;
    private Integer idPasso;
    private int ordem;
    private String objectType;  
    private String objectName;
    private String objectValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPasso() {
        return idPasso;
    }

    public void setIdPasso(Integer idPasso) {
        this.idPasso = idPasso;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String ObjectType) {
        this.objectType = ObjectType;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String ObjectName) {
        this.objectName = ObjectName;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(String ObjectValue) {
        this.objectValue = ObjectValue;
    }
}
