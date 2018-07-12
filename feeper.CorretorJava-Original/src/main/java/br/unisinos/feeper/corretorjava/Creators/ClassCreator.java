/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisinos.feeper.corretorjava.Creators;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilvani
 */
public class ClassCreator {

    public String getCodigoAdaptado(String codigoOriginal) {

        codigoOriginal = codigoOriginal.substring(0, codigoOriginal.lastIndexOf("}") - 1);
        codigoOriginal += getPrivateFieldsGetter();
        codigoOriginal += "}";
        return codigoOriginal;
    }

    private String getPrivateFieldsGetter() {
        String lineSeparator = System.getProperty("line.separator");
        
        StringBuilder builder = new StringBuilder();
        builder.append("public Object get_Private_Field_Acessor(String fieldName){" + lineSeparator);
        builder.append("try {" + lineSeparator);
        builder.append("java.lang.reflect.Field field = this.getClass().getDeclaredField(fieldName);" + lineSeparator);
        builder.append("return field.get(this);" + lineSeparator);
        builder.append("} catch (Exception ex) {" + lineSeparator);
        builder.append("return null;" + lineSeparator);
        builder.append("}" + lineSeparator);
        builder.append("}" + lineSeparator);
        return builder.toString();
    }
}
