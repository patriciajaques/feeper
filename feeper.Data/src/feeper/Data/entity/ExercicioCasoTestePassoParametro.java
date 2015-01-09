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
    private String ObjectType;  
    private String ObjectName;
    private String ObjectValue;

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

    public String getObjectType() {
        return ObjectType;
    }

    public void setObjectType(String ObjectType) {
        this.ObjectType = ObjectType;
    }

    public String getObjectName() {
        return ObjectName;
    }

    public void setObjectName(String ObjectName) {
        this.ObjectName = ObjectName;
    }

    public String getObjectValue() {
        return ObjectValue;
    }

    public void setObjectValue(String ObjectValue) {
        this.ObjectValue = ObjectValue;
    }
}
