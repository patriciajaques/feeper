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
public class ExercicioInterfaceMembro {
        
    private int Modifier;
    private String Type;
    private String Name;
    
    private ExercicioInterfaceParametro[] parametros;

    public int getModifier() {
        return Modifier;
    }

    public void setModifier(int Modifier) {
        this.Modifier = Modifier;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public ExercicioInterfaceParametro[] getParametros() {
        return parametros;
    }

    public void setParametros(ExercicioInterfaceParametro[] parametros) {
        this.parametros = parametros;
    }
}
