/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.entity;

import feeper.Data.model.EmemberType;
import feeper.Data.model.Emodifier;
import java.util.List;

/**
 *
 * @author gilvani
 */
public class ExercicioInterfaceMembro {

    private int id;
    private int idInterface;

    private int memberType;
    private int modifier;
    private String type;
    private String name;

    private List<ExercicioInterfaceMembroParametro> parametros;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdInterface() {
        return idInterface;
    }

    public void setIdInterface(int idInterface) {
        this.idInterface = idInterface;
    }

    public int getMemberType() {
        return memberType;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public List<ExercicioInterfaceMembroParametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<ExercicioInterfaceMembroParametro> parametros) {
        this.parametros = parametros;
    }
}
