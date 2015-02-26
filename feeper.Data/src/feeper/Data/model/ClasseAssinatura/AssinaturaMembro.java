/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feeper.Data.model.ClasseAssinatura;

import feeper.Data.model.EmemberType;
import feeper.Data.model.Emodifier;
import java.util.List;

/**
 *
 * @author gilvani
 */
public class AssinaturaMembro {

    private int memberType;
    private int modifier;
    private String type;
    private String name;

    private List<AssinaturaMembroParametro> parametros;

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

    public List<AssinaturaMembroParametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<AssinaturaMembroParametro> parametros) {
        this.parametros = parametros;
    }
}
